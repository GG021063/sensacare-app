package com.sensacare.veepoo.rpm;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0018\b\u0086\b\u0018\u00002\u00020\u0001B7\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\u0003\u0012\b\b\u0002\u0010\n\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u000bJ\t\u0010\u0015\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0016\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0017\u001a\u00020\u0006H\u00c6\u0003J\t\u0010\u0018\u001a\u00020\bH\u00c6\u0003J\t\u0010\u0019\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001a\u001a\u00020\u0003H\u00c6\u0003JE\u0010\u001b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\u00032\b\b\u0002\u0010\n\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\u001c\u001a\u00020\b2\b\u0010\u001d\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001e\u001a\u00020\u0006H\u00d6\u0001J\t\u0010\u001f\u001a\u00020\u0003H\u00d6\u0001R\u0016\u0010\u0005\u001a\u00020\u00068\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0016\u0010\u0007\u001a\u00020\b8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0016\u0010\t\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u000fR\u0016\u0010\u0004\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u000fR\u0016\u0010\n\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u000f\u00a8\u0006 "}, d2 = {"Lcom/sensacare/veepoo/rpm/DeviceStatusPayload;", "", "clientId", "", "lastSync", "battery", "", "connected", "", "firmware", "sdkVersion", "(Ljava/lang/String;Ljava/lang/String;IZLjava/lang/String;Ljava/lang/String;)V", "getBattery", "()I", "getClientId", "()Ljava/lang/String;", "getConnected", "()Z", "getFirmware", "getLastSync", "getSdkVersion", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "other", "hashCode", "toString", "app_debug"})
public final class DeviceStatusPayload {
    @com.google.gson.annotations.SerializedName(value = "client_id")
    @org.jetbrains.annotations.NotNull
    private final java.lang.String clientId = null;
    @com.google.gson.annotations.SerializedName(value = "last_sync")
    @org.jetbrains.annotations.NotNull
    private final java.lang.String lastSync = null;
    @com.google.gson.annotations.SerializedName(value = "battery")
    private final int battery = 0;
    @com.google.gson.annotations.SerializedName(value = "connected")
    private final boolean connected = false;
    @com.google.gson.annotations.SerializedName(value = "firmware")
    @org.jetbrains.annotations.NotNull
    private final java.lang.String firmware = null;
    @com.google.gson.annotations.SerializedName(value = "sdk_version")
    @org.jetbrains.annotations.NotNull
    private final java.lang.String sdkVersion = null;
    
    public DeviceStatusPayload(@org.jetbrains.annotations.NotNull
    java.lang.String clientId, @org.jetbrains.annotations.NotNull
    java.lang.String lastSync, int battery, boolean connected, @org.jetbrains.annotations.NotNull
    java.lang.String firmware, @org.jetbrains.annotations.NotNull
    java.lang.String sdkVersion) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getClientId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getLastSync() {
        return null;
    }
    
    public final int getBattery() {
        return 0;
    }
    
    public final boolean getConnected() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getFirmware() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getSdkVersion() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component2() {
        return null;
    }
    
    public final int component3() {
        return 0;
    }
    
    public final boolean component4() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.sensacare.veepoo.rpm.DeviceStatusPayload copy(@org.jetbrains.annotations.NotNull
    java.lang.String clientId, @org.jetbrains.annotations.NotNull
    java.lang.String lastSync, int battery, boolean connected, @org.jetbrains.annotations.NotNull
    java.lang.String firmware, @org.jetbrains.annotations.NotNull
    java.lang.String sdkVersion) {
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