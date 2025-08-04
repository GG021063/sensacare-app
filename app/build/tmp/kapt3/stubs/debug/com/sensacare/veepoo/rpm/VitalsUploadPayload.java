package com.sensacare.veepoo.rpm;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0017\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B?\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\u0003\u0012\b\b\u0002\u0010\f\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\rJ\t\u0010\u0019\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001a\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001b\u001a\u00020\u0006H\u00c6\u0003J\t\u0010\u001c\u001a\u00020\bH\u00c6\u0003J\t\u0010\u001d\u001a\u00020\nH\u00c6\u0003J\t\u0010\u001e\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001f\u001a\u00020\u0003H\u00c6\u0003JO\u0010 \u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\u00032\b\b\u0002\u0010\f\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010!\u001a\u00020\"2\b\u0010#\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010$\u001a\u00020%H\u00d6\u0001J\t\u0010&\u001a\u00020\u0003H\u00d6\u0001R\u0016\u0010\u0004\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0016\u0010\t\u001a\u00020\n8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0016\u0010\u000b\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u000fR\u0016\u0010\u0005\u001a\u00020\u00068\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0016\u0010\f\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u000fR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u000fR\u0016\u0010\u0007\u001a\u00020\b8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018\u00a8\u0006\'"}, d2 = {"Lcom/sensacare/veepoo/rpm/VitalsUploadPayload;", "", "timestamp", "", "clientId", "device", "Lcom/sensacare/veepoo/rpm/DeviceMetadata;", "vitals", "Lcom/sensacare/veepoo/rpm/VitalsData;", "confidence", "", "consentToken", "source", "(Ljava/lang/String;Ljava/lang/String;Lcom/sensacare/veepoo/rpm/DeviceMetadata;Lcom/sensacare/veepoo/rpm/VitalsData;DLjava/lang/String;Ljava/lang/String;)V", "getClientId", "()Ljava/lang/String;", "getConfidence", "()D", "getConsentToken", "getDevice", "()Lcom/sensacare/veepoo/rpm/DeviceMetadata;", "getSource", "getTimestamp", "getVitals", "()Lcom/sensacare/veepoo/rpm/VitalsData;", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
public final class VitalsUploadPayload {
    @com.google.gson.annotations.SerializedName(value = "timestamp")
    @org.jetbrains.annotations.NotNull
    private final java.lang.String timestamp = null;
    @com.google.gson.annotations.SerializedName(value = "client_id")
    @org.jetbrains.annotations.NotNull
    private final java.lang.String clientId = null;
    @com.google.gson.annotations.SerializedName(value = "device")
    @org.jetbrains.annotations.NotNull
    private final com.sensacare.veepoo.rpm.DeviceMetadata device = null;
    @com.google.gson.annotations.SerializedName(value = "vitals")
    @org.jetbrains.annotations.NotNull
    private final com.sensacare.veepoo.rpm.VitalsData vitals = null;
    @com.google.gson.annotations.SerializedName(value = "confidence")
    private final double confidence = 0.0;
    @com.google.gson.annotations.SerializedName(value = "consent_token")
    @org.jetbrains.annotations.NotNull
    private final java.lang.String consentToken = null;
    @com.google.gson.annotations.SerializedName(value = "source")
    @org.jetbrains.annotations.NotNull
    private final java.lang.String source = null;
    
    public VitalsUploadPayload(@org.jetbrains.annotations.NotNull
    java.lang.String timestamp, @org.jetbrains.annotations.NotNull
    java.lang.String clientId, @org.jetbrains.annotations.NotNull
    com.sensacare.veepoo.rpm.DeviceMetadata device, @org.jetbrains.annotations.NotNull
    com.sensacare.veepoo.rpm.VitalsData vitals, double confidence, @org.jetbrains.annotations.NotNull
    java.lang.String consentToken, @org.jetbrains.annotations.NotNull
    java.lang.String source) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getTimestamp() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getClientId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.sensacare.veepoo.rpm.DeviceMetadata getDevice() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.sensacare.veepoo.rpm.VitalsData getVitals() {
        return null;
    }
    
    public final double getConfidence() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getConsentToken() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getSource() {
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
    
    @org.jetbrains.annotations.NotNull
    public final com.sensacare.veepoo.rpm.DeviceMetadata component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.sensacare.veepoo.rpm.VitalsData component4() {
        return null;
    }
    
    public final double component5() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.sensacare.veepoo.rpm.VitalsUploadPayload copy(@org.jetbrains.annotations.NotNull
    java.lang.String timestamp, @org.jetbrains.annotations.NotNull
    java.lang.String clientId, @org.jetbrains.annotations.NotNull
    com.sensacare.veepoo.rpm.DeviceMetadata device, @org.jetbrains.annotations.NotNull
    com.sensacare.veepoo.rpm.VitalsData vitals, double confidence, @org.jetbrains.annotations.NotNull
    java.lang.String consentToken, @org.jetbrains.annotations.NotNull
    java.lang.String source) {
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