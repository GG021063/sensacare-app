package com.sensacare.veepoo.rpm

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RPMApiManager(
    private val context: Context,
    private val clientContextManager: RPMClientContextManager,
    private val syncQueueDao: SyncQueueDao
) {
    
    companion object {
        private const val TAG = "RPMApiManager"
        private const val MAX_RETRY_ATTEMPTS = 3
        private const val RETRY_DELAY_MS = 5000L
        private const val HEARTBEAT_INTERVAL_MS = 15 * 60 * 1000L // 15 minutes
    }
    
    private val gson = Gson()
    private val apiScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    
    // State flows
    private val _isConnected = MutableStateFlow(false)
    val isConnected: StateFlow<Boolean> = _isConnected.asStateFlow()
    
    private val _lastSyncTime = MutableStateFlow(0L)
    val lastSyncTime: StateFlow<Long> = _lastSyncTime.asStateFlow()
    
    private val _queueSize = MutableStateFlow(0)
    val queueSize: StateFlow<Int> = _queueSize.asStateFlow()
    
    // API service
    private val apiService: RPMApiService by lazy {
        createApiService()
    }
    
    // Heartbeat job
    private var heartbeatJob: Job? = null
    
    init {
        startHeartbeat()
        monitorQueueSize()
    }
    
    private fun createApiService(): RPMApiService {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(RPMAuthInterceptor(clientContextManager))
            .addInterceptor(RPMRetryInterceptor())
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
        
        val retrofit = Retrofit.Builder()
            .baseUrl(RPMConfig.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        
        return retrofit.create(RPMApiService::class.java)
    }
    
    /**
     * Upload vitals data to RPM
     */
    suspend fun uploadVitals(payload: VitalsUploadPayload): Result<UploadResponse> {
        return try {
            val clientId = clientContextManager.getClientId()
                ?: return Result.failure(IllegalStateException("No client ID configured"))
            
            val response = apiService.uploadVitals(clientId, payload)
            
            if (response.isSuccessful) {
                val uploadResponse = response.body()
                if (uploadResponse != null) {
                    clientContextManager.setLastSync(System.currentTimeMillis())
                    _lastSyncTime.value = System.currentTimeMillis()
                    _isConnected.value = true
                    Log.d(TAG, "Vitals uploaded successfully: ${uploadResponse.vitalsId}")
                    Result.success(uploadResponse)
                } else {
                    Result.failure(Exception("Empty response body"))
                }
            } else {
                val errorBody = response.errorBody()?.string() ?: "Unknown error"
                Log.e(TAG, "Upload failed: ${response.code()} - $errorBody")
                Result.failure(Exception("Upload failed: ${response.code()} - $errorBody"))
            }
        } catch (e: Exception) {
            Log.e(TAG, "Upload exception", e)
            Result.failure(e)
        }
    }
    
    /**
     * Queue vitals for later upload (when offline or API fails)
     */
    suspend fun queueVitalsForUpload(payload: VitalsUploadPayload, priority: Int = 1) {
        try {
            val payloadJson = gson.toJson(payload)
            val queueEntity = SyncQueueEntity(
                payload = payloadJson,
                timestamp = System.currentTimeMillis(),
                priority = priority
            )
            
            syncQueueDao.insert(queueEntity)
            Log.d(TAG, "Vitals queued for upload: ${payload.timestamp}")
        } catch (e: Exception) {
            Log.e(TAG, "Failed to queue vitals", e)
        }
    }
    
    /**
     * Process queued items
     */
    suspend fun processQueuedItems() {
        try {
            val pendingItems = syncQueueDao.getPendingItems(maxRetries = MAX_RETRY_ATTEMPTS, limit = 10)
            
            for (item in pendingItems) {
                try {
                    val payload = gson.fromJson(item.payload, VitalsUploadPayload::class.java)
                    val result = uploadVitals(payload)
                    
                    if (result.isSuccess) {
                        // Success - remove from queue
                        syncQueueDao.delete(item)
                        Log.d(TAG, "Queued item uploaded successfully: ${item.id}")
                    } else {
                        // Failure - increment retry count
                        syncQueueDao.incrementRetryCount(
                            item.id,
                            System.currentTimeMillis(),
                            result.exceptionOrNull()?.message
                        )
                        Log.w(TAG, "Queued item upload failed, retry ${item.retryCount + 1}: ${item.id}")
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Error processing queued item ${item.id}", e)
                    syncQueueDao.incrementRetryCount(
                        item.id,
                        System.currentTimeMillis(),
                        e.message
                    )
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error processing queued items", e)
        }
    }
    
    /**
     * Get recent alerts from RPM
     */
    suspend fun getRecentAlerts(limit: Int = 10): Result<List<AlertData>> {
        return try {
            val clientId = clientContextManager.getClientId()
                ?: return Result.failure(IllegalStateException("No client ID configured"))
            
            val response = apiService.getRecentAlerts(clientId, limit)
            
            if (response.isSuccessful) {
                val alerts = response.body() ?: emptyList()
                Log.d(TAG, "Retrieved ${alerts.size} recent alerts")
                Result.success(alerts)
            } else {
                val errorBody = response.errorBody()?.string() ?: "Unknown error"
                Log.e(TAG, "Get alerts failed: ${response.code()} - $errorBody")
                Result.failure(Exception("Get alerts failed: ${response.code()} - $errorBody"))
            }
        } catch (e: Exception) {
            Log.e(TAG, "Get alerts exception", e)
            Result.failure(e)
        }
    }
    
    /**
     * Acknowledge an alert
     */
    suspend fun acknowledgeAlert(alertId: String): Result<Unit> {
        return try {
            val clientId = clientContextManager.getClientId()
                ?: return Result.failure(IllegalStateException("No client ID configured"))
            
            val response = apiService.acknowledgeAlert(clientId, alertId)
            
            if (response.isSuccessful) {
                Log.d(TAG, "Alert acknowledged: $alertId")
                Result.success(Unit)
            } else {
                val errorBody = response.errorBody()?.string() ?: "Unknown error"
                Log.e(TAG, "Acknowledge alert failed: ${response.code()} - $errorBody")
                Result.failure(Exception("Acknowledge alert failed: ${response.code()} - $errorBody"))
            }
        } catch (e: Exception) {
            Log.e(TAG, "Acknowledge alert exception", e)
            Result.failure(e)
        }
    }
    
    /**
     * Report device status (heartbeat)
     */
    suspend fun reportDeviceStatus(status: DeviceStatusPayload): Result<Unit> {
        return try {
            val clientId = clientContextManager.getClientId()
                ?: return Result.failure(IllegalStateException("No client ID configured"))
            
            val response = apiService.reportDeviceStatus(clientId, status)
            
            if (response.isSuccessful) {
                Log.d(TAG, "Device status reported successfully")
                Result.success(Unit)
            } else {
                val errorBody = response.errorBody()?.string() ?: "Unknown error"
                Log.e(TAG, "Report device status failed: ${response.code()} - $errorBody")
                Result.failure(Exception("Report device status failed: ${response.code()} - $errorBody"))
            }
        } catch (e: Exception) {
            Log.e(TAG, "Report device status exception", e)
            Result.failure(e)
        }
    }
    
    /**
     * Get client thresholds from RPM
     */
    suspend fun getClientThresholds(): Result<ClientThresholds> {
        return try {
            val clientId = clientContextManager.getClientId()
                ?: return Result.failure(IllegalStateException("No client ID configured"))
            
            val response = apiService.getClientThresholds(clientId)
            
            if (response.isSuccessful) {
                val thresholds = response.body()
                if (thresholds != null) {
                    clientContextManager.setThresholds(thresholds)
                    Log.d(TAG, "Client thresholds retrieved successfully")
                    Result.success(thresholds)
                } else {
                    Result.failure(Exception("Empty thresholds response"))
                }
            } else {
                val errorBody = response.errorBody()?.string() ?: "Unknown error"
                Log.e(TAG, "Get thresholds failed: ${response.code()} - $errorBody")
                Result.failure(Exception("Get thresholds failed: ${response.code()} - $errorBody"))
            }
        } catch (e: Exception) {
            Log.e(TAG, "Get thresholds exception", e)
            Result.failure(e)
        }
    }
    
    /**
     * Get vitals timeline from RPM
     */
    suspend fun getVitalsTimeline(range: String = "24h", limit: Int = 100): Result<List<VitalsTimelineEntry>> {
        return try {
            val clientId = clientContextManager.getClientId()
                ?: return Result.failure(IllegalStateException("No client ID configured"))
            
            val response = apiService.getVitalsTimeline(clientId, range, limit)
            
            if (response.isSuccessful) {
                val timeline = response.body() ?: emptyList()
                Log.d(TAG, "Retrieved ${timeline.size} timeline entries")
                Result.success(timeline)
            } else {
                val errorBody = response.errorBody()?.string() ?: "Unknown error"
                Log.e(TAG, "Get timeline failed: ${response.code()} - $errorBody")
                Result.failure(Exception("Get timeline failed: ${response.code()} - $errorBody"))
            }
        } catch (e: Exception) {
            Log.e(TAG, "Get timeline exception", e)
            Result.failure(e)
        }
    }
    
    /**
     * Report sync gap to RPM
     */
    suspend fun reportSyncGap(gap: SyncGapReport): Result<Unit> {
        return try {
            val clientId = clientContextManager.getClientId()
                ?: return Result.failure(IllegalStateException("No client ID configured"))
            
            val response = apiService.reportSyncGap(clientId, gap)
            
            if (response.isSuccessful) {
                Log.d(TAG, "Sync gap reported successfully: ${gap.startTime} to ${gap.endTime}")
                Result.success(Unit)
            } else {
                val errorBody = response.errorBody()?.string() ?: "Unknown error"
                Log.e(TAG, "Report sync gap failed: ${response.code()} - $errorBody")
                Result.failure(Exception("Report sync gap failed: ${response.code()} - $errorBody"))
            }
        } catch (e: Exception) {
            Log.e(TAG, "Report sync gap exception", e)
            Result.failure(e)
        }
    }
    
    /**
     * Start heartbeat reporting
     */
    private fun startHeartbeat() {
        heartbeatJob?.cancel()
        heartbeatJob = apiScope.launch {
            while (isActive) {
                try {
                    val status = clientContextManager.getDeviceMetadata()?.let { metadata ->
                        DeviceStatusPayload(
                            clientId = clientContextManager.getClientId() ?: return@launch,
                            lastSync = TimestampUtils.formatTimestamp(clientContextManager.getLastSync()),
                            battery = metadata.battery,
                            connected = clientContextManager.isConnected.value,
                            firmware = metadata.firmware
                        )
                    }
                    
                    status?.let { deviceStatus ->
                        reportDeviceStatus(deviceStatus)
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Heartbeat failed", e)
                }
                
                delay(HEARTBEAT_INTERVAL_MS)
            }
        }
    }
    
    /**
     * Monitor queue size
     */
    private fun monitorQueueSize() {
        apiScope.launch {
            while (isActive) {
                try {
                    val size = syncQueueDao.getQueueSize()
                    _queueSize.value = size
                } catch (e: Exception) {
                    Log.e(TAG, "Error monitoring queue size", e)
                }
                delay(5000) // Check every 5 seconds
            }
        }
    }
    
    /**
     * Clean up old queue items
     */
    suspend fun cleanupOldQueueItems() {
        try {
            val cutoffTime = System.currentTimeMillis() - (7 * 24 * 60 * 60 * 1000L) // 7 days
            syncQueueDao.deleteOldItems(cutoffTime)
            Log.d(TAG, "Cleaned up old queue items")
        } catch (e: Exception) {
            Log.e(TAG, "Error cleaning up old queue items", e)
        }
    }
    
    /**
     * Get queue statistics
     */
    suspend fun getQueueStats(): QueueStats {
        return try {
            val totalSize = syncQueueDao.getQueueSize()
            val failedCount = syncQueueDao.getFailedItemsCount()
            val oldestTimestamp = syncQueueDao.getOldestItemTimestamp()
            val newestTimestamp = syncQueueDao.getNewestItemTimestamp()
            
            QueueStats(
                totalItems = totalSize,
                failedItems = failedCount,
                oldestTimestamp = oldestTimestamp,
                newestTimestamp = newestTimestamp
            )
        } catch (e: Exception) {
            Log.e(TAG, "Error getting queue stats", e)
            QueueStats(0, 0, null, null)
        }
    }
    
    data class QueueStats(
        val totalItems: Int,
        val failedItems: Int,
        val oldestTimestamp: Long?,
        val newestTimestamp: Long?
    )
    
    /**
     * Cleanup
     */
    fun cleanup() {
        heartbeatJob?.cancel()
        apiScope.cancel()
    }
} 