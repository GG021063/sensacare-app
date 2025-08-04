package com.sensacare.veepoo.rpm

import android.bluetooth.BluetoothDevice
import com.sensacare.veepoo.VitalsData as AppVitalsData
import com.sensacare.veepoo.VitalsEntity

class VitalsPayloadBuilder(
    private val clientContextManager: RPMClientContextManager
) {
    
    companion object {
        private const val DEFAULT_CONFIDENCE = 0.95
        private const val MIN_CONFIDENCE = 0.5
        private const val MAX_CONFIDENCE = 1.0
        
        // Confidence factors
        private const val RSSI_WEIGHT = 0.3
        private const val DATA_COMPLETENESS_WEIGHT = 0.4
        private const val TIMESTAMP_FRESHNESS_WEIGHT = 0.3
    }
    
    /**
     * Build RPM-ready payload from VitalsData
     */
    fun buildPayload(
        vitalsData: AppVitalsData,
        device: BluetoothDevice?,
        rssi: Int?,
        timestamp: Long = System.currentTimeMillis()
    ): VitalsUploadPayload? {
        val clientId = clientContextManager.getClientId() ?: return null
        val consentToken = clientContextManager.getConsentToken() ?: return null
        
        val deviceMetadata = buildDeviceMetadata(device)
        val confidence = calculateConfidence(vitalsData, rssi, timestamp)
        
        return VitalsUploadPayload(
            timestamp = TimestampUtils.formatTimestamp(timestamp),
            clientId = clientId,
            device = deviceMetadata,
            vitals = convertToRPMVitals(vitalsData),
            confidence = confidence,
            consentToken = consentToken
        )
    }
    
    /**
     * Build RPM-ready payload from VitalsEntity (for sync queue)
     */
    fun buildPayloadFromEntity(
        entity: VitalsEntity,
        device: BluetoothDevice?
    ): VitalsUploadPayload? {
        val clientId = clientContextManager.getClientId() ?: return null
        val consentToken = clientContextManager.getConsentToken() ?: return null
        
        val deviceMetadata = buildDeviceMetadata(device)
        val confidence = calculateConfidenceFromEntity(entity)
        
        return VitalsUploadPayload(
            timestamp = TimestampUtils.formatTimestamp(entity.timestamp),
            clientId = clientId,
            device = deviceMetadata,
            vitals = convertEntityToRPMVitals(entity),
            confidence = confidence,
            consentToken = consentToken
        )
    }
    
    /**
     * Build device status payload for heartbeat / connectivity reporting
     */
    fun buildDeviceStatusPayload(
        connected: Boolean,
        battery: Int? = null,
        firmware: String? = null
    ): DeviceStatusPayload? {
        val clientId = clientContextManager.getClientId() ?: return null
        val deviceMetadata = clientContextManager.getDeviceMetadata()

        return DeviceStatusPayload(
            clientId = clientId,
            lastSync = TimestampUtils.formatTimestamp(clientContextManager.getLastSync()),
            battery = battery ?: deviceMetadata?.battery ?: 0,
            connected = connected,
            firmware = firmware ?: deviceMetadata?.firmware ?: "unknown"
        )
    }

    /**
     * Build sync gap report
     */
    fun buildSyncGapReport(
        startTime: Long,
        endTime: Long,
        reason: String,
        vitalsCount: Int
    ): SyncGapReport? {
        val clientId = clientContextManager.getClientId() ?: return null
        
        return SyncGapReport(
            startTime = TimestampUtils.formatTimestamp(startTime),
            endTime = TimestampUtils.formatTimestamp(endTime),
            reason = reason,
            vitalsCount = vitalsCount
        )
    }
    
    private fun buildDeviceMetadata(device: BluetoothDevice?): DeviceMetadata {
        val deviceMetadata = clientContextManager.getDeviceMetadata()
        
        return DeviceMetadata(
            type = deviceMetadata?.type ?: "veepoo_ring",
            mac = device?.address ?: deviceMetadata?.mac ?: "unknown",
            firmware = deviceMetadata?.firmware ?: "unknown",
            battery = deviceMetadata?.battery ?: 0,
            sdkVersion = deviceMetadata?.sdkVersion ?: "veepoo_sdk_1.0"
        )
    }
    
    /**
     * Convert application-layer vitals model to the RPM wire-format model that
     * the backend expects.  Only field names differ, so we copy values 1-to-1.
     */
    private fun convertToRPMVitals(vitalsData: AppVitalsData): VitalsData {
        return VitalsData(
            heartRate = vitalsData.heartRate,
            bloodPressureSystolic = vitalsData.bloodPressureSystolic,
            bloodPressureDiastolic = vitalsData.bloodPressureDiastolic,
            spo2 = vitalsData.spo2,
            temperature = vitalsData.temperature
        )
    }
    
    private fun convertEntityToRPMVitals(entity: VitalsEntity): VitalsData {
        return VitalsData(
            heartRate = entity.heartRate,
            bloodPressureSystolic = entity.bloodPressureSystolic,
            bloodPressureDiastolic = entity.bloodPressureDiastolic,
            spo2 = entity.spo2,
            temperature = entity.temperature
        )
    }
    
    /**
     * Calculate confidence score based on multiple factors
     */
    private fun calculateConfidence(
        vitalsData: AppVitalsData,
        rssi: Int?,
        timestamp: Long
    ): Double {
        val rssiConfidence = calculateRssiConfidence(rssi)
        val dataCompleteness = calculateDataCompleteness(vitalsData)
        val timestampFreshness = calculateTimestampFreshness(timestamp)
        
        val confidence = (rssiConfidence * RSSI_WEIGHT) +
                (dataCompleteness * DATA_COMPLETENESS_WEIGHT) +
                (timestampFreshness * TIMESTAMP_FRESHNESS_WEIGHT)
        
        return confidence.coerceIn(MIN_CONFIDENCE, MAX_CONFIDENCE)
    }
    
    private fun calculateConfidenceFromEntity(entity: VitalsEntity): Double {
        val vitalsData = AppVitalsData(
            heartRate = entity.heartRate,
            bloodPressureSystolic = entity.bloodPressureSystolic,
            bloodPressureDiastolic = entity.bloodPressureDiastolic,
            spo2 = entity.spo2,
            temperature = entity.temperature
        )
        
        return calculateConfidence(vitalsData, null, entity.timestamp)
    }
    
    /**
     * Calculate confidence based on RSSI signal strength
     */
    private fun calculateRssiConfidence(rssi: Int?): Double {
        if (rssi == null) return DEFAULT_CONFIDENCE
        
        return when {
            rssi >= -50 -> 1.0      // Excellent signal
            rssi >= -60 -> 0.95     // Very good signal
            rssi >= -70 -> 0.9      // Good signal
            rssi >= -80 -> 0.8      // Fair signal
            rssi >= -90 -> 0.7      // Poor signal
            else -> 0.5             // Very poor signal
        }
    }
    
    /**
     * Calculate confidence based on data completeness
     */
    private fun calculateDataCompleteness(vitalsData: AppVitalsData): Double {
        val totalFields = 5
        val presentFields = listOfNotNull(
            vitalsData.heartRate,
            vitalsData.bloodPressureSystolic,
            vitalsData.bloodPressureDiastolic,
            vitalsData.spo2,
            vitalsData.temperature
        ).size
        
        return presentFields.toDouble() / totalFields
    }
    
    /**
     * Calculate confidence based on timestamp freshness
     */
    private fun calculateTimestampFreshness(timestamp: Long): Double {
        val currentTime = System.currentTimeMillis()
        val ageMs = currentTime - timestamp
        val ageSeconds = ageMs / 1000.0
        
        return when {
            ageSeconds <= 30 -> 1.0      // Very fresh (≤30s)
            ageSeconds <= 60 -> 0.95     // Fresh (≤1min)
            ageSeconds <= 300 -> 0.9     // Recent (≤5min)
            ageSeconds <= 600 -> 0.8     // Somewhat old (≤10min)
            ageSeconds <= 1800 -> 0.7    // Old (≤30min)
            else -> 0.5                  // Very old (>30min)
        }
    }
    
    /**
     * Validate vitals data for RPM upload
     */
    fun validateVitalsForUpload(vitalsData: AppVitalsData): ValidationResult {
        val errors = mutableListOf<String>()
        val warnings = mutableListOf<String>()
        
        // Check for completely missing data
        if (vitalsData.heartRate == null && 
            vitalsData.bloodPressureSystolic == null && 
            vitalsData.spo2 == null && 
            vitalsData.temperature == null) {
            errors.add("No vitals data available")
        }
        
        // Check for extreme values that might indicate sensor errors
        vitalsData.heartRate?.let { hr ->
            when {
                hr <= 0 -> errors.add("Invalid heart rate: $hr")
                hr > 250 -> warnings.add("Unusually high heart rate: $hr")
                hr < 30 -> warnings.add("Unusually low heart rate: $hr")
                else -> { /* Heart-rate value within normal range */ }
            }
        }
        
        vitalsData.spo2?.let { spo2 ->
            when {
                spo2 < 70 -> errors.add("Invalid SpO2: $spo2%")
                spo2 > 100 -> errors.add("Invalid SpO2: $spo2%")
                spo2 < 90 -> warnings.add("Low SpO2: $spo2%")
                else -> { /* SpO₂ value within normal range */ }
            }
        }
        
        vitalsData.temperature?.let { temp ->
            when {
                temp < 30.0 -> errors.add("Invalid temperature: ${temp}°C")
                temp > 45.0 -> errors.add("Invalid temperature: ${temp}°C")
                temp > 40.0 -> warnings.add("High temperature: ${temp}°C")
                else -> { /* Temperature value within normal range */ }
            }
        }
        
        return ValidationResult(
            isValid = errors.isEmpty(),
            errors = errors,
            warnings = warnings
        )
    }
    
    data class ValidationResult(
        val isValid: Boolean,
        val errors: List<String>,
        val warnings: List<String>
    )
} 