package com.sensacare.veepoo.rpm

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.util.concurrent.TimeUnit

class RPMRetryInterceptor : Interceptor {
    
    companion object {
        private const val TAG = "RPMRetryInterceptor"
        private const val MAX_RETRIES = 3
        private const val BASE_DELAY_MS = 1000L
    }
    
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        var response: Response? = null
        var exception: IOException? = null
        
        // Try up to MAX_RETRIES times
        for (attempt in 0..MAX_RETRIES) {
            try {
                response = chain.proceed(request)
                
                // If successful, return immediately
                if (response.isSuccessful) {
                    return response
                }
                
                // If it's a client error (4xx), don't retry
                if (response.code in 400..499) {
                    Log.w(TAG, "Client error ${response.code}, not retrying: ${request.url}")
                    return response
                }
                
                // If it's a server error (5xx), retry
                if (response.code in 500..599) {
                    Log.w(TAG, "Server error ${response.code}, attempt ${attempt + 1}/${MAX_RETRIES + 1}: ${request.url}")
                    
                    if (attempt < MAX_RETRIES) {
                        response.close()
                        val delayMs = BASE_DELAY_MS * (1L shl attempt) // Exponential backoff
                        Log.d(TAG, "Retrying in ${delayMs}ms...")
                        Thread.sleep(delayMs)
                        continue
                    }
                }
                
                // For other errors, don't retry
                return response
                
            } catch (e: IOException) {
                exception = e
                Log.w(TAG, "Network error on attempt ${attempt + 1}/${MAX_RETRIES + 1}: ${request.url}", e)
                
                if (attempt < MAX_RETRIES) {
                    val delayMs = BASE_DELAY_MS * (1L shl attempt) // Exponential backoff
                    Log.d(TAG, "Retrying in ${delayMs}ms...")
                    try {
                        Thread.sleep(delayMs)
                    } catch (ie: InterruptedException) {
                        Thread.currentThread().interrupt()
                        throw IOException("Interrupted during retry delay", ie)
                    }
                    continue
                }
            }
        }
        
        // If we get here, all retries failed
        Log.e(TAG, "All ${MAX_RETRIES + 1} attempts failed for: ${request.url}")
        
        // Return the last response if we have one, otherwise throw the last exception
        return response ?: throw exception ?: IOException("Unknown error after $MAX_RETRIES retries")
    }
} 