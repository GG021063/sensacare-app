package com.sensacare.veepoo

import android.content.Context
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ConcurrentLinkedQueue

class DiagnosticLogger private constructor(private val context: Context) {
    companion object {
        private const val TAG = "DiagnosticLogger"
        private const val MAX_LOG_ENTRIES = 1000
        private const val DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS"
        
        @Volatile
        private var INSTANCE: DiagnosticLogger? = null
        
        fun getInstance(context: Context): DiagnosticLogger {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: DiagnosticLogger(context).also { INSTANCE = it }
            }
        }
    }

    private val logQueue = ConcurrentLinkedQueue<LogEntry>()
    private val dateFormatter = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
    private var isDebugModeEnabled = false

    data class LogEntry(
        val timestamp: Long,
        val timestampFormatted: String,
        val level: LogLevel,
        val category: LogCategory,
        val message: String,
        val details: String? = null
    )

    enum class LogLevel {
        DEBUG, INFO, WARNING, ERROR
    }

    enum class LogCategory {
        BLE_SCAN, BLE_CONNECTION, BLE_CHARACTERISTIC, ROOM_DATABASE, API_CALL, SECURITY, GENERAL
    }

    fun setDebugMode(enabled: Boolean) {
        isDebugModeEnabled = enabled
        if (enabled) {
            log(LogLevel.INFO, LogCategory.GENERAL, "Debug mode enabled")
        } else {
            log(LogLevel.INFO, LogCategory.GENERAL, "Debug mode disabled")
        }
    }

    fun isDebugModeEnabled(): Boolean = isDebugModeEnabled

    // BLE Scanning Logs
    fun logBleScan(deviceName: String?, deviceAddress: String, rssi: Int, scanRecord: ByteArray?) {
        if (!isDebugModeEnabled) return
        
        val message = "BLE Scan Result"
        val details = buildString {
            appendLine("Device: ${deviceName ?: "Unknown"} ($deviceAddress)")
            appendLine("RSSI: $rssi dBm")
            if (scanRecord != null) {
                appendLine("Scan Record: ${bytesToHex(scanRecord)}")
            }
        }
        
        log(LogLevel.DEBUG, LogCategory.BLE_SCAN, message, details)
    }

    fun logBleScanStart() {
        if (!isDebugModeEnabled) return
        log(LogLevel.INFO, LogCategory.BLE_SCAN, "BLE scanning started")
    }

    fun logBleScanStop() {
        if (!isDebugModeEnabled) return
        log(LogLevel.INFO, LogCategory.BLE_SCAN, "BLE scanning stopped")
    }

    fun logBleScanFailed(errorCode: Int) {
        if (!isDebugModeEnabled) return
        val errorMessage = when (errorCode) {
            1 -> "SCAN_FAILED_ALREADY_STARTED"
            2 -> "SCAN_FAILED_APPLICATION_REGISTRATION_FAILED"
            3 -> "SCAN_FAILED_FEATURE_UNSUPPORTED"
            4 -> "SCAN_FAILED_INTERNAL_ERROR"
            5 -> "SCAN_FAILED_OUT_OF_HARDWARE_RESOURCES"
            else -> "Unknown error code: $errorCode"
        }
        log(LogLevel.ERROR, LogCategory.BLE_SCAN, "BLE scan failed: $errorMessage")
    }

    // BLE Connection Logs
    fun logBleConnectionAttempt(deviceAddress: String) {
        if (!isDebugModeEnabled) return
        log(LogLevel.INFO, LogCategory.BLE_CONNECTION, "Attempting to connect to device: $deviceAddress")
    }

    fun logBleConnectionStateChange(deviceAddress: String, oldState: Int, newState: Int) {
        if (!isDebugModeEnabled) return
        
        val oldStateStr = getConnectionStateString(oldState)
        val newStateStr = getConnectionStateString(newState)
        
        val message = "BLE Connection State Change"
        val details = buildString {
            appendLine("Device: $deviceAddress")
            appendLine("Old State: $oldStateStr ($oldState)")
            appendLine("New State: $newStateStr ($newState)")
        }
        
        log(LogLevel.INFO, LogCategory.BLE_CONNECTION, message, details)
    }

    fun logBleConnectionSuccess(deviceAddress: String) {
        if (!isDebugModeEnabled) return
        log(LogLevel.INFO, LogCategory.BLE_CONNECTION, "Successfully connected to device: $deviceAddress")
    }

    fun logBleConnectionFailure(deviceAddress: String, error: String) {
        if (!isDebugModeEnabled) return
        log(LogLevel.ERROR, LogCategory.BLE_CONNECTION, "Failed to connect to device: $deviceAddress", "Error: $error")
    }

    fun logBleDisconnection(deviceAddress: String, reason: String) {
        if (!isDebugModeEnabled) return
        log(LogLevel.WARNING, LogCategory.BLE_CONNECTION, "Device disconnected: $deviceAddress", "Reason: $reason")
    }

    // BLE Characteristic Logs
    fun logBleServiceDiscovery(deviceAddress: String, services: List<String>) {
        if (!isDebugModeEnabled) return
        
        val message = "BLE Services Discovered"
        val details = buildString {
            appendLine("Device: $deviceAddress")
            appendLine("Services found: ${services.size}")
            services.forEach { service ->
                appendLine("  - $service")
            }
        }
        
        log(LogLevel.DEBUG, LogCategory.BLE_CHARACTERISTIC, message, details)
    }

    fun logBleCharacteristicRead(deviceAddress: String, serviceUuid: String, characteristicUuid: String, value: ByteArray) {
        if (!isDebugModeEnabled) return
        
        val message = "BLE Characteristic Read"
        val details = buildString {
            appendLine("Device: $deviceAddress")
            appendLine("Service: $serviceUuid")
            appendLine("Characteristic: $characteristicUuid")
            appendLine("Value: ${bytesToHex(value)}")
            appendLine("Value (ASCII): ${String(value)}")
        }
        
        log(LogLevel.DEBUG, LogCategory.BLE_CHARACTERISTIC, message, details)
    }

    fun logBleCharacteristicWrite(deviceAddress: String, serviceUuid: String, characteristicUuid: String, value: ByteArray) {
        if (!isDebugModeEnabled) return
        
        val message = "BLE Characteristic Write"
        val details = buildString {
            appendLine("Device: $deviceAddress")
            appendLine("Service: $serviceUuid")
            appendLine("Characteristic: $characteristicUuid")
            appendLine("Value: ${bytesToHex(value)}")
        }
        
        log(LogLevel.DEBUG, LogCategory.BLE_CHARACTERISTIC, message, details)
    }

    fun logBleCharacteristicChanged(deviceAddress: String, serviceUuid: String, characteristicUuid: String, value: ByteArray) {
        if (!isDebugModeEnabled) return
        
        val message = "BLE Characteristic Changed"
        val details = buildString {
            appendLine("Device: $deviceAddress")
            appendLine("Service: $serviceUuid")
            appendLine("Characteristic: $characteristicUuid")
            appendLine("Value: ${bytesToHex(value)}")
            appendLine("Value (ASCII): ${String(value)}")
        }
        
        log(LogLevel.DEBUG, LogCategory.BLE_CHARACTERISTIC, message, details)
    }

    fun logVitalsParsed(vitalsData: VitalsData) {
        if (!isDebugModeEnabled) return
        
        val message = "Vitals Data Parsed"
        val details = buildString {
            appendLine("Heart Rate: ${vitalsData.heartRate} bpm")
            appendLine("Blood Pressure: ${vitalsData.bloodPressureSystolic}/${vitalsData.bloodPressureDiastolic} mmHg")
            appendLine("SpO2: ${vitalsData.spo2}%")
            appendLine("Temperature: ${vitalsData.temperature}°C")
            appendLine("Timestamp: ${dateFormatter.format(Date(vitalsData.timestamp))}")
        }
        
        log(LogLevel.INFO, LogCategory.BLE_CHARACTERISTIC, message, details)
    }

    // Room Database Logs
    fun logRoomInsert(entity: VitalsEntity) {
        if (!isDebugModeEnabled) return
        
        val message = "Room Database Insert"
        val details = buildString {
            appendLine("ID: ${entity.id}")
            appendLine("Heart Rate: ${entity.heartRate} bpm")
            appendLine("Blood Pressure: ${entity.bloodPressureSystolic}/${entity.bloodPressureDiastolic} mmHg")
            appendLine("SpO2: ${entity.spo2}%")
            appendLine("Temperature: ${entity.temperature}°C")
            appendLine("Timestamp: ${dateFormatter.format(Date(entity.timestamp))}")
        }
        
        log(LogLevel.DEBUG, LogCategory.ROOM_DATABASE, message, details)
    }

    fun logRoomQuery(query: String, resultCount: Int) {
        if (!isDebugModeEnabled) return
        log(LogLevel.DEBUG, LogCategory.ROOM_DATABASE, "Room Database Query", "Query: $query\nResult Count: $resultCount")
    }

    fun logRoomError(operation: String, error: String) {
        if (!isDebugModeEnabled) return
        log(LogLevel.ERROR, LogCategory.ROOM_DATABASE, "Room Database Error: $operation", "Error: $error")
    }

    // API Call Logs
    fun logApiRequest(url: String, method: String, headers: Map<String, String>?, body: String?) {
        if (!isDebugModeEnabled) return
        
        val message = "API Request"
        val details = buildString {
            appendLine("URL: $url")
            appendLine("Method: $method")
            if (headers != null) {
                appendLine("Headers:")
                headers.forEach { (key, value) ->
                    appendLine("  $key: $value")
                }
            }
            if (body != null) {
                appendLine("Body: $body")
            }
        }
        
        log(LogLevel.DEBUG, LogCategory.API_CALL, message, details)
    }

    fun logApiResponse(url: String, statusCode: Int, responseBody: String?, headers: Map<String, String>?) {
        if (!isDebugModeEnabled) return
        
        val message = "API Response"
        val details = buildString {
            appendLine("URL: $url")
            appendLine("Status Code: $statusCode")
            if (headers != null) {
                appendLine("Response Headers:")
                headers.forEach { (key, value) ->
                    appendLine("  $key: $value")
                }
            }
            if (responseBody != null) {
                appendLine("Response Body: $responseBody")
            }
        }
        
        val level = if (statusCode in 200..299) LogLevel.DEBUG else LogLevel.WARNING
        log(level, LogCategory.API_CALL, message, details)
    }

    fun logApiError(url: String, error: String) {
        if (!isDebugModeEnabled) return
        log(LogLevel.ERROR, LogCategory.API_CALL, "API Error: $url", "Error: $error")
    }

    // Security Logs
    fun logTokenRefresh(expired: Boolean) {
        if (!isDebugModeEnabled) return
        val message = if (expired) "Token expired, attempting refresh" else "Token refresh successful"
        log(LogLevel.INFO, LogCategory.SECURITY, message)
    }

    fun logAuthenticationSuccess(userId: String?) {
        if (!isDebugModeEnabled) return
        log(LogLevel.INFO, LogCategory.SECURITY, "Authentication successful", "User ID: $userId")
    }

    fun logAuthenticationFailure(error: String) {
        if (!isDebugModeEnabled) return
        log(LogLevel.ERROR, LogCategory.SECURITY, "Authentication failed", "Error: $error")
    }

    fun logOfflineQueueAdd(requestCount: Int) {
        if (!isDebugModeEnabled) return
        log(LogLevel.WARNING, LogCategory.API_CALL, "Request added to offline queue", "Queue size: $requestCount")
    }

    fun logOfflineQueueProcess(processedCount: Int, successCount: Int) {
        if (!isDebugModeEnabled) return
        log(LogLevel.INFO, LogCategory.API_CALL, "Offline queue processed", "Processed: $processedCount, Success: $successCount")
    }

    // General Logs
    fun logInfo(message: String, details: String? = null) {
        if (!isDebugModeEnabled) return
        log(LogLevel.INFO, LogCategory.GENERAL, message, details)
    }

    fun logWarning(message: String, details: String? = null) {
        if (!isDebugModeEnabled) return
        log(LogLevel.WARNING, LogCategory.GENERAL, message, details)
    }

    fun logError(message: String, details: String? = null) {
        if (!isDebugModeEnabled) return
        log(LogLevel.ERROR, LogCategory.GENERAL, message, details)
    }

    // Internal logging method
    private fun log(level: LogLevel, category: LogCategory, message: String, details: String? = null) {
        val timestamp = System.currentTimeMillis()
        val timestampFormatted = dateFormatter.format(Date(timestamp))
        
        val logEntry = LogEntry(timestamp, timestampFormatted, level, category, message, details)
        logQueue.offer(logEntry)
        
        // Limit queue size
        while (logQueue.size > MAX_LOG_ENTRIES) {
            logQueue.poll()
        }
        
        // Also log to Android LogCat
        val logMessage = buildString {
            append("[${category.name}] $message")
            if (details != null) {
                append("\n$details")
            }
        }
        
        when (level) {
            LogLevel.DEBUG -> Log.d(TAG, logMessage)
            LogLevel.INFO -> Log.i(TAG, logMessage)
            LogLevel.WARNING -> Log.w(TAG, logMessage)
            LogLevel.ERROR -> Log.e(TAG, logMessage)
        }
    }

    // Get all logs
    fun getLogs(): List<LogEntry> {
        return logQueue.toList().sortedBy { it.timestamp }
    }

    // Get logs by category
    fun getLogsByCategory(category: LogCategory): List<LogEntry> {
        return logQueue.filter { it.category == category }.sortedBy { it.timestamp }
    }

    // Get logs by level
    fun getLogsByLevel(level: LogLevel): List<LogEntry> {
        return logQueue.filter { it.level == level }.sortedBy { it.timestamp }
    }

    // Clear all logs
    fun clearLogs() {
        logQueue.clear()
        log(LogLevel.INFO, LogCategory.GENERAL, "Diagnostic logs cleared")
    }

    // Export logs as text
    fun exportLogsAsText(): String {
        return buildString {
            appendLine("=== Sensacare App Diagnostic Logs ===")
            appendLine("Generated: ${dateFormatter.format(Date())}")
            appendLine("Debug Mode: $isDebugModeEnabled")
            appendLine("Total Entries: ${logQueue.size}")
            appendLine()
            
            getLogs().forEach { entry ->
                appendLine("[${entry.timestampFormatted}] ${entry.level.name} [${entry.category.name}] ${entry.message}")
                if (entry.details != null) {
                    appendLine(entry.details)
                }
                appendLine()
            }
        }
    }

    // Utility methods
    private fun getConnectionStateString(state: Int): String {
        return when (state) {
            0 -> "DISCONNECTED"
            1 -> "CONNECTING"
            2 -> "CONNECTED"
            3 -> "DISCONNECTING"
            else -> "UNKNOWN($state)"
        }
    }

    private fun bytesToHex(bytes: ByteArray): String {
        return bytes.joinToString("") { "%02x".format(it) }
    }
} 