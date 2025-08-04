package com.sensacare.veepoo

data class VitalsData(
    val heartRate: Int?,
    val bloodPressureSystolic: Int?,
    val bloodPressureDiastolic: Int?,
    val spo2: Int?,
    val temperature: Float?,
    val timestamp: Long = System.currentTimeMillis()
)
