package com.sensacare.veepoo

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.util.Log
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.gotrue.SessionStatus
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.gotrue.user.UserInfo
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.Count
import io.github.jan.supabase.postgrest.query.Order
import io.github.jan.supabase.storage.Storage
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.util.*
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.coroutines.CoroutineContext

/**
 * SupabaseManager - Comprehensive manager for all Supabase interactions
 * Handles authentication, database operations, storage, and offline sync
 */
class SupabaseManager(private val context: Context) : CoroutineScope {
    
    companion object {
        private const val TAG = "SupabaseManager"
        private const val ENCRYPTED_PREFS_NAME = "sensacare_secure_prefs"
        private const val KEY_SESSION = "supabase_session"
        private const val KEY_USER_ID = "user_id"
        private const val KEY_USER_EMAIL = "user_email"
        private const val KEY_DEVICE_ID = "device_id"
        
        // Supabase project details
        private const val SUPABASE_URL = "https://mvyxacmcmpkogqnoiout.supabase.co"
        private const val SUPABASE_ANON_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Im12eXhhY21jbXBrb2dxbm9pb3V0Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTIwOTYwOTQsImV4cCI6MjA2NzY3MjA5NH0.CYRv3RyiNSvvpX518f401TeNlv59UtD5x9Y4mG0j550"
        
        // Demo user credentials
        private const val DEMO_EMAIL = "admin@sensacare.com"
        private const val DEMO_PASSWORD = "admin123"
        
        // Sync configuration
        private const val MAX_SYNC_BATCH_SIZE = 50
        private const val MAX_SYNC_RETRY_ATTEMPTS = 3
        private const val SYNC_RETRY_DELAY_MS = 5000L
    }
    
    // Coroutine scope for background operations
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext = job + Dispatchers.IO
    
    // Encrypted storage for sensitive data
    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()
    
    private val encryptedPrefs = EncryptedSharedPreferences.create(
        context,
        ENCRYPTED_PREFS_NAME,
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
    
    // Supabase client
    private val supabase = createSupabaseClient(
        supabaseUrl = SUPABASE_URL,
        supabaseKey = SUPABASE_ANON_KEY
    ) {
        install(Auth)
        install(Postgrest)
        install(Storage)
    }
    
    // Network monitoring
    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private var isNetworkAvailable = AtomicBoolean(false)
    private val syncQueue = ConcurrentLinkedQueue<SyncOperation>()
    private var isSyncInProgress = AtomicBoolean(false)
    
    // Session state
    private val _authState = MutableStateFlow<AuthState>(AuthState.Loading)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()
    
    init {
        // Initialize network monitoring
        setupNetworkMonitoring()
        
        // Initialize auth state monitoring
        launch {
            supabase.auth.sessionStatus.collect { status ->
                when (status) {
                    is SessionStatus.Authenticated -> {
                        val user = supabase.auth.currentUserOrNull()
                        if (user != null) {
                            saveUserSession(user)
                            _authState.value = AuthState.Authenticated(user)
                        }
                    }
                    is SessionStatus.LoadingFromStorage -> {
                        _authState.value = AuthState.Loading
                    }
                    is SessionStatus.NetworkError -> {
                        // Check if we have a cached session
                        val cachedUserId = encryptedPrefs.getString(KEY_USER_ID, null)
                        val cachedEmail = encryptedPrefs.getString(KEY_USER_EMAIL, null)
                        
                        if (cachedUserId != null && cachedEmail != null) {
                            _authState.value = AuthState.AuthenticatedOffline(cachedUserId, cachedEmail)
                        } else {
                            _authState.value = AuthState.Error("Network error: Unable to connect to authentication service")
                        }
                    }
                    else -> {
                        _authState.value = AuthState.NotAuthenticated
                    }
                }
            }
        }
    }
    
    /**
     * Set up network monitoring to detect connectivity changes
     */
    private fun setupNetworkMonitoring() {
        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                Log.d(TAG, "Network available")
                isNetworkAvailable.set(true)
                // Process sync queue when network becomes available
                processSyncQueue()
            }

            override fun onLost(network: Network) {
                Log.d(TAG, "Network lost")
                isNetworkAvailable.set(false)
            }

            override fun onCapabilitiesChanged(network: Network, capabilities: NetworkCapabilities) {
                val hasInternet = capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                val hasValidated = capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
                Log.d(TAG, "Network capabilities changed: Internet=$hasInternet, Validated=$hasValidated")
            }
        }
        
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
        
        // Initial network check
        val activeNetwork = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
        isNetworkAvailable.set(capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true)
    }

    /**
     * Send OTP e-mail using password reset flow (compatible with Supabase 2.0.4)
     */
    suspend fun signInWithOtp(email: String): Result<Unit> {
        return try {
            // Use password reset flow to send OTP code
            supabase.auth.resetPasswordForEmail(email)
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "Send OTP failed", e)
            Result.failure(e)
        }
    }

    /**
     * Verify OTP code from email using password reset flow (compatible with Supabase 2.0.4)
     */
    suspend fun verifyOtp(email: String, token: String): Result<UserInfo> {
        return try {
            // Use the token as a temporary password for sign-in
            // This works when the password reset email template includes the token
            supabase.auth.signInWith(Email) {
                this.email = email
                this.password = token
            }

            val user = supabase.auth.currentUserOrNull()
                ?: return Result.failure(Exception("Authentication failed"))

            // Lazily create profile if missing
            ensureUserProfileExists()

            Result.success(user)
        } catch (e: Exception) {
            Log.e(TAG, "OTP verification failed", e)
            Result.failure(e)
        }
    }
    
    /**
     * Sign in with email and password
     */
    suspend fun signIn(email: String, password: String): Result<UserInfo> {
        return try {
            // Sign in with email and password
            val authResponse = supabase.auth.signInWith(Email) {
                this.email = email
                this.password = password
            }
            
            // Get the current user info after successful sign-in
            val user = supabase.auth.currentUserOrNull()
                ?: return Result.failure(Exception("Authentication succeeded but no user found"))
            
            // Create user profile if it doesn't exist yet
            ensureUserProfileExists()
            
            Result.success(user)
        } catch (e: Exception) {
            Log.e(TAG, "Sign in failed", e)
            Result.failure(e)
        }
    }
    
    /**
     * Sign up with email and password
     */
    suspend fun signUp(email: String, password: String): Result<UserInfo> {
        return try {
            // Sign up with email and password
            supabase.auth.signUpWith(Email) {
                this.email = email
                this.password = password
            }
            
            // Get the current user info after successful sign-up
            val user = supabase.auth.currentUserOrNull()
                ?: return Result.failure(Exception("Sign up succeeded but no user found"))
            
            /*
             * IMPORTANT:
             * ------------------------------------------------------------
             * Creating the user_profile record here causes a "Database error
             * saving new user" because RLS policies on `user_profiles`
             * require the auth.user row to exist AND a valid JWT that
             * already belongs to that user.
             *
             * During the sign-up request `Gotrue` has not finished inserting
             * the auth.users row yet, so PostgREST blocks our insert.
             *
             * Solution:  
             *   1. Let Supabase finish creating the auth user first.  
             *   2. We will create the profile lazily ‑ either on first
             *      login (`ensureUserProfileExists()`) or from a dedicated
             *      profile-setup screen.
             */
            
            Result.success(user)
        } catch (e: Exception) {
            Log.e(TAG, "Sign up failed", e)
            Result.failure(e)
        }
    }
    
    /**
     * Sign out the current user
     */
    suspend fun signOut(): Result<Unit> {
        return try {
            supabase.auth.signOut()
            clearUserSession()
            _authState.value = AuthState.NotAuthenticated
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "Sign out failed", e)
            Result.failure(e)
        }
    }
    
    /**
     * Reset password for a user
     */
    suspend fun resetPassword(email: String): Result<Unit> {
        return try {
            supabase.auth.resetPasswordForEmail(email)
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "Password reset failed", e)
            Result.failure(e)
        }
    }
    
    /**
     * Ensure the user profile exists in the database
     */
    private suspend fun ensureUserProfileExists() {
        val user = supabase.auth.currentUserOrNull() ?: return
        
        try {
            // Check if profile exists
            val profile = supabase.postgrest["user_profiles"]
                .select(Columns.list("id")) {
                    filter {
                        eq("user_id", user.id)
                    }
                }
                .decodeSingleOrNull<Map<String, Any?>>()
            
            // If we get here, profile exists
            Log.d(TAG, "User profile exists")
        } catch (e: Exception) {
            // Profile doesn't exist, create it
            Log.d(TAG, "Creating user profile")
            createUserProfile(user.id, user.email ?: "")
        }
    }
    
    /**
     * Create a user profile in the database
     */
    private suspend fun createUserProfile(userId: String, email: String) {
        try {
            val profile = UserProfile(
                userId = userId,
                email = email,
                displayName = email.substringBefore('@'),
                createdAt = System.currentTimeMillis()
            )
            
            supabase.postgrest["user_profiles"].insert(profile)
            Log.d(TAG, "User profile created")
        } catch (e: Exception) {
            Log.e(TAG, "Failed to create user profile", e)
            throw e
        }
    }
    
    /**
     * Get the current user's profile
     */
    suspend fun getUserProfile(): Result<UserProfile> {
        val userId = getCurrentUserId() ?: return Result.failure(Exception("Not authenticated"))
        
        return try {
            val profile = supabase.postgrest["user_profiles"]
                .select {
                    filter {
                        eq("user_id", userId)
                    }
                }
                .decodeSingleOrNull<UserProfile>()
            
            if (profile != null) {
                Result.success(profile)
            } else {
                Result.failure(Exception("Profile not found"))
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to get user profile", e)
            Result.failure(e)
        }
    }
    
    /**
     * Update the user's profile
     */
    suspend fun updateUserProfile(profile: UserProfile): Result<UserProfile> {
        val userId = getCurrentUserId() ?: return Result.failure(Exception("Not authenticated"))
        
        return try {
            supabase.postgrest["user_profiles"]
                .update(profile) {
                    filter {
                        eq("user_id", userId)
                    }
                }
            
            Result.success(profile)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to update user profile", e)
            
            // Queue for offline sync
            queueOperation(SyncOperation.UpdateProfile(profile))
            
            Result.failure(e)
        }
    }
    
    /**
     * Get user's vital thresholds
     */
    suspend fun getUserVitalThresholds(): Result<List<VitalThreshold>> {
        val userId = getCurrentUserId() ?: return Result.failure(Exception("Not authenticated"))
        
        return try {
            val thresholds = supabase.postgrest["user_vital_thresholds"]
                .select {
                    filter {
                        eq("user_id", userId)
                    }
                }
                .decodeList<VitalThreshold>()
            
            Result.success(thresholds)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to get vital thresholds", e)
            Result.failure(e)
        }
    }
    
    /**
     * Update user's vital threshold
     */
    suspend fun updateVitalThreshold(threshold: VitalThreshold): Result<VitalThreshold> {
        return try {
            supabase.postgrest["user_vital_thresholds"]
                .upsert(threshold)
            
            Result.success(threshold)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to update vital threshold", e)
            
            // Queue for offline sync
            queueOperation(SyncOperation.UpdateThreshold(threshold))
            
            Result.failure(e)
        }
    }
    
    /**
     * Upload vitals data
     */
    suspend fun uploadVitalsData(vitalsData: VitalsData): Result<VitalsData> {
        return try {
            supabase.postgrest["vitals_data"]
                .insert(vitalsData)
            
            // If specific vital type data is available, store in specific table
            when (vitalsData.vitalType) {
                "heart_rate" -> {
                    val heartRateData = HeartRateData(
                        userId = vitalsData.userId,
                        deviceId = vitalsData.deviceId,
                        timestamp = vitalsData.timestamp,
                        value = vitalsData.value.toInt(),
                        confidence = vitalsData.confidence ?: 0
                    )
                    supabase.postgrest["heart_rate_data"].insert(heartRateData)
                }
                "spo2" -> {
                    val spo2Data = SpO2Data(
                        userId = vitalsData.userId,
                        deviceId = vitalsData.deviceId,
                        timestamp = vitalsData.timestamp,
                        value = vitalsData.value.toInt(),
                        confidence = vitalsData.confidence ?: 0
                    )
                    supabase.postgrest["spo2_data"].insert(spo2Data)
                }
                "temperature" -> {
                    val temperatureData = TemperatureData(
                        userId = vitalsData.userId,
                        deviceId = vitalsData.deviceId,
                        timestamp = vitalsData.timestamp,
                        value = vitalsData.value,
                        unit = vitalsData.unit ?: "C"
                    )
                    supabase.postgrest["temperature_data"].insert(temperatureData)
                }
                "respiration" -> {
                    val respirationData = RespirationData(
                        userId = vitalsData.userId,
                        deviceId = vitalsData.deviceId,
                        timestamp = vitalsData.timestamp,
                        value = vitalsData.value.toInt()
                    )
                    supabase.postgrest["respiration_data"].insert(respirationData)
                }
            }
            
            Result.success(vitalsData)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to upload vitals data", e)
            
            // Queue for offline sync
            queueOperation(SyncOperation.UploadVitals(vitalsData))
            
            Result.failure(e)
        }
    }
    
    /**
     * Get vitals data for a specific time range
     */
    suspend fun getVitalsData(
        vitalType: String,
        startTime: Long,
        endTime: Long,
        limit: Int = 100
    ): Result<List<VitalsData>> {
        val userId = getCurrentUserId() ?: return Result.failure(Exception("Not authenticated"))
        
        return try {
            val vitals = supabase.postgrest["vitals_data"]
                .select {
                    filter {
                        eq("user_id", userId)
                        eq("vital_type", vitalType)
                        gte("timestamp", startTime)
                        lte("timestamp", endTime)
                    }
                    order("timestamp", Order.DESCENDING)
                    limit(limit.toLong())
                }
                .decodeList<VitalsData>()
            
            Result.success(vitals)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to get vitals data", e)
            Result.failure(e)
        }
    }
    
    /**
     * Get daily health summary
     */
    suspend fun getDailyHealthSummary(date: Long): Result<DailyHealthSummary> {
        val userId = getCurrentUserId() ?: return Result.failure(Exception("Not authenticated"))
        
        // Calculate start and end of day
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = date
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val startOfDay = calendar.timeInMillis
        
        calendar.add(Calendar.DAY_OF_MONTH, 1)
        val endOfDay = calendar.timeInMillis - 1
        
        return try {
            val summary = supabase.postgrest["daily_health_summaries"]
                .select {
                    filter {
                        eq("user_id", userId)
                        gte("date", startOfDay)
                        lte("date", endOfDay)
                    }
                }
                .decodeSingleOrNull<DailyHealthSummary>()
            
            if (summary != null) {
                Result.success(summary)
            } else {
                // Generate summary from raw data
                generateDailySummary(userId, startOfDay, endOfDay)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to get daily health summary", e)
            Result.failure(e)
        }
    }
    
    /**
     * Generate a daily summary from raw vitals data
     */
    private suspend fun generateDailySummary(
        userId: String,
        startOfDay: Long,
        endOfDay: Long
    ): Result<DailyHealthSummary> {
        return try {
            // Get heart rate data
            val heartRateData = supabase.postgrest["heart_rate_data"]
                .select {
                    filter {
                        eq("user_id", userId)
                        gte("timestamp", startOfDay)
                        lte("timestamp", endOfDay)
                    }
                }
                .decodeList<HeartRateData>()
            
            // Get SpO2 data
            val spo2Data = supabase.postgrest["spo2_data"]
                .select {
                    filter {
                        eq("user_id", userId)
                        gte("timestamp", startOfDay)
                        lte("timestamp", endOfDay)
                    }
                }
                .decodeList<SpO2Data>()
            
            // Calculate averages
            val avgHeartRate = if (heartRateData.isNotEmpty()) {
                heartRateData.sumOf { it.value } / heartRateData.size
            } else null
            
            val avgSpo2 = if (spo2Data.isNotEmpty()) {
                spo2Data.sumOf { it.value } / spo2Data.size
            } else null
            
            // Create summary
            val summary = DailyHealthSummary(
                id = UUID.randomUUID().toString(),
                userId = userId,
                date = startOfDay,
                avgHeartRate = avgHeartRate,
                minHeartRate = heartRateData.minOfOrNull { it.value },
                maxHeartRate = heartRateData.maxOfOrNull { it.value },
                avgSpo2 = avgSpo2,
                minSpo2 = spo2Data.minOfOrNull { it.value },
                maxSpo2 = spo2Data.maxOfOrNull { it.value },
                heartRateReadings = heartRateData.size,
                spo2Readings = spo2Data.size
            )
            
            // Store the summary
            supabase.postgrest["daily_health_summaries"].insert(summary)
            
            Result.success(summary)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to generate daily summary", e)
            Result.failure(e)
        }
    }
    
    /**
     * Get user's connected devices
     */
    suspend fun getConnectedDevices(): Result<List<ConnectedDevice>> {
        val userId = getCurrentUserId() ?: return Result.failure(Exception("Not authenticated"))
        
        return try {
            val devices = supabase.postgrest["connected_devices"]
                .select {
                    filter {
                        eq("user_id", userId)
                    }
                }
                .decodeList<ConnectedDevice>()
            
            Result.success(devices)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to get connected devices", e)
            Result.failure(e)
        }
    }
    
    /**
     * Add a connected device
     */
    suspend fun addConnectedDevice(device: ConnectedDevice): Result<ConnectedDevice> {
        return try {
            supabase.postgrest["connected_devices"]
                .insert(device)
            
            // Save device ID for quick access
            encryptedPrefs.edit()
                .putString(KEY_DEVICE_ID, device.deviceId)
                .apply()
            
            Result.success(device)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to add connected device", e)
            
            // Queue for offline sync
            queueOperation(SyncOperation.AddDevice(device))
            
            Result.failure(e)
        }
    }
    
    /**
     * Update device connection status
     */
    suspend fun updateDeviceStatus(deviceId: String, isConnected: Boolean): Result<Unit> {
        val userId = getCurrentUserId() ?: return Result.failure(Exception("Not authenticated"))
        
        return try {
            supabase.postgrest["connected_devices"]
                .update({
                    set("is_connected", isConnected)
                    set("last_connected", System.currentTimeMillis())
                }) {
                    filter {
                        eq("user_id", userId)
                        eq("device_id", deviceId)
                    }
                }
            
            // Log connection event
            val syncLog = DeviceSyncLog(
                userId = userId,
                deviceId = deviceId,
                timestamp = System.currentTimeMillis(),
                eventType = if (isConnected) "connected" else "disconnected",
                success = true
            )
            supabase.postgrest["device_sync_logs"].insert(syncLog)
            
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to update device status", e)
            
            // Queue for offline sync
            queueOperation(SyncOperation.UpdateDeviceStatus(deviceId, isConnected))
            
            Result.failure(e)
        }
    }
    
    /**
     * Get alerts for the user
     */
    suspend fun getAlerts(limit: Int = 20): Result<List<Alert>> {
        val userId = getCurrentUserId() ?: return Result.failure(Exception("Not authenticated"))
        
        return try {
            val alerts = supabase.postgrest["alerts"]
                .select {
                    filter {
                        eq("user_id", userId)
                    }
                    order("timestamp", Order.DESCENDING)
                    limit(limit.toLong())
                }
                .decodeList<Alert>()
            
            Result.success(alerts)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to get alerts", e)
            Result.failure(e)
        }
    }
    
    /**
     * Create a new alert
     */
    suspend fun createAlert(alert: Alert): Result<Alert> {
        return try {
            supabase.postgrest["alerts"]
                .insert(alert)
            
            Result.success(alert)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to create alert", e)
            
            // Queue for offline sync
            queueOperation(SyncOperation.CreateAlert(alert))
            
            Result.failure(e)
        }
    }
    
    /**
     * Mark an alert as read
     */
    suspend fun markAlertAsRead(alertId: String): Result<Unit> {
        return try {
            supabase.postgrest["alerts"]
                .update({
                    set("is_read", true)
                    set("read_at", System.currentTimeMillis())
                }) {
                    filter {
                        eq("id", alertId)
                    }
                }
            
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to mark alert as read", e)
            
            // Queue for offline sync
            queueOperation(SyncOperation.MarkAlertRead(alertId))
            
            Result.failure(e)
        }
    }
    
    /**
     * Get health insights for the user
     */
    suspend fun getHealthInsights(limit: Int = 10): Result<List<HealthInsight>> {
        val userId = getCurrentUserId() ?: return Result.failure(Exception("Not authenticated"))
        
        return try {
            val insights = supabase.postgrest["health_insights"]
                .select {
                    filter {
                        eq("user_id", userId)
                    }
                    order("created_at", Order.DESCENDING)
                    limit(limit.toLong())
                }
                .decodeList<HealthInsight>()
            
            Result.success(insights)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to get health insights", e)
            Result.failure(e)
        }
    }
    
    /**
     * Get health goals for the user
     */
    suspend fun getHealthGoals(): Result<List<HealthGoal>> {
        val userId = getCurrentUserId() ?: return Result.failure(Exception("Not authenticated"))
        
        return try {
            val goals = supabase.postgrest["health_goals"]
                .select {
                    filter {
                        eq("user_id", userId)
                    }
                }
                .decodeList<HealthGoal>()
            
            Result.success(goals)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to get health goals", e)
            Result.failure(e)
        }
    }
    
    /**
     * Create or update a health goal
     */
    suspend fun upsertHealthGoal(goal: HealthGoal): Result<HealthGoal> {
        return try {
            supabase.postgrest["health_goals"]
                .upsert(goal)
            
            Result.success(goal)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to upsert health goal", e)
            
            // Queue for offline sync
            queueOperation(SyncOperation.UpsertGoal(goal))
            
            Result.failure(e)
        }
    }
    
    /**
     * Record user consent
     */
    suspend fun recordConsent(consent: ConsentRecord): Result<ConsentRecord> {
        return try {
            supabase.postgrest["consent_records"]
                .insert(consent)
            
            Result.success(consent)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to record consent", e)
            
            // Queue for offline sync
            queueOperation(SyncOperation.RecordConsent(consent))
            
            Result.failure(e)
        }
    }
    
    /**
     * Get user's consent records
     */
    suspend fun getConsentRecords(): Result<List<ConsentRecord>> {
        val userId = getCurrentUserId() ?: return Result.failure(Exception("Not authenticated"))
        
        return try {
            val records = supabase.postgrest["consent_records"]
                .select {
                    filter {
                        eq("user_id", userId)
                    }
                    order("timestamp", Order.DESCENDING)
                }
                .decodeList<ConsentRecord>()
            
            Result.success(records)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to get consent records", e)
            Result.failure(e)
        }
    }
    
    /**
     * Queue an operation for offline sync
     */
    private fun queueOperation(operation: SyncOperation) {
        syncQueue.add(operation)
        Log.d(TAG, "Operation queued for sync: $operation")
        
        // Save queue to persistent storage
        saveQueueToStorage()
        
        // Try to process queue if network is available
        if (isNetworkAvailable.get()) {
            processSyncQueue()
        }
    }
    
    /**
     * Save the sync queue to storage
     */
    private fun saveQueueToStorage() {
        try {
            val json = Json { 
                ignoreUnknownKeys = true 
                classDiscriminator = "type"
            }
            val queueJson = json.encodeToString(syncQueue.toList())
            
            // Save to database
            launch {
                try {
                    val userId = getCurrentUserId() ?: return@launch
                    
                    val queueEntry = SyncQueueEntry(
                        userId = userId,
                        queueData = queueJson,
                        updatedAt = System.currentTimeMillis()
                    )
                    
                    supabase.postgrest["sync_queue"]
                        .upsert(queueEntry)
                    
                    Log.d(TAG, "Sync queue saved to database")
                } catch (e: Exception) {
                    Log.e(TAG, "Failed to save sync queue to database", e)
                    
                    // Fall back to encrypted prefs
                    encryptedPrefs.edit()
                        .putString("sync_queue", queueJson)
                        .apply()
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to save sync queue", e)
        }
    }
    
    /**
     * Load the sync queue from storage
     */
    private fun loadQueueFromStorage() {
        try {
            val queueJson = encryptedPrefs.getString("sync_queue", null)
            if (queueJson != null) {
                val json = Json { 
                    ignoreUnknownKeys = true 
                    classDiscriminator = "type"
                }
                val operations = json.decodeFromString<List<SyncOperation>>(queueJson)
                syncQueue.clear()
                syncQueue.addAll(operations)
                Log.d(TAG, "Loaded ${operations.size} operations from sync queue")
            }
            
            // Also try to load from database
            launch {
                try {
                    val userId = getCurrentUserId() ?: return@launch
                    
                    val queueEntry = supabase.postgrest["sync_queue"]
                        .select {
                            filter {
                                eq("user_id", userId)
                            }
                        }
                        .decodeSingleOrNull<SyncQueueEntry>()
                    
                    if (queueEntry != null) {
                        val json = Json { 
                            ignoreUnknownKeys = true 
                            classDiscriminator = "type"
                        }
                        val operations = json.decodeFromString<List<SyncOperation>>(queueEntry.queueData)
                        syncQueue.clear()
                        syncQueue.addAll(operations)
                        Log.d(TAG, "Loaded ${operations.size} operations from database sync queue")
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Failed to load sync queue from database", e)
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to load sync queue", e)
        }
    }
    
    /**
     * Process the sync queue
     */
    private fun processSyncQueue() {
        if (!isNetworkAvailable.get() || isSyncInProgress.get() || syncQueue.isEmpty()) {
            return
        }
        
        isSyncInProgress.set(true)
        
        launch {
            try {
                Log.d(TAG, "Processing sync queue with ${syncQueue.size} operations")
                
                val operations = mutableListOf<SyncOperation>()
                var count = 0
                
                // Take a batch of operations
                while (count < MAX_SYNC_BATCH_SIZE && syncQueue.isNotEmpty()) {
                    syncQueue.poll()?.let {
                        operations.add(it)
                        count++
                    }
                }
                
                // Process each operation
                for (operation in operations) {
                    try {
                        processOperation(operation)
                    } catch (e: Exception) {
                        Log.e(TAG, "Failed to process operation: $operation", e)
                        // Re-queue the operation
                        syncQueue.add(operation)
                    }
                }
                
                // Save updated queue
                saveQueueToStorage()
                
                // If there are more operations, continue processing
                if (syncQueue.isNotEmpty()) {
                    processSyncQueue()
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error processing sync queue", e)
            } finally {
                isSyncInProgress.set(false)
            }
        }
    }
    
    /**
     * Process a single sync operation
     */
    private suspend fun processOperation(operation: SyncOperation) {
        when (operation) {
            is SyncOperation.UploadVitals -> {
                supabase.postgrest["vitals_data"].insert(operation.vitalsData)
                Log.d(TAG, "Synced vitals data")
            }
            is SyncOperation.UpdateProfile -> {
                supabase.postgrest["user_profiles"]
                    .update(operation.profile) {
                        filter {
                            eq("user_id", operation.profile.userId)
                        }
                    }
                Log.d(TAG, "Synced profile update")
            }
            is SyncOperation.UpdateThreshold -> {
                supabase.postgrest["user_vital_thresholds"].upsert(operation.threshold)
                Log.d(TAG, "Synced threshold update")
            }
            is SyncOperation.AddDevice -> {
                supabase.postgrest["connected_devices"].insert(operation.device)
                Log.d(TAG, "Synced device addition")
            }
            is SyncOperation.UpdateDeviceStatus -> {
                val userId = getCurrentUserId() ?: throw Exception("Not authenticated")
                supabase.postgrest["connected_devices"]
                    .update({
                        set("is_connected", operation.isConnected)
                        set("last_connected", System.currentTimeMillis())
                    }) {
                        filter {
                            eq("user_id", userId)
                            eq("device_id", operation.deviceId)
                        }
                    }
                Log.d(TAG, "Synced device status update")
            }
            is SyncOperation.CreateAlert -> {
                supabase.postgrest["alerts"].insert(operation.alert)
                Log.d(TAG, "Synced alert creation")
            }
            is SyncOperation.MarkAlertRead -> {
                supabase.postgrest["alerts"]
                    .update({
                        set("is_read", true)
                        set("read_at", System.currentTimeMillis())
                    }) {
                        filter {
                            eq("id", operation.alertId)
                        }
                    }
                Log.d(TAG, "Synced alert read status")
            }
            is SyncOperation.UpsertGoal -> {
                supabase.postgrest["health_goals"].upsert(operation.goal)
                Log.d(TAG, "Synced health goal")
            }
            is SyncOperation.RecordConsent -> {
                supabase.postgrest["consent_records"].insert(operation.consent)
                Log.d(TAG, "Synced consent record")
            }
        }
    }
    
    /**
     * Get the current user ID
     */
    fun getCurrentUserId(): String? {
        return supabase.auth.currentUserOrNull()?.id
            ?: encryptedPrefs.getString(KEY_USER_ID, null)
    }
    
    /**
     * Get the current user email
     */
    fun getCurrentUserEmail(): String? {
        return supabase.auth.currentUserOrNull()?.email
            ?: encryptedPrefs.getString(KEY_USER_EMAIL, null)
    }
    
    /**
     * Get the current device ID
     */
    fun getCurrentDeviceId(): String? {
        return encryptedPrefs.getString(KEY_DEVICE_ID, null)
    }
    
    /**
     * Save user session information
     */
    private fun saveUserSession(user: UserInfo) {
        encryptedPrefs.edit()
            .putString(KEY_USER_ID, user.id)
            .putString(KEY_USER_EMAIL, user.email)
            .apply()
        
        Log.d(TAG, "User session saved: ${user.id}")
    }
    
    /**
     * Clear user session information
     */
    private fun clearUserSession() {
        encryptedPrefs.edit()
            .remove(KEY_USER_ID)
            .remove(KEY_USER_EMAIL)
            .remove(KEY_SESSION)
            .apply()
        
        Log.d(TAG, "User session cleared")
    }
    
    /**
     * Get the sync queue size
     */
    fun getSyncQueueSize(): Int {
        return syncQueue.size
    }
    
    /**
     * Clean up resources
     */
    fun cleanup() {
        job.cancel()
    }
    
    /**
     * Auth state sealed class
     */
    sealed class AuthState {
        object Loading : AuthState()
        object NotAuthenticated : AuthState()
        data class Authenticated(val user: UserInfo) : AuthState()
        data class AuthenticatedOffline(val userId: String, val email: String) : AuthState()
        data class Error(val message: String) : AuthState()
    }
    
    /**
     * Data classes for database tables
     */
    @Serializable
    data class UserProfile(
        val id: String = UUID.randomUUID().toString(),
        val userId: String,
        val email: String,
        val displayName: String? = null,
        val phoneNumber: String? = null,
        val birthDate: Long? = null,
        val gender: String? = null,
        val height: Float? = null,
        val weight: Float? = null,
        val createdAt: Long = System.currentTimeMillis(),
        val updatedAt: Long = System.currentTimeMillis()
    )
    
    @Serializable
    data class VitalsData(
        val id: String = UUID.randomUUID().toString(),
        val userId: String,
        val deviceId: String,
        val vitalType: String,
        val value: Float,
        val unit: String? = null,
        val timestamp: Long = System.currentTimeMillis(),
        val confidence: Int? = null,
        val notes: String? = null
    )
    
    @Serializable
    data class HeartRateData(
        val id: String = UUID.randomUUID().toString(),
        val userId: String,
        val deviceId: String,
        val value: Int,
        val timestamp: Long = System.currentTimeMillis(),
        val confidence: Int = 0
    )
    
    @Serializable
    data class SpO2Data(
        val id: String = UUID.randomUUID().toString(),
        val userId: String,
        val deviceId: String,
        val value: Int,
        val timestamp: Long = System.currentTimeMillis(),
        val confidence: Int = 0
    )
    
    @Serializable
    data class TemperatureData(
        val id: String = UUID.randomUUID().toString(),
        val userId: String,
        val deviceId: String,
        val value: Float,
        val unit: String = "C",
        val timestamp: Long = System.currentTimeMillis()
    )
    
    @Serializable
    data class RespirationData(
        val id: String = UUID.randomUUID().toString(),
        val userId: String,
        val deviceId: String,
        val value: Int,
        val timestamp: Long = System.currentTimeMillis()
    )
    
    @Serializable
    data class VitalThreshold(
        val id: String = UUID.randomUUID().toString(),
        val userId: String,
        val vitalType: String,
        val minValue: Float? = null,
        val maxValue: Float? = null,
        val alertLevel: String = "warning",
        val createdAt: Long = System.currentTimeMillis(),
        val updatedAt: Long = System.currentTimeMillis()
    )
    
    @Serializable
    data class ConnectedDevice(
        val id: String = UUID.randomUUID().toString(),
        val userId: String,
        val deviceId: String,
        val deviceName: String,
        val deviceType: String,
        val isConnected: Boolean = false,
        val lastConnected: Long = System.currentTimeMillis(),
        val createdAt: Long = System.currentTimeMillis()
    )
    
    @Serializable
    data class DeviceSyncLog(
        val id: String = UUID.randomUUID().toString(),
        val userId: String,
        val deviceId: String,
        val timestamp: Long = System.currentTimeMillis(),
        val eventType: String,
        val success: Boolean = true,
        val errorMessage: String? = null
    )
    
    @Serializable
    data class Alert(
        val id: String = UUID.randomUUID().toString(),
        val userId: String,
        val vitalType: String? = null,
        val alertLevel: String,
        val message: String,
        val timestamp: Long = System.currentTimeMillis(),
        val isRead: Boolean = false,
        val readAt: Long? = null,
        val relatedValue: Float? = null
    )
    
    @Serializable
    data class HealthInsight(
        val id: String = UUID.randomUUID().toString(),
        val userId: String,
        val insightType: String,
        val title: String,
        val description: String,
        val recommendation: String? = null,
        val severity: String? = null,
        val createdAt: Long = System.currentTimeMillis()
    )
    
    @Serializable
    data class HealthGoal(
        val id: String = UUID.randomUUID().toString(),
        val userId: String,
        val goalType: String,
        val targetValue: Float,
        val unit: String? = null,
        val startDate: Long = System.currentTimeMillis(),
        val endDate: Long? = null,
        val isCompleted: Boolean = false,
        val completedAt: Long? = null,
        val createdAt: Long = System.currentTimeMillis(),
        val updatedAt: Long = System.currentTimeMillis()
    )
    
    @Serializable
    data class ConsentRecord(
        val id: String = UUID.randomUUID().toString(),
        val userId: String,
        val consentType: String,
        val consentVersion: String,
        val consentText: String,
        val isAccepted: Boolean,
        val timestamp: Long = System.currentTimeMillis(),
        val ipAddress: String? = null
    )
    
    @Serializable
    data class DailyHealthSummary(
        val id: String = UUID.randomUUID().toString(),
        val userId: String,
        val date: Long,
        val avgHeartRate: Int? = null,
        val minHeartRate: Int? = null,
        val maxHeartRate: Int? = null,
        val avgSpo2: Int? = null,
        val minSpo2: Int? = null,
        val maxSpo2: Int? = null,
        val avgRespirationRate: Int? = null,
        val avgTemperature: Float? = null,
        val steps: Int? = null,
        val activeMinutes: Int? = null,
        val heartRateReadings: Int = 0,
        val spo2Readings: Int = 0,
        val temperatureReadings: Int = 0,
        val respirationReadings: Int = 0,
        val createdAt: Long = System.currentTimeMillis(),
        val updatedAt: Long = System.currentTimeMillis()
    )
    
    @Serializable
    data class SyncQueueEntry(
        val id: String = UUID.randomUUID().toString(),
        val userId: String,
        val queueData: String,
        val updatedAt: Long = System.currentTimeMillis()
    )
    
    /**
     * Sync operations for offline queue
     */
    @Serializable
    sealed class SyncOperation {
        @Serializable
        data class UploadVitals(val vitalsData: VitalsData) : SyncOperation()
        
        @Serializable
        data class UpdateProfile(val profile: UserProfile) : SyncOperation()
        
        @Serializable
        data class UpdateThreshold(val threshold: VitalThreshold) : SyncOperation()
        
        @Serializable
        data class AddDevice(val device: ConnectedDevice) : SyncOperation()
        
        @Serializable
        data class UpdateDeviceStatus(val deviceId: String, val isConnected: Boolean) : SyncOperation()
        
        @Serializable
        data class CreateAlert(val alert: Alert) : SyncOperation()
        
        @Serializable
        data class MarkAlertRead(val alertId: String) : SyncOperation()
        
        @Serializable
        data class UpsertGoal(val goal: HealthGoal) : SyncOperation()
        
        @Serializable
        data class RecordConsent(val consent: ConsentRecord) : SyncOperation()
    }

    /**
     * Create a demo user account for testing
     */
    suspend fun createDemoUser(): Result<UserInfo> {
        return try {
            // First try to sign up the demo user (this will fail if user already exists)
            supabase.auth.signUpWith(Email) {
                this.email = DEMO_EMAIL
                this.password = DEMO_PASSWORD
            }
            
            // Get the current user info after successful sign-up
            val user = supabase.auth.currentUserOrNull()
                ?: return Result.failure(Exception("Demo user creation succeeded but no user found"))
            
            // Create user profile
            ensureUserProfileExists()
            
            Log.d(TAG, "Demo user created successfully: ${user.email}")
            Result.success(user)
        } catch (e: Exception) {
            Log.d(TAG, "Demo user creation failed, trying to sign in: ${e.message}")
            // If sign-up fails (user already exists), try to sign in
            try {
                val signInResult = signIn(DEMO_EMAIL, DEMO_PASSWORD)
                if (signInResult.isSuccess) {
                    Log.d(TAG, "Demo user sign-in successful")
                    return signInResult
                } else {
                    Log.e(TAG, "Demo user sign-in failed", signInResult.exceptionOrNull())
                    return Result.failure(Exception("Demo user exists but sign-in failed. Please check your Supabase configuration."))
                }
            } catch (signInException: Exception) {
                Log.e(TAG, "Demo user sign-in exception", signInException)
                return Result.failure(Exception("Demo user creation and sign-in failed: ${signInException.message}"))
            }
        }
    }

    /**
     * Sign in with demo credentials
     */
    suspend fun signInWithDemo(): Result<UserInfo> {
        return signIn(DEMO_EMAIL, DEMO_PASSWORD)
    }

    /**
     * Demo mode for testing without Supabase
     */
    suspend fun signInWithDemoOffline(): Result<UserInfo> {
        return try {
            // Try to create a simple demo user by signing up
            supabase.auth.signUpWith(Email) {
                this.email = DEMO_EMAIL
                this.password = DEMO_PASSWORD
            }
            
            val user = supabase.auth.currentUserOrNull()
                ?: return Result.failure(Exception("Failed to create demo user"))
            
            // Save demo session locally
            saveUserSession(user)
            _authState.value = AuthState.Authenticated(user)
            
            Log.d(TAG, "Demo offline login successful")
            Result.success(user)
        } catch (e: Exception) {
            Log.e(TAG, "Demo offline login failed", e)
            Result.failure(e)
        }
    }
}
