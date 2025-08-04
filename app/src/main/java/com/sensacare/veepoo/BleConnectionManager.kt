package com.sensacare.veepoo

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattDescriptor
import android.bluetooth.BluetoothGattService
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothProfile
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanFilter
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.content.Context
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.os.ParcelUuid
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import kotlinx.coroutines.*
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger
import kotlin.math.min
import kotlin.math.pow

/**
 * Comprehensive BLE Connection Manager for VeePoo ET492/ET593 devices.
 * Handles device discovery, connection, data parsing, and error recovery.
 */
class BleConnectionManager(
    private val context: Context,
    private val preferencesManager: PreferencesManager,
    lifecycleOwner: LifecycleOwner? = null
) : LifecycleObserver {

    companion object {
        private const val TAG = "BleConnectionManager"
        
        // Reconnection parameters
        private const val MAX_RECONNECT_ATTEMPTS = 5
        private const val BASE_RECONNECT_DELAY_MS = 1000L
        private const val MAX_RECONNECT_DELAY_MS = 30000L // 30 seconds
        
        // Timeouts
        private const val SCAN_TIMEOUT_MS = 15000L // 15 seconds
        private const val CONNECTION_TIMEOUT_MS = 10000L // 10 seconds
        private const val SERVICE_DISCOVERY_TIMEOUT_MS = 5000L // 5 seconds
        
        // BLE Standard UUIDs
        private val CLIENT_CHARACTERISTIC_CONFIG_UUID = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb")
        
        // Standard BLE notification/indication values
        private val ENABLE_NOTIFICATION_VALUE = BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE
        private val ENABLE_INDICATION_VALUE = BluetoothGattDescriptor.ENABLE_INDICATION_VALUE
        
        // VeePoo ET492/ET593 UUIDs
        private val VEEPOO_SERVICE_UUID = UUID.fromString("0000fff0-0000-1000-8000-00805f9b34fb")
        private val VEEPOO_COMMAND_CHAR_UUID = UUID.fromString("0000fff1-0000-1000-8000-00805f9b34fb")
        private val VEEPOO_DATA_CHAR_UUID = UUID.fromString("0000fff2-0000-1000-8000-00805f9b34fb")
        private val VEEPOO_REALTIME_DATA_CHAR_UUID = UUID.fromString("0000fff4-0000-1000-8000-00805f9b34fb")
        
        // VeePoo device commands
        private val CMD_GET_DEVICE_INFO = byteArrayOf(0xAB.toByte(), 0x00, 0x04, 0xFF.toByte(), 0x00, 0x00, 0x00)
        private val CMD_START_REALTIME_MEASUREMENT = byteArrayOf(0xAB.toByte(), 0x00, 0x04, 0x31, 0x00, 0x00, 0x00)
        private val CMD_STOP_REALTIME_MEASUREMENT = byteArrayOf(0xAB.toByte(), 0x00, 0x04, 0x32, 0x00, 0x00, 0x00)
        
        // GATT status code descriptions
        private val GATT_STATUS_MESSAGES = mapOf(
            BluetoothGatt.GATT_SUCCESS to "Success",
            BluetoothGatt.GATT_READ_NOT_PERMITTED to "Read not permitted",
            BluetoothGatt.GATT_WRITE_NOT_PERMITTED to "Write not permitted",
            BluetoothGatt.GATT_INSUFFICIENT_AUTHENTICATION to "Insufficient authentication",
            BluetoothGatt.GATT_REQUEST_NOT_SUPPORTED to "Request not supported",
            BluetoothGatt.GATT_INSUFFICIENT_ENCRYPTION to "Insufficient encryption",
            BluetoothGatt.GATT_INVALID_OFFSET to "Invalid offset",
            BluetoothGatt.GATT_INVALID_ATTRIBUTE_LENGTH to "Invalid attribute length",
            BluetoothGatt.GATT_CONNECTION_CONGESTED to "Connection congested",
            BluetoothGatt.GATT_FAILURE to "GATT failure",
            8 to "GATT operation already in progress",
            22 to "GATT service discovery already in progress",
            133 to "GATT connection timeout or link loss",
            19 to "GATT device not found",
            62 to "GATT authentication failure",
            256 to "GATT disconnection by local host"
        )
        
        // Device models
        enum class DeviceModel {
            ET492, ET593, OTHER
        }
    }

    // Connection state
    private var bluetoothGatt: BluetoothGatt? = null
    private var isConnecting = AtomicBoolean(false)
    private var isConnected = AtomicBoolean(false)
    private var isDiscoveringServices = AtomicBoolean(false)
    private var reconnectAttempts = AtomicInteger(0)
    private var deviceModel = DeviceModel.OTHER
    private var lastConnectedDeviceAddress: String? = null
    private var pendingOperations = ConcurrentHashMap<UUID, Job>()
    
    // Cache for scan results by device address
    private val scanResultsCache = mutableMapOf<String, ScanResult>()
    
    // Coroutine scopes
    private val connectionScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val mainScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    
    // Jobs
    private var connectionJob: Job? = null
    private var reconnectJob: Job? = null
    private var serviceDiscoveryJob: Job? = null
    private var scanJob: Job? = null

    // Handlers
    private val mainHandler = Handler(Looper.getMainLooper())

    // Bluetooth components
    private val bluetoothManager: BluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    private val bluetoothAdapter: BluetoothAdapter = bluetoothManager.adapter
    private val bluetoothLeScanner = bluetoothAdapter.bluetoothLeScanner

    // Diagnostic logger
    private val diagnosticLogger = DiagnosticLogger.getInstance(context)

    // Connection states
    enum class ConnectionState {
        DISCONNECTED,
        SCANNING,
        CONNECTING,
        DISCOVERING_SERVICES,
        CONNECTED,
        RECONNECTING,
        ERROR
    }

    // Current state
    private var currentState = ConnectionState.DISCONNECTED

    // Callbacks
    private var connectionStateCallback: ((ConnectionState) -> Unit)? = null
    private var dataCallback: ((VitalsData) -> Unit)? = null
    private var errorCallback: ((String, Int?) -> Unit)? = null
    private var scanResultCallback: ((List<BluetoothDevice>) -> Unit)? = null

    init {
        // Register lifecycle observer if provided
        lifecycleOwner?.lifecycle?.addObserver(this)
        
        // Restore connection state if enabled
        if (preferencesManager.isConnectionPersistenceEnabled) {
            restoreConnectionState()
        }
    }
    
    /**
     * Start scanning for VeePoo devices with proper filters
     */
    fun startScan() {
        if (isScanning() || isConnected.get() || isConnecting.get()) {
            Log.d(TAG, "Cannot start scan: already scanning or connected")
            return
        }
        
        scanJob?.cancel()
        scanJob = connectionScope.launch {
            try {
                Log.d(TAG, "Starting BLE scan for VeePoo devices")
                updateState(ConnectionState.SCANNING)
                
                // Create filters for VeePoo devices
                val filters = mutableListOf<ScanFilter>()
                
                // Add service UUID filter if applicable
                filters.add(
                    ScanFilter.Builder()
                        .setServiceUuid(ParcelUuid(VEEPOO_SERVICE_UUID))
                        .build()
                )
                
                // Add name-based filters for ET492/ET593
                filters.add(
                    ScanFilter.Builder()
                        .setDeviceName("ET492")
                        .build()
                )
                
                filters.add(
                    ScanFilter.Builder()
                        .setDeviceName("ET593")
                        .build()
                )
                
                // Add partial name matches
                listOf("ET", "VP", "Veepoo").forEach { prefix ->
                    filters.add(
                        ScanFilter.Builder()
                            .setDeviceNameContains(prefix)
                            .build()
                    )
                }
                
                // Configure scan settings for low power
                val settings = ScanSettings.Builder()
                    .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
                    .setCallbackType(ScanSettings.CALLBACK_TYPE_ALL_MATCHES)
                    .setMatchMode(ScanSettings.MATCH_MODE_AGGRESSIVE)
                    .build()
                
                // Start scanning
                val discoveredDevices = mutableListOf<BluetoothDevice>()
                
                bluetoothLeScanner?.startScan(filters, settings, object : ScanCallback() {
                    override fun onScanResult(callbackType: Int, result: ScanResult) {
                        val device = result.device
                        if (!discoveredDevices.contains(device)) {
                            Log.d(TAG, "Found device: ${device.name ?: "Unknown"} (${device.address}) RSSI: ${result.rssi}dBm")
                            discoveredDevices.add(device)
                            
                            // Determine device model
                            val deviceName = device.name?.lowercase() ?: ""
                            when {
                                deviceName.contains("et492") -> {
                                    Log.d(TAG, "Identified as ET492 model")
                                    // No need to update global deviceModel yet as we haven't connected
                                }
                                deviceName.contains("et593") -> {
                                    Log.d(TAG, "Identified as ET593 model")
                                    // No need to update global deviceModel yet as we haven't connected
                                }
                            }
                            
                            // Notify callback
                            mainScope.launch {
                                scanResultCallback?.invoke(discoveredDevices.toList())
                            }

                            // Cache the scan result
                            scanResultsCache[device.address] = result
                        }
                    }
                    
                    override fun onScanFailed(errorCode: Int) {
                        Log.e(TAG, "Scan failed with error code: $errorCode")
                        val errorMessage = when (errorCode) {
                            ScanCallback.SCAN_FAILED_ALREADY_STARTED -> "Scan already started"
                            ScanCallback.SCAN_FAILED_APPLICATION_REGISTRATION_FAILED -> "App registration failed"
                            ScanCallback.SCAN_FAILED_FEATURE_UNSUPPORTED -> "BLE scan not supported"
                            ScanCallback.SCAN_FAILED_INTERNAL_ERROR -> "Internal scan error"
                            else -> "Unknown scan error: $errorCode"
                        }
                        notifyError(errorMessage, errorCode)
                        updateState(ConnectionState.ERROR)
                    }
                })
                
                // Set scan timeout
                delay(SCAN_TIMEOUT_MS)
                stopScan()
                
                // If no devices found, notify error
                if (discoveredDevices.isEmpty()) {
                    Log.w(TAG, "No VeePoo devices found during scan")
                    notifyError("No VeePoo devices found", null)
                    updateState(ConnectionState.DISCONNECTED)
                }
                
            } catch (e: Exception) {
                Log.e(TAG, "Error during scan", e)
                notifyError("Scan error: ${e.message}", null)
                updateState(ConnectionState.ERROR)
            }
        }
    }
    
    /**
     * Stop ongoing BLE scan
     */
    fun stopScan() {
        try {
            bluetoothLeScanner?.stopScan(object : ScanCallback() {})
            scanJob?.cancel()
            scanJob = null
            if (currentState == ConnectionState.SCANNING) {
                updateState(ConnectionState.DISCONNECTED)
            }
            Log.d(TAG, "BLE scan stopped")
        } catch (e: Exception) {
            Log.e(TAG, "Error stopping scan", e)
        }
    }
    
    /**
     * Check if currently scanning
     */
    fun isScanning(): Boolean = scanJob?.isActive == true
    
    /**
     * Connect to a specific device by address
     */
    fun connectToDevice(deviceAddress: String) {
        if (isConnecting.get() || isConnected.get()) {
            Log.d(TAG, "Already connecting or connected, ignoring connect request")
            return
        }
        
        connectionJob?.cancel()
        connectionJob = connectionScope.launch {
            try {
                Log.d(TAG, "Attempting to connect to device: $deviceAddress")
                isConnecting.set(true)
                updateState(ConnectionState.CONNECTING)
                
                // Save the device address for reconnection
                lastConnectedDeviceAddress = deviceAddress
                preferencesManager.pairedDeviceAddress = deviceAddress
                
                // Get the device and connect
                val device = bluetoothAdapter.getRemoteDevice(deviceAddress)
                
                // Determine device model from name
                val deviceName = device.name?.lowercase() ?: ""
                deviceModel = when {
                    deviceName.contains("et492") -> DeviceModel.ET492
                    deviceName.contains("et593") -> DeviceModel.ET593
                    else -> DeviceModel.OTHER
                }
                
                Log.d(TAG, "Device model identified as: $deviceModel")
                
                // Connect with autoConnect=false for faster initial connection
                // This is recommended for user-initiated connections
                bluetoothGatt = device.connectGatt(context, false, gattCallback, BluetoothDevice.TRANSPORT_LE)
                
                // Set connection timeout
                withTimeout(CONNECTION_TIMEOUT_MS) {
                    while (!isConnected.get() && isConnecting.get()) {
                        delay(100)
                    }
                }
                
                if (!isConnected.get()) {
                    Log.e(TAG, "Connection timeout")
                    notifyError("Connection timeout", null)
                    updateState(ConnectionState.ERROR)
                    cleanup(deviceAddress)
                }
            } catch (e: Exception) {
                Log.e(TAG, "Connection failed", e)
                notifyError("Connection failed: ${e.message}", null)
                updateState(ConnectionState.ERROR)
                cleanup(deviceAddress)
            } finally {
                isConnecting.set(false)
            }
        }
    }
    
    /**
     * Connect to last known device
     */
    fun connectToLastKnownDevice() {
        val lastDeviceAddress = preferencesManager.pairedDeviceAddress
        if (lastDeviceAddress != null) {
            Log.d(TAG, "Connecting to last known device: $lastDeviceAddress")
            connectToDevice(lastDeviceAddress)
        } else {
            Log.w(TAG, "No last known device address available")
            notifyError("No paired device found", null)
        }
    }
    
    /**
     * GATT callback implementation with comprehensive error handling
     */
    private val gattCallback = object : BluetoothGattCallback() {
        override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {
            val deviceAddress = gatt.device.address
            
            // Log state change with status code
            val statusMessage = getStatusMessage(status)
            Log.d(TAG, "Connection state changed: $newState, status: $status ($statusMessage)")
            
            // Log to diagnostic logger
            diagnosticLogger.logBleConnectionStateChange(
                deviceAddress,
                if (isConnected.get()) BluetoothProfile.STATE_CONNECTED else BluetoothProfile.STATE_DISCONNECTED, 
                newState
            )
            
            // Handle connection state change based on status and new state
            if (status == BluetoothGatt.GATT_SUCCESS) {
                // Success path
                when (newState) {
                    BluetoothProfile.STATE_CONNECTED -> {
                        Log.d(TAG, "Connected to GATT server: $deviceAddress")
                        diagnosticLogger.logBleConnectionSuccess(deviceAddress)
                        isConnected.set(true)
                        isConnecting.set(false)
                        reconnectAttempts.set(0)
                        
                        // Save connection state
                        preferencesManager.isDeviceConnected = true
                        
                        // Discover services after a short delay to ensure connection stability
                        mainHandler.postDelayed({
                            discoverServices(gatt)
                        }, 300) // 300ms delay for connection stability
                    }
                    
                    BluetoothProfile.STATE_DISCONNECTED -> {
                        Log.d(TAG, "Disconnected from GATT server: $deviceAddress")
                        diagnosticLogger.logBleDisconnection(deviceAddress, "Status: $status")
                        handleDisconnection(gatt, status)
                    }
                }
            } else {
                // Error path - handle specific error codes
                when (status) {
                    133 -> {
                        Log.e(TAG, "Connection error: GATT_ERROR (133) - Connection timeout or link loss")
                        diagnosticLogger.logBleError(deviceAddress, "Connection timeout or link loss (133)")
                        notifyError("Connection timeout or link loss", status)
                        handleDisconnection(gatt, status)
                    }
                    8 -> {
                        Log.e(TAG, "Connection error: GATT operation already in progress (8)")
                        diagnosticLogger.logBleError(deviceAddress, "GATT operation in progress (8)")
                        notifyError("GATT operation already in progress", status)
                        handleDisconnection(gatt, status)
                    }
                    22 -> {
                        Log.e(TAG, "Connection error: Service discovery already in progress (22)")
                        diagnosticLogger.logBleError(deviceAddress, "Service discovery in progress (22)")
                        // This is usually recoverable, so just log it
                    }
                    62 -> {
                        Log.e(TAG, "Connection error: Authentication failure (62)")
                        diagnosticLogger.logBleError(deviceAddress, "Authentication failure (62)")
                        notifyError("Device authentication failed", status)
                        handleDisconnection(gatt, status)
                    }
                    else -> {
                        // Handle other error codes
                        Log.e(TAG, "Connection error: $status ($statusMessage)")
                        diagnosticLogger.logBleError(deviceAddress, "Error $status: $statusMessage")
                        notifyError("Connection error: $statusMessage", status)
                        handleDisconnection(gatt, status)
                    }
                }
            }
        }

        override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {
            isDiscoveringServices.set(false)
            serviceDiscoveryJob?.cancel()
            
            if (status == BluetoothGatt.GATT_SUCCESS) {
                Log.d(TAG, "Services discovered successfully")
                
                // Log discovered services for debugging
                gatt.services.forEach { service ->
                    Log.d(TAG, "Service: ${service.uuid}")
                    service.characteristics.forEach { characteristic ->
                        Log.d(TAG, "  Characteristic: ${characteristic.uuid}")
                    }
                }
                
                // Setup notifications for the appropriate characteristics
                setupCharacteristicNotifications(gatt)
                
                // Update state to connected
                updateState(ConnectionState.CONNECTED)
                
                // Request device info
                requestDeviceInfo(gatt)
                
                // Start real-time measurement
                startRealtimeMeasurement(gatt)
            } else {
                // Handle service discovery failure
                val statusMessage = getStatusMessage(status)
                Log.e(TAG, "Service discovery failed with status: $status ($statusMessage)")
                notifyError("Service discovery failed: $statusMessage", status)
                updateState(ConnectionState.ERROR)
                
                // Attempt to recover by reconnecting
                if (preferencesManager.isAutoReconnectEnabled) {
                    scheduleReconnect()
                }
            }
        }

        override fun onCharacteristicRead(
            gatt: BluetoothGatt,
            characteristic: BluetoothGattCharacteristic,
            status: Int
        ) {
            val charUuid = characteristic.uuid
            pendingOperations.remove(charUuid)
            
            if (status == BluetoothGatt.GATT_SUCCESS) {
                Log.d(TAG, "Characteristic read successfully: $charUuid")
                
                // Handle specific characteristic reads
                when (charUuid) {
                    VEEPOO_COMMAND_CHAR_UUID -> {
                        Log.d(TAG, "Command characteristic read: ${bytesToHex(characteristic.value)}")
                    }
                    else -> {
                        Log.d(TAG, "Other characteristic read: $charUuid")
                    }
                }
            } else {
                // Handle read failure
                val statusMessage = getStatusMessage(status)
                Log.e(TAG, "Characteristic read failed with status: $status ($statusMessage)")
                notifyError("Failed to read characteristic: $statusMessage", status)
            }
        }
        
        override fun onCharacteristicWrite(
            gatt: BluetoothGatt,
            characteristic: BluetoothGattCharacteristic,
            status: Int
        ) {
            val charUuid = characteristic.uuid
            pendingOperations.remove(charUuid)
            
            if (status == BluetoothGatt.GATT_SUCCESS) {
                Log.d(TAG, "Characteristic write successful: $charUuid")
                
                // Handle specific characteristic writes
                when (charUuid) {
                    VEEPOO_COMMAND_CHAR_UUID -> {
                        Log.d(TAG, "Command sent successfully: ${bytesToHex(characteristic.value)}")
                    }
                    else -> {
                        Log.d(TAG, "Other characteristic written: $charUuid")
                    }
                }
            } else {
                // Handle write failure
                val statusMessage = getStatusMessage(status)
                Log.e(TAG, "Characteristic write failed with status: $status ($statusMessage)")
                notifyError("Failed to write characteristic: $statusMessage", status)
            }
        }
        
        override fun onCharacteristicChanged(
            gatt: BluetoothGatt,
            characteristic: BluetoothGattCharacteristic,
            value: ByteArray
        ) {
            // Handle data based on characteristic UUID
            when (characteristic.uuid) {
                VEEPOO_DATA_CHAR_UUID -> {
                    Log.d(TAG, "Data characteristic changed: ${bytesToHex(value)}")
                    parseVitalsData(value)?.let { vitalsData ->
                        mainScope.launch {
                            dataCallback?.invoke(vitalsData)
                        }
                    }
                }
                VEEPOO_REALTIME_DATA_CHAR_UUID -> {
                    Log.d(TAG, "Realtime data characteristic changed: ${bytesToHex(value)}")
                    parseRealtimeData(value)?.let { vitalsData ->
                        mainScope.launch {
                            dataCallback?.invoke(vitalsData)
                        }
                    }
                }
                else -> {
                    Log.d(TAG, "Other characteristic changed: ${characteristic.uuid}, value: ${bytesToHex(value)}")
                }
            }
        }

        // For Android versions below API 33
        override fun onCharacteristicChanged(
            gatt: BluetoothGatt,
            characteristic: BluetoothGattCharacteristic
        ) {
            val value = characteristic.value
            onCharacteristicChanged(gatt, characteristic, value)
        }
        
        override fun onDescriptorWrite(
            gatt: BluetoothGatt,
            descriptor: BluetoothGattDescriptor,
            status: Int
        ) {
            val charUuid = descriptor.characteristic.uuid
            
            if (status == BluetoothGatt.GATT_SUCCESS) {
                Log.d(TAG, "Descriptor write successful for characteristic: $charUuid")
                
                // Check if this was a notification/indication enablement
                if (descriptor.uuid == CLIENT_CHARACTERISTIC_CONFIG_UUID) {
                    if (Arrays.equals(descriptor.value, ENABLE_NOTIFICATION_VALUE) ||
                        Arrays.equals(descriptor.value, ENABLE_INDICATION_VALUE)) {
                        Log.d(TAG, "Notifications/indications enabled for: $charUuid")
                    }
                }
            } else {
                // Handle descriptor write failure
                val statusMessage = getStatusMessage(status)
                Log.e(TAG, "Descriptor write failed with status: $status ($statusMessage)")
                notifyError("Failed to configure notifications: $statusMessage", status)
            }
        }
        
        override fun onMtuChanged(gatt: BluetoothGatt, mtu: Int, status: Int) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                Log.d(TAG, "MTU changed to: $mtu")
            } else {
                val statusMessage = getStatusMessage(status)
                Log.e(TAG, "MTU change failed with status: $status ($statusMessage)")
            }
        }
    }

    /**
     * Handle device disconnection with proper cleanup and reconnection logic
     */
    private fun handleDisconnection(gatt: BluetoothGatt, status: Int) {
        isConnected.set(false)
        isConnecting.set(false)
        
        // Save disconnected state
        preferencesManager.isDeviceConnected = false
        
        // Check if this was an unexpected disconnection
        if (preferencesManager.isAutoReconnectEnabled && 
            reconnectAttempts.get() < MAX_RECONNECT_ATTEMPTS && 
            currentState != ConnectionState.DISCONNECTED) {
            
            Log.d(TAG, "Unexpected disconnection detected, attempting reconnect...")
            updateState(ConnectionState.RECONNECTING)
            scheduleReconnect()
        } else {
            updateState(ConnectionState.DISCONNECTED)
            if (reconnectAttempts.get() >= MAX_RECONNECT_ATTEMPTS) {
                notifyError("Failed to reconnect after ${MAX_RECONNECT_ATTEMPTS} attempts", null)
            }
            
            // Clean up resources
            cleanup(gatt.device.address)
        }
    }
    
    /**
     * Discover services with timeout
     */
    private fun discoverServices(gatt: BluetoothGatt) {
        if (isDiscoveringServices.get()) {
            Log.d(TAG, "Service discovery already in progress")
            return
        }

        serviceDiscoveryJob?.cancel()
        serviceDiscoveryJob = connectionScope.launch {
            try {
                Log.d(TAG, "Starting service discovery")
                isDiscoveringServices.set(true)
                updateState(ConnectionState.DISCOVERING_SERVICES)

                // Request service discovery
                val success = gatt.discoverServices()
                if (!success) {
                    Log.e(TAG, "Failed to start service discovery")
                    notifyError("Failed to start service discovery", null)
                    updateState(ConnectionState.ERROR)
                    return@launch
                }

                // Set discovery timeout
                withTimeout(SERVICE_DISCOVERY_TIMEOUT_MS) {
                    while (isDiscoveringServices.get()) {
                        delay(100)
                    }
                }

                if (isDiscoveringServices.get()) {
                    Log.e(TAG, "Service discovery timeout")
                    notifyError("Service discovery timeout", null)
                    isDiscoveringServices.set(false)
                    updateState(ConnectionState.ERROR)
                    
                    // Attempt to recover
                    if (preferencesManager.isAutoReconnectEnabled) {
                        scheduleReconnect()
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error during service discovery", e)
                notifyError("Service discovery error: ${e.message}", null)
                isDiscoveringServices.set(false)
                updateState(ConnectionState.ERROR)
            }
        }
    }

    /**
     * Setup notifications for required characteristics
     */
    private fun setupCharacteristicNotifications(gatt: BluetoothGatt) {
        try {
            val service = gatt.getService(VEEPOO_SERVICE_UUID)
            if (service == null) {
                Log.e(TAG, "VeePoo service not found")
                notifyError("Required VeePoo service not found", null)
                return
            }
            
            // Setup data characteristic
            setupCharacteristicNotification(gatt, service, VEEPOO_DATA_CHAR_UUID)
            
            // Setup realtime data characteristic
            setupCharacteristicNotification(gatt, service, VEEPOO_REALTIME_DATA_CHAR_UUID)
            
            Log.d(TAG, "Characteristic notifications setup completed")
        } catch (e: Exception) {
            Log.e(TAG, "Failed to setup characteristic notifications", e)
            notifyError("Failed to setup notifications: ${e.message}", null)
        }
    }
    
    /**
     * Setup notification for a specific characteristic
     */
    private fun setupCharacteristicNotification(
        gatt: BluetoothGatt,
        service: BluetoothGattService,
        characteristicUuid: UUID
    ) {
        val characteristic = service.getCharacteristic(characteristicUuid)
        if (characteristic != null) {
            // Enable local notifications
            val success = gatt.setCharacteristicNotification(characteristic, true)
            if (!success) {
                Log.e(TAG, "Failed to set local notification for: $characteristicUuid")
                return
            }
            
            // Get the descriptor for client characteristic configuration
            val descriptor = characteristic.getDescriptor(CLIENT_CHARACTERISTIC_CONFIG_UUID)
            if (descriptor != null) {
                // Write descriptor to enable remote notifications
                descriptor.value = ENABLE_NOTIFICATION_VALUE
                gatt.writeDescriptor(descriptor)
                Log.d(TAG, "Notification enabled for: $characteristicUuid")
            } else {
                Log.e(TAG, "CCCD descriptor not found for: $characteristicUuid")
            }
        } else {
            Log.e(TAG, "Characteristic not found: $characteristicUuid")
        }
    }

    /**
     * Request device information
     */
    private fun requestDeviceInfo(gatt: BluetoothGatt) {
        try {
            val service = gatt.getService(VEEPOO_SERVICE_UUID)
            val commandChar = service?.getCharacteristic(VEEPOO_COMMAND_CHAR_UUID)
            
            if (commandChar != null) {
                // Write the get device info command
                commandChar.value = CMD_GET_DEVICE_INFO
                gatt.writeCharacteristic(commandChar)
                Log.d(TAG, "Requested device info")
            } else {
                Log.e(TAG, "Command characteristic not found")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to request device info", e)
        }
    }
    
    /**
     * Start realtime measurement
     */
    private fun startRealtimeMeasurement(gatt: BluetoothGatt) {
        try {
            val service = gatt.getService(VEEPOO_SERVICE_UUID)
            val commandChar = service?.getCharacteristic(VEEPOO_COMMAND_CHAR_UUID)
            
            if (commandChar != null) {
                // Write the start realtime measurement command
                commandChar.value = CMD_START_REALTIME_MEASUREMENT
                gatt.writeCharacteristic(commandChar)
                Log.d(TAG, "Started realtime measurement")
            } else {
                Log.e(TAG, "Command characteristic not found")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to start realtime measurement", e)
        }
    }
    
    /**
     * Stop realtime measurement
     */
    private fun stopRealtimeMeasurement(gatt: BluetoothGatt) {
        try {
            val service = gatt.getService(VEEPOO_SERVICE_UUID)
            val commandChar = service?.getCharacteristic(VEEPOO_COMMAND_CHAR_UUID)
            
            if (commandChar != null) {
                // Write the stop realtime measurement command
                commandChar.value = CMD_STOP_REALTIME_MEASUREMENT
                gatt.writeCharacteristic(commandChar)
                Log.d(TAG, "Stopped realtime measurement")
            } else {
                Log.e(TAG, "Command characteristic not found")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to stop realtime measurement", e)
        }
    }
    
    /**
     * Schedule reconnection with exponential backoff
     */
    private fun scheduleReconnect() {
        reconnectJob?.cancel()
        reconnectJob = connectionScope.launch {
            try {
                val currentAttempts = reconnectAttempts.incrementAndGet()
                
                // Calculate delay with exponential backoff
                val delay = calculateBackoffDelay(currentAttempts)
                Log.d(TAG, "Scheduling reconnect attempt $currentAttempts with delay ${delay}ms")
                
                delay(delay)
                
                if (currentAttempts <= MAX_RECONNECT_ATTEMPTS) {
                    val deviceAddress = lastConnectedDeviceAddress ?: preferencesManager.pairedDeviceAddress
                    if (deviceAddress != null) {
                        Log.d(TAG, "Attempting reconnect to: $deviceAddress")
                        
                        // Close any existing connection
                        cleanup(deviceAddress, false)
                        
                        // Reconnect with autoConnect=true for better reconnection stability
                        isConnecting.set(true)
                        updateState(ConnectionState.CONNECTING)
                        
                        val device = bluetoothAdapter.getRemoteDevice(deviceAddress)
                        bluetoothGatt = device.connectGatt(
                            context,
                            true,  // Use autoConnect=true for reconnection
                            gattCallback,
                            BluetoothDevice.TRANSPORT_LE
                        )
                    } else {
                        Log.e(TAG, "No device address available for reconnection")
                        notifyError("No device address available for reconnection", null)
                        updateState(ConnectionState.DISCONNECTED)
                    }
                } else {
                    Log.e(TAG, "Max reconnection attempts reached")
                    notifyError("Failed to reconnect after $MAX_RECONNECT_ATTEMPTS attempts", null)
                    updateState(ConnectionState.ERROR)
                    cleanup(lastConnectedDeviceAddress)
                }
            } catch (e: Exception) {
                Log.e(TAG, "Reconnection failed", e)
                notifyError("Reconnection failed: ${e.message}", null)
                updateState(ConnectionState.ERROR)
            }
        }
    }

    /**
     * Calculate exponential backoff delay
     */
    private fun calculateBackoffDelay(attempt: Int): Long {
        // Base delay with exponential backoff: BASE_DELAY * 2^(attempt-1)
        val exponentialDelay = BASE_RECONNECT_DELAY_MS * 2.0.pow(attempt - 1).toLong()
        
        // Add jitter (0-20% of the delay) to prevent reconnection storms
        val jitter = (exponentialDelay * 0.2 * Math.random()).toLong()
        
        // Cap at maximum delay
        return min(exponentialDelay + jitter, MAX_RECONNECT_DELAY_MS)
    }
        
    /**
     * Parse vitals data from VeePoo device
     * This implementation is specific to ET492/ET593 devices
     */
    private fun parseVitalsData(data: ByteArray): VitalsData? {
        return try {
            if (data.size < 8) {
                Log.w(TAG, "Data packet too small: ${data.size} bytes")
                return null
            }
            
            // Check packet header based on device model
            when (deviceModel) {
                DeviceModel.ET492 -> parseET492Data(data)
                DeviceModel.ET593 -> parseET593Data(data)
                else -> parseGenericData(data)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to parse vitals data", e)
            null
        }
    }

    /**
     * Parse realtime data from VeePoo device
     */
    private fun parseRealtimeData(data: ByteArray): VitalsData? {
        return try {
            if (data.size < 8) {
                Log.w(TAG, "Realtime data packet too small: ${data.size} bytes")
                return null
            }
            
            // Check packet header for realtime data
            if (data[0] == 0xAB.toByte() && data[3] == 0x31.toByte()) {
                // Parse realtime data based on device model
                when (deviceModel) {
                    DeviceModel.ET492 -> parseET492RealtimeData(data)
                    DeviceModel.ET593 -> parseET593RealtimeData(data)
                    else -> parseGenericRealtimeData(data)
                }
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to parse realtime data", e)
            null
        }
    }

    /**
     * Parse ET492 specific data format
     */
    private fun parseET492Data(data: ByteArray): VitalsData? {
        // ET492 data format (example implementation - adjust based on actual protocol)
        // Byte 0: Header (0xAB)
        // Byte 1-2: Length
        // Byte 3: Command
        // Byte 4: Heart rate
        // Byte 5: SpO2
        // Byte 6-7: Systolic/Diastolic BP
        // Byte 8-9: Temperature (x10)
        
        if (data[0] != 0xAB.toByte()) {
            Log.w(TAG, "Invalid ET492 data header: ${bytesToHex(data)}")
            return null
        }
        
        val heartRate = data[4].toInt() and 0xFF
        val spo2 = data[5].toInt() and 0xFF
        val systolicBP = data[6].toInt() and 0xFF
        val diastolicBP = data[7].toInt() and 0xFF
        
        // Temperature is stored as a 16-bit value (x10)
        val tempRaw = ((data[8].toInt() and 0xFF) shl 8) or (data[9].toInt() and 0xFF)
        val temperature = tempRaw / 10.0f
        
        return VitalsData(
            heartRate = if (heartRate in 30..220) heartRate else null,
            spo2 = if (spo2 in 70..100) spo2 else null,
            bloodPressureSystolic = if (systolicBP in 60..240) systolicBP else null,
            bloodPressureDiastolic = if (diastolicBP in 40..160) diastolicBP else null,
            temperature = if (temperature in 35.0f..42.0f) temperature else null,
            timestamp = System.currentTimeMillis()
        )
    }
    
    /**
     * Parse ET593 specific data format
     */
    private fun parseET593Data(data: ByteArray): VitalsData? {
        // ET593 data format (example implementation - adjust based on actual protocol)
        // Similar to ET492 but with different byte positions
        
        if (data[0] != 0xAB.toByte()) {
            Log.w(TAG, "Invalid ET593 data header: ${bytesToHex(data)}")
            return null
        }
        
        val heartRate = data[4].toInt() and 0xFF
        val spo2 = data[5].toInt() and 0xFF
        val systolicBP = data[6].toInt() and 0xFF
        val diastolicBP = data[7].toInt() and 0xFF
        
        // ET593 temperature format might be different
        val tempRaw = ((data[8].toInt() and 0xFF) shl 8) or (data[9].toInt() and 0xFF)
        val temperature = tempRaw / 10.0f
        
        return VitalsData(
            heartRate = if (heartRate in 30..220) heartRate else null,
            spo2 = if (spo2 in 70..100) spo2 else null,
            bloodPressureSystolic = if (systolicBP in 60..240) systolicBP else null,
            bloodPressureDiastolic = if (diastolicBP in 40..160) diastolicBP else null,
            temperature = if (temperature in 35.0f..42.0f) temperature else null,
            timestamp = System.currentTimeMillis()
        )
    }
    
    /**
     * Parse generic VeePoo data format
     */
    private fun parseGenericData(data: ByteArray): VitalsData? {
        // Generic fallback parser for unknown device models
        
        if (data[0] != 0xAB.toByte()) {
            Log.w(TAG, "Invalid data header: ${bytesToHex(data)}")
            return null
        }
        
        // Try to extract values from common positions
        val heartRate = if (data.size > 4) data[4].toInt() and 0xFF else null
        val spo2 = if (data.size > 5) data[5].toInt() and 0xFF else null
        val systolicBP = if (data.size > 6) data[6].toInt() and 0xFF else null
        val diastolicBP = if (data.size > 7) data[7].toInt() and 0xFF else null
        
        // Temperature might be at different positions
        val temperature = if (data.size > 9) {
            val tempRaw = ((data[8].toInt() and 0xFF) shl 8) or (data[9].toInt() and 0xFF)
            tempRaw / 10.0f
        } else null
        
        return VitalsData(
            heartRate = if (heartRate != null && heartRate in 30..220) heartRate else null,
            spo2 = if (spo2 != null && spo2 in 70..100) spo2 else null,
            bloodPressureSystolic = if (systolicBP != null && systolicBP in 60..240) systolicBP else null,
            bloodPressureDiastolic = if (diastolicBP != null && diastolicBP in 40..160) diastolicBP else null,
            temperature = if (temperature != null && temperature in 35.0f..42.0f) temperature else null,
            timestamp = System.currentTimeMillis()
        )
    }
    
    /**
     * Parse ET492 realtime data
     */
    private fun parseET492RealtimeData(data: ByteArray): VitalsData? {
        // ET492 realtime data format (adjust based on actual protocol)
        
        if (data[0] != 0xAB.toByte() || data[3] != 0x31.toByte()) {
            return null
        }
        
        // Extract realtime values
        val heartRate = data[4].toInt() and 0xFF
        val spo2 = data[5].toInt() and 0xFF
        
        return VitalsData(
            heartRate = if (heartRate in 30..220) heartRate else null,
            spo2 = if (spo2 in 70..100) spo2 else null,
            bloodPressureSystolic = null,  // BP not available in realtime
            bloodPressureDiastolic = null, // BP not available in realtime
            temperature = null,            // Temperature not available in realtime
            timestamp = System.currentTimeMillis()
        )
    }
    
    /**
     * Parse ET593 realtime data
     */
    private fun parseET593RealtimeData(data: ByteArray): VitalsData? {
        // Similar to ET492 but with potential differences
        return parseET492RealtimeData(data)
    }
    
    /**
     * Parse generic realtime data
     */
    private fun parseGenericRealtimeData(data: ByteArray): VitalsData? {
        // Generic fallback for unknown device models
        
        if (data[0] != 0xAB.toByte() || data[3] != 0x31.toByte()) {
            return null
        }
        
        // Try to extract common realtime values
        val heartRate = if (data.size > 4) data[4].toInt() and 0xFF else null
        val spo2 = if (data.size > 5) data[5].toInt() and 0xFF else null
        
        return VitalsData(
            heartRate = if (heartRate != null && heartRate in 30..220) heartRate else null,
            spo2 = if (spo2 != null && spo2 in 70..100) spo2 else null,
            bloodPressureSystolic = null,
            bloodPressureDiastolic = null,
            temperature = null,
            timestamp = System.currentTimeMillis()
        )
    }
    
    /**
     * Disconnect from current device
     */
    fun disconnect() {
        Log.d(TAG, "Disconnecting from device")
        
        // Cancel pending jobs
        reconnectJob?.cancel()
        connectionJob?.cancel()
        serviceDiscoveryJob?.cancel()
        scanJob?.cancel()
        
        // Stop realtime measurement if connected
        if (isConnected.get() && bluetoothGatt != null) {
            stopRealtimeMeasurement(bluetoothGatt!!)
        }
        
        // Disconnect and close GATT
        bluetoothGatt?.let { gatt ->
            gatt.disconnect()
            gatt.close()
        }
        
        // Reset state
        bluetoothGatt = null
        isConnected.set(false)
        isConnecting.set(false)
        isDiscoveringServices.set(false)
        reconnectAttempts.set(0)
        
        // Save disconnected state
        preferencesManager.isDeviceConnected = false
        
        // Update state
        updateState(ConnectionState.DISCONNECTED)
    }

    /**
     * Clean up resources for a specific device
     */
    private fun cleanup(deviceAddress: String?, updateState: Boolean = true) {
        Log.d(TAG, "Cleaning up resources for device: $deviceAddress")
        
        // Cancel pending jobs
        reconnectJob?.cancel()
        connectionJob?.cancel()
        serviceDiscoveryJob?.cancel()
        
        // Close GATT connection
        bluetoothGatt?.let { gatt ->
            if (gatt.device.address == deviceAddress || deviceAddress == null) {
                gatt.disconnect()
                gatt.close()
                bluetoothGatt = null
            }
        }
        
        // Reset state
        isConnected.set(false)
        isConnecting.set(false)
        isDiscoveringServices.set(false)
        
        // Clear pending operations
        pendingOperations.clear()
        
        // Update state if requested
        if (updateState) {
            updateState(ConnectionState.DISCONNECTED)
        }
    }
    
    /**
     * Update connection state and notify callback
     */
    private fun updateState(newState: ConnectionState) {
        if (currentState != newState) {
            Log.d(TAG, "Connection state changed: $currentState -> $newState")
            currentState = newState
            
            // Notify on main thread
            mainScope.launch {
                connectionStateCallback?.invoke(newState)
            }
            
            // Save state for persistence if connected or disconnected
            if (newState == ConnectionState.CONNECTED) {
                preferencesManager.isDeviceConnected = true
            } else if (newState == ConnectionState.DISCONNECTED) {
                preferencesManager.isDeviceConnected = false
            }
        }
    }
    
    /**
     * Notify error callback
     */
    private fun notifyError(message: String, statusCode: Int?) {
        Log.e(TAG, "BLE error: $message${statusCode?.let { " (code: $it)" } ?: ""}")
        
        // Notify on main thread
        mainScope.launch {
            errorCallback?.invoke(message, statusCode)
        }
    }
    
    /**
     * Get status message for GATT status code
     */
    private fun getStatusMessage(status: Int): String {
        return GATT_STATUS_MESSAGES[status] ?: "Unknown status code: $status"
    }
    
    /**
     * Convert byte array to hex string for logging
     */
    private fun bytesToHex(bytes: ByteArray?): String {
        if (bytes == null) return "null"
        return bytes.joinToString("") { "%02X".format(it) }
    }
    
    /**
     * Check if currently connected
     */
    fun isConnected(): Boolean = isConnected.get()

    /**
     * Check if currently connecting
     */
    fun isConnecting(): Boolean = isConnecting.get()

    /**
     * Get current connection state
     */
    fun getConnectionState(): ConnectionState = currentState
    
    /**
     * Restore connection state from preferences
     */
    private fun restoreConnectionState() {
        if (preferencesManager.isDeviceConnected) {
            val deviceAddress = preferencesManager.pairedDeviceAddress
            if (deviceAddress != null) {
                Log.d(TAG, "Restoring connection to: $deviceAddress")
                connectToDevice(deviceAddress)
            }
        }
    }
    
    /**
     * Set callbacks
     */
    fun setConnectionStateCallback(callback: (ConnectionState) -> Unit) {
        connectionStateCallback = callback
    }

    fun setDataCallback(callback: (VitalsData) -> Unit) {
        dataCallback = callback
    }

    fun setErrorCallback(callback: (String, Int?) -> Unit) {
        errorCallback = callback
    }

    fun setScanResultCallback(callback: (List<BluetoothDevice>) -> Unit) {
        scanResultCallback = callback
    }

    /**
     * Retrieve the last {@link ScanResult} for a particular device address if it was
     * seen during the current/previous scan session.
     *
     * @param deviceAddress MAC address of the BLE peripheral.
     * @return ScanResult or null if the device has not been discovered / cached.
     */
    fun getDeviceScanResult(deviceAddress: String): ScanResult? {
        return scanResultsCache[deviceAddress]
    }
    
    /**
     * Request MTU change for better throughput
     */
    fun requestMtu(mtu: Int) {
        if (isConnected.get() && bluetoothGatt != null) {
            bluetoothGatt?.requestMtu(mtu)
        }
    }
    
    /**
     * Lifecycle methods
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        // Stop scanning if in background to save battery
        if (isScanning()) {
            stopScan()
        }
    }
    
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        // Clean up all resources
        cleanup()
    }
    
    /**
     * Complete cleanup of all resources
     */
    fun cleanup() {
        Log.d(TAG, "Performing complete cleanup")
        
        // Cancel all jobs
        reconnectJob?.cancel()
        connectionJob?.cancel()
        serviceDiscoveryJob?.cancel()
        scanJob?.cancel()
        
        // Stop scanning
        stopScan()
        
        // Disconnect from device
        disconnect()
        
        // Cancel coroutine scopes
        connectionScope.cancel()
        mainScope.cancel()
        
        // Clear callbacks to prevent memory leaks
        connectionStateCallback = null
        dataCallback = null
        errorCallback = null
        scanResultCallback = null
        
        // Clear pending operations
        pendingOperations.clear()
        
        Log.d(TAG, "Cleanup completed")
    }
}