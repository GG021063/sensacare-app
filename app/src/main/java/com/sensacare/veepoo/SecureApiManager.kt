package com.sensacare.veepoo

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.util.Log
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import kotlinx.coroutines.*
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

class SecureApiManager(private val context: Context) {
    companion object {
        private const val TAG = "SecureApiManager"
        private const val MAX_RETRY_ATTEMPTS = 3
        private const val BASE_RETRY_DELAY_MS = 1000L
        private const val MAX_RETRY_DELAY_MS = 10000L
        private const val REQUEST_TIMEOUT_SECONDS = 30L
        private const val ENCRYPTED_PREFS_NAME = "secure_api_prefs"
        private const val KEY_ACCESS_TOKEN = "access_token"
        private const val KEY_REFRESH_TOKEN = "refresh_token"
        private const val KEY_TOKEN_EXPIRY = "token_expiry"
        private const val KEY_USER_ID = "user_id"
    }

    // Encrypted storage for tokens
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

    // Offline data queue
    private val offlineQueue = mutableListOf<QueuedApiRequest>()
    private val queueScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private var isProcessingQueue = false

    // Network state monitoring
    private var isNetworkAvailable = true
    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            isNetworkAvailable = true
            Log.d(TAG, "Network available, processing offline queue")
            processOfflineQueue()
        }

        override fun onLost(network: Network) {
            isNetworkAvailable = false
            Log.d(TAG, "Network lost, queuing requests")
        }
    }

    // HTTP client with interceptors
    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor())
        .addInterceptor(RetryInterceptor())
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .connectTimeout(REQUEST_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .readTimeout(REQUEST_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .writeTimeout(REQUEST_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .build()

    // Retrofit instance
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.sensacare.com/") // Update with actual API base URL
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(SensacareApiService::class.java)

    // Authentication interceptor
    private inner class AuthInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val originalRequest = chain.request()
            
            // Skip authentication for login/refresh endpoints
            if (originalRequest.url.encodedPath.contains("/auth/")) {
                return chain.proceed(originalRequest)
            }

            val token = getAccessToken()
            return if (token != null && !isTokenExpired()) {
                val authenticatedRequest = originalRequest.newBuilder()
                    .header("Authorization", "Bearer $token")
                    .build()
                chain.proceed(authenticatedRequest)
            } else {
                // Token expired or missing, try to refresh
                val refreshToken = getRefreshToken()
                if (refreshToken != null) {
                    try {
                        // Use runBlocking to call suspend function from non-suspend context
                        runBlocking {
                            refreshAccessToken(refreshToken)
                        }
                        val newToken = getAccessToken()
                        if (newToken != null) {
                            val authenticatedRequest = originalRequest.newBuilder()
                                .header("Authorization", "Bearer $newToken")
                                .build()
                            chain.proceed(authenticatedRequest)
                        } else {
                            // Refresh failed, queue request for later
                            queueRequest(originalRequest)
                            throw IOException("Authentication failed")
                        }
                    } catch (e: Exception) {
                        Log.e(TAG, "Token refresh failed", e)
                        queueRequest(originalRequest)
                        throw IOException("Authentication failed")
                    }
                } else {
                    // No refresh token, queue request
                    queueRequest(originalRequest)
                    throw IOException("No authentication token")
                }
            }
        }
    }

    // Retry interceptor with exponential backoff
    private inner class RetryInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()
            var response: Response? = null
            var exception: IOException? = null

            for (attempt in 0..MAX_RETRY_ATTEMPTS) {
                try {
                    response = chain.proceed(request)
                    
                    // Only retry on server errors (5xx) or network errors
                    if (response.isSuccessful || response.code < 500) {
                        return response
                    }
                    
                    response.close()
                    
                    if (attempt < MAX_RETRY_ATTEMPTS) {
                        val delay = calculateRetryDelay(attempt)
                        Log.d(TAG, "Retrying request after ${delay}ms (attempt ${attempt + 1})")
                        // Use Thread.sleep instead of suspend delay() function
                        Thread.sleep(delay)
                    }
                } catch (e: IOException) {
                    exception = e
                    if (attempt < MAX_RETRY_ATTEMPTS) {
                        val delay = calculateRetryDelay(attempt)
                        Log.d(TAG, "Network error, retrying after ${delay}ms (attempt ${attempt + 1})")
                        // Use Thread.sleep instead of suspend delay() function
                        Thread.sleep(delay)
                    }
                }
            }

            // All retries failed, queue request if it's a network error
            if (exception != null && !isNetworkAvailable) {
                queueRequest(request)
            }
            
            throw exception ?: IOException("Request failed after ${MAX_RETRY_ATTEMPTS + 1} attempts")
        }

        private fun calculateRetryDelay(attempt: Int): Long {
            val delay = BASE_RETRY_DELAY_MS * (1L shl attempt) // Exponential backoff
            return minOf(delay, MAX_RETRY_DELAY_MS)
        }
    }

    // Token management
    fun setAccessToken(token: String, expiryTime: Long) {
        encryptedPrefs.edit()
            .putString(KEY_ACCESS_TOKEN, token)
            .putLong(KEY_TOKEN_EXPIRY, expiryTime)
            .apply()
        Log.d(TAG, "Access token stored")
    }

    fun setRefreshToken(token: String) {
        encryptedPrefs.edit()
            .putString(KEY_REFRESH_TOKEN, token)
            .apply()
        Log.d(TAG, "Refresh token stored")
    }

    fun setUserId(userId: String) {
        encryptedPrefs.edit()
            .putString(KEY_USER_ID, userId)
            .apply()
        Log.d(TAG, "User ID stored")
    }

    private fun getAccessToken(): String? {
        return encryptedPrefs.getString(KEY_ACCESS_TOKEN, null)
    }

    private fun getRefreshToken(): String? {
        return encryptedPrefs.getString(KEY_REFRESH_TOKEN, null)
    }

    fun getUserId(): String? {
        return encryptedPrefs.getString(KEY_USER_ID, null)
    }

    private fun isTokenExpired(): Boolean {
        val expiryTime = encryptedPrefs.getLong(KEY_TOKEN_EXPIRY, 0L)
        return System.currentTimeMillis() >= expiryTime
    }

    private suspend fun refreshAccessToken(refreshToken: String) {
        try {
            val response = apiService.refreshToken(RefreshTokenRequest(refreshToken))
            if (response.isSuccessful) {
                response.body()?.let { authResponse ->
                    setAccessToken(authResponse.accessToken, authResponse.expiryTime)
                    authResponse.refreshToken?.let { setRefreshToken(it) }
                    Log.d(TAG, "Token refreshed successfully")
                }
            } else {
                throw IOException("Token refresh failed: ${response.code}")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Token refresh failed", e)
            throw e
        }
    }

    // Offline queue management
    private fun queueRequest(request: Request) {
        val queuedRequest = QueuedApiRequest(
            url = request.url.toString(),
            method = request.method,
            headers = request.headers.toMap(),
            body = request.body?.let { body ->
                // Convert request body to string for storage
                val buffer = okio.Buffer()
                body.writeTo(buffer)
                buffer.readUtf8()
            }
        )
        
        offlineQueue.add(queuedRequest)
        Log.d(TAG, "Request queued for offline processing: ${request.url}")
        
        // Save queue to persistent storage
        saveOfflineQueue()
    }

    private fun saveOfflineQueue() {
        // Save queue to Room database or SharedPreferences
        // This is a simplified implementation
        Log.d(TAG, "Offline queue saved with ${offlineQueue.size} items")
    }

    private fun loadOfflineQueue() {
        // Load queue from persistent storage
        // This is a simplified implementation
        Log.d(TAG, "Offline queue loaded with ${offlineQueue.size} items")
    }

    private fun processOfflineQueue() {
        if (isProcessingQueue || offlineQueue.isEmpty()) {
            return
        }

        isProcessingQueue = true
        queueScope.launch {
            try {
                val requestsToProcess = offlineQueue.toList()
                offlineQueue.clear()
                
                for (queuedRequest in requestsToProcess) {
                    try {
                        val request = Request.Builder()
                            .url(queuedRequest.url)
                            .method(queuedRequest.method, queuedRequest.body?.let { body ->
                                okhttp3.RequestBody.create(null, body)
                            })
                            .apply {
                                queuedRequest.headers.forEach { (key, value) ->
                                    header(key, value)
                                }
                            }
                            .build()

                        val response = httpClient.newCall(request).execute()
                        if (response.isSuccessful) {
                            Log.d(TAG, "Queued request processed successfully: ${queuedRequest.url}")
                        } else {
                            Log.w(TAG, "Queued request failed: ${response.code}")
                            // Re-queue failed requests
                            offlineQueue.add(queuedRequest)
                        }
                        response.close()
                    } catch (e: Exception) {
                        Log.e(TAG, "Failed to process queued request", e)
                        // Re-queue failed requests
                        offlineQueue.add(queuedRequest)
                    }
                }
                
                saveOfflineQueue()
            } finally {
                isProcessingQueue = false
            }
        }
    }

    // API methods
    suspend fun uploadVitals(vitalsData: VitalsData): Result<Unit> {
        return try {
            val response = apiService.uploadVitals(vitalsData)
            if (response.isSuccessful) {
                Log.d(TAG, "Vitals uploaded successfully")
                Result.success(Unit)
            } else {
                Log.e(TAG, "Vitals upload failed: ${response.code}")
                Result.failure(IOException("Upload failed: ${response.code}"))
            }
        } catch (e: Exception) {
            Log.e(TAG, "Vitals upload error", e)
            Result.failure(e)
        }
    }

    suspend fun login(username: String, password: String): Result<AuthResponse> {
        return try {
            val response = apiService.login(LoginRequest(username, password))
            if (response.isSuccessful) {
                response.body()?.let { authResponse ->
                    setAccessToken(authResponse.accessToken, authResponse.expiryTime)
                    authResponse.refreshToken?.let { setRefreshToken(it) }
                    authResponse.userId?.let { setUserId(it) }
                    Log.d(TAG, "Login successful")
                    Result.success(authResponse)
                } ?: Result.failure(IOException("Empty response"))
            } else {
                Log.e(TAG, "Login failed: ${response.code}")
                Result.failure(IOException("Login failed: ${response.code}"))
            }
        } catch (e: Exception) {
            Log.e(TAG, "Login error", e)
            Result.failure(e)
        }
    }

    fun logout() {
        encryptedPrefs.edit().clear().apply()
        offlineQueue.clear()
        Log.d(TAG, "Logged out, cleared all tokens and queue")
    }

    fun isAuthenticated(): Boolean {
        return getAccessToken() != null && !isTokenExpired()
    }

    fun getOfflineQueueSize(): Int = offlineQueue.size

    fun clearOfflineQueue() {
        offlineQueue.clear()
        saveOfflineQueue()
        Log.d(TAG, "Offline queue cleared")
    }

    // Cleanup
    fun cleanup() {
        queueScope.cancel()
    }

    // Data classes for API requests/responses
    data class LoginRequest(val username: String, val password: String)
    data class RefreshTokenRequest(val refreshToken: String)
    data class AuthResponse(
        val accessToken: String,
        val refreshToken: String?,
        val expiryTime: Long,
        val userId: String?
    )

    data class QueuedApiRequest(
        val url: String,
        val method: String,
        val headers: Map<String, String>,
        val body: String?
    )
}