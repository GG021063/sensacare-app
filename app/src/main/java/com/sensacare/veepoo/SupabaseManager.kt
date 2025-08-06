package com.sensacare.veepoo

import android.content.Context
import android.util.Log
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.gotrue.user.UserInfo
import io.github.jan.supabase.gotrue.providers.builtin.Email
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class SupabaseManager(context: Context) {

    companion object {
        private const val TAG = "SupabaseManager"
        private const val KEY_SESSION = "supabase_session"
        private const val PREFS_NAME = "sensacare_secure_prefs"
        
        // Supabase project details
        private const val SUPABASE_URL = "https://mvyxacmcmpkogqnoiout.supabase.co"
        private const val SUPABASE_ANON_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Im12eXhhY21jbXBrb2dxbm9pb3V0Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTIwOTYwOTQsImV4cCI6MjA2NzY3MjA5NH0.CYRv3RyiNSvvpX518f401TeNlv59UtD5x9Y4mG0j550"
    }

    private val supabase: SupabaseClient = createSupabaseClient(
        supabaseUrl = SUPABASE_URL,
        supabaseKey = SUPABASE_ANON_KEY
    ) {
        install(Auth)
    }

    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val prefs = EncryptedSharedPreferences.create(
        context,
        PREFS_NAME,
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    private val _authState = MutableStateFlow<AuthState>(AuthState.Unauthenticated)
    val authState: StateFlow<AuthState> = _authState

    init {
        restoreSession()
    }

    fun getCurrentUserId(): String? {
        return supabase.auth.currentUserOrNull()?.id
    }

    fun getCurrentUser(): UserInfo? {
        return supabase.auth.currentUserOrNull()
    }

    fun isAuthenticated(): Boolean {
        return supabase.auth.currentUserOrNull() != null
    }

    fun sendOtpToEmail(email: String, onComplete: (Boolean, String?) -> Unit) {
        runBlocking {
            try {
                // Use password reset flow to send OTP
                supabase.auth.resetPasswordForEmail(email)
                onComplete(true, null)
            } catch (e: Exception) {
                Log.e(TAG, "OTP send failed", e)
                onComplete(false, e.message)
            }
        }
    }

    fun verifyOtp(email: String, code: String, onComplete: (Boolean, String?) -> Unit) {
        runBlocking {
            try {
                // Use the OTP code as a temporary password for sign-in
                supabase.auth.signInWith(Email) {
                    this.email = email
                    this.password = code
                }
                
                val user = supabase.auth.currentUserOrNull()
                if (user != null) {
                    saveSession(user.id)
                    _authState.value = AuthState.Authenticated(user)
                    onComplete(true, null)
                } else {
                    onComplete(false, "Authentication failed")
                }
            } catch (e: Exception) {
                Log.e(TAG, "OTP verification failed", e)
                onComplete(false, e.message)
            }
        }
    }

    private fun saveSession(userId: String) {
        prefs.edit().putString(KEY_SESSION, userId).apply()
    }

    private fun restoreSession() {
        val userId = prefs.getString(KEY_SESSION, null)
        if (userId != null) {
            runBlocking {
                try {
                    val user = supabase.auth.currentUserOrNull()
                    if (user != null) {
                        _authState.value = AuthState.Authenticated(user)
                        Log.d(TAG, "Session restored for user: ${user.email}")
                    } else {
                        _authState.value = AuthState.Unauthenticated
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Session restore failed", e)
                    _authState.value = AuthState.Unauthenticated
                }
            }
        }
    }

    sealed class AuthState {
        object Unauthenticated : AuthState()
        data class Authenticated(val user: UserInfo) : AuthState()
    }
}
