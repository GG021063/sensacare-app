package com.sensacare.veepoo.rpm;

/**
 * Consent record data class
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0018\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001BG\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\u0003\u0012\u0006\u0010\f\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\rJ\t\u0010\u0019\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001a\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001b\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010\u001c\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\t\u0010\u001d\u001a\u00020\bH\u00c6\u0003J\t\u0010\u001e\u001a\u00020\nH\u00c6\u0003J\t\u0010\u001f\u001a\u00020\u0003H\u00c6\u0003J\t\u0010 \u001a\u00020\u0003H\u00c6\u0003J[\u0010!\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\u00032\b\b\u0002\u0010\f\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\"\u001a\u00020#2\b\u0010$\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010%\u001a\u00020&H\u00d6\u0001J\t\u0010\'\u001a\u00020\u0003H\u00d6\u0001R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000fR\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000fR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u000fR\u0011\u0010\u000b\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u000fR\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\f\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u000f\u00a8\u0006("}, d2 = {"Lcom/sensacare/veepoo/rpm/ConsentRecord;", "", "consentId", "", "clientId", "clientName", "carerName", "consentType", "Lcom/sensacare/veepoo/rpm/ConsentType;", "timestamp", "", "consentToken", "version", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lcom/sensacare/veepoo/rpm/ConsentType;JLjava/lang/String;Ljava/lang/String;)V", "getCarerName", "()Ljava/lang/String;", "getClientId", "getClientName", "getConsentId", "getConsentToken", "getConsentType", "()Lcom/sensacare/veepoo/rpm/ConsentType;", "getTimestamp", "()J", "getVersion", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
public final class ConsentRecord {
    @org.jetbrains.annotations.NotNull
    private final java.lang.String consentId = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String clientId = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String clientName = null;
    @org.jetbrains.annotations.Nullable
    private final java.lang.String carerName = null;
    @org.jetbrains.annotations.NotNull
    private final com.sensacare.veepoo.rpm.ConsentType consentType = null;
    private final long timestamp = 0L;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String consentToken = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String version = null;
    
    public ConsentRecord(@org.jetbrains.annotations.NotNull
    java.lang.String consentId, @org.jetbrains.annotations.NotNull
    java.lang.String clientId, @org.jetbrains.annotations.NotNull
    java.lang.String clientName, @org.jetbrains.annotations.Nullable
    java.lang.String carerName, @org.jetbrains.annotations.NotNull
    com.sensacare.veepoo.rpm.ConsentType consentType, long timestamp, @org.jetbrains.annotations.NotNull
    java.lang.String consentToken, @org.jetbrains.annotations.NotNull
    java.lang.String version) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getConsentId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getClientId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getClientName() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getCarerName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.sensacare.veepoo.rpm.ConsentType getConsentType() {
        return null;
    }
    
    public final long getTimestamp() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getConsentToken() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getVersion() {
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
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.sensacare.veepoo.rpm.ConsentType component5() {
        return null;
    }
    
    public final long component6() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component8() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.sensacare.veepoo.rpm.ConsentRecord copy(@org.jetbrains.annotations.NotNull
    java.lang.String consentId, @org.jetbrains.annotations.NotNull
    java.lang.String clientId, @org.jetbrains.annotations.NotNull
    java.lang.String clientName, @org.jetbrains.annotations.Nullable
    java.lang.String carerName, @org.jetbrains.annotations.NotNull
    com.sensacare.veepoo.rpm.ConsentType consentType, long timestamp, @org.jetbrains.annotations.NotNull
    java.lang.String consentToken, @org.jetbrains.annotations.NotNull
    java.lang.String version) {
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