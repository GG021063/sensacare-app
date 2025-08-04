package com.sensacare.veepoo.rpm;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u0012\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B7\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b\u00a2\u0006\u0002\u0010\tJ\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u000bJ\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u000bJ\u0010\u0010\u0015\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u000bJ\u0010\u0010\u0016\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u000bJ\u0010\u0010\u0017\u001a\u0004\u0018\u00010\bH\u00c6\u0003\u00a2\u0006\u0002\u0010\u0011JJ\u0010\u0018\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\bH\u00c6\u0001\u00a2\u0006\u0002\u0010\u0019J\u0013\u0010\u001a\u001a\u00020\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001d\u001a\u00020\u0003H\u00d6\u0001J\t\u0010\u001e\u001a\u00020\u001fH\u00d6\u0001R\u001a\u0010\u0005\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\f\u001a\u0004\b\n\u0010\u000bR\u001a\u0010\u0004\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\f\u001a\u0004\b\r\u0010\u000bR\u001a\u0010\u0002\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\f\u001a\u0004\b\u000e\u0010\u000bR\u001a\u0010\u0006\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\f\u001a\u0004\b\u000f\u0010\u000bR\u001a\u0010\u0007\u001a\u0004\u0018\u00010\b8\u0006X\u0087\u0004\u00a2\u0006\n\n\u0002\u0010\u0012\u001a\u0004\b\u0010\u0010\u0011\u00a8\u0006 "}, d2 = {"Lcom/sensacare/veepoo/rpm/VitalsData;", "", "heartRate", "", "bloodPressureSystolic", "bloodPressureDiastolic", "spo2", "temperature", "", "(Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Float;)V", "getBloodPressureDiastolic", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getBloodPressureSystolic", "getHeartRate", "getSpo2", "getTemperature", "()Ljava/lang/Float;", "Ljava/lang/Float;", "component1", "component2", "component3", "component4", "component5", "copy", "(Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Float;)Lcom/sensacare/veepoo/rpm/VitalsData;", "equals", "", "other", "hashCode", "toString", "", "app_debug"})
public final class VitalsData {
    @com.google.gson.annotations.SerializedName(value = "hr")
    @org.jetbrains.annotations.Nullable
    private final java.lang.Integer heartRate = null;
    @com.google.gson.annotations.SerializedName(value = "bp_systolic")
    @org.jetbrains.annotations.Nullable
    private final java.lang.Integer bloodPressureSystolic = null;
    @com.google.gson.annotations.SerializedName(value = "bp_diastolic")
    @org.jetbrains.annotations.Nullable
    private final java.lang.Integer bloodPressureDiastolic = null;
    @com.google.gson.annotations.SerializedName(value = "spo2")
    @org.jetbrains.annotations.Nullable
    private final java.lang.Integer spo2 = null;
    @com.google.gson.annotations.SerializedName(value = "temperature")
    @org.jetbrains.annotations.Nullable
    private final java.lang.Float temperature = null;
    
    public VitalsData(@org.jetbrains.annotations.Nullable
    java.lang.Integer heartRate, @org.jetbrains.annotations.Nullable
    java.lang.Integer bloodPressureSystolic, @org.jetbrains.annotations.Nullable
    java.lang.Integer bloodPressureDiastolic, @org.jetbrains.annotations.Nullable
    java.lang.Integer spo2, @org.jetbrains.annotations.Nullable
    java.lang.Float temperature) {
        super();
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
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Integer component1() {
        return null;
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
    public final java.lang.Float component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.sensacare.veepoo.rpm.VitalsData copy(@org.jetbrains.annotations.Nullable
    java.lang.Integer heartRate, @org.jetbrains.annotations.Nullable
    java.lang.Integer bloodPressureSystolic, @org.jetbrains.annotations.Nullable
    java.lang.Integer bloodPressureDiastolic, @org.jetbrains.annotations.Nullable
    java.lang.Integer spo2, @org.jetbrains.annotations.Nullable
    java.lang.Float temperature) {
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