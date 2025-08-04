package com.sensacare.veepoo.device;

/**
 * Device SDK Manager that abstracts different device SDKs
 * Currently supports Veepoo SDK with extensibility for other SDKs
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u0000 )2\u00020\u0001:\u0001)B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u0017\u001a\u00020\u0018J\u0019\u0010\u0019\u001a\u00020\t2\u0006\u0010\u001a\u001a\u00020\u001bH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001cJ\u0012\u0010\u001d\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u001e\u001a\u00020\rH\u0002J\u0011\u0010\u001f\u001a\u00020\tH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010 J\b\u0010!\u001a\u0004\u0018\u00010\rJ\b\u0010\u0012\u001a\u0004\u0018\u00010\u0007J\b\u0010\"\u001a\u0004\u0018\u00010#J\u000e\u0010$\u001a\u00020\t2\u0006\u0010\u001e\u001a\u00020\rJ\u0006\u0010%\u001a\u00020\tJ\u0011\u0010&\u001a\u00020\tH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010 J\u0011\u0010\'\u001a\u00020\tH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010 J\u0010\u0010(\u001a\u00020\u00182\u0006\u0010\u001a\u001a\u00020\u001bH\u0002R\u0016\u0010\u0005\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\n\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0019\u0010\u0010\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0017\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\t0\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0013R\u0019\u0010\u0015\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0013\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006*"}, d2 = {"Lcom/sensacare/veepoo/device/DeviceSDKManager;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "_deviceMetadata", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/sensacare/veepoo/rpm/DeviceMetadata;", "_isConnected", "", "_lastVitalsData", "Lcom/sensacare/veepoo/VitalsData;", "currentDeviceType", "", "currentSDK", "Lcom/sensacare/veepoo/device/DeviceSDK;", "deviceMetadata", "Lkotlinx/coroutines/flow/StateFlow;", "getDeviceMetadata", "()Lkotlinx/coroutines/flow/StateFlow;", "isConnected", "lastVitalsData", "getLastVitalsData", "cleanup", "", "connectToDevice", "device", "Landroid/bluetooth/BluetoothDevice;", "(Landroid/bluetooth/BluetoothDevice;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "createSDKForDeviceType", "deviceType", "disconnectFromDevice", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getCurrentDeviceType", "getSDKCapabilities", "Lcom/sensacare/veepoo/device/DeviceCapabilities;", "initializeSDK", "isSDKInitialized", "startVitalsMonitoring", "stopVitalsMonitoring", "updateDeviceMetadata", "Companion", "app_debug"})
public final class DeviceSDKManager {
    @org.jetbrains.annotations.NotNull
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String TAG = "DeviceSDKManager";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String DEVICE_TYPE_VEEPOO_RING = "veepoo_ring";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String DEVICE_TYPE_VEEPOO_WATCH = "veepoo_watch";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String DEVICE_TYPE_GENERIC_BLE = "generic_ble";
    @org.jetbrains.annotations.Nullable
    private com.sensacare.veepoo.device.DeviceSDK currentSDK;
    @org.jetbrains.annotations.Nullable
    private java.lang.String currentDeviceType;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _isConnected = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isConnected = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<com.sensacare.veepoo.rpm.DeviceMetadata> _deviceMetadata = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<com.sensacare.veepoo.rpm.DeviceMetadata> deviceMetadata = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<com.sensacare.veepoo.VitalsData> _lastVitalsData = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<com.sensacare.veepoo.VitalsData> lastVitalsData = null;
    @org.jetbrains.annotations.NotNull
    public static final com.sensacare.veepoo.device.DeviceSDKManager.Companion Companion = null;
    
    public DeviceSDKManager(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isConnected() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<com.sensacare.veepoo.rpm.DeviceMetadata> getDeviceMetadata() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<com.sensacare.veepoo.VitalsData> getLastVitalsData() {
        return null;
    }
    
    /**
     * Initialize device SDK for the specified device type
     */
    public final boolean initializeSDK(@org.jetbrains.annotations.NotNull
    java.lang.String deviceType) {
        return false;
    }
    
    /**
     * Create appropriate SDK for device type
     */
    private final com.sensacare.veepoo.device.DeviceSDK createSDKForDeviceType(java.lang.String deviceType) {
        return null;
    }
    
    /**
     * Connect to device
     */
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object connectToDevice(@org.jetbrains.annotations.NotNull
    android.bluetooth.BluetoothDevice device, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    /**
     * Disconnect from device
     */
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object disconnectFromDevice(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    /**
     * Start vitals monitoring
     */
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object startVitalsMonitoring(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    /**
     * Stop vitals monitoring
     */
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object stopVitalsMonitoring(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    /**
     * Get device metadata
     */
    @org.jetbrains.annotations.Nullable
    public final com.sensacare.veepoo.rpm.DeviceMetadata getDeviceMetadata() {
        return null;
    }
    
    /**
     * Update device metadata
     */
    private final void updateDeviceMetadata(android.bluetooth.BluetoothDevice device) {
    }
    
    /**
     * Get current device type
     */
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getCurrentDeviceType() {
        return null;
    }
    
    /**
     * Check if SDK is initialized
     */
    public final boolean isSDKInitialized() {
        return false;
    }
    
    /**
     * Get SDK capabilities
     */
    @org.jetbrains.annotations.Nullable
    public final com.sensacare.veepoo.device.DeviceCapabilities getSDKCapabilities() {
        return null;
    }
    
    /**
     * Cleanup
     */
    public final void cleanup() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\b"}, d2 = {"Lcom/sensacare/veepoo/device/DeviceSDKManager$Companion;", "", "()V", "DEVICE_TYPE_GENERIC_BLE", "", "DEVICE_TYPE_VEEPOO_RING", "DEVICE_TYPE_VEEPOO_WATCH", "TAG", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}