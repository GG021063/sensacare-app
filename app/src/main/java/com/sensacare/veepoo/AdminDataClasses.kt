package com.sensacare.veepoo

import com.sensacare.veepoo.VitalsAlertManager.AlertLevel
import com.sensacare.veepoo.DiagnosticLogger.LogLevel

data class AdminVitalsData(
    val id: Long,
    val heartRate: Int?,
    val bloodPressureSystolic: Int?,
    val bloodPressureDiastolic: Int?,
    val spo2: Int?,
    val temperature: Float?,
    val timestamp: Long,
    val alertLevel: AlertLevel
)

data class AdminAlertData(
    val id: Long,
    val vitalType: String,
    val value: String,
    val threshold: String,
    val alertLevel: AlertLevel,
    val timestamp: Long
)

data class AdminEventData(
    val id: Long,
    val eventType: String,
    val description: String,
    val details: String?,
    val level: LogLevel,
    val timestamp: Long
) 