package com.sensacare.veepoo;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u001a\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001BO\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\t\u001a\u0004\u0018\u00010\n\u0012\u0006\u0010\u000b\u001a\u00020\u0003\u0012\u0006\u0010\f\u001a\u00020\r\u00a2\u0006\u0002\u0010\u000eJ\t\u0010\u001d\u001a\u00020\u0003H\u00c6\u0003J\u0010\u0010\u001e\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0012J\u0010\u0010\u001f\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0012J\u0010\u0010 \u001a\u0004\u0018\u00010\u0005H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0012J\u0010\u0010!\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0012J\u0010\u0010\"\u001a\u0004\u0018\u00010\nH\u00c6\u0003\u00a2\u0006\u0002\u0010\u001aJ\t\u0010#\u001a\u00020\u0003H\u00c6\u0003J\t\u0010$\u001a\u00020\rH\u00c6\u0003Jh\u0010%\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n2\b\b\u0002\u0010\u000b\u001a\u00020\u00032\b\b\u0002\u0010\f\u001a\u00020\rH\u00c6\u0001\u00a2\u0006\u0002\u0010&J\u0013\u0010\'\u001a\u00020(2\b\u0010)\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010*\u001a\u00020\u0005H\u00d6\u0001J\t\u0010+\u001a\u00020,H\u00d6\u0001R\u0011\u0010\f\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0015\u0010\u0007\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\n\n\u0002\u0010\u0013\u001a\u0004\b\u0011\u0010\u0012R\u0015\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\n\n\u0002\u0010\u0013\u001a\u0004\b\u0014\u0010\u0012R\u0015\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\n\n\u0002\u0010\u0013\u001a\u0004\b\u0015\u0010\u0012R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0015\u0010\b\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\n\n\u0002\u0010\u0013\u001a\u0004\b\u0018\u0010\u0012R\u0015\u0010\t\u001a\u0004\u0018\u00010\n\u00a2\u0006\n\n\u0002\u0010\u001b\u001a\u0004\b\u0019\u0010\u001aR\u0011\u0010\u000b\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0017\u00a8\u0006-"}, d2 = {"Lcom/sensacare/veepoo/AdminVitalsData;", "", "id", "", "heartRate", "", "bloodPressureSystolic", "bloodPressureDiastolic", "spo2", "temperature", "", "timestamp", "alertLevel", "Lcom/sensacare/veepoo/VitalsAlertManager$AlertLevel;", "(JLjava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Float;JLcom/sensacare/veepoo/VitalsAlertManager$AlertLevel;)V", "getAlertLevel", "()Lcom/sensacare/veepoo/VitalsAlertManager$AlertLevel;", "getBloodPressureDiastolic", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getBloodPressureSystolic", "getHeartRate", "getId", "()J", "getSpo2", "getTemperature", "()Ljava/lang/Float;", "Ljava/lang/Float;", "getTimestamp", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "copy", "(JLjava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Float;JLcom/sensacare/veepoo/VitalsAlertManager$AlertLevel;)Lcom/sensacare/veepoo/AdminVitalsData;", "equals", "", "other", "hashCode", "toString", "", "app_debug"})
public final class AdminVitalsData {
    private final long id = 0L;
    @org.jetbrains.annotations.Nullable
    private final java.lang.Integer heartRate = null;
    @org.jetbrains.annotations.Nullable
    private final java.lang.Integer bloodPressureSystolic = null;
    @org.jetbrains.annotations.Nullable
    private final java.lang.Integer bloodPressureDiastolic = null;
    @org.jetbrains.annotations.Nullable
    private final java.lang.Integer spo2 = null;
    @org.jetbrains.annotations.Nullable
    private final java.lang.Float temperature = null;
    private final long timestamp = 0L;
    @org.jetbrains.annotations.NotNull
    private final com.sensacare.veepoo.VitalsAlertManager.AlertLevel alertLevel = null;
    
    public AdminVitalsData(long id, @org.jetbrains.annotations.Nullable
    java.lang.Integer heartRate, @org.jetbrains.annotations.Nullable
    java.lang.Integer bloodPressureSystolic, @org.jetbrains.annotations.Nullable
    java.lang.Integer bloodPressureDiastolic, @org.jetbrains.annotations.Nullable
    java.lang.Integer spo2, @org.jetbrains.annotations.Nullable
    java.lang.Float temperature, long timestamp, @org.jetbrains.annotations.NotNull
    com.sensacare.veepoo.VitalsAlertManager.AlertLevel alertLevel) {
        super();
    }
    
    public final long getId() {
        return 0L;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Integer getHeartRate() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Integer getBloodPressureSystolic() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Integer getBloodPressureDiastolic() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Integer getSpo2() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Float getTemperature() {
        return null;
    }
    
    public final long getTimestamp() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.sensacare.veepoo.VitalsAlertManager.AlertLevel getAlertLevel() {
        return null;
    }
    
    public final long component1() {
        return 0L;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Integer component2() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Integer component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Integer component4() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Integer component5() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Float component6() {
        return null;
    }
    
    public final long component7() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.sensacare.veepoo.VitalsAlertManager.AlertLevel component8() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.sensacare.veepoo.AdminVitalsData copy(long id, @org.jetbrains.annotations.Nullable
    java.lang.Integer heartRate, @org.jetbrains.annotations.Nullable
    java.lang.Integer bloodPressureSystolic, @org.jetbrains.annotations.Nullable
    java.lang.Integer bloodPressureDiastolic, @org.jetbrains.annotations.Nullable
    java.lang.Integer spo2, @org.jetbrains.annotations.Nullable
    java.lang.Float temperature, long timestamp, @org.jetbrains.annotations.NotNull
    com.sensacare.veepoo.VitalsAlertManager.AlertLevel alertLevel) {
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