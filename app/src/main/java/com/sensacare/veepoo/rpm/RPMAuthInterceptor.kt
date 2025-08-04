package com.sensacare.veepoo.rpm

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class RPMAuthInterceptor(
    private val clientContextManager: RPMClientContextManager
) : Interceptor {
    
    companion object {
        private const val TAG = "RPMAuthInterceptor"
    }
    
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        
        val userToken = clientContextManager.getUserToken()
        if (userToken == null) {
            Log.w(TAG, "No user token available for request: ${originalRequest.url}")
            return chain.proceed(originalRequest)
        }
        
        val authenticatedRequest = originalRequest.newBuilder()
            .addHeader("Authorization", "Bearer $userToken")
            .addHeader("Content-Type", "application/json")
            .addHeader("Accept", "application/json")
            .addHeader("User-Agent", "Sensacare-BLE-Android/1.0")
            .build()
        
        Log.d(TAG, "Adding auth header to request: ${originalRequest.url}")
        
        return chain.proceed(authenticatedRequest)
    }
} 