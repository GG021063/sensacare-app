package com.sensacare.veepoo.rpm;

/**
 * Comprehensive logging utility for RPM debugging and production monitoring
 * Provides structured logging, file output, and log rotation
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000z\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010$\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\u001a\n\u0002\u0018\u0002\n\u0002\b\u000e\u0018\u0000 Q2\u00020\u0001:\u0005QRSTUB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u000e\u001a\u00020\u000fJ:\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u00162\u0016\b\u0002\u0010\u0017\u001a\u0010\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u0018J.\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0016\b\u0002\u0010\u0017\u001a\u0010\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u0018J:\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u00162\u0016\b\u0002\u0010\u0017\u001a\u0010\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u0018J\u0010\u0010\u001b\u001a\u00020\u00142\u0006\u0010\u001c\u001a\u00020\rH\u0002J\u0010\u0010\u001d\u001a\u00020\u00142\u0006\u0010\u001c\u001a\u00020\rH\u0002J\b\u0010\u001e\u001a\u00020\u001fH\u0002J\u0006\u0010 \u001a\u00020!J.\u0010\"\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0016\b\u0002\u0010\u0017\u001a\u0010\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u0018JD\u0010#\u001a\u00020\u000f2\u0006\u0010$\u001a\u00020%2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0016\b\u0002\u0010\u0017\u001a\u0010\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u00182\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0002J7\u0010&\u001a\u00020\u000f2\u0006\u0010\'\u001a\u00020\u00142\u0006\u0010(\u001a\u00020\u00142\u0006\u0010)\u001a\u00020\t2\u0006\u0010*\u001a\u00020+2\n\b\u0002\u0010,\u001a\u0004\u0018\u00010-\u00a2\u0006\u0002\u0010.J\u001e\u0010/\u001a\u00020\u000f2\u0006\u00100\u001a\u00020\u00142\u0006\u00101\u001a\u00020\u00142\u0006\u0010\u0013\u001a\u00020\u0014J\u001e\u00102\u001a\u00020\u000f2\u0006\u00103\u001a\u00020\u00142\u0006\u00104\u001a\u00020\u00142\u0006\u0010)\u001a\u00020\tJ\"\u00105\u001a\u00020\u000f2\u0006\u00106\u001a\u00020\u00142\u0006\u00107\u001a\u00020\t2\n\b\u0002\u00108\u001a\u0004\u0018\u00010\u0014J \u00109\u001a\u00020\u000f2\u0006\u0010:\u001a\u00020\u00142\u0006\u0010;\u001a\u00020+2\b\b\u0002\u0010<\u001a\u00020\u0014J\"\u0010=\u001a\u00020\u000f2\u0006\u00103\u001a\u00020\u00142\u0006\u0010)\u001a\u00020\t2\n\b\u0002\u00108\u001a\u0004\u0018\u00010\u0014J\u001e\u0010>\u001a\u00020\u000f2\u0006\u0010?\u001a\u00020-2\u0006\u0010@\u001a\u00020-2\u0006\u0010A\u001a\u00020-J&\u0010B\u001a\u00020\u000f2\u0006\u0010C\u001a\u00020\u00142\u0006\u0010D\u001a\u00020-2\u0006\u0010E\u001a\u00020+2\u0006\u0010)\u001a\u00020\tJ\u001e\u0010F\u001a\u00020\u000f2\u0006\u0010G\u001a\u00020H2\u0006\u0010)\u001a\u00020\t2\u0006\u0010I\u001a\u00020+J\b\u0010J\u001a\u00020\u000fH\u0002J\u000e\u0010K\u001a\u00020\u000f2\u0006\u0010L\u001a\u00020\tJ\u000e\u0010M\u001a\u00020\u000f2\u0006\u0010L\u001a\u00020\tJ\b\u0010N\u001a\u00020\u000fH\u0002J.\u0010O\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0016\b\u0002\u0010\u0017\u001a\u0010\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u0018J\u0010\u0010P\u001a\u00020\u000f2\u0006\u0010\u001c\u001a\u00020\rH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006V"}, d2 = {"Lcom/sensacare/veepoo/rpm/RPMLogger;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "dateFormatter", "Ljava/text/SimpleDateFormat;", "fileDateFormatter", "isDebugMode", "", "isFileLoggingEnabled", "logQueue", "Ljava/util/concurrent/ConcurrentLinkedQueue;", "Lcom/sensacare/veepoo/rpm/RPMLogger$LogEntry;", "clearLogs", "", "critical", "category", "Lcom/sensacare/veepoo/rpm/RPMLogger$LogCategory;", "message", "", "exception", "", "data", "", "debug", "error", "formatFileEntry", "entry", "formatLogcatMessage", "getLogFile", "Ljava/io/File;", "getLogStatistics", "Lcom/sensacare/veepoo/rpm/RPMLogger$LogStatistics;", "info", "log", "level", "Lcom/sensacare/veepoo/rpm/RPMLogger$LogLevel;", "logAPICall", "endpoint", "method", "success", "responseTime", "", "statusCode", "", "(Ljava/lang/String;Ljava/lang/String;ZJLjava/lang/Integer;)V", "logAlertReceived", "alertId", "severity", "logConsentCapture", "clientId", "consentType", "logDeviceConnection", "deviceId", "connected", "errorMessage", "logPerformance", "metric", "value", "unit", "logRPMInit", "logSyncQueue", "queueSize", "processedCount", "failedCount", "logTimelineDataLoad", "range", "entryCount", "loadTime", "logVitalsProcessing", "vitalsData", "Lcom/sensacare/veepoo/rpm/VitalsData;", "processingTime", "rotateLogFiles", "setDebugMode", "enabled", "setFileLoggingEnabled", "startLogProcessor", "warning", "writeToFile", "Companion", "LogCategory", "LogEntry", "LogLevel", "LogStatistics", "app_debug"})
public final class RPMLogger {
    @org.jetbrains.annotations.NotNull
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String TAG = "RPMLogger";
    private static final long MAX_LOG_FILE_SIZE = 10485760L;
    private static final int MAX_LOG_FILES = 5;
    private static final int LOG_BUFFER_SIZE = 1000;
    @org.jetbrains.annotations.NotNull
    private final java.util.concurrent.ConcurrentLinkedQueue<com.sensacare.veepoo.rpm.RPMLogger.LogEntry> logQueue = null;
    @org.jetbrains.annotations.NotNull
    private final java.text.SimpleDateFormat dateFormatter = null;
    @org.jetbrains.annotations.NotNull
    private final java.text.SimpleDateFormat fileDateFormatter = null;
    private boolean isFileLoggingEnabled = true;
    private boolean isDebugMode = false;
    @org.jetbrains.annotations.NotNull
    public static final com.sensacare.veepoo.rpm.RPMLogger.Companion Companion = null;
    
    public RPMLogger(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
        super();
    }
    
    /**
     * Log a debug message
     */
    public final void debug(@org.jetbrains.annotations.NotNull
    com.sensacare.veepoo.rpm.RPMLogger.LogCategory category, @org.jetbrains.annotations.NotNull
    java.lang.String message, @org.jetbrains.annotations.Nullable
    java.util.Map<java.lang.String, ? extends java.lang.Object> data) {
    }
    
    /**
     * Log an info message
     */
    public final void info(@org.jetbrains.annotations.NotNull
    com.sensacare.veepoo.rpm.RPMLogger.LogCategory category, @org.jetbrains.annotations.NotNull
    java.lang.String message, @org.jetbrains.annotations.Nullable
    java.util.Map<java.lang.String, ? extends java.lang.Object> data) {
    }
    
    /**
     * Log a warning message
     */
    public final void warning(@org.jetbrains.annotations.NotNull
    com.sensacare.veepoo.rpm.RPMLogger.LogCategory category, @org.jetbrains.annotations.NotNull
    java.lang.String message, @org.jetbrains.annotations.Nullable
    java.util.Map<java.lang.String, ? extends java.lang.Object> data) {
    }
    
    /**
     * Log an error message
     */
    public final void error(@org.jetbrains.annotations.NotNull
    com.sensacare.veepoo.rpm.RPMLogger.LogCategory category, @org.jetbrains.annotations.NotNull
    java.lang.String message, @org.jetbrains.annotations.Nullable
    java.lang.Throwable exception, @org.jetbrains.annotations.Nullable
    java.util.Map<java.lang.String, ? extends java.lang.Object> data) {
    }
    
    /**
     * Log a critical message
     */
    public final void critical(@org.jetbrains.annotations.NotNull
    com.sensacare.veepoo.rpm.RPMLogger.LogCategory category, @org.jetbrains.annotations.NotNull
    java.lang.String message, @org.jetbrains.annotations.Nullable
    java.lang.Throwable exception, @org.jetbrains.annotations.Nullable
    java.util.Map<java.lang.String, ? extends java.lang.Object> data) {
    }
    
    /**
     * Log RPM initialization
     */
    public final void logRPMInit(@org.jetbrains.annotations.NotNull
    java.lang.String clientId, boolean success, @org.jetbrains.annotations.Nullable
    java.lang.String errorMessage) {
    }
    
    /**
     * Log vitals processing
     */
    public final void logVitalsProcessing(@org.jetbrains.annotations.NotNull
    com.sensacare.veepoo.rpm.VitalsData vitalsData, boolean success, long processingTime) {
    }
    
    /**
     * Log API communication
     */
    public final void logAPICall(@org.jetbrains.annotations.NotNull
    java.lang.String endpoint, @org.jetbrains.annotations.NotNull
    java.lang.String method, boolean success, long responseTime, @org.jetbrains.annotations.Nullable
    java.lang.Integer statusCode) {
    }
    
    /**
     * Log device connection events
     */
    public final void logDeviceConnection(@org.jetbrains.annotations.NotNull
    java.lang.String deviceId, boolean connected, @org.jetbrains.annotations.Nullable
    java.lang.String errorMessage) {
    }
    
    /**
     * Log consent management events
     */
    public final void logConsentCapture(@org.jetbrains.annotations.NotNull
    java.lang.String clientId, @org.jetbrains.annotations.NotNull
    java.lang.String consentType, boolean success) {
    }
    
    /**
     * Log timeline data events
     */
    public final void logTimelineDataLoad(@org.jetbrains.annotations.NotNull
    java.lang.String range, int entryCount, long loadTime, boolean success) {
    }
    
    /**
     * Log sync queue events
     */
    public final void logSyncQueue(int queueSize, int processedCount, int failedCount) {
    }
    
    /**
     * Log alert events
     */
    public final void logAlertReceived(@org.jetbrains.annotations.NotNull
    java.lang.String alertId, @org.jetbrains.annotations.NotNull
    java.lang.String severity, @org.jetbrains.annotations.NotNull
    java.lang.String message) {
    }
    
    /**
     * Log performance metrics
     */
    public final void logPerformance(@org.jetbrains.annotations.NotNull
    java.lang.String metric, long value, @org.jetbrains.annotations.NotNull
    java.lang.String unit) {
    }
    
    /**
     * Core logging method
     */
    private final void log(com.sensacare.veepoo.rpm.RPMLogger.LogLevel level, com.sensacare.veepoo.rpm.RPMLogger.LogCategory category, java.lang.String message, java.util.Map<java.lang.String, ? extends java.lang.Object> data, java.lang.Throwable exception) {
    }
    
    /**
     * Format message for logcat
     */
    private final java.lang.String formatLogcatMessage(com.sensacare.veepoo.rpm.RPMLogger.LogEntry entry) {
        return null;
    }
    
    /**
     * Start log processor for file output
     */
    private final void startLogProcessor() {
    }
    
    /**
     * Write log entry to file
     */
    private final void writeToFile(com.sensacare.veepoo.rpm.RPMLogger.LogEntry entry) {
    }
    
    /**
     * Format log entry for file output
     */
    private final java.lang.String formatFileEntry(com.sensacare.veepoo.rpm.RPMLogger.LogEntry entry) {
        return null;
    }
    
    /**
     * Get current log file
     */
    private final java.io.File getLogFile() {
        return null;
    }
    
    /**
     * Rotate log files
     */
    private final void rotateLogFiles() {
    }
    
    /**
     * Enable/disable debug mode
     */
    public final void setDebugMode(boolean enabled) {
    }
    
    /**
     * Enable/disable file logging
     */
    public final void setFileLoggingEnabled(boolean enabled) {
    }
    
    /**
     * Get log statistics
     */
    @org.jetbrains.annotations.NotNull
    public final com.sensacare.veepoo.rpm.RPMLogger.LogStatistics getLogStatistics() {
        return null;
    }
    
    /**
     * Clear all log files
     */
    public final void clearLogs() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\n"}, d2 = {"Lcom/sensacare/veepoo/rpm/RPMLogger$Companion;", "", "()V", "LOG_BUFFER_SIZE", "", "MAX_LOG_FILES", "MAX_LOG_FILE_SIZE", "", "TAG", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u000b\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000b\u00a8\u0006\f"}, d2 = {"Lcom/sensacare/veepoo/rpm/RPMLogger$LogCategory;", "", "(Ljava/lang/String;I)V", "RPM_INIT", "VITALS_PROCESSING", "API_COMMUNICATION", "DEVICE_CONNECTION", "CONSENT_MANAGEMENT", "TIMELINE_DATA", "SYNC_QUEUE", "ALERTS", "PERFORMANCE", "app_debug"})
    public static enum LogCategory {
        /*public static final*/ RPM_INIT /* = new RPM_INIT() */,
        /*public static final*/ VITALS_PROCESSING /* = new VITALS_PROCESSING() */,
        /*public static final*/ API_COMMUNICATION /* = new API_COMMUNICATION() */,
        /*public static final*/ DEVICE_CONNECTION /* = new DEVICE_CONNECTION() */,
        /*public static final*/ CONSENT_MANAGEMENT /* = new CONSENT_MANAGEMENT() */,
        /*public static final*/ TIMELINE_DATA /* = new TIMELINE_DATA() */,
        /*public static final*/ SYNC_QUEUE /* = new SYNC_QUEUE() */,
        /*public static final*/ ALERTS /* = new ALERTS() */,
        /*public static final*/ PERFORMANCE /* = new PERFORMANCE() */;
        
        LogCategory() {
        }
        
        @org.jetbrains.annotations.NotNull
        public static kotlin.enums.EnumEntries<com.sensacare.veepoo.rpm.RPMLogger.LogCategory> getEntries() {
            return null;
        }
    }
    
    /**
     * Data class for log entries
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010$\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0015\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0082\b\u0018\u00002\u00020\u0001BE\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0014\u0010\n\u001a\u0010\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u000b\u0012\b\u0010\f\u001a\u0004\u0018\u00010\r\u00a2\u0006\u0002\u0010\u000eJ\t\u0010\u001b\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001c\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u001d\u001a\u00020\u0007H\u00c6\u0003J\t\u0010\u001e\u001a\u00020\tH\u00c6\u0003J\u0017\u0010\u001f\u001a\u0010\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u000bH\u00c6\u0003J\u000b\u0010 \u001a\u0004\u0018\u00010\rH\u00c6\u0003JU\u0010!\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t2\u0016\b\u0002\u0010\n\u001a\u0010\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u000b2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\rH\u00c6\u0001J\u0013\u0010\"\u001a\u00020#2\b\u0010$\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010%\u001a\u00020&H\u00d6\u0001J\t\u0010\'\u001a\u00020\tH\u00d6\u0001R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u001f\u0010\n\u001a\u0010\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0013\u0010\f\u001a\u0004\u0018\u00010\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001a\u00a8\u0006("}, d2 = {"Lcom/sensacare/veepoo/rpm/RPMLogger$LogEntry;", "", "timestamp", "", "level", "Lcom/sensacare/veepoo/rpm/RPMLogger$LogLevel;", "category", "Lcom/sensacare/veepoo/rpm/RPMLogger$LogCategory;", "message", "", "data", "", "exception", "", "(JLcom/sensacare/veepoo/rpm/RPMLogger$LogLevel;Lcom/sensacare/veepoo/rpm/RPMLogger$LogCategory;Ljava/lang/String;Ljava/util/Map;Ljava/lang/Throwable;)V", "getCategory", "()Lcom/sensacare/veepoo/rpm/RPMLogger$LogCategory;", "getData", "()Ljava/util/Map;", "getException", "()Ljava/lang/Throwable;", "getLevel", "()Lcom/sensacare/veepoo/rpm/RPMLogger$LogLevel;", "getMessage", "()Ljava/lang/String;", "getTimestamp", "()J", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
    static final class LogEntry {
        private final long timestamp = 0L;
        @org.jetbrains.annotations.NotNull
        private final com.sensacare.veepoo.rpm.RPMLogger.LogLevel level = null;
        @org.jetbrains.annotations.NotNull
        private final com.sensacare.veepoo.rpm.RPMLogger.LogCategory category = null;
        @org.jetbrains.annotations.NotNull
        private final java.lang.String message = null;
        @org.jetbrains.annotations.Nullable
        private final java.util.Map<java.lang.String, java.lang.Object> data = null;
        @org.jetbrains.annotations.Nullable
        private final java.lang.Throwable exception = null;
        
        public LogEntry(long timestamp, @org.jetbrains.annotations.NotNull
        com.sensacare.veepoo.rpm.RPMLogger.LogLevel level, @org.jetbrains.annotations.NotNull
        com.sensacare.veepoo.rpm.RPMLogger.LogCategory category, @org.jetbrains.annotations.NotNull
        java.lang.String message, @org.jetbrains.annotations.Nullable
        java.util.Map<java.lang.String, ? extends java.lang.Object> data, @org.jetbrains.annotations.Nullable
        java.lang.Throwable exception) {
            super();
        }
        
        public final long getTimestamp() {
            return 0L;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.sensacare.veepoo.rpm.RPMLogger.LogLevel getLevel() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.sensacare.veepoo.rpm.RPMLogger.LogCategory getCategory() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getMessage() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable
        public final java.util.Map<java.lang.String, java.lang.Object> getData() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable
        public final java.lang.Throwable getException() {
            return null;
        }
        
        public final long component1() {
            return 0L;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.sensacare.veepoo.rpm.RPMLogger.LogLevel component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.sensacare.veepoo.rpm.RPMLogger.LogCategory component3() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component4() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable
        public final java.util.Map<java.lang.String, java.lang.Object> component5() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable
        public final java.lang.Throwable component6() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.sensacare.veepoo.rpm.RPMLogger.LogEntry copy(long timestamp, @org.jetbrains.annotations.NotNull
        com.sensacare.veepoo.rpm.RPMLogger.LogLevel level, @org.jetbrains.annotations.NotNull
        com.sensacare.veepoo.rpm.RPMLogger.LogCategory category, @org.jetbrains.annotations.NotNull
        java.lang.String message, @org.jetbrains.annotations.Nullable
        java.util.Map<java.lang.String, ? extends java.lang.Object> data, @org.jetbrains.annotations.Nullable
        java.lang.Throwable exception) {
            return null;
        }
        
        @java.lang.Override
        public boolean equals(@org.jetbrains.annotations.Nullable
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override
        @org.jetbrains.annotations.NotNull
        public java.lang.String toString() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0007\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007\u00a8\u0006\b"}, d2 = {"Lcom/sensacare/veepoo/rpm/RPMLogger$LogLevel;", "", "(Ljava/lang/String;I)V", "DEBUG", "INFO", "WARNING", "ERROR", "CRITICAL", "app_debug"})
    public static enum LogLevel {
        /*public static final*/ DEBUG /* = new DEBUG() */,
        /*public static final*/ INFO /* = new INFO() */,
        /*public static final*/ WARNING /* = new WARNING() */,
        /*public static final*/ ERROR /* = new ERROR() */,
        /*public static final*/ CRITICAL /* = new CRITICAL() */;
        
        LogLevel() {
        }
        
        @org.jetbrains.annotations.NotNull
        public static kotlin.enums.EnumEntries<com.sensacare.veepoo.rpm.RPMLogger.LogLevel> getEntries() {
            return null;
        }
    }
    
    /**
     * Data class for log statistics
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u000f\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0010\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0011\u001a\u00020\u0007H\u00c6\u0003J\'\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007H\u00c6\u0001J\u0013\u0010\u0013\u001a\u00020\u00072\b\u0010\u0014\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0015\u001a\u00020\u0003H\u00d6\u0001J\t\u0010\u0016\u001a\u00020\u0017H\u00d6\u0001R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e\u00a8\u0006\u0018"}, d2 = {"Lcom/sensacare/veepoo/rpm/RPMLogger$LogStatistics;", "", "totalLogEntries", "", "logFileSize", "", "logFileExists", "", "(IJZ)V", "getLogFileExists", "()Z", "getLogFileSize", "()J", "getTotalLogEntries", "()I", "component1", "component2", "component3", "copy", "equals", "other", "hashCode", "toString", "", "app_debug"})
    public static final class LogStatistics {
        private final int totalLogEntries = 0;
        private final long logFileSize = 0L;
        private final boolean logFileExists = false;
        
        public LogStatistics(int totalLogEntries, long logFileSize, boolean logFileExists) {
            super();
        }
        
        public final int getTotalLogEntries() {
            return 0;
        }
        
        public final long getLogFileSize() {
            return 0L;
        }
        
        public final boolean getLogFileExists() {
            return false;
        }
        
        public final int component1() {
            return 0;
        }
        
        public final long component2() {
            return 0L;
        }
        
        public final boolean component3() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.sensacare.veepoo.rpm.RPMLogger.LogStatistics copy(int totalLogEntries, long logFileSize, boolean logFileExists) {
            return null;
        }
        
        @java.lang.Override
        public boolean equals(@org.jetbrains.annotations.Nullable
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override
        @org.jetbrains.annotations.NotNull
        public java.lang.String toString() {
            return null;
        }
    }
}