package com.sensacare.veepoo.rpm

import retrofit2.Response
import retrofit2.http.*

interface RPMApiService {
    
    // Upload vitals data to RPM
    @POST("clients/{clientId}/vitals")
    suspend fun uploadVitals(
        @Path("clientId") clientId: String,
        @Body payload: VitalsUploadPayload
    ): Response<UploadResponse>
    
    // Get recent alerts from RPM
    @GET("clients/{clientId}/alerts/recent")
    suspend fun getRecentAlerts(
        @Path("clientId") clientId: String,
        @Query("limit") limit: Int = 10
    ): Response<List<AlertData>>
    
    // Acknowledge an alert
    @POST("clients/{clientId}/alerts/{alertId}/acknowledge")
    suspend fun acknowledgeAlert(
        @Path("clientId") clientId: String,
        @Path("alertId") alertId: String
    ): Response<Unit>
    
    // Report device status (heartbeat)
    @POST("clients/{clientId}/device-status")
    suspend fun reportDeviceStatus(
        @Path("clientId") clientId: String,
        @Body status: DeviceStatusPayload
    ): Response<Unit>
    
    // Get client thresholds from RPM
    @GET("clients/{clientId}/thresholds")
    suspend fun getClientThresholds(
        @Path("clientId") clientId: String
    ): Response<ClientThresholds>
    
    // Get vitals timeline from RPM
    @GET("clients/{clientId}/vitals")
    suspend fun getVitalsTimeline(
        @Path("clientId") clientId: String,
        @Query("range") range: String = "24h",
        @Query("limit") limit: Int = 100
    ): Response<List<VitalsTimelineEntry>>
    
    // Validate client credentials
    @POST("clients/{clientId}/validate")
    suspend fun validateClient(
        @Path("clientId") clientId: String,
        @Body token: Map<String, String>
    ): Response<Map<String, Boolean>>
    
    // Report sync gaps to RPM
    @POST("clients/{clientId}/sync-gaps")
    suspend fun reportSyncGap(
        @Path("clientId") clientId: String,
        @Body gap: SyncGapReport
    ): Response<Unit>
}

// Sync gap reporting
data class SyncGapReport(
    val startTime: String, // ISO 8601 timestamp
    val endTime: String,   // ISO 8601 timestamp
    val reason: String,    // "network_error", "device_disconnected", "api_error"
    val vitalsCount: Int   // Number of vitals records affected
)

// RPM API configuration
object RPMConfig {
    const val BASE_URL = "https://api.sensacare.com/rpm/v1/"
    const val HEARTBEAT_INTERVAL_MS = 15 * 60 * 1000L // 15 minutes
    const val SYNC_RETRY_ATTEMPTS = 3
    const val SYNC_RETRY_DELAY_MS = 5000L
    const val MAX_SYNC_QUEUE_SIZE = 1000
} 