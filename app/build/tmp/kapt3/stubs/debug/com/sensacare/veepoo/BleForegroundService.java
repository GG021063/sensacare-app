package com.sensacare.veepoo;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u000b\u0018\u0000 -2\u00020\u0001:\u0001-B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J\b\u0010\u001a\u001a\u00020\u001bH\u0002J\u0014\u0010\u001c\u001a\u0004\u0018\u00010\u001d2\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u0016J\b\u0010 \u001a\u00020\u001bH\u0016J\b\u0010!\u001a\u00020\u001bH\u0016J\"\u0010\"\u001a\u00020#2\b\u0010\u001e\u001a\u0004\u0018\u00010\u001f2\u0006\u0010$\u001a\u00020#2\u0006\u0010%\u001a\u00020#H\u0016J\b\u0010&\u001a\u00020\u001bH\u0002J\u0011\u0010\'\u001a\u00020\u001bH\u0082@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010(J\u0011\u0010)\u001a\u00020\u001bH\u0082@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010(J\u0011\u0010*\u001a\u00020\u001bH\u0082@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010(J\u0010\u0010+\u001a\u00020\u001b2\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J\u0011\u0010,\u001a\u00020\u001bH\u0082@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010(R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006."}, d2 = {"Lcom/sensacare/veepoo/BleForegroundService;", "Landroid/app/Service;", "()V", "connectionManager", "Lcom/sensacare/veepoo/BleConnectionManager;", "dataBuffer", "", "Lcom/sensacare/veepoo/VitalsData;", "database", "Lcom/sensacare/veepoo/AppDatabase;", "lastUploadTime", "", "preferencesManager", "Lcom/sensacare/veepoo/PreferencesManager;", "scanCallback", "Landroid/bluetooth/le/ScanCallback;", "secureApiManager", "Lcom/sensacare/veepoo/SecureApiManager;", "serviceScope", "Lkotlinx/coroutines/CoroutineScope;", "vitalsDao", "Lcom/sensacare/veepoo/VitalsDao;", "createNotification", "Landroid/app/Notification;", "content", "", "createNotificationChannel", "", "onBind", "Landroid/os/IBinder;", "intent", "Landroid/content/Intent;", "onCreate", "onDestroy", "onStartCommand", "", "flags", "startId", "setupConnectionCallbacks", "startBleConnection", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "startDataUploadScheduler", "startDeviceDiscovery", "updateNotification", "uploadBufferedData", "Companion", "app_debug"})
public final class BleForegroundService extends android.app.Service {
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.CoroutineScope serviceScope = null;
    @org.jetbrains.annotations.NotNull
    private final com.sensacare.veepoo.AppDatabase database = null;
    @org.jetbrains.annotations.NotNull
    private final com.sensacare.veepoo.VitalsDao vitalsDao = null;
    private com.sensacare.veepoo.PreferencesManager preferencesManager;
    private com.sensacare.veepoo.SecureApiManager secureApiManager;
    private com.sensacare.veepoo.BleConnectionManager connectionManager;
    @org.jetbrains.annotations.NotNull
    private final java.util.List<com.sensacare.veepoo.VitalsData> dataBuffer = null;
    private long lastUploadTime = 0L;
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String TAG = "BleForegroundService";
    private static final int NOTIFICATION_ID = 1001;
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String CHANNEL_ID = "ble_service_channel";
    private static final long UPLOAD_INTERVAL = 30000L;
    private static final java.util.UUID VEEPOO_SERVICE_UUID = null;
    private static final java.util.UUID VEEPOO_CHARACTERISTIC_UUID = null;
    @org.jetbrains.annotations.NotNull
    private final android.bluetooth.le.ScanCallback scanCallback = null;
    @org.jetbrains.annotations.NotNull
    public static final com.sensacare.veepoo.BleForegroundService.Companion Companion = null;
    
    public BleForegroundService() {
        super();
    }
    
    @java.lang.Override
    public void onCreate() {
    }
    
    @java.lang.Override
    public int onStartCommand(@org.jetbrains.annotations.Nullable
    android.content.Intent intent, int flags, int startId) {
        return 0;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public android.os.IBinder onBind(@org.jetbrains.annotations.Nullable
    android.content.Intent intent) {
        return null;
    }
    
    @java.lang.Override
    public void onDestroy() {
    }
    
    private final void createNotificationChannel() {
    }
    
    private final android.app.Notification createNotification(java.lang.String content) {
        return null;
    }
    
    private final void setupConnectionCallbacks() {
    }
    
    private final java.lang.Object startBleConnection(kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.Object startDeviceDiscovery(kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.Object startDataUploadScheduler(kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.Object uploadBufferedData(kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final void updateNotification(java.lang.String content) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011J\u000e\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082T\u00a2\u0006\u0002\n\u0000R\u0016\u0010\n\u001a\n \f*\u0004\u0018\u00010\u000b0\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\r\u001a\n \f*\u0004\u0018\u00010\u000b0\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0013"}, d2 = {"Lcom/sensacare/veepoo/BleForegroundService$Companion;", "", "()V", "CHANNEL_ID", "", "NOTIFICATION_ID", "", "TAG", "UPLOAD_INTERVAL", "", "VEEPOO_CHARACTERISTIC_UUID", "Ljava/util/UUID;", "kotlin.jvm.PlatformType", "VEEPOO_SERVICE_UUID", "startService", "", "context", "Landroid/content/Context;", "stopService", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        public final void startService(@org.jetbrains.annotations.NotNull
        android.content.Context context) {
        }
        
        public final void stopService(@org.jetbrains.annotations.NotNull
        android.content.Context context) {
        }
    }
}