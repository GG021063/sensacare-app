package com.sensacare.veepoo

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothProfile
import android.content.Context
import android.util.Log
import kotlinx.coroutines.*
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger

class BleConnectionManager(
    private val context: Context,
    private val preferencesManager: PreferencesManager
) {
    companion object {
        private const val TAG = "BleConnectionManager"
        private const val MAX_RECONNECT_ATTEMPTS = 3
        private const val RECONNECT_DELAY_MS = 2000L
        private const val SCAN_TIMEOUT_MS = 10000L
        private const val CONNECTION_TIMEOUT_MS = 15000L
        
        // Standard BLE notification enable value
        val ENABLE_NOTIFICATION_VALUE = byteArrayOf(0x01, 0x00)
    }

    // Connection state
    private var bluetoothGatt: BluetoothGatt? = null
    private var isConnecting = AtomicBoolean(false)
    private var isConnected = AtomicBoolean(false)
    private var reconnectAttempts = AtomicInteger(0)
    private var connectionJob: Job? = null
    private var reconnectJob: Job? = null

    // Callbacks
    private var connectionStateCallback: ((ConnectionState) -> Unit)? = null
    private var dataCallback: ((VitalsData) -> Unit)? = null
    private var errorCallback: ((String) -> Unit)? = null

    // Coroutine scope
    private val connectionScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    // Connection states
    enum class ConnectionState {
        DISCONNECTED,
        CONNECTING,
        CONNECTED,
        RECONNECTING,
        ERROR
    }

    // Veepoo BLE UUIDs (update these with actual Veepoo device UUIDs)
    private val VEEPOO_SERVICE_UUID = "0000fff0-0000-1000-8000-00805f9b34fb"
    private val VEEPOO_CHARACTERISTIC_UUID = "0000fff1-0000-1000-8000-00805f9b34fb"

    private val bluetoothManager: BluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    private val bluetoothAdapter: BluetoothAdapter = bluetoothManager.adapter

    // GATT callback for connection state changes
    private val gattCallback = object : BluetoothGattCallback() {
        override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {
            val deviceAddress = gatt.device.address
            val diagnosticLogger = DiagnosticLogger.getInstance(context)
            
            // Log diagnostic information
            diagnosticLogger.logBleConnectionStateChange(deviceAddress, 
                if (isConnected.get()) BluetoothProfile.STATE_CONNECTED else BluetoothProfile.STATE_DISCONNECTED, 
                newState)
            
            when (newState) {
                BluetoothProfile.STATE_CONNECTED -> {
                    Log.d(TAG, "Connected to GATT server: $deviceAddress")
                    diagnosticLogger.logBleConnectionSuccess(deviceAddress)
                    isConnected.set(true)
                    isConnecting.set(false)
                    reconnectAttempts.set(0)
                    
                    // Discover services
                    gatt.discoverServices()
                    connectionStateCallback?.invoke(ConnectionState.CONNECTED)
                }
                
                BluetoothProfile.STATE_DISCONNECTED -> {
                    Log.d(TAG, "Disconnected from GATT server: $deviceAddress")
                    diagnosticLogger.logBleDisconnection(deviceAddress, "Status: $status")
                    isConnected.set(false)
                    isConnecting.set(false)
                    
                    // Check if this was an unexpected disconnection
                    if (preferencesManager.isAutoReconnectEnabled && reconnectAttempts.get() < MAX_RECONNECT_ATTEMPTS) {
                        Log.d(TAG, "Unexpected disconnection detected, attempting reconnect...")
                        connectionStateCallback?.invoke(ConnectionState.RECONNECTING)
                        scheduleReconnect()
                    } else {
                        connectionStateCallback?.invoke(ConnectionState.DISCONNECTED)
                        if (reconnectAttempts.get() >= MAX_RECONNECT_ATTEMPTS) {
                            errorCallback?.invoke("Failed to reconnect after ${MAX_RECONNECT_ATTEMPTS} attempts")
                        }
                    }
                }
            }
        }

        override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                Log.d(TAG, "Services discovered successfully")
                setupCharacteristicNotifications(gatt)
            } else {
                Log.e(TAG, "Service discovery failed with status: $status")
                errorCallback?.invoke("Service discovery failed")
            }
        }

        override fun onCharacteristicChanged(
            gatt: BluetoothGatt,
            characteristic: BluetoothGattCharacteristic,
            value: ByteArray
        ) {
            // Parse vitals data from characteristic value
            parseVitalsData(value)?.let { vitalsData ->
                dataCallback?.invoke(vitalsData)
            }
        }

        override fun onCharacteristicRead(
            gatt: BluetoothGatt,
            characteristic: BluetoothGattCharacteristic,
            status: Int
        ) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                Log.d(TAG, "Characteristic read successfully")
            } else {
                Log.e(TAG, "Characteristic read failed with status: $status")
            }
        }
    }

    // Connect to a specific device
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
                connectionStateCallback?.invoke(ConnectionState.CONNECTING)

                val device = bluetoothAdapter.getRemoteDevice(deviceAddress)
                bluetoothGatt = device.connectGatt(context, false, gattCallback)

                // Set connection timeout
                withTimeout(CONNECTION_TIMEOUT_MS) {
                    while (!isConnected.get() && isConnecting.get()) {
                        delay(100)
                    }
                }

                if (!isConnected.get()) {
                    Log.e(TAG, "Connection timeout")
                    errorCallback?.invoke("Connection timeout")
                    connectionStateCallback?.invoke(ConnectionState.ERROR)
                }
            } catch (e: Exception) {
                Log.e(TAG, "Connection failed", e)
                errorCallback?.invoke("Connection failed: ${e.message}")
                connectionStateCallback?.invoke(ConnectionState.ERROR)
            } finally {
                isConnecting.set(false)
            }
        }
    }

    // Connect to last known device
    fun connectToLastKnownDevice() {
        val lastDeviceAddress = preferencesManager.pairedDeviceAddress
        if (lastDeviceAddress != null) {
            Log.d(TAG, "Connecting to last known device: $lastDeviceAddress")
            connectToDevice(lastDeviceAddress)
        } else {
            Log.w(TAG, "No last known device address available")
            errorCallback?.invoke("No paired device found")
        }
    }

    // Schedule reconnection attempt
    private fun scheduleReconnect() {
        reconnectJob?.cancel()
        reconnectJob = connectionScope.launch {
            try {
                val currentAttempts = reconnectAttempts.incrementAndGet()
                Log.d(TAG, "Scheduling reconnect attempt $currentAttempts of $MAX_RECONNECT_ATTEMPTS")
                
                delay(RECONNECT_DELAY_MS)
                
                if (currentAttempts <= MAX_RECONNECT_ATTEMPTS) {
                    val lastDeviceAddress = preferencesManager.pairedDeviceAddress
                    if (lastDeviceAddress != null) {
                        Log.d(TAG, "Attempting reconnect to: $lastDeviceAddress")
                        connectToDevice(lastDeviceAddress)
                    } else {
                        Log.e(TAG, "No device address available for reconnection")
                        errorCallback?.invoke("No device address available for reconnection")
                    }
                } else {
                    Log.e(TAG, "Max reconnection attempts reached")
                    errorCallback?.invoke("Failed to reconnect after $MAX_RECONNECT_ATTEMPTS attempts")
                    connectionStateCallback?.invoke(ConnectionState.ERROR)
                }
            } catch (e: Exception) {
                Log.e(TAG, "Reconnection failed", e)
                errorCallback?.invoke("Reconnection failed: ${e.message}")
            }
        }
    }

    // Setup characteristic notifications
    private fun setupCharacteristicNotifications(gatt: BluetoothGatt) {
        try {
            val service = gatt.getService(java.util.UUID.fromString(VEEPOO_SERVICE_UUID))
            val characteristic = service?.getCharacteristic(java.util.UUID.fromString(VEEPOO_CHARACTERISTIC_UUID))
            
            if (characteristic != null) {
                // Enable notifications
                gatt.setCharacteristicNotification(characteristic, true)
                
                // Get the descriptor for client characteristic configuration
                val descriptor = characteristic.getDescriptor(
                    java.util.UUID.fromString("00002902-0000-1000-8000-00805f9b34fb")
                )
                descriptor?.value = ENABLE_NOTIFICATION_VALUE
                gatt.writeDescriptor(descriptor)
                
                Log.d(TAG, "Characteristic notifications enabled")
            } else {
                Log.e(TAG, "Required characteristic not found")
                errorCallback?.invoke("Required characteristic not found")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to setup characteristic notifications", e)
            errorCallback?.invoke("Failed to setup notifications: ${e.message}")
        }
    }

    // Parse vitals data from BLE characteristic value
    private fun parseVitalsData(value: ByteArray): VitalsData? {
        return try {
            // This is a placeholder implementation - update with actual Veepoo data format
            if (value.size >= 8) {
                val heartRate = value[0].toInt() and 0xFF
                val spo2 = value[1].toInt() and 0xFF
                val systolicBP = value[2].toInt() and 0xFF
                val diastolicBP = value[3].toInt() and 0xFF
                val temperature = (value[4].toInt() and 0xFF) / 10.0f
                
                VitalsData(
                    heartRate = if (heartRate > 0) heartRate else null,
                    spo2 = if (spo2 > 0) spo2 else null,
                    bloodPressureSystolic = if (systolicBP > 0) systolicBP else null,
                    bloodPressureDiastolic = if (diastolicBP > 0) diastolicBP else null,
                    temperature = if (temperature > 0) temperature else null,
                    timestamp = System.currentTimeMillis()
                )
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to parse vitals data", e)
            null
        }
    }

    // Disconnect from current device
    fun disconnect() {
        Log.d(TAG, "Disconnecting from device")
        reconnectJob?.cancel()
        connectionJob?.cancel()
        
        bluetoothGatt?.let { gatt ->
            gatt.disconnect()
            gatt.close()
        }
        
        bluetoothGatt = null
        isConnected.set(false)
        isConnecting.set(false)
        reconnectAttempts.set(0)
        connectionStateCallback?.invoke(ConnectionState.DISCONNECTED)
    }

    // Check if currently connected
    fun isConnected(): Boolean = isConnected.get()

    // Check if currently connecting
    fun isConnecting(): Boolean = isConnecting.get()

    // Get current connection state
    fun getConnectionState(): ConnectionState {
        return when {
            isConnected.get() -> ConnectionState.CONNECTED
            isConnecting.get() -> ConnectionState.CONNECTING
            reconnectAttempts.get() > 0 -> ConnectionState.RECONNECTING
            else -> ConnectionState.DISCONNECTED
        }
    }

    // Set callbacks
    fun setConnectionStateCallback(callback: (ConnectionState) -> Unit) {
        connectionStateCallback = callback
    }

    fun setDataCallback(callback: (VitalsData) -> Unit) {
        dataCallback = callback
    }

    fun setErrorCallback(callback: (String) -> Unit) {
        errorCallback = callback
    }

    // Cleanup
    fun cleanup() {
        disconnect()
        connectionScope.cancel()
    }
}