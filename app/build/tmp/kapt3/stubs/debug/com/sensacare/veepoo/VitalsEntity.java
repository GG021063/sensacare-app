package com.sensacare.veepoo;

@com.sensacare.veepoo.room.Entity(tableName = "vitals")
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\t\n\u0002\b\u0018\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0087\b\u0018\u00002\u00020\u0001BI\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u00a2\u0006\u0002\u0010\fJ\t\u0010\u001a\u001a\u00020\u0003H\u00c6\u0003J\u0010\u0010\u001b\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u000eJ\u0010\u0010\u001c\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u000eJ\u0010\u0010\u001d\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u000eJ\u0010\u0010\u001e\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u000eJ\u0010\u0010\u001f\u001a\u0004\u0018\u00010\tH\u00c6\u0003\u00a2\u0006\u0002\u0010\u0016J\t\u0010 \u001a\u00020\u000bH\u00c6\u0003J^\u0010!\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t2\b\b\u0002\u0010\n\u001a\u00020\u000bH\u00c6\u0001\u00a2\u0006\u0002\u0010\"J\u0013\u0010#\u001a\u00020$2\b\u0010%\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010&\u001a\u00020\u0003H\u00d6\u0001J\t\u0010\'\u001a\u00020(H\u00d6\u0001R\u0015\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010\u000f\u001a\u0004\b\r\u0010\u000eR\u0015\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010\u000f\u001a\u0004\b\u0010\u0010\u000eR\u0015\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010\u000f\u001a\u0004\b\u0011\u0010\u000eR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0015\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010\u000f\u001a\u0004\b\u0014\u0010\u000eR\u0015\u0010\b\u001a\u0004\u0018\u00010\t\u00a2\u0006\n\n\u0002\u0010\u0017\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\n\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019\u00a8\u0006)"}, d2 = {"Lcom/sensacare/veepoo/VitalsEntity;", "", "id", "", "heartRate", "bloodPressureSystolic", "bloodPressureDiastolic", "spo2", "temperature", "", "timestamp", "", "(ILjava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Float;J)V", "getBloodPressureDiastolic", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getBloodPressureSystolic", "getHeartRate", "getId", "()I", "getSpo2", "getTemperature", "()Ljava/lang/Float;", "Ljava/lang/Float;", "getTimestamp", "()J", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "copy", "(ILjava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Float;J)Lcom/sensacare/veepoo/VitalsEntity;", "equals", "", "other", "hashCode", "toString", "", "app_debug"})
public final class VitalsEntity {
    @com.sensacare.veepoo.room.PrimaryKey(autoGenerate = true)
    private final int id = 0;
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
    
    public VitalsEntity(int id, @org.jetbrains.annotations.Nullable
    java.lang.Integer heartRate, @org.jetbrains.annotations.Nullable
    java.lang.Integer bloodPressureSystolic, @org.jetbrains.annotations.Nullable
    java.lang.Integer bloodPressureDiastolic, @org.jetbrains.annotations.Nullable
    java.lang.Integer spo2, @org.jetbrains.annotations.Nullable
    java.lang.Float temperature, long timestamp) {
        super();
    }
    
    public final int getId() {
        return 0;
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
    
    public final int component1() {
        return 0;
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
    public final com.sensacare.veepoo.VitalsEntity copy(int id, @org.jetbrains.annotations.Nullable
    java.lang.Integer heartRate, @org.jetbrains.annotations.Nullable
    java.lang.Integer bloodPressureSystolic, @org.jetbrains.annotations.Nullable
    java.lang.Integer bloodPressureDiastolic, @org.jetbrains.annotations.Nullable
    java.lang.Integer spo2, @org.jetbrains.annotations.Nullable
    java.lang.Float temperature, long timestamp) {
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