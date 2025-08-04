package com.sensacare.veepoo;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u000e\n\u0002\u0010\u000b\n\u0002\b\f\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001:\u0001KB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J=\u0010?\u001a\u00020@2\b\u0010A\u001a\u0004\u0018\u00010\u00062\b\u0010B\u001a\u0004\u0018\u00010\u00062\b\u0010C\u001a\u0004\u0018\u00010\u00062\b\u0010D\u001a\u0004\u0018\u00010\u00062\b\u0010E\u001a\u0004\u0018\u000106\u00a2\u0006\u0002\u0010FJ\u0006\u0010G\u001a\u00020HJ\u0006\u0010I\u001a\u00020(J\u0006\u0010J\u001a\u00020\u0015R$\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u00068F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR$\u0010\f\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u00068F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\b\r\u0010\t\"\u0004\b\u000e\u0010\u000bR$\u0010\u000f\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u00068F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\b\u0010\u0010\t\"\u0004\b\u0011\u0010\u000bR$\u0010\u0012\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u00068F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\b\u0013\u0010\t\"\u0004\b\u0014\u0010\u000bR$\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u0005\u001a\u00020\u00158F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R$\u0010\u001a\u001a\u00020\u00152\u0006\u0010\u0005\u001a\u00020\u00158F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\b\u001a\u0010\u0017\"\u0004\b\u001b\u0010\u0019R$\u0010\u001c\u001a\u00020\u00152\u0006\u0010\u0005\u001a\u00020\u00158F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\b\u001c\u0010\u0017\"\u0004\b\u001d\u0010\u0019R$\u0010\u001e\u001a\u00020\u00152\u0006\u0010\u0005\u001a\u00020\u00158F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\b\u001e\u0010\u0017\"\u0004\b\u001f\u0010\u0019R$\u0010 \u001a\u00020\u00152\u0006\u0010\u0005\u001a\u00020\u00158F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\b \u0010\u0017\"\u0004\b!\u0010\u0019R$\u0010#\u001a\u00020\"2\u0006\u0010\u0005\u001a\u00020\"8F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\b$\u0010%\"\u0004\b&\u0010\'R(\u0010)\u001a\u0004\u0018\u00010(2\b\u0010\u0005\u001a\u0004\u0018\u00010(8F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\b*\u0010+\"\u0004\b,\u0010-R(\u0010.\u001a\u0004\u0018\u00010(2\b\u0010\u0005\u001a\u0004\u0018\u00010(8F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\b/\u0010+\"\u0004\b0\u0010-R\u000e\u00101\u001a\u000202X\u0082\u0004\u00a2\u0006\u0002\n\u0000R$\u00103\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u00068F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\b4\u0010\t\"\u0004\b5\u0010\u000bR$\u00107\u001a\u0002062\u0006\u0010\u0005\u001a\u0002068F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\b8\u00109\"\u0004\b:\u0010;R$\u0010<\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u00068F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\b=\u0010\t\"\u0004\b>\u0010\u000b\u00a8\u0006L"}, d2 = {"Lcom/sensacare/veepoo/PreferencesManager;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "value", "", "bloodPressureDiastolicMaxThreshold", "getBloodPressureDiastolicMaxThreshold", "()I", "setBloodPressureDiastolicMaxThreshold", "(I)V", "bloodPressureSystolicMaxThreshold", "getBloodPressureSystolicMaxThreshold", "setBloodPressureSystolicMaxThreshold", "heartRateMaxThreshold", "getHeartRateMaxThreshold", "setHeartRateMaxThreshold", "heartRateMinThreshold", "getHeartRateMinThreshold", "setHeartRateMinThreshold", "", "isAutoReconnectEnabled", "()Z", "setAutoReconnectEnabled", "(Z)V", "isBackgroundServiceEnabled", "setBackgroundServiceEnabled", "isDataUploadEnabled", "setDataUploadEnabled", "isNotificationsEnabled", "setNotificationsEnabled", "isOnboardingCompleted", "setOnboardingCompleted", "", "onboardingTimestamp", "getOnboardingTimestamp", "()J", "setOnboardingTimestamp", "(J)V", "", "pairedDeviceAddress", "getPairedDeviceAddress", "()Ljava/lang/String;", "setPairedDeviceAddress", "(Ljava/lang/String;)V", "pairedDeviceName", "getPairedDeviceName", "setPairedDeviceName", "sharedPreferences", "Landroid/content/SharedPreferences;", "spo2MinThreshold", "getSpo2MinThreshold", "setSpo2MinThreshold", "", "temperatureMaxThreshold", "getTemperatureMaxThreshold", "()F", "setTemperatureMaxThreshold", "(F)V", "uploadIntervalSeconds", "getUploadIntervalSeconds", "setUploadIntervalSeconds", "checkVitalsThresholds", "Lcom/sensacare/veepoo/PreferencesManager$VitalsAlertStatus;", "heartRate", "spo2", "systolicBP", "diastolicBP", "temperature", "(Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Float;)Lcom/sensacare/veepoo/PreferencesManager$VitalsAlertStatus;", "clearAllPreferences", "", "getDeviceInfo", "isDevicePaired", "VitalsAlertStatus", "app_debug"})
public final class PreferencesManager {
    @org.jetbrains.annotations.NotNull
    private final android.content.SharedPreferences sharedPreferences = null;
    
    public PreferencesManager(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
        super();
    }
    
    public final boolean isOnboardingCompleted() {
        return false;
    }
    
    public final void setOnboardingCompleted(boolean value) {
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getPairedDeviceName() {
        return null;
    }
    
    public final void setPairedDeviceName(@org.jetbrains.annotations.Nullable
    java.lang.String value) {
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getPairedDeviceAddress() {
        return null;
    }
    
    public final void setPairedDeviceAddress(@org.jetbrains.annotations.Nullable
    java.lang.String value) {
    }
    
    public final long getOnboardingTimestamp() {
        return 0L;
    }
    
    public final void setOnboardingTimestamp(long value) {
    }
    
    public final int getHeartRateMinThreshold() {
        return 0;
    }
    
    public final void setHeartRateMinThreshold(int value) {
    }
    
    public final int getHeartRateMaxThreshold() {
        return 0;
    }
    
    public final void setHeartRateMaxThreshold(int value) {
    }
    
    public final int getSpo2MinThreshold() {
        return 0;
    }
    
    public final void setSpo2MinThreshold(int value) {
    }
    
    public final int getBloodPressureSystolicMaxThreshold() {
        return 0;
    }
    
    public final void setBloodPressureSystolicMaxThreshold(int value) {
    }
    
    public final int getBloodPressureDiastolicMaxThreshold() {
        return 0;
    }
    
    public final void setBloodPressureDiastolicMaxThreshold(int value) {
    }
    
    public final float getTemperatureMaxThreshold() {
        return 0.0F;
    }
    
    public final void setTemperatureMaxThreshold(float value) {
    }
    
    public final boolean isBackgroundServiceEnabled() {
        return false;
    }
    
    public final void setBackgroundServiceEnabled(boolean value) {
    }
    
    public final boolean isNotificationsEnabled() {
        return false;
    }
    
    public final void setNotificationsEnabled(boolean value) {
    }
    
    public final boolean isAutoReconnectEnabled() {
        return false;
    }
    
    public final void setAutoReconnectEnabled(boolean value) {
    }
    
    public final int getUploadIntervalSeconds() {
        return 0;
    }
    
    public final void setUploadIntervalSeconds(int value) {
    }
    
    public final boolean isDataUploadEnabled() {
        return false;
    }
    
    public final void setDataUploadEnabled(boolean value) {
    }
    
    public final void clearAllPreferences() {
    }
    
    public final boolean isDevicePaired() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getDeviceInfo() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.sensacare.veepoo.PreferencesManager.VitalsAlertStatus checkVitalsThresholds(@org.jetbrains.annotations.Nullable
    java.lang.Integer heartRate, @org.jetbrains.annotations.Nullable
    java.lang.Integer spo2, @org.jetbrains.annotations.Nullable
    java.lang.Integer systolicBP, @org.jetbrains.annotations.Nullable
    java.lang.Integer diastolicBP, @org.jetbrains.annotations.Nullable
    java.lang.Float temperature) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u000b\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\u0002\u0010\u0007J\t\u0010\f\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u00c6\u0003J#\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u00c6\u0001J\u0013\u0010\u000f\u001a\u00020\u00032\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0011\u001a\u00020\u0012H\u00d6\u0001J\t\u0010\u0013\u001a\u00020\u0006H\u00d6\u0001R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u0014"}, d2 = {"Lcom/sensacare/veepoo/PreferencesManager$VitalsAlertStatus;", "", "hasAlerts", "", "alerts", "", "", "(ZLjava/util/List;)V", "getAlerts", "()Ljava/util/List;", "getHasAlerts", "()Z", "component1", "component2", "copy", "equals", "other", "hashCode", "", "toString", "app_debug"})
    public static final class VitalsAlertStatus {
        private final boolean hasAlerts = false;
        @org.jetbrains.annotations.NotNull
        private final java.util.List<java.lang.String> alerts = null;
        
        public VitalsAlertStatus(boolean hasAlerts, @org.jetbrains.annotations.NotNull
        java.util.List<java.lang.String> alerts) {
            super();
        }
        
        public final boolean getHasAlerts() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.List<java.lang.String> getAlerts() {
            return null;
        }
        
        public final boolean component1() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.List<java.lang.String> component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.sensacare.veepoo.PreferencesManager.VitalsAlertStatus copy(boolean hasAlerts, @org.jetbrains.annotations.NotNull
        java.util.List<java.lang.String> alerts) {
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