package com.sensacare.veepoo

import android.content.Context
import com.sensacare.veepoo.room.Database
import com.sensacare.veepoo.room.Room
import com.sensacare.veepoo.room.RoomDatabase
import com.sensacare.veepoo.rpm.SyncQueueEntity

@Database(
    entities = [
        VitalsEntity::class,
        SyncQueueEntity::class
    ], 
    version = 2
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun vitalsDao(): VitalsDao
    abstract fun syncQueueDao(): com.sensacare.veepoo.rpm.SyncQueueDao

    companion object {
        @Volatile private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java, "sensacare.db"
                )
                .fallbackToDestructiveMigration() // For development - in production, implement proper migration
                .build().also { instance = it }
            }
    }
}
