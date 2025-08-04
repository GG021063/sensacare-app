package com.sensacare.veepoo.rpm

import com.sensacare.veepoo.room.*     // use stubbed Room annotations/classes
import kotlinx.coroutines.flow.Flow

@Dao
interface SyncQueueDao {
    
    @Insert
    suspend fun insert(syncQueueEntity: SyncQueueEntity): Long
    
    @Update
    suspend fun update(syncQueueEntity: SyncQueueEntity)
    
    @Delete
    suspend fun delete(syncQueueEntity: SyncQueueEntity)
    
    @Query("SELECT * FROM sync_queue ORDER BY priority DESC, timestamp ASC")
    fun getAllQueuedItems(): Flow<List<SyncQueueEntity>>
    
    @Query("SELECT * FROM sync_queue WHERE retryCount < :maxRetries ORDER BY priority DESC, timestamp ASC LIMIT :limit")
    suspend fun getPendingItems(maxRetries: Int = 3, limit: Int = 10): List<SyncQueueEntity>
    
    @Query("SELECT COUNT(*) FROM sync_queue")
    suspend fun getQueueSize(): Int
    
    @Query("SELECT COUNT(*) FROM sync_queue WHERE retryCount >= :maxRetries")
    suspend fun getFailedItemsCount(maxRetries: Int = 3): Int
    
    @Query("DELETE FROM sync_queue WHERE retryCount >= :maxRetries")
    suspend fun deleteFailedItems(maxRetries: Int = 3)
    
    @Query("DELETE FROM sync_queue WHERE timestamp < :cutoffTime")
    suspend fun deleteOldItems(cutoffTime: Long)
    
    @Query("UPDATE sync_queue SET retryCount = retryCount + 1, lastAttempt = :attemptTime, errorMessage = :errorMessage WHERE id = :id")
    suspend fun incrementRetryCount(id: Long, attemptTime: Long, errorMessage: String?)
    
    @Query("SELECT * FROM sync_queue WHERE id = :id")
    suspend fun getById(id: Long): SyncQueueEntity?
    
    @Query("SELECT MIN(timestamp) FROM sync_queue")
    suspend fun getOldestItemTimestamp(): Long?
    
    @Query("SELECT MAX(timestamp) FROM sync_queue")
    suspend fun getNewestItemTimestamp(): Long?
} 