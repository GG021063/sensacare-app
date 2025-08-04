package com.sensacare.veepoo

import com.sensacare.veepoo.room.Dao
import com.sensacare.veepoo.room.Insert
import com.sensacare.veepoo.room.Query

@Dao
interface VitalsDao {
    @Insert
    suspend fun insert(vitals: VitalsEntity)

    @Query("SELECT * FROM vitals ORDER BY timestamp DESC LIMIT 50")
    suspend fun getRecentVitals(): List<VitalsEntity>

    @Query("SELECT * FROM vitals WHERE timestamp >= :startTime ORDER BY timestamp ASC")
    suspend fun getVitalsFromTime(startTime: Long): List<VitalsEntity>

    @Query("SELECT * FROM vitals WHERE timestamp >= :startTime AND timestamp <= :endTime ORDER BY timestamp DESC")
    suspend fun getVitalsByDateRange(startTime: Long, endTime: Long): List<VitalsEntity>

    @Query("SELECT * FROM vitals WHERE timestamp >= :startTime AND timestamp <= :endTime ORDER BY timestamp DESC LIMIT :limit OFFSET :offset")
    suspend fun getVitalsWithPagination(startTime: Long, endTime: Long, limit: Int, offset: Int): List<VitalsEntity>

    @Query("SELECT COUNT(*) FROM vitals WHERE timestamp >= :startTime AND timestamp <= :endTime")
    suspend fun getVitalsCount(startTime: Long, endTime: Long): Int
}
