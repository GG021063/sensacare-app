package com.sensacare.veepoo.rpm

import android.content.Context
import android.util.Log
import com.sensacare.veepoo.VitalsData
import com.sensacare.veepoo.device.DeviceSDKManager
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * RPM Integration Manager - Central orchestrator for all RPM functionality
 * This provides a unified interface for the app to interact with RPM
 */
class RPMIntegrationManager(private val context: Context) {
    
    companion object {
        private const val TAG = "RPMIntegrationManager"
        private const val SYNC_INTERVAL_MS = 30 * 1000L // 30 seconds
        private const val QUEUE_PROCESSING_INTERVAL_MS = 60 * 1000L // 1 minute
    }
    
    // Core components
    private val clientContextManager = RPMClientContextManager(context)
    private val deviceSDKManager = DeviceSDKManager(context)
    private val vitalsPayloadBuilder = VitalsPayloadBuilder(clientContextManager)
    private val vitalsStatusEvaluator = VitalsStatusEvaluator()
    private val consentManager = ConsentManager(context)
    
    // Database and API
    private val database = com.sensacare.veepoo.AppDatabase.getInstance(context)
    private val vitalsDao = database.vitalsDao()
    private val syncQueueDao = database.syncQueueDao()
    private val rpmApiManager = RPMApiManager(context, clientContextManager, syncQueueDao)
    
    // Coroutine scope
    private val integrationScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    
    // State flows
    private val _rpmStatus = MutableStateFlow(RPMStatus.NOT_INITIALIZED)
    val rpmStatus: StateFlow<RPMStatus> = _rpmStatus.asStateFlow()
    
    private val _currentVitalsStatus = MutableStateFlow(VitalsStatus.NO_DATA)
    val currentVitalsStatus: StateFlow<VitalsStatus> = _currentVitalsStatus.asStateFlow()
    
    private val _lastVitalsData = MutableStateFlow<VitalsData?>(null)
    val lastVitalsData: StateFlow<VitalsData?> = _lastVitalsData.asStateFlow()
    
    private val _recentAlerts = MutableStateFlow<List<AlertData>>(emptyList())
    val recentAlerts: StateFlow<List<AlertData>> = _recentAlerts.asStateFlow()
    
    // Jobs
    private var syncJob: Job? = null
    private var queueProcessingJob: Job? = null
    private var alertsPollingJob: Job? = null
    
    init {
        Log.d(TAG, "RPM Integration Manager initialized")
        startQueueProcessing()
    }
    
    /**
     * Initialize RPM with client context
     */
    suspend fun initializeRPM(
        clientId: String,
        userToken: String,
        deviceType: String = DeviceSDKManager.DEVICE_TYPE_VEEPOO_RING
    ): Boolean {
        return try {
            Log.d(TAG, "Initializing RPM for client: $clientId")
            
            // Set up client context
            clientContextManager.setClientId(clientId)
            clientContextManager.setUserToken(userToken)
            
            // Initialize device SDK
            val sdkInitialized = deviceSDKManager.initializeSDK(deviceType)
            if (!sdkInitialized) {
                Log.e(TAG, "Failed to initialize device SDK")
                return false
            }
            
            // Fetch client thresholds from RPM
            val thresholdsResult = rpmApiManager.getClientThresholds()
            if (thresholdsResult.isSuccess) {
                Log.d(TAG, "Retrieved client thresholds from RPM")
            } else {
                Log.w(TAG, "Failed to retrieve client thresholds: ${thresholdsResult.exceptionOrNull()?.message}")
            }
            
            // Start background processes
            startSyncProcess()
            startAlertsPolling()
            
            _rpmStatus.value = RPMStatus.READY
            Log.d(TAG, "RPM initialization completed successfully")
            true
            
        } catch (e: Exception) {
            Log.e(TAG, "Error initializing RPM", e)
            _rpmStatus.value = RPMStatus.ERROR
            false
        }
    }
    
    /**
     * Capture consent and set up RPM
     */
    suspend fun setupWithConsent(
        clientId: String,
        clientName: String,
        userToken: String,
        carerName: String? = null,
        deviceType: String = DeviceSDKManager.DEVICE_TYPE_VEEPOO_RING
    ): Boolean {
        return try {
            Log.d(TAG, "Setting up RPM with consent for client: $clientId")
            
            // Capture consent
            val consentResult = consentManager.captureConsent(
                clientId = clientId,
                clientName = clientName,
                carerName = carerName
            )
            
            when (consentResult) {
                is ConsentResult.Success -> {
                    val consentRecord = consentResult.consentRecord
                    Log.d(TAG, "Consent captured successfully: ${consentRecord.consentId}")
                    
                    // Store consent token
                    clientContextManager.setConsentToken(consentRecord.consentToken)
                    
                    // Initialize RPM
                    val initialized = initializeRPM(clientId, userToken, deviceType)
                    if (initialized) {
                        _rpmStatus.value = RPMStatus.READY
                        Log.d(TAG, "RPM setup with consent completed successfully")
                    }
                    initialized
                }
                is ConsentResult.Failure -> {
                    Log.e(TAG, "Failed to capture consent: ${consentResult.errorMessage}")
                    _rpmStatus.value = RPMStatus.ERROR
                    false
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error setting up RPM with consent", e)
            _rpmStatus.value = RPMStatus.ERROR
            false
        }
    }
    
    /**
     * Connect to device and start monitoring
     */
    suspend fun connectAndStartMonitoring(device: android.bluetooth.BluetoothDevice): Boolean {
        return try {
            Log.d(TAG, "Connecting to device and starting monitoring")
            
            // Connect to device
            val connected = deviceSDKManager.connectToDevice(device)
            if (!connected) {
                Log.e(TAG, "Failed to connect to device")
                return false
            }
            
            // Start vitals monitoring
            val monitoringStarted = deviceSDKManager.startVitalsMonitoring()
            if (!monitoringStarted) {
                Log.e(TAG, "Failed to start vitals monitoring")
                return false
            }
            
            // Update device metadata in client context
            val metadata = deviceSDKManager.getDeviceMetadata()
            metadata?.let { meta ->
                clientContextManager.setDeviceMetadata(convertToRPMDeviceMetadata(meta))
            }
            
            _rpmStatus.value = RPMStatus.MONITORING
            Log.d(TAG, "Device connected and monitoring started successfully")
            true
            
        } catch (e: Exception) {
            Log.e(TAG, "Error connecting and starting monitoring", e)
            false
        }
    }
    
    /**
     * Process vitals data and upload to RPM
     */
    suspend fun processVitalsData(vitalsData: VitalsData, rssi: Int? = null): Boolean {
        return try {
            Log.d(TAG, "Processing vitals data: $vitalsData")
            
            // Update last vitals data
            _lastVitalsData.value = vitalsData
            
            // Evaluate vitals status
            val status = vitalsStatusEvaluator.evaluateStatus(
                vitalsData = vitalsData,
                lastDataTimestamp = System.currentTimeMillis(),
                isConnected = deviceSDKManager.isConnected.value
            )
            _currentVitalsStatus.value = status
            
            // Validate vitals for upload
            val validation = vitalsPayloadBuilder.validateVitalsForUpload(vitalsData)
            if (!validation.isValid) {
                Log.w(TAG, "Vitals validation failed: ${validation.errors}")
                return false
            }
            
            // Build RPM payload
            val payload = vitalsPayloadBuilder.buildPayload(
                vitalsData = vitalsData,
                device = null, // Will be set from device metadata
                rssi = rssi
            )
            
            if (payload == null) {
                Log.e(TAG, "Failed to build RPM payload")
                return false
            }
            
            // Upload to RPM
            val uploadResult = rpmApiManager.uploadVitals(payload)
            if (uploadResult.isSuccess) {
                Log.d(TAG, "Vitals uploaded to RPM successfully")
                
                // Store locally for backup
                val entity = com.sensacare.veepoo.VitalsEntity(
                    heartRate = vitalsData.heartRate,
                    bloodPressureSystolic = vitalsData.bloodPressureSystolic,
                    bloodPressureDiastolic = vitalsData.bloodPressureDiastolic,
                    spo2 = vitalsData.spo2,
                    temperature = vitalsData.temperature,
                    timestamp = System.currentTimeMillis()
                )
                vitalsDao.insert(entity)
                
                true
            } else {
                Log.w(TAG, "Failed to upload vitals to RPM, queuing for retry")
                
                // Queue for later upload
                rpmApiManager.queueVitalsForUpload(payload, priority = 1)
                false
            }
            
        } catch (e: Exception) {
            Log.e(TAG, "Error processing vitals data", e)
            false
        }
    }
    
    /**
     * Convert SDK-level DeviceMetadata into the type expected by the
     * RPMClientContextManager so that the client context can be updated
     * without type-mismatch compilation errors.
     */
    private fun convertToRPMDeviceMetadata(
        metadata: DeviceMetadata
    ): RPMClientContextManager.DeviceMetadata =
        RPMClientContextManager.DeviceMetadata(
            type = metadata.type,
            mac = metadata.mac,
            firmware = metadata.firmware,
            battery = metadata.battery,
            sdkVersion = metadata.sdkVersion
        )

    /**
     * Get recent alerts from RPM
     */
    suspend fun refreshAlerts(): Boolean {
        return try {
            val alertsResult = rpmApiManager.getRecentAlerts(limit = 10)
            if (alertsResult.isSuccess) {
                val alerts = alertsResult.getOrNull() ?: emptyList()
                _recentAlerts.value = alerts
                Log.d(TAG, "Refreshed ${alerts.size} alerts from RPM")
                true
            } else {
                Log.w(TAG, "Failed to refresh alerts: ${alertsResult.exceptionOrNull()?.message}")
                false
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error refreshing alerts", e)
            false
        }
    }
    
    /**
     * Acknowledge an alert
     */
    suspend fun acknowledgeAlert(alertId: String): Boolean {
        return try {
            val result = rpmApiManager.acknowledgeAlert(alertId)
            if (result.isSuccess) {
                Log.d(TAG, "Alert acknowledged: $alertId")
                // Refresh alerts to update the list
                refreshAlerts()
                true
            } else {
                Log.w(TAG, "Failed to acknowledge alert: ${result.exceptionOrNull()?.message}")
                false
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error acknowledging alert", e)
            false
        }
    }
    
    /**
     * Get vitals timeline from RPM
     */
    suspend fun getVitalsTimeline(range: String = "24h"): List<VitalsTimelineEntry>? {
        return try {
            val result = rpmApiManager.getVitalsTimeline(range = range)
            if (result.isSuccess) {
                val timeline = result.getOrNull() ?: emptyList()
                Log.d(TAG, "Retrieved ${timeline.size} timeline entries from RPM")
                timeline
            } else {
                Log.w(TAG, "Failed to get vitals timeline: ${result.exceptionOrNull()?.message}")
                null
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error getting vitals timeline", e)
            null
        }
    }
    
    /**
     * Start sync process
     */
    private fun startSyncProcess() {
        syncJob?.cancel()
        syncJob = integrationScope.launch {
            while (isActive) {
                try {
                    // Process queued items
                    rpmApiManager.processQueuedItems()
                    
                    // Clean up old queue items
                    rpmApiManager.cleanupOldQueueItems()
                    
                    delay(SYNC_INTERVAL_MS)
                } catch (e: Exception) {
                    Log.e(TAG, "Error in sync process", e)
                    delay(SYNC_INTERVAL_MS)
                }
            }
        }
    }
    
    /**
     * Start queue processing
     */
    private fun startQueueProcessing() {
        queueProcessingJob?.cancel()
        queueProcessingJob = integrationScope.launch {
            while (isActive) {
                try {
                    rpmApiManager.processQueuedItems()
                    delay(QUEUE_PROCESSING_INTERVAL_MS)
                } catch (e: Exception) {
                    Log.e(TAG, "Error in queue processing", e)
                    delay(QUEUE_PROCESSING_INTERVAL_MS)
                }
            }
        }
    }
    
    /**
     * Start alerts polling
     */
    private fun startAlertsPolling() {
        alertsPollingJob?.cancel()
        alertsPollingJob = integrationScope.launch {
            while (isActive) {
                try {
                    refreshAlerts()
                    delay(5 * 60 * 1000L) // Poll every 5 minutes
                } catch (e: Exception) {
                    Log.e(TAG, "Error in alerts polling", e)
                    delay(5 * 60 * 1000L)
                }
            }
        }
    }
    
    /**
     * Get queue statistics
     */
    suspend fun getQueueStats(): RPMApiManager.QueueStats? {
        return try {
            rpmApiManager.getQueueStats()
        } catch (e: Exception) {
            Log.e(TAG, "Error getting queue stats", e)
            null
        }
    }
    
    /**
     * Check if RPM is ready
     */
    fun isRPMReady(): Boolean {
        return _rpmStatus.value == RPMStatus.READY || _rpmStatus.value == RPMStatus.MONITORING
    }
    
    /**
     * Get current client context
     */
    fun getCurrentClientContext(): RPMClientContextManager.ClientProfile? {
        return clientContextManager.getCurrentProfile()
    }
    
    /**
     * Cleanup
     */
    fun cleanup() {
        try {
            Log.d(TAG, "Cleaning up RPM Integration Manager")
            
            syncJob?.cancel()
            queueProcessingJob?.cancel()
            alertsPollingJob?.cancel()
            integrationScope.cancel()
            
            deviceSDKManager.cleanup()
            rpmApiManager.cleanup()
            
            Log.d(TAG, "RPM Integration Manager cleanup completed")
        } catch (e: Exception) {
            Log.e(TAG, "Error during cleanup", e)
        }
    }
}

/**
 * RPM Status enum
 */
enum class RPMStatus {
    NOT_INITIALIZED,
    INITIALIZING,
    READY,
    MONITORING,
    ERROR,
    DISCONNECTED
} 