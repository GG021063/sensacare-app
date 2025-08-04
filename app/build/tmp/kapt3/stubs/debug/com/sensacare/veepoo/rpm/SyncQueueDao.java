package com.sensacare.veepoo.rpm;

@com.sensacare.veepoo.room.Dao
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\r\n\u0002\u0010\u000e\n\u0002\b\u0004\bg\u0018\u00002\u00020\u0001J\u0019\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006J\u001b\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\tH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\nJ\u0019\u0010\u000b\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\rH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000eJ\u0014\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00110\u0010H\'J\u001b\u0010\u0012\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0013\u001a\u00020\rH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000eJ\u001b\u0010\u0014\u001a\u00020\t2\b\b\u0002\u0010\b\u001a\u00020\tH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\nJ\u0013\u0010\u0015\u001a\u0004\u0018\u00010\rH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0016J\u0013\u0010\u0017\u001a\u0004\u0018\u00010\rH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0016J+\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00050\u00112\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\u0019\u001a\u00020\tH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001aJ\u0011\u0010\u001b\u001a\u00020\tH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0016J+\u0010\u001c\u001a\u00020\u00032\u0006\u0010\u0013\u001a\u00020\r2\u0006\u0010\u001d\u001a\u00020\r2\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010 J\u0019\u0010!\u001a\u00020\r2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006J\u0019\u0010\"\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006#"}, d2 = {"Lcom/sensacare/veepoo/rpm/SyncQueueDao;", "", "delete", "", "syncQueueEntity", "Lcom/sensacare/veepoo/rpm/SyncQueueEntity;", "(Lcom/sensacare/veepoo/rpm/SyncQueueEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteFailedItems", "maxRetries", "", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteOldItems", "cutoffTime", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllQueuedItems", "Lkotlinx/coroutines/flow/Flow;", "", "getById", "id", "getFailedItemsCount", "getNewestItemTimestamp", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getOldestItemTimestamp", "getPendingItems", "limit", "(IILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getQueueSize", "incrementRetryCount", "attemptTime", "errorMessage", "", "(JJLjava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insert", "update", "app_debug"})
public abstract interface SyncQueueDao {
    
    @com.sensacare.veepoo.room.Insert
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object insert(@org.jetbrains.annotations.NotNull
    com.sensacare.veepoo.rpm.SyncQueueEntity syncQueueEntity, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @com.sensacare.veepoo.room.Update
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object update(@org.jetbrains.annotations.NotNull
    com.sensacare.veepoo.rpm.SyncQueueEntity syncQueueEntity, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @com.sensacare.veepoo.room.Delete
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object delete(@org.jetbrains.annotations.NotNull
    com.sensacare.veepoo.rpm.SyncQueueEntity syncQueueEntity, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @com.sensacare.veepoo.room.Query(value = "SELECT * FROM sync_queue ORDER BY priority DESC, timestamp ASC")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.sensacare.veepoo.rpm.SyncQueueEntity>> getAllQueuedItems();
    
    @com.sensacare.veepoo.room.Query(value = "SELECT * FROM sync_queue WHERE retryCount < :maxRetries ORDER BY priority DESC, timestamp ASC LIMIT :limit")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getPendingItems(int maxRetries, int limit, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.util.List<com.sensacare.veepoo.rpm.SyncQueueEntity>> $completion);
    
    @com.sensacare.veepoo.room.Query(value = "SELECT COUNT(*) FROM sync_queue")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getQueueSize(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    @com.sensacare.veepoo.room.Query(value = "SELECT COUNT(*) FROM sync_queue WHERE retryCount >= :maxRetries")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getFailedItemsCount(int maxRetries, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    @com.sensacare.veepoo.room.Query(value = "DELETE FROM sync_queue WHERE retryCount >= :maxRetries")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object deleteFailedItems(int maxRetries, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @com.sensacare.veepoo.room.Query(value = "DELETE FROM sync_queue WHERE timestamp < :cutoffTime")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object deleteOldItems(long cutoffTime, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @com.sensacare.veepoo.room.Query(value = "UPDATE sync_queue SET retryCount = retryCount + 1, lastAttempt = :attemptTime, errorMessage = :errorMessage WHERE id = :id")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object incrementRetryCount(long id, long attemptTime, @org.jetbrains.annotations.Nullable
    java.lang.String errorMessage, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @com.sensacare.veepoo.room.Query(value = "SELECT * FROM sync_queue WHERE id = :id")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getById(long id, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.sensacare.veepoo.rpm.SyncQueueEntity> $completion);
    
    @com.sensacare.veepoo.room.Query(value = "SELECT MIN(timestamp) FROM sync_queue")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getOldestItemTimestamp(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @com.sensacare.veepoo.room.Query(value = "SELECT MAX(timestamp) FROM sync_queue")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getNewestItemTimestamp(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}