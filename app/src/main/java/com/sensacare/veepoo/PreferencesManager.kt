package com.sensacare.veepoo

import android.content.Context
import android.content.SharedPreferences

class PreferencesManager(context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        "SensacareAppPrefs", Context.MODE_PRIVATE
    )

    // Onboarding
    var isOnboardingCompleted: Boolean
        get() = sharedPreferences.getBoolean("onboarding_completed", false)
        set(value) = sharedPreferences.edit().putBoolean("onboarding_completed", value).apply()

    // Device Information
    var pairedDeviceName: String?
        get() = sharedPreferences.getString("paired_device_name", null)
        set(value) = sharedPreferences.edit().putString("paired_device_name", value).apply()

    var pairedDeviceAddress: String?
        get() = sharedPreferences.getString("paired_device_address", null)
        set(value) = sharedPreferences.edit().putString("paired_device_address", value).apply()

    var onboardingTimestamp: Long
        get() = sharedPreferences.getLong("onboarding_timestamp", 0L)
        set(value) = sharedPreferences.edit().putLong("onboarding_timestamp", value).apply()

    // Vital Sign Thresholds
    var heartRateMinThreshold: Int
        get() = sharedPreferences.getInt("hr_min_threshold", 60)
        set(value) = sharedPreferences.edit().putInt("hr_min_threshold", value).apply()

    var heartRateMaxThreshold: Int
        get() = sharedPreferences.getInt("hr_max_threshold", 100)
        set(value) = sharedPreferences.edit().putInt("hr_max_threshold", value).apply()

    var spo2MinThreshold: Int
        get() = sharedPreferences.getInt("spo2_min_threshold", 95)
        set(value) = sharedPreferences.edit().putInt("spo2_min_threshold", value).apply()

    var bloodPressureSystolicMaxThreshold: Int
        get() = sharedPreferences.getInt("bp_systolic_max_threshold", 140)
        set(value) = sharedPreferences.edit().putInt("bp_systolic_max_threshold", value).apply()

    var bloodPressureDiastolicMaxThreshold: Int
        get() = sharedPreferences.getInt("bp_diastolic_max_threshold", 90)
        set(value) = sharedPreferences.edit().putInt("bp_diastolic_max_threshold", value).apply()

    var temperatureMaxThreshold: Float
        get() = sharedPreferences.getFloat("temp_max_threshold", 37.5f)
        set(value) = sharedPreferences.edit().putFloat("temp_max_threshold", value).apply()

    // App Settings
    var isBackgroundServiceEnabled: Boolean
        get() = sharedPreferences.getBoolean("background_service_enabled", true)
        set(value) = sharedPreferences.edit().putBoolean("background_service_enabled", value).apply()

    var isNotificationsEnabled: Boolean
        get() = sharedPreferences.getBoolean("notifications_enabled", true)
        set(value) = sharedPreferences.edit().putBoolean("notifications_enabled", value).apply()

    var isAutoReconnectEnabled: Boolean
        get() = sharedPreferences.getBoolean("auto_reconnect_enabled", true)
        set(value) = sharedPreferences.edit().putBoolean("auto_reconnect_enabled", value).apply()

    // Data Upload Settings
    var uploadIntervalSeconds: Int
        get() = sharedPreferences.getInt("upload_interval_seconds", 30)
        set(value) = sharedPreferences.edit().putInt("upload_interval_seconds", value).apply()

    var isDataUploadEnabled: Boolean
        get() = sharedPreferences.getBoolean("data_upload_enabled", true)
        set(value) = sharedPreferences.edit().putBoolean("data_upload_enabled", value).apply()

    // Clear all preferences (for testing/reset)
    fun clearAllPreferences() {
        sharedPreferences.edit().clear().apply()
    }

    // Check if device is paired
    fun isDevicePaired(): Boolean {
        return !pairedDeviceAddress.isNullOrEmpty()
    }

    // Get device info as string
    fun getDeviceInfo(): String {
        return "${pairedDeviceName ?: "Unknown"} (${pairedDeviceAddress ?: "Not paired"})"
    }

    // Check if vitals are within normal ranges
    fun checkVitalsThresholds(
        heartRate: Int?,
        spo2: Int?,
        systolicBP: Int?,
        diastolicBP: Int?,
        temperature: Float?
    ): VitalsAlertStatus {
        val alerts = mutableListOf<String>()

        heartRate?.let { hr ->
            when {
                hr < heartRateMinThreshold -> alerts.add("Heart rate too low: ${hr} bpm")
                hr > heartRateMaxThreshold -> alerts.add("Heart rate too high: ${hr} bpm")
            }
        }

        spo2?.let { s ->
            if (s < spo2MinThreshold) {
                alerts.add("SpO2 too low: ${s}%")
            }
        }

        systolicBP?.let { sbp ->
            if (sbp > bloodPressureSystolicMaxThreshold) {
                alerts.add("Systolic BP too high: ${sbp} mmHg")
            }
        }

        diastolicBP?.let { dbp ->
            if (dbp > bloodPressureDiastolicMaxThreshold) {
                alerts.add("Diastolic BP too high: ${dbp} mmHg")
            }
        }

        temperature?.let { temp ->
            if (temp > temperatureMaxThreshold) {
                alerts.add("Temperature too high: ${temp}°C")
            }
        }

        return VitalsAlertStatus(
            hasAlerts = alerts.isNotEmpty(),
            alerts = alerts
        )
    }

    data class VitalsAlertStatus(
        val hasAlerts: Boolean,
        val alerts: List<String>
    )
} 