package com.sensacare.veepoo

import com.sensacare.veepoo.room.Entity
import com.sensacare.veepoo.room.PrimaryKey

@Entity(tableName = "vitals")
data class VitalsEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val heartRate: Int?,
    val bloodPressureSystolic: Int?,
    val bloodPressureDiastolic: Int?,
    val spo2: Int?,
    val temperature: Float?,
    val timestamp: Long
)
