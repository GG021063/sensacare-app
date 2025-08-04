package com.sensacare.veepoo

import android.app.*
import android.bluetooth.*
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.*
import java.util.*
import java.util.concurrent.TimeUnit

class BleForegroundService : Service() {

    private val serviceScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val database = AppDatabase.getInstance(this)
    private val vitalsDao = database.vitalsDao()
    private lateinit var preferencesManager: PreferencesManager
    private lateinit var secureApiManager: SecureApiManager
    private lateinit var connectionManager: BleConnectionManager
    
    private val dataBuffer = mutableListOf<VitalsData>()
    private var lastUploadTime = 0L
    
    companion object {
        private const val TAG = "BleForegroundService"
        private const val NOTIFICATION_ID = 1001
        private const val CHANNEL_ID = "ble_service_channel"
        private const val UPLOAD_INTERVAL = 30_000L // 30 seconds
        
        // Veepoo device UUIDs (update for your specific device)
        private val VEEPOO_SERVICE_UUID = UUID.fromString("0000fff0-0000-1000-8000-00805f9b34fb")
        private val VEEPOO_CHARACTERISTIC_UUID = UUID.fromString("0000fff1-0000-1000-8000-00805f9b34fb")
        
        fun startService(context: Context) {
            val intent = Intent(context, BleForegroundService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(intent)
            } else {
                context.startService(intent)
            }
        }
        
        fun stopService(context: Context) {
            val intent = Intent(context, BleForegroundService::class.java)
            context.stopService(intent)
        }
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        startForeground(NOTIFICATION_ID, createNotification("BLE Service Running"))
        
        // Initialize managers
        preferencesManager = PreferencesManager(this)
        secureApiManager = SecureApiManager(this)
        connectionManager = BleConnectionManager(this, preferencesManager)
        
        // Setup connection callbacks
        setupConnectionCallbacks()
        
        // Start background tasks
        serviceScope.launch {
            startBleConnection()
            startDataUploadScheduler()
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "BLE Foreground Service started")
        return START_STICKY // Restart service if killed
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
        connectionManager.cleanup()
        secureApiManager.cleanup()
        Log.d(TAG, "BLE Foreground Service destroyed")
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "BLE Service",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Background BLE monitoring service"
                setShowBadge(false)
            }
            
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun createNotification(content: String): Notification {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(this, CHANNEL_ID)
                            .setContentTitle("Sensacare App")
            .setContentText(content)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .build()
    }

    private fun setupConnectionCallbacks() {
        connectionManager.setConnectionStateCallback { state ->
            when (state) {
                BleConnectionManager.ConnectionState.CONNECTED -> {
                    updateNotification("Connected to Veepoo device")
                }
                BleConnectionManager.ConnectionState.CONNECTING -> {
                    updateNotification("Connecting to Veepoo device...")
                }
                BleConnectionManager.ConnectionState.RECONNECTING -> {
                    updateNotification("Reconnecting to Veepoo device...")
                }
                BleConnectionManager.ConnectionState.DISCONNECTED -> {
                    updateNotification("Disconnected - Attempting to reconnect")
                }
                BleConnectionManager.ConnectionState.ERROR -> {
                    updateNotification("Connection error - Check device")
                }
            }
        }

        connectionManager.setDataCallback { vitalsData ->
            dataBuffer.add(vitalsData)
            Log.d(TAG, "Vitals buffered: HR=${vitalsData.heartRate}, BP=${vitalsData.bloodPressureSystolic}/${vitalsData.bloodPressureDiastolic}")
        }

        connectionManager.setErrorCallback { error ->
            Log.e(TAG, "Connection error: $error")
            updateNotification("Connection error: $error")
        }
    }

    private suspend fun startBleConnection() {
        // Try to connect to last known device
        if (preferencesManager.isDevicePaired()) {
            Log.d(TAG, "Attempting to connect to last known device")
            connectionManager.connectToLastKnownDevice()
        } else {
            Log.d(TAG, "No paired device found, starting device discovery")
            startDeviceDiscovery()
        }
    }

    private suspend fun startDeviceDiscovery() {
        val bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        val bluetoothAdapter = bluetoothManager.adapter
        val bluetoothLeScanner = bluetoothAdapter.bluetoothLeScanner

        if (bluetoothLeScanner == null) {
            Log.e(TAG, "BluetoothLeScanner is null")
            return
        }

        // Start scanning for Veepoo devices
        while (isActive && !connectionManager.isConnected()) {
            try {
                Log.d(TAG, "Starting BLE scan for Veepoo devices")
                bluetoothLeScanner.startScan(scanCallback)
                
                // Scan for 10 seconds
                delay(10_000)
                bluetoothLeScanner.stopScan(scanCallback)
                delay(5_000)
                
            } catch (e: Exception) {
                Log.e(TAG, "Error during BLE scanning", e)
                delay(5_000)
            }
        }
    }

    private val scanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            val device = result.device
            val diagnosticLogger = DiagnosticLogger.getInstance(this@BleForegroundService)
            
            // Log diagnostic information
            diagnosticLogger.logBleScan(device.name, device.address, result.rssi, result.scanRecord)
            Log.d(TAG, "Found device: ${device.name ?: "Unknown"} - ${device.address}")
            
            // Filter for Veepoo devices
            if (device.name?.contains("Veepoo", ignoreCase = true) == true || 
                device.name?.contains("VP", ignoreCase = true) == true) {
                
                Log.d(TAG, "Veepoo device found: ${device.name}")
                
                // Save device info and connect
                preferencesManager.pairedDeviceName = device.name
                preferencesManager.pairedDeviceAddress = device.address
                
                connectionManager.connectToDevice(device.address)
            }
        }

        override fun onScanFailed(errorCode: Int) {
            val diagnosticLogger = DiagnosticLogger.getInstance(this@BleForegroundService)
            diagnosticLogger.logBleScanFailed(errorCode)
            Log.e(TAG, "Scan failed with error: $errorCode")
        }
    }

    private suspend fun startDataUploadScheduler() {
        while (isActive) {
            try {
                delay(UPLOAD_INTERVAL)
                uploadBufferedData()
            } catch (e: Exception) {
                Log.e(TAG, "Error in data upload scheduler", e)
            }
        }
    }

    private suspend fun uploadBufferedData() {
        if (dataBuffer.isEmpty()) {
            Log.d(TAG, "No data to upload")
            return
        }

        val dataToUpload = dataBuffer.toList()
        dataBuffer.clear()
        
        Log.d(TAG, "Uploading ${dataToUpload.size} vitals records")
        
        try {
            // Store in Room database
            dataToUpload.forEach { vitals ->
                val entity = VitalsEntity(
                    heartRate = vitals.heartRate,
                    bloodPressureSystolic = vitals.bloodPressureSystolic,
                    bloodPressureDiastolic = vitals.bloodPressureDiastolic,
                    spo2 = vitals.spo2,
                    temperature = vitals.temperature,
                    timestamp = vitals.timestamp
                )
                vitalsDao.insert(entity)
            }
            
            // Upload to API using SecureApiManager
            dataToUpload.forEach { vitals ->
                try {
                    val result = secureApiManager.uploadVitals(vitals)
                    result.fold(
                        onSuccess = {
                            Log.d(TAG, "Vitals uploaded to API successfully")
                        },
                        onFailure = { exception ->
                            Log.e(TAG, "Failed to upload vitals to API", exception)
                        }
                    )
                } catch (e: Exception) {
                    Log.e(TAG, "Failed to upload vitals to API", e)
                }
            }
            
            lastUploadTime = System.currentTimeMillis()
            updateNotification("Uploaded ${dataToUpload.size} vitals records")
            
        } catch (e: Exception) {
            Log.e(TAG, "Error uploading buffered data", e)
            // Re-add data to buffer for retry
            dataBuffer.addAll(dataToUpload)
        }
    }

    private fun updateNotification(content: String) {
        val notification = createNotification(content)
        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.notify(NOTIFICATION_ID, notification)
    }
} 