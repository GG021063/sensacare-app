package com.sensacare.veepoo.device;

/**
 * Device SDK interface for abstraction
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\u0019\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\bJ\u0011\u0010\t\u001a\u00020\u0005H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\nJ\u000f\u0010\u000b\u001a\u0004\u0018\u00010\fH&\u00a2\u0006\u0002\u0010\rJ\b\u0010\u000e\u001a\u00020\u000fH&J\n\u0010\u0010\u001a\u0004\u0018\u00010\u0011H&J\b\u0010\u0012\u001a\u00020\u0011H&J%\u0010\u0013\u001a\u00020\u00052\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020\u00030\u0015H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0017J\u0011\u0010\u0018\u001a\u00020\u0005H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\n\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0019"}, d2 = {"Lcom/sensacare/veepoo/device/DeviceSDK;", "", "cleanup", "", "connect", "", "device", "Landroid/bluetooth/BluetoothDevice;", "(Landroid/bluetooth/BluetoothDevice;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "disconnect", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getBatteryLevel", "", "()Ljava/lang/Integer;", "getCapabilities", "Lcom/sensacare/veepoo/device/DeviceCapabilities;", "getFirmwareVersion", "", "getSDKVersion", "startVitalsMonitoring", "onVitalsReceived", "Lkotlin/Function1;", "Lcom/sensacare/veepoo/VitalsData;", "(Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "stopVitalsMonitoring", "app_debug"})
public abstract interface DeviceSDK {
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object connect(@org.jetbrains.annotations.NotNull
    android.bluetooth.BluetoothDevice device, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object disconnect(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object startVitalsMonitoring(@org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super com.sensacare.veepoo.VitalsData, kotlin.Unit> onVitalsReceived, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object stopVitalsMonitoring(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.String getFirmwareVersion();
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Integer getBatteryLevel();
    
    @org.jetbrains.annotations.NotNull
    public abstract java.lang.String getSDKVersion();
    
    @org.jetbrains.annotations.NotNull
    public abstract com.sensacare.veepoo.device.DeviceCapabilities getCapabilities();
    
    public abstract void cleanup();
}