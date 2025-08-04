package com.sensacare.veepoo.rpm;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\u0018\u0000 \u001d2\u00020\u0001:\u0001\u001dB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J,\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\b2\b\b\u0002\u0010\u000b\u001a\u00020\fJ\u0010\u0010\r\u001a\u00020\b2\u0006\u0010\u0007\u001a\u00020\bH\u0002J\u0018\u0010\u000e\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J\u0006\u0010\u0012\u001a\u00020\bJ\u000e\u0010\u0013\u001a\u00020\b2\u0006\u0010\u0014\u001a\u00020\u0015J\u0016\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u0019\u001a\u00020\bJ\u000e\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001e"}, d2 = {"Lcom/sensacare/veepoo/rpm/ConsentManager;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "captureConsent", "Lcom/sensacare/veepoo/rpm/ConsentResult;", "clientId", "", "clientName", "carerName", "consentType", "Lcom/sensacare/veepoo/rpm/ConsentType;", "generateConsentId", "generateConsentToken", "consentId", "timestamp", "", "getConsentPolicyText", "getConsentSummary", "consentRecord", "Lcom/sensacare/veepoo/rpm/ConsentRecord;", "includeConsentInPayload", "Lcom/sensacare/veepoo/rpm/VitalsUploadPayload;", "payload", "consentToken", "validateConsent", "", "token", "Companion", "app_debug"})
public final class ConsentManager {
    @org.jetbrains.annotations.NotNull
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String TAG = "ConsentManager";
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String CONSENT_VERSION = "1.0";
    @org.jetbrains.annotations.NotNull
    public static final com.sensacare.veepoo.rpm.ConsentManager.Companion Companion = null;
    
    public ConsentManager(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
        super();
    }
    
    /**
     * Capture consent and generate consent token
     */
    @org.jetbrains.annotations.NotNull
    public final com.sensacare.veepoo.rpm.ConsentResult captureConsent(@org.jetbrains.annotations.NotNull
    java.lang.String clientId, @org.jetbrains.annotations.NotNull
    java.lang.String clientName, @org.jetbrains.annotations.Nullable
    java.lang.String carerName, @org.jetbrains.annotations.NotNull
    com.sensacare.veepoo.rpm.ConsentType consentType) {
        return null;
    }
    
    /**
     * Validate consent token
     */
    public final boolean validateConsent(@org.jetbrains.annotations.NotNull
    java.lang.String token) {
        return false;
    }
    
    /**
     * Include consent in vitals payload
     */
    @org.jetbrains.annotations.NotNull
    public final com.sensacare.veepoo.rpm.VitalsUploadPayload includeConsentInPayload(@org.jetbrains.annotations.NotNull
    com.sensacare.veepoo.rpm.VitalsUploadPayload payload, @org.jetbrains.annotations.NotNull
    java.lang.String consentToken) {
        return null;
    }
    
    /**
     * Generate unique consent ID
     */
    private final java.lang.String generateConsentId(java.lang.String clientId) {
        return null;
    }
    
    /**
     * Generate consent token
     */
    private final java.lang.String generateConsentToken(java.lang.String consentId, long timestamp) {
        return null;
    }
    
    /**
     * Get consent policy text for display
     */
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getConsentPolicyText() {
        return null;
    }
    
    /**
     * Get consent summary for display
     */
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getConsentSummary(@org.jetbrains.annotations.NotNull
    com.sensacare.veepoo.rpm.ConsentRecord consentRecord) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0006"}, d2 = {"Lcom/sensacare/veepoo/rpm/ConsentManager$Companion;", "", "()V", "CONSENT_VERSION", "", "TAG", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}