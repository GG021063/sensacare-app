package com.sensacare.veepoo.rpm

import com.sensacare.veepoo.VitalsData
import java.util.concurrent.TimeUnit

/**
 * Non-clinical status evaluator for device-side sanity checks only.
 * Clinical logic and alerting is handled by the RPM system.
 */
class VitalsStatusEvaluator {
    
    companion object {
        private const val MAX_SENSOR_DISCONNECTION_TIME_MS = TimeUnit.HOURS.toMillis(1) // 1 hour
        private const val DATA_FRESHNESS_THRESHOLD_MS = TimeUnit.MINUTES.toMillis(5) // 5 minutes
    }
    
    /**
     * Evaluate vitals status for device-side display only
     */
    fun evaluateStatus(
        vitalsData: VitalsData?,
        lastDataTimestamp: Long?,
        isConnected: Boolean,
        connectionDuration: Long = 0L
    ): VitalsStatus {
        
        // Check for sensor disconnection
        if (!isConnected) {
            return VitalsStatus.BLE_ERROR
        }
        
        // Check for data freshness
        if (lastDataTimestamp == null || isDataStale(lastDataTimestamp)) {
            return VitalsStatus.NO_DATA
        }
        
        // Check for critical sensor errors
        if (vitalsData != null && hasCriticalSensorError(vitalsData)) {
            return VitalsStatus.CRITICAL_ERROR
        }
        
        // Check for sensor disconnection timeout
        if (connectionDuration > MAX_SENSOR_DISCONNECTION_TIME_MS) {
            return VitalsStatus.BLE_ERROR
        }
        
        return VitalsStatus.NORMAL
    }
    
    /**
     * Check if data is stale (older than threshold)
     */
    private fun isDataStale(timestamp: Long): Boolean {
        val currentTime = System.currentTimeMillis()
        return (currentTime - timestamp) > DATA_FRESHNESS_THRESHOLD_MS
    }
    
    /**
     * Check for critical sensor errors that indicate hardware issues
     */
    private fun hasCriticalSensorError(vitalsData: VitalsData): Boolean {
        // Heart rate = 0 indicates sensor error
        if (vitalsData.heartRate == 0) {
            return true
        }
        
        // SpO2 = 0 indicates sensor error
        if (vitalsData.spo2 == 0) {
            return true
        }
        
        // Temperature = 0 indicates sensor error
        if (vitalsData.temperature == 0.0f) {
            return true
        }
        
        // All vitals null indicates sensor not reading
        if (vitalsData.heartRate == null && 
            vitalsData.bloodPressureSystolic == null && 
            vitalsData.bloodPressureDiastolic == null && 
            vitalsData.spo2 == null && 
            vitalsData.temperature == null) {
            return true
        }
        
        return false
    }
    
    /**
     * Get status description for UI display
     */
    fun getStatusDescription(status: VitalsStatus): String {
        return when (status) {
            VitalsStatus.NORMAL -> "Normal"
            VitalsStatus.NO_DATA -> "No recent data"
            VitalsStatus.CRITICAL_ERROR -> "Sensor error detected"
            VitalsStatus.BLE_ERROR -> "Device disconnected"
        }
    }
    
    /**
     * Get status color for UI display
     */
    fun getStatusColor(status: VitalsStatus): String {
        return when (status) {
            VitalsStatus.NORMAL -> "#28A745" // Green
            VitalsStatus.NO_DATA -> "#FFC107" // Yellow
            VitalsStatus.CRITICAL_ERROR -> "#DC3545" // Red
            VitalsStatus.BLE_ERROR -> "#DC3545" // Red
        }
    }
    
    /**
     * Check if status requires user attention
     */
    fun requiresAttention(status: VitalsStatus): Boolean {
        return status == VitalsStatus.CRITICAL_ERROR || status == VitalsStatus.BLE_ERROR
    }
    
    /**
     * Get recommended action for status
     */
    fun getRecommendedAction(status: VitalsStatus): String? {
        return when (status) {
            VitalsStatus.NORMAL -> null
            VitalsStatus.NO_DATA -> "Check device connection"
            VitalsStatus.CRITICAL_ERROR -> "Contact support - sensor error detected"
            VitalsStatus.BLE_ERROR -> "Reconnect device"
        }
    }
}

/**
 * Vitals status enum for device-side display
 */
enum class VitalsStatus {
    NORMAL,           // Everything working normally
    NO_DATA,          // No recent data from sensor
    CRITICAL_ERROR,   // Critical sensor error detected
    BLE_ERROR         // Bluetooth connection issue
} 