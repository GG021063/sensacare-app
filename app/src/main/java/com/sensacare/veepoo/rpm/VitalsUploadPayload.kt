package com.sensacare.veepoo.rpm

import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.*

data class VitalsUploadPayload(
    @SerializedName("timestamp")
    val timestamp: String, // ISO 8601 with timezone
    
    @SerializedName("client_id")
    val clientId: String,
    
    @SerializedName("device")
    val device: DeviceMetadata,
    
    @SerializedName("vitals")
    val vitals: VitalsData,
    
    @SerializedName("confidence")
    val confidence: Double,
    
    @SerializedName("consent_token")
    val consentToken: String,
    
    @SerializedName("source")
    val source: String = "android:veepoo" // Device SDK identifier
)

data class DeviceMetadata(
    @SerializedName("type")
    val type: String, // "veepoo_ring", "veepoo_watch", etc.
    
    @SerializedName("mac")
    val mac: String,
    
    @SerializedName("firmware")
    val firmware: String,
    
    @SerializedName("battery")
    val battery: Int,
    
    @SerializedName("sdk_version")
    val sdkVersion: String = "veepoo_sdk_1.0" // SDK version for tracking
)

data class VitalsData(
    @SerializedName("hr")
    val heartRate: Int?,
    
    @SerializedName("bp_systolic")
    val bloodPressureSystolic: Int?,
    
    @SerializedName("bp_diastolic")
    val bloodPressureDiastolic: Int?,
    
    @SerializedName("spo2")
    val spo2: Int?,
    
    @SerializedName("temperature")
    val temperature: Float?
)

data class UploadResponse(
    @SerializedName("success")
    val success: Boolean,
    
    @SerializedName("message")
    val message: String?,
    
    @SerializedName("vitals_id")
    val vitalsId: String?
)

// Device status payload for heartbeat reporting
data class DeviceStatusPayload(
    @SerializedName("client_id")
    val clientId: String,
    
    @SerializedName("last_sync")
    val lastSync: String, // ISO 8601 timestamp
    
    @SerializedName("battery")
    val battery: Int,
    
    @SerializedName("connected")
    val connected: Boolean,
    
    @SerializedName("firmware")
    val firmware: String,
    
    @SerializedName("sdk_version")
    val sdkVersion: String = "veepoo_sdk_1.0"
)

// Alert data from RPM
data class AlertData(
    @SerializedName("id")
    val id: String,
    
    @SerializedName("timestamp")
    val timestamp: String,
    
    @SerializedName("severity")
    val severity: String, // "critical", "warning", "info"
    
    @SerializedName("message")
    val message: String,
    
    @SerializedName("acknowledged")
    val acknowledged: Boolean
)

// Timeline entry from RPM
data class VitalsTimelineEntry(
    @SerializedName("timestamp")
    val timestamp: String,
    
    @SerializedName("vitals")
    val vitals: VitalsData?,
    
    @SerializedName("status")
    val status: String, // "synced", "gap", "alert"
    
    @SerializedName("alert_message")
    val alertMessage: String?
)

// Client thresholds from RPM
data class ClientThresholds(
    @SerializedName("heart_rate_min")
    val heartRateMin: Int?,
    
    @SerializedName("heart_rate_max")
    val heartRateMax: Int?,
    
    @SerializedName("spo2_min")
    val spo2Min: Int?,
    
    @SerializedName("blood_pressure_systolic_max")
    val bloodPressureSystolicMax: Int?,
    
    @SerializedName("blood_pressure_diastolic_max")
    val bloodPressureDiastolicMax: Int?,
    
    @SerializedName("temperature_max")
    val temperatureMax: Float?
)

// Utility class for timestamp formatting
object TimestampUtils {
    private val isoFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }
    
    fun formatTimestamp(timestamp: Long): String {
        return isoFormatter.format(Date(timestamp))
    }
    
    fun formatTimestampNow(): String {
        return formatTimestamp(System.currentTimeMillis())
    }
} 