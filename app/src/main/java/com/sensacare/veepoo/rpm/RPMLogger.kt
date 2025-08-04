package com.sensacare.veepoo.rpm

import android.content.Context
import android.util.Log
import java.io.File
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ConcurrentLinkedQueue

/**
 * Comprehensive logging utility for RPM debugging and production monitoring
 * Provides structured logging, file output, and log rotation
 */
class RPMLogger(private val context: Context) {
    
    companion object {
        private const val TAG = "RPMLogger"
        private const val MAX_LOG_FILE_SIZE = 10 * 1024 * 1024L // 10MB
        private const val MAX_LOG_FILES = 5
        private const val LOG_BUFFER_SIZE = 1000
    }
    
    enum class LogLevel {
        DEBUG, INFO, WARNING, ERROR, CRITICAL
    }
    
    enum class LogCategory {
        RPM_INIT, VITALS_PROCESSING, API_COMMUNICATION, DEVICE_CONNECTION, 
        CONSENT_MANAGEMENT, TIMELINE_DATA, SYNC_QUEUE, ALERTS, PERFORMANCE
    }
    
    private val logQueue = ConcurrentLinkedQueue<LogEntry>()
    private val dateFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.US)
    private val fileDateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    
    private var isFileLoggingEnabled = true
    private var isDebugMode = false
    
    init {
        // Start log processing
        startLogProcessor()
    }
    
    /**
     * Log a debug message
     */
    fun debug(category: LogCategory, message: String, data: Map<String, Any>? = null) {
        if (isDebugMode) {
            log(LogLevel.DEBUG, category, message, data)
        }
    }
    
    /**
     * Log an info message
     */
    fun info(category: LogCategory, message: String, data: Map<String, Any>? = null) {
        log(LogLevel.INFO, category, message, data)
    }
    
    /**
     * Log a warning message
     */
    fun warning(category: LogCategory, message: String, data: Map<String, Any>? = null) {
        log(LogLevel.WARNING, category, message, data)
    }
    
    /**
     * Log an error message
     */
    fun error(category: LogCategory, message: String, exception: Throwable? = null, data: Map<String, Any>? = null) {
        log(LogLevel.ERROR, category, message, data, exception)
    }
    
    /**
     * Log a critical message
     */
    fun critical(category: LogCategory, message: String, exception: Throwable? = null, data: Map<String, Any>? = null) {
        log(LogLevel.CRITICAL, category, message, data, exception)
    }
    
    /**
     * Log RPM initialization
     */
    fun logRPMInit(clientId: String, success: Boolean, errorMessage: String? = null) {
        val data = mapOf(
            "clientId" to clientId,
            "success" to success,
            "timestamp" to System.currentTimeMillis()
        )
        
        if (success) {
            info(LogCategory.RPM_INIT, "RPM initialized successfully for client: $clientId", data)
        } else {
            error(LogCategory.RPM_INIT, "RPM initialization failed for client: $clientId", null, data + ("errorMessage" to (errorMessage ?: "Unknown error")))
        }
    }
    
    /**
     * Log vitals processing
     */
    fun logVitalsProcessing(vitalsData: com.sensacare.veepoo.rpm.VitalsData, success: Boolean, processingTime: Long) {
        val data = mapOf(
            "heartRate" to (vitalsData.heartRate ?: "null"),
            "spo2" to (vitalsData.spo2 ?: "null"),
            "success" to success,
            "processingTimeMs" to processingTime,
            "timestamp" to System.currentTimeMillis()
        )
        
        if (success) {
            info(LogCategory.VITALS_PROCESSING, "Vitals processed successfully", data)
        } else {
            error(LogCategory.VITALS_PROCESSING, "Vitals processing failed", null, data)
        }
    }
    
    /**
     * Log API communication
     */
    fun logAPICall(endpoint: String, method: String, success: Boolean, responseTime: Long, statusCode: Int? = null) {
        val data = mapOf(
            "endpoint" to endpoint,
            "method" to method,
            "success" to success,
            "responseTimeMs" to responseTime,
            "statusCode" to (statusCode ?: "null"),
            "timestamp" to System.currentTimeMillis()
        )
        
        if (success) {
            info(LogCategory.API_COMMUNICATION, "API call successful: $method $endpoint", data)
        } else {
            error(LogCategory.API_COMMUNICATION, "API call failed: $method $endpoint", null, data)
        }
    }
    
    /**
     * Log device connection events
     */
    fun logDeviceConnection(deviceId: String, connected: Boolean, errorMessage: String? = null) {
        val data = mapOf(
            "deviceId" to deviceId,
            "connected" to connected,
            "timestamp" to System.currentTimeMillis()
        )
        
        if (connected) {
            info(LogCategory.DEVICE_CONNECTION, "Device connected: $deviceId", data)
        } else {
            error(LogCategory.DEVICE_CONNECTION, "Device disconnected: $deviceId", null, data + ("errorMessage" to (errorMessage ?: "Unknown error")))
        }
    }
    
    /**
     * Log consent management events
     */
    fun logConsentCapture(clientId: String, consentType: String, success: Boolean) {
        val data = mapOf(
            "clientId" to clientId,
            "consentType" to consentType,
            "success" to success,
            "timestamp" to System.currentTimeMillis()
        )
        
        if (success) {
            info(LogCategory.CONSENT_MANAGEMENT, "Consent captured successfully for client: $clientId", data)
        } else {
            error(LogCategory.CONSENT_MANAGEMENT, "Consent capture failed for client: $clientId", null, data)
        }
    }
    
    /**
     * Log timeline data events
     */
    fun logTimelineDataLoad(range: String, entryCount: Int, loadTime: Long, success: Boolean) {
        val data = mapOf(
            "range" to range,
            "entryCount" to entryCount,
            "loadTimeMs" to loadTime,
            "success" to success,
            "timestamp" to System.currentTimeMillis()
        )
        
        if (success) {
            info(LogCategory.TIMELINE_DATA, "Timeline data loaded: $entryCount entries for range $range", data)
        } else {
            error(LogCategory.TIMELINE_DATA, "Timeline data load failed for range: $range", null, data)
        }
    }
    
    /**
     * Log sync queue events
     */
    fun logSyncQueue(queueSize: Int, processedCount: Int, failedCount: Int) {
        val data = mapOf(
            "queueSize" to queueSize,
            "processedCount" to processedCount,
            "failedCount" to failedCount,
            "timestamp" to System.currentTimeMillis()
        )
        
        info(LogCategory.SYNC_QUEUE, "Sync queue processed: $processedCount items, $failedCount failed", data)
    }
    
    /**
     * Log alert events
     */
    fun logAlertReceived(alertId: String, severity: String, message: String) {
        val data = mapOf(
            "alertId" to alertId,
            "severity" to severity,
            "message" to message,
            "timestamp" to System.currentTimeMillis()
        )
        
        info(LogCategory.ALERTS, "Alert received: $severity - $message", data)
    }
    
    /**
     * Log performance metrics
     */
    fun logPerformance(metric: String, value: Long, unit: String = "ms") {
        val data = mapOf(
            "metric" to metric,
            "value" to value,
            "unit" to unit,
            "timestamp" to System.currentTimeMillis()
        )
        
        info(LogCategory.PERFORMANCE, "Performance metric: $metric = $value$unit", data)
    }
    
    /**
     * Core logging method
     */
    private fun log(
        level: LogLevel,
        category: LogCategory,
        message: String,
        data: Map<String, Any>? = null,
        exception: Throwable? = null
    ) {
        val logEntry = LogEntry(
            timestamp = System.currentTimeMillis(),
            level = level,
            category = category,
            message = message,
            data = data,
            exception = exception
        )
        
        // Add to queue
        logQueue.offer(logEntry)
        
        // Also log to Android logcat
        val logcatMessage = formatLogcatMessage(logEntry)
        when (level) {
            LogLevel.DEBUG -> Log.d(TAG, logcatMessage)
            LogLevel.INFO -> Log.i(TAG, logcatMessage)
            LogLevel.WARNING -> Log.w(TAG, logcatMessage)
            LogLevel.ERROR -> Log.e(TAG, logcatMessage, exception)
            LogLevel.CRITICAL -> Log.e(TAG, "CRITICAL: $logcatMessage", exception)
        }
    }
    
    /**
     * Format message for logcat
     */
    private fun formatLogcatMessage(entry: LogEntry): String {
        val timestamp = dateFormatter.format(Date(entry.timestamp))
        val dataString = entry.data?.entries?.joinToString(", ") { "${it.key}=${it.value}" } ?: ""
        return "[${entry.category}] $timestamp - ${entry.message} $dataString".trim()
    }
    
    /**
     * Start log processor for file output
     */
    private fun startLogProcessor() {
        Thread {
            while (true) {
                try {
                    val entry = logQueue.poll()
                    if (entry != null) {
                        writeToFile(entry)
                    } else {
                        Thread.sleep(100) // Wait for more logs
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Error in log processor", e)
                }
            }
        }.apply {
            isDaemon = true
            start()
        }
    }
    
    /**
     * Write log entry to file
     */
    private fun writeToFile(entry: LogEntry) {
        if (!isFileLoggingEnabled) return
        
        try {
            val logFile = getLogFile()
            if (logFile.length() > MAX_LOG_FILE_SIZE) {
                rotateLogFiles()
            }
            
            FileWriter(logFile, true).use { writer ->
                val formattedEntry = formatFileEntry(entry)
                writer.write(formattedEntry + "\n")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error writing to log file", e)
        }
    }
    
    /**
     * Format log entry for file output
     */
    private fun formatFileEntry(entry: LogEntry): String {
        val timestamp = dateFormatter.format(Date(entry.timestamp))
        val dataString = entry.data?.entries?.joinToString("|") { "${it.key}=${it.value}" } ?: ""
        val exceptionString = entry.exception?.let { " | Exception: ${it.message}" } ?: ""
        
        return "$timestamp | ${entry.level} | ${entry.category} | ${entry.message} | $dataString$exceptionString"
    }
    
    /**
     * Get current log file
     */
    private fun getLogFile(): File {
        val today = fileDateFormatter.format(Date())
        val logDir = File(context.filesDir, "rpm_logs")
        if (!logDir.exists()) {
            logDir.mkdirs()
        }
        return File(logDir, "rpm_$today.log")
    }
    
    /**
     * Rotate log files
     */
    private fun rotateLogFiles() {
        val logDir = File(context.filesDir, "rpm_logs")
        val logFiles = logDir.listFiles { file -> file.name.startsWith("rpm_") && file.name.endsWith(".log") }
            ?.sortedBy { it.lastModified() }
            ?: return
        
        // Remove oldest files if we have too many
        while (logFiles.size >= MAX_LOG_FILES) {
            logFiles.firstOrNull()?.delete()
        }
    }
    
    /**
     * Enable/disable debug mode
     */
    fun setDebugMode(enabled: Boolean) {
        isDebugMode = enabled
        info(LogCategory.RPM_INIT, "Debug mode ${if (enabled) "enabled" else "disabled"}")
    }
    
    /**
     * Enable/disable file logging
     */
    fun setFileLoggingEnabled(enabled: Boolean) {
        isFileLoggingEnabled = enabled
        info(LogCategory.RPM_INIT, "File logging ${if (enabled) "enabled" else "disabled"}")
    }
    
    /**
     * Get log statistics
     */
    fun getLogStatistics(): LogStatistics {
        val logFile = getLogFile()
        return LogStatistics(
            totalLogEntries = logQueue.size,
            logFileSize = if (logFile.exists()) logFile.length() else 0,
            logFileExists = logFile.exists()
        )
    }
    
    /**
     * Clear all log files
     */
    fun clearLogs() {
        val logDir = File(context.filesDir, "rpm_logs")
        logDir.listFiles()?.forEach { it.delete() }
        logQueue.clear()
        info(LogCategory.RPM_INIT, "All logs cleared")
    }
    
    /**
     * Data class for log entries
     */
    private data class LogEntry(
        val timestamp: Long,
        val level: LogLevel,
        val category: LogCategory,
        val message: String,
        val data: Map<String, Any>?,
        val exception: Throwable?
    )
    
    /**
     * Data class for log statistics
     */
    data class LogStatistics(
        val totalLogEntries: Int,
        val logFileSize: Long,
        val logFileExists: Boolean
    )
} 