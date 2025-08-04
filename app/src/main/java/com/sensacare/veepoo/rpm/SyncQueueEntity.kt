package com.sensacare.veepoo.rpm

import com.sensacare.veepoo.room.Entity
import com.sensacare.veepoo.room.PrimaryKey

@Entity(tableName = "sync_queue")
data class SyncQueueEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    val payload: String, // JSON serialized VitalsUploadPayload
    val timestamp: Long,
    val retryCount: Int = 0,
    val lastAttempt: Long = 0,
    val errorMessage: String? = null,
    val priority: Int = 1 // 1 = normal, 2 = high, 3 = critical
) 