package com.sensacare.veepoo;

@com.sensacare.veepoo.room.Dao
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0003\bg\u0018\u00002\u00020\u0001J\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0005J\'\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\bH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\nJ!\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\bH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\nJ\u001f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0007\u001a\u00020\bH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000eJ7\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\f2\u0006\u0010\u0011\u001a\u00020\fH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0012J\u0019\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0004H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0016\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0017"}, d2 = {"Lcom/sensacare/veepoo/VitalsDao;", "", "getRecentVitals", "", "Lcom/sensacare/veepoo/VitalsEntity;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getVitalsByDateRange", "startTime", "", "endTime", "(JJLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getVitalsCount", "", "getVitalsFromTime", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getVitalsWithPagination", "limit", "offset", "(JJIILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insert", "", "vitals", "(Lcom/sensacare/veepoo/VitalsEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public abstract interface VitalsDao {
    
    @com.sensacare.veepoo.room.Insert
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object insert(@org.jetbrains.annotations.NotNull
    com.sensacare.veepoo.VitalsEntity vitals, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @com.sensacare.veepoo.room.Query(value = "SELECT * FROM vitals ORDER BY timestamp DESC LIMIT 50")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getRecentVitals(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.util.List<com.sensacare.veepoo.VitalsEntity>> $completion);
    
    @com.sensacare.veepoo.room.Query(value = "SELECT * FROM vitals WHERE timestamp >= :startTime ORDER BY timestamp ASC")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getVitalsFromTime(long startTime, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.util.List<com.sensacare.veepoo.VitalsEntity>> $completion);
    
    @com.sensacare.veepoo.room.Query(value = "SELECT * FROM vitals WHERE timestamp >= :startTime AND timestamp <= :endTime ORDER BY timestamp DESC")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getVitalsByDateRange(long startTime, long endTime, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.util.List<com.sensacare.veepoo.VitalsEntity>> $completion);
    
    @com.sensacare.veepoo.room.Query(value = "SELECT * FROM vitals WHERE timestamp >= :startTime AND timestamp <= :endTime ORDER BY timestamp DESC LIMIT :limit OFFSET :offset")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getVitalsWithPagination(long startTime, long endTime, int limit, int offset, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.util.List<com.sensacare.veepoo.VitalsEntity>> $completion);
    
    @com.sensacare.veepoo.room.Query(value = "SELECT COUNT(*) FROM vitals WHERE timestamp >= :startTime AND timestamp <= :endTime")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getVitalsCount(long startTime, long endTime, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
}