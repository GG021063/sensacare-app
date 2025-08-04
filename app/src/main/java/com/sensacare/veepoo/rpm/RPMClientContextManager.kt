package com.sensacare.veepoo.rpm

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class RPMClientContextManager(private val context: Context) {
    
    companion object {
        private const val PREFS_NAME = "rpm_client_prefs"
        private const val KEY_CLIENT_ID = "client_id"
        private const val KEY_USER_TOKEN = "user_token"
        private const val KEY_CONSENT_TOKEN = "consent_token"
        private const val KEY_THRESHOLDS = "thresholds"
        private const val KEY_CURRENT_PROFILE = "current_profile"
        private const val KEY_PROFILES = "profiles"
        private const val KEY_LAST_SYNC = "last_sync"
        private const val KEY_DEVICE_METADATA = "device_metadata"
    }
    
    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()
    
    private val securePrefs = EncryptedSharedPreferences.create(
        context,
        PREFS_NAME,
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
    
    private val gson = Gson()
    
    // State flows for reactive updates
    private val _currentClientId = MutableStateFlow<String?>(null)
    val currentClientId: StateFlow<String?> = _currentClientId.asStateFlow()
    
    private val _thresholds = MutableStateFlow<ClientThresholds?>(null)
    val thresholds: StateFlow<ClientThresholds?> = _thresholds.asStateFlow()
    
    private val _isConnected = MutableStateFlow(false)
    val isConnected: StateFlow<Boolean> = _isConnected.asStateFlow()
    
    init {
        // Load current client ID on initialization
        _currentClientId.value = getClientId()
        _thresholds.value = getThresholds()
    }
    
    // Client ID management
    fun setClientId(clientId: String) {
        securePrefs.edit().putString(KEY_CLIENT_ID, clientId).apply()
        _currentClientId.value = clientId
    }
    
    fun getClientId(): String? {
        return securePrefs.getString(KEY_CLIENT_ID, null)
    }
    
    fun hasClientId(): Boolean {
        return getClientId() != null
    }
    
    // User token management
    fun setUserToken(token: String) {
        securePrefs.edit().putString(KEY_USER_TOKEN, token).apply()
    }
    
    fun getUserToken(): String? {
        return securePrefs.getString(KEY_USER_TOKEN, null)
    }
    
    // Consent token management
    fun setConsentToken(token: String) {
        securePrefs.edit().putString(KEY_CONSENT_TOKEN, token).apply()
    }
    
    fun getConsentToken(): String? {
        return securePrefs.getString(KEY_CONSENT_TOKEN, null)
    }
    
    fun hasValidConsent(): Boolean {
        return getConsentToken() != null
    }
    
    // Thresholds management
    fun setThresholds(thresholds: ClientThresholds) {
        val thresholdsJson = gson.toJson(thresholds)
        securePrefs.edit().putString(KEY_THRESHOLDS, thresholdsJson).apply()
        _thresholds.value = thresholds
    }
    
    fun getThresholds(): ClientThresholds? {
        val thresholdsJson = securePrefs.getString(KEY_THRESHOLDS, null)
        return if (thresholdsJson != null) {
            try {
                gson.fromJson(thresholdsJson, ClientThresholds::class.java)
            } catch (e: Exception) {
                null
            }
        } else null
    }
    
    // Multi-profile support
    data class ClientProfile(
        val clientId: String,
        val clientName: String,
        val userToken: String,
        val consentToken: String,
        val thresholds: ClientThresholds? = null,
        val createdAt: Long = System.currentTimeMillis()
    )
    
    fun addProfile(profile: ClientProfile) {
        val profiles = getProfiles().toMutableList()
        profiles.removeAll { it.clientId == profile.clientId }
        profiles.add(profile)
        saveProfiles(profiles)
    }
    
    fun removeProfile(clientId: String) {
        val profiles = getProfiles().toMutableList()
        profiles.removeAll { it.clientId == clientId }
        saveProfiles(profiles)
        
        // If removing current profile, clear current context
        if (getClientId() == clientId) {
            clearCurrentContext()
        }
    }
    
    fun getProfiles(): List<ClientProfile> {
        val profilesJson = securePrefs.getString(KEY_PROFILES, "[]")
        return try {
            val type = object : TypeToken<List<ClientProfile>>() {}.type
            gson.fromJson(profilesJson, type) ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }
    
    private fun saveProfiles(profiles: List<ClientProfile>) {
        val profilesJson = gson.toJson(profiles)
        securePrefs.edit().putString(KEY_PROFILES, profilesJson).apply()
    }
    
    fun switchToProfile(clientId: String): Boolean {
        val profile = getProfiles().find { it.clientId == clientId }
        return if (profile != null) {
            setClientId(profile.clientId)
            setUserToken(profile.userToken)
            setConsentToken(profile.consentToken)
            profile.thresholds?.let { setThresholds(it) }
            securePrefs.edit().putString(KEY_CURRENT_PROFILE, clientId).apply()
            true
        } else false
    }
    
    fun getCurrentProfile(): ClientProfile? {
        val clientId = getClientId() ?: return null
        return getProfiles().find { it.clientId == clientId }
    }
    
    // Device metadata management
    data class DeviceMetadata(
        val type: String,
        val mac: String,
        val firmware: String,
        val battery: Int,
        val sdkVersion: String
    )
    
    fun setDeviceMetadata(metadata: DeviceMetadata) {
        val metadataJson = gson.toJson(metadata)
        securePrefs.edit().putString(KEY_DEVICE_METADATA, metadataJson).apply()
    }
    
    fun getDeviceMetadata(): DeviceMetadata? {
        val metadataJson = securePrefs.getString(KEY_DEVICE_METADATA, null)
        return if (metadataJson != null) {
            try {
                gson.fromJson(metadataJson, DeviceMetadata::class.java)
            } catch (e: Exception) {
                null
            }
        } else null
    }
    
    // Sync tracking
    fun setLastSync(timestamp: Long) {
        securePrefs.edit().putLong(KEY_LAST_SYNC, timestamp).apply()
    }
    
    fun getLastSync(): Long {
        return securePrefs.getLong(KEY_LAST_SYNC, 0L)
    }
    
    // Connection status
    fun setConnected(connected: Boolean) {
        _isConnected.value = connected
    }
    
    // Context validation
    fun isContextValid(): Boolean {
        return hasClientId() && getUserToken() != null && hasValidConsent()
    }
    
    // Clear current context (for logout or profile switch)
    fun clearCurrentContext() {
        securePrefs.edit()
            .remove(KEY_CLIENT_ID)
            .remove(KEY_USER_TOKEN)
            .remove(KEY_CONSENT_TOKEN)
            .remove(KEY_THRESHOLDS)
            .remove(KEY_CURRENT_PROFILE)
            .apply()
        
        _currentClientId.value = null
        _thresholds.value = null
        _isConnected.value = false
    }
    
    // Clear all data (for complete reset)
    fun clearAllData() {
        securePrefs.edit().clear().apply()
        _currentClientId.value = null
        _thresholds.value = null
        _isConnected.value = false
    }
} 