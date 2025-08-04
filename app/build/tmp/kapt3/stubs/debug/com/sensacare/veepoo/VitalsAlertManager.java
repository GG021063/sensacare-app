package com.sensacare.veepoo;

/**
 * Stub implementation of VitalsAlertManager for compilation purposes.
 * This class manages health alert thresholds and statuses.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0014\u0018\u0000 ,2\u00020\u0001:\u0003*+,B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010J\u0016\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016J\u0016\u0010\u0017\u001a\u00020\u00122\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u0019J\u000e\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u001c\u001a\u00020\u0019J\u000e\u0010\u001d\u001a\u00020\u00122\u0006\u0010\u001e\u001a\u00020\u0019J\u000e\u0010\u001f\u001a\u00020\u00122\u0006\u0010 \u001a\u00020\u0016J\u0006\u0010!\u001a\u00020\u000eJ&\u0010\"\u001a\u00020\u000e2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010#\u001a\u00020\u00122\u0006\u0010$\u001a\u00020\u0014J\u000e\u0010%\u001a\u00020\u00192\u0006\u0010#\u001a\u00020\u0012J\u000e\u0010&\u001a\u00020\u00192\u0006\u0010#\u001a\u00020\u0012J\u000e\u0010\'\u001a\u00020\u00192\u0006\u0010#\u001a\u00020\u0012J\u0016\u0010(\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\b\b\u0002\u0010)\u001a\u00020\u0019R\u001a\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f\u00a8\u0006-"}, d2 = {"Lcom/sensacare/veepoo/VitalsAlertManager;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "_alertsLiveData", "Landroidx/lifecycle/MutableLiveData;", "", "Lcom/sensacare/veepoo/VitalsAlertManager$AlertData;", "alertsLiveData", "Landroidx/lifecycle/LiveData;", "getAlertsLiveData", "()Landroidx/lifecycle/LiveData;", "acknowledgeAlert", "", "alertId", "", "calculateAlertLevel", "Lcom/sensacare/veepoo/VitalsAlertManager$AlertLevel;", "vitalType", "", "value", "", "checkBloodPressureAlert", "systolic", "", "diastolic", "checkHeartRateAlert", "heartRate", "checkSpo2Alert", "spo2", "checkTemperatureAlert", "temperature", "clearAlerts", "createAlert", "alertLevel", "message", "getAlertBackgroundColor", "getAlertColor", "getAlertTextColor", "getRecentAlerts", "limit", "AlertData", "AlertLevel", "Companion", "app_debug"})
public final class VitalsAlertManager {
    @org.jetbrains.annotations.Nullable
    private static com.sensacare.veepoo.VitalsAlertManager instance;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.MutableLiveData<java.util.List<com.sensacare.veepoo.VitalsAlertManager.AlertData>> _alertsLiveData = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.LiveData<java.util.List<com.sensacare.veepoo.VitalsAlertManager.AlertData>> alertsLiveData = null;
    @org.jetbrains.annotations.NotNull
    public static final com.sensacare.veepoo.VitalsAlertManager.Companion Companion = null;
    
    private VitalsAlertManager(android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.LiveData<java.util.List<com.sensacare.veepoo.VitalsAlertManager.AlertData>> getAlertsLiveData() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.sensacare.veepoo.VitalsAlertManager.AlertLevel checkHeartRateAlert(int heartRate) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.sensacare.veepoo.VitalsAlertManager.AlertLevel checkBloodPressureAlert(int systolic, int diastolic) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.sensacare.veepoo.VitalsAlertManager.AlertLevel checkSpo2Alert(int spo2) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.sensacare.veepoo.VitalsAlertManager.AlertLevel checkTemperatureAlert(float temperature) {
        return null;
    }
    
    public final int getAlertColor(@org.jetbrains.annotations.NotNull
    com.sensacare.veepoo.VitalsAlertManager.AlertLevel alertLevel) {
        return 0;
    }
    
    public final int getAlertTextColor(@org.jetbrains.annotations.NotNull
    com.sensacare.veepoo.VitalsAlertManager.AlertLevel alertLevel) {
        return 0;
    }
    
    public final int getAlertBackgroundColor(@org.jetbrains.annotations.NotNull
    com.sensacare.veepoo.VitalsAlertManager.AlertLevel alertLevel) {
        return 0;
    }
    
    public final void createAlert(@org.jetbrains.annotations.NotNull
    java.lang.String vitalType, float value, @org.jetbrains.annotations.NotNull
    com.sensacare.veepoo.VitalsAlertManager.AlertLevel alertLevel, @org.jetbrains.annotations.NotNull
    java.lang.String message) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.sensacare.veepoo.VitalsAlertManager.AlertData> getRecentAlerts(int limit) {
        return null;
    }
    
    public final void acknowledgeAlert(long alertId) {
    }
    
    public final void clearAlerts() {
    }
    
    /**
     * Generic dispatcher used throughout the app (e.g. AdminFragment) to obtain the
     * calculated AlertLevel for an arbitrary vital type + value.
     *
     * Note: For blood-pressure we expect `value` to be systolic; diastolic will be
     *      derived as value – 40 for heuristic purposes in this stub.
     */
    @org.jetbrains.annotations.NotNull
    public final com.sensacare.veepoo.VitalsAlertManager.AlertLevel calculateAlertLevel(@org.jetbrains.annotations.NotNull
    java.lang.String vitalType, float value) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0018\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B?\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\u0006\u0012\b\b\u0002\u0010\f\u001a\u00020\r\u00a2\u0006\u0002\u0010\u000eJ\t\u0010\u001b\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001c\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001d\u001a\u00020\u0006H\u00c6\u0003J\t\u0010\u001e\u001a\u00020\bH\u00c6\u0003J\t\u0010\u001f\u001a\u00020\nH\u00c6\u0003J\t\u0010 \u001a\u00020\u0006H\u00c6\u0003J\t\u0010!\u001a\u00020\rH\u00c6\u0003JO\u0010\"\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\u00062\b\b\u0002\u0010\f\u001a\u00020\rH\u00c6\u0001J\u0013\u0010#\u001a\u00020\r2\b\u0010$\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010%\u001a\u00020&H\u00d6\u0001J\t\u0010\'\u001a\u00020\u0006H\u00d6\u0001R\u0011\u0010\f\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u000b\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0014R\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0016\u00a8\u0006("}, d2 = {"Lcom/sensacare/veepoo/VitalsAlertManager$AlertData;", "", "id", "", "timestamp", "vitalType", "", "value", "", "alertLevel", "Lcom/sensacare/veepoo/VitalsAlertManager$AlertLevel;", "message", "acknowledged", "", "(JJLjava/lang/String;FLcom/sensacare/veepoo/VitalsAlertManager$AlertLevel;Ljava/lang/String;Z)V", "getAcknowledged", "()Z", "getAlertLevel", "()Lcom/sensacare/veepoo/VitalsAlertManager$AlertLevel;", "getId", "()J", "getMessage", "()Ljava/lang/String;", "getTimestamp", "getValue", "()F", "getVitalType", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "copy", "equals", "other", "hashCode", "", "toString", "app_debug"})
    public static final class AlertData {
        private final long id = 0L;
        private final long timestamp = 0L;
        @org.jetbrains.annotations.NotNull
        private final java.lang.String vitalType = null;
        private final float value = 0.0F;
        @org.jetbrains.annotations.NotNull
        private final com.sensacare.veepoo.VitalsAlertManager.AlertLevel alertLevel = null;
        @org.jetbrains.annotations.NotNull
        private final java.lang.String message = null;
        private final boolean acknowledged = false;
        
        public AlertData(long id, long timestamp, @org.jetbrains.annotations.NotNull
        java.lang.String vitalType, float value, @org.jetbrains.annotations.NotNull
        com.sensacare.veepoo.VitalsAlertManager.AlertLevel alertLevel, @org.jetbrains.annotations.NotNull
        java.lang.String message, boolean acknowledged) {
            super();
        }
        
        public final long getId() {
            return 0L;
        }
        
        public final long getTimestamp() {
            return 0L;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getVitalType() {
            return null;
        }
        
        public final float getValue() {
            return 0.0F;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.sensacare.veepoo.VitalsAlertManager.AlertLevel getAlertLevel() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getMessage() {
            return null;
        }
        
        public final boolean getAcknowledged() {
            return false;
        }
        
        public final long component1() {
            return 0L;
        }
        
        public final long component2() {
            return 0L;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component3() {
            return null;
        }
        
        public final float component4() {
            return 0.0F;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.sensacare.veepoo.VitalsAlertManager.AlertLevel component5() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component6() {
            return null;
        }
        
        public final boolean component7() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.sensacare.veepoo.VitalsAlertManager.AlertData copy(long id, long timestamp, @org.jetbrains.annotations.NotNull
        java.lang.String vitalType, float value, @org.jetbrains.annotations.NotNull
        com.sensacare.veepoo.VitalsAlertManager.AlertLevel alertLevel, @org.jetbrains.annotations.NotNull
        java.lang.String message, boolean acknowledged) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/sensacare/veepoo/VitalsAlertManager$AlertLevel;", "", "(Ljava/lang/String;I)V", "NORMAL", "WARNING", "CRITICAL", "NONE", "app_debug"})
    public static enum AlertLevel {
        /*public static final*/ NORMAL /* = new NORMAL() */,
        /*public static final*/ WARNING /* = new WARNING() */,
        /*public static final*/ CRITICAL /* = new CRITICAL() */,
        /*public static final*/ NONE /* = new NONE() */;
        
        AlertLevel() {
        }
        
        @org.jetbrains.annotations.NotNull
        public static kotlin.enums.EnumEntries<com.sensacare.veepoo.VitalsAlertManager.AlertLevel> getEntries() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0007R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\b"}, d2 = {"Lcom/sensacare/veepoo/VitalsAlertManager$Companion;", "", "()V", "instance", "Lcom/sensacare/veepoo/VitalsAlertManager;", "getInstance", "context", "Landroid/content/Context;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.sensacare.veepoo.VitalsAlertManager getInstance(@org.jetbrains.annotations.NotNull
        android.content.Context context) {
            return null;
        }
    }
}