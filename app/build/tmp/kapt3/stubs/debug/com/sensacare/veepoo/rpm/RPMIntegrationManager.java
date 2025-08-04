package com.sensacare.veepoo.rpm;

/**
 * RPM Integration Manager - Central orchestrator for all RPM functionality
 * This provides a unified interface for the app to interact with RPM
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u00c8\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\b\n\u0002\b\u000b\u0018\u0000 [2\u00020\u0001:\u0001[B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0019\u00101\u001a\u0002022\u0006\u00103\u001a\u000204H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u00105J\u0006\u00106\u001a\u000207J\u0019\u00108\u001a\u0002022\u0006\u00109\u001a\u00020:H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010;J\u0010\u0010<\u001a\u00020=2\u0006\u0010>\u001a\u00020?H\u0002J\b\u0010@\u001a\u0004\u0018\u00010AJ\u0013\u0010B\u001a\u0004\u0018\u00010CH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010DJ#\u0010E\u001a\n\u0012\u0004\u0012\u00020F\u0018\u00010\u000b2\b\b\u0002\u0010G\u001a\u000204H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u00105J+\u0010H\u001a\u0002022\u0006\u0010I\u001a\u0002042\u0006\u0010J\u001a\u0002042\b\b\u0002\u0010K\u001a\u000204H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010LJ\u0006\u0010M\u001a\u000202J%\u0010N\u001a\u0002022\u0006\u0010O\u001a\u00020\t2\n\b\u0002\u0010P\u001a\u0004\u0018\u00010QH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010RJ\u0011\u0010S\u001a\u000202H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010DJ?\u0010T\u001a\u0002022\u0006\u0010I\u001a\u0002042\u0006\u0010U\u001a\u0002042\u0006\u0010J\u001a\u0002042\n\b\u0002\u0010V\u001a\u0004\u0018\u0001042\b\b\u0002\u0010K\u001a\u000204H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010WJ\b\u0010X\u001a\u000207H\u0002J\b\u0010Y\u001a\u000207H\u0002J\b\u0010Z\u001a\u000207H\u0002R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u000b0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00070\u0016\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u001cX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u001eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u0010\u001f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\u0016\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u0018R\u0010\u0010!\u001a\u0004\u0018\u00010\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001d\u0010\"\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u000b0\u0016\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010\u0018R\u000e\u0010$\u001a\u00020%X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010&\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0016\u00a2\u0006\b\n\u0000\u001a\u0004\b\'\u0010\u0018R\u0010\u0010(\u001a\u0004\u0018\u00010\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010)\u001a\u00020*X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010+\u001a\u00020,X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010-\u001a\u00020.X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010/\u001a\u000200X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\\"}, d2 = {"Lcom/sensacare/veepoo/rpm/RPMIntegrationManager;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "_currentVitalsStatus", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/sensacare/veepoo/rpm/VitalsStatus;", "_lastVitalsData", "Lcom/sensacare/veepoo/VitalsData;", "_recentAlerts", "", "Lcom/sensacare/veepoo/rpm/AlertData;", "_rpmStatus", "Lcom/sensacare/veepoo/rpm/RPMStatus;", "alertsPollingJob", "Lkotlinx/coroutines/Job;", "clientContextManager", "Lcom/sensacare/veepoo/rpm/RPMClientContextManager;", "consentManager", "Lcom/sensacare/veepoo/rpm/ConsentManager;", "currentVitalsStatus", "Lkotlinx/coroutines/flow/StateFlow;", "getCurrentVitalsStatus", "()Lkotlinx/coroutines/flow/StateFlow;", "database", "Lcom/sensacare/veepoo/AppDatabase;", "deviceSDKManager", "Lcom/sensacare/veepoo/device/DeviceSDKManager;", "integrationScope", "Lkotlinx/coroutines/CoroutineScope;", "lastVitalsData", "getLastVitalsData", "queueProcessingJob", "recentAlerts", "getRecentAlerts", "rpmApiManager", "Lcom/sensacare/veepoo/rpm/RPMApiManager;", "rpmStatus", "getRpmStatus", "syncJob", "syncQueueDao", "Lcom/sensacare/veepoo/rpm/SyncQueueDao;", "vitalsDao", "Lcom/sensacare/veepoo/VitalsDao;", "vitalsPayloadBuilder", "Lcom/sensacare/veepoo/rpm/VitalsPayloadBuilder;", "vitalsStatusEvaluator", "Lcom/sensacare/veepoo/rpm/VitalsStatusEvaluator;", "acknowledgeAlert", "", "alertId", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "cleanup", "", "connectAndStartMonitoring", "device", "Landroid/bluetooth/BluetoothDevice;", "(Landroid/bluetooth/BluetoothDevice;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "convertToRPMDeviceMetadata", "Lcom/sensacare/veepoo/rpm/RPMClientContextManager$DeviceMetadata;", "metadata", "Lcom/sensacare/veepoo/rpm/DeviceMetadata;", "getCurrentClientContext", "Lcom/sensacare/veepoo/rpm/RPMClientContextManager$ClientProfile;", "getQueueStats", "Lcom/sensacare/veepoo/rpm/RPMApiManager$QueueStats;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getVitalsTimeline", "Lcom/sensacare/veepoo/rpm/VitalsTimelineEntry;", "range", "initializeRPM", "clientId", "userToken", "deviceType", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "isRPMReady", "processVitalsData", "vitalsData", "rssi", "", "(Lcom/sensacare/veepoo/VitalsData;Ljava/lang/Integer;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "refreshAlerts", "setupWithConsent", "clientName", "carerName", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "startAlertsPolling", "startQueueProcessing", "startSyncProcess", "Companion", "app_debug"})
public final class RPMIntegrationManager {
    @org.jetbrains.annotations.NotNull
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String TAG = "RPMIntegrationManager";
    private static final long SYNC_INTERVAL_MS = 30000L;
    private static final long QUEUE_PROCESSING_INTERVAL_MS = 60000L;
    @org.jetbrains.annotations.NotNull
    private final com.sensacare.veepoo.rpm.RPMClientContextManager clientContextManager = null;
    @org.jetbrains.annotations.NotNull
    private final com.sensacare.veepoo.device.DeviceSDKManager deviceSDKManager = null;
    @org.jetbrains.annotations.NotNull
    private final com.sensacare.veepoo.rpm.VitalsPayloadBuilder vitalsPayloadBuilder = null;
    @org.jetbrains.annotations.NotNull
    private final com.sensacare.veepoo.rpm.VitalsStatusEvaluator vitalsStatusEvaluator = null;
    @org.jetbrains.annotations.NotNull
    private final com.sensacare.veepoo.rpm.ConsentManager consentManager = null;
    @org.jetbrains.annotations.NotNull
    private final com.sensacare.veepoo.AppDatabase database = null;
    @org.jetbrains.annotations.NotNull
    private final com.sensacare.veepoo.VitalsDao vitalsDao = null;
    @org.jetbrains.annotations.NotNull
    private final com.sensacare.veepoo.rpm.SyncQueueDao syncQueueDao = null;
    @org.jetbrains.annotations.NotNull
    private final com.sensacare.veepoo.rpm.RPMApiManager rpmApiManager = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.CoroutineScope integrationScope = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<com.sensacare.veepoo.rpm.RPMStatus> _rpmStatus = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<com.sensacare.veepoo.rpm.RPMStatus> rpmStatus = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<com.sensacare.veepoo.rpm.VitalsStatus> _currentVitalsStatus = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<com.sensacare.veepoo.rpm.VitalsStatus> currentVitalsStatus = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<com.sensacare.veepoo.VitalsData> _lastVitalsData = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<com.sensacare.veepoo.VitalsData> lastVitalsData = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.sensacare.veepoo.rpm.AlertData>> _recentAlerts = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.sensacare.veepoo.rpm.AlertData>> recentAlerts = null;
    @org.jetbrains.annotations.Nullable
    private kotlinx.coroutines.Job syncJob;
    @org.jetbrains.annotations.Nullable
    private kotlinx.coroutines.Job queueProcessingJob;
    @org.jetbrains.annotations.Nullable
    private kotlinx.coroutines.Job alertsPollingJob;
    @org.jetbrains.annotations.NotNull
    public static final com.sensacare.veepoo.rpm.RPMIntegrationManager.Companion Companion = null;
    
    public RPMIntegrationManager(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<com.sensacare.veepoo.rpm.RPMStatus> getRpmStatus() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<com.sensacare.veepoo.rpm.VitalsStatus> getCurrentVitalsStatus() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<com.sensacare.veepoo.VitalsData> getLastVitalsData() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.sensacare.veepoo.rpm.AlertData>> getRecentAlerts() {
        return null;
    }
    
    /**
     * Initialize RPM with client context
     */
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object initializeRPM(@org.jetbrains.annotations.NotNull
    java.lang.String clientId, @org.jetbrains.annotations.NotNull
    java.lang.String userToken, @org.jetbrains.annotations.NotNull
    java.lang.String deviceType, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    /**
     * Capture consent and set up RPM
     */
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object setupWithConsent(@org.jetbrains.annotations.NotNull
    java.lang.String clientId, @org.jetbrains.annotations.NotNull
    java.lang.String clientName, @org.jetbrains.annotations.NotNull
    java.lang.String userToken, @org.jetbrains.annotations.Nullable
    java.lang.String carerName, @org.jetbrains.annotations.NotNull
    java.lang.String deviceType, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    /**
     * Connect to device and start monitoring
     */
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object connectAndStartMonitoring(@org.jetbrains.annotations.NotNull
    android.bluetooth.BluetoothDevice device, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    /**
     * Process vitals data and upload to RPM
     */
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object processVitalsData(@org.jetbrains.annotations.NotNull
    com.sensacare.veepoo.VitalsData vitalsData, @org.jetbrains.annotations.Nullable
    java.lang.Integer rssi, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    /**
     * Convert SDK-level DeviceMetadata into the type expected by the
     * RPMClientContextManager so that the client context can be updated
     * without type-mismatch compilation errors.
     */
    private final com.sensacare.veepoo.rpm.RPMClientContextManager.DeviceMetadata convertToRPMDeviceMetadata(com.sensacare.veepoo.rpm.DeviceMetadata metadata) {
        return null;
    }
    
    /**
     * Get recent alerts from RPM
     */
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object refreshAlerts(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    /**
     * Acknowledge an alert
     */
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object acknowledgeAlert(@org.jetbrains.annotations.NotNull
    java.lang.String alertId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    /**
     * Get vitals timeline from RPM
     */
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object getVitalsTimeline(@org.jetbrains.annotations.NotNull
    java.lang.String range, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.util.List<com.sensacare.veepoo.rpm.VitalsTimelineEntry>> $completion) {
        return null;
    }
    
    /**
     * Start sync process
     */
    private final void startSyncProcess() {
    }
    
    /**
     * Start queue processing
     */
    private final void startQueueProcessing() {
    }
    
    /**
     * Start alerts polling
     */
    private final void startAlertsPolling() {
    }
    
    /**
     * Get queue statistics
     */
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object getQueueStats(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.sensacare.veepoo.rpm.RPMApiManager.QueueStats> $completion) {
        return null;
    }
    
    /**
     * Check if RPM is ready
     */
    public final boolean isRPMReady() {
        return false;
    }
    
    /**
     * Get current client context
     */
    @org.jetbrains.annotations.Nullable
    public final com.sensacare.veepoo.rpm.RPMClientContextManager.ClientProfile getCurrentClientContext() {
        return null;
    }
    
    /**
     * Cleanup
     */
    public final void cleanup() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\b"}, d2 = {"Lcom/sensacare/veepoo/rpm/RPMIntegrationManager$Companion;", "", "()V", "QUEUE_PROCESSING_INTERVAL_MS", "", "SYNC_INTERVAL_MS", "TAG", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}