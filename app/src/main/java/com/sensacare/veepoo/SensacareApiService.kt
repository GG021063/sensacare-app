package com.sensacare.veepoo

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SensacareApiService {
    @POST("vitals/upload")
    suspend fun uploadVitals(@Body vitalsData: VitalsData): Response<Unit>
    
    @POST("auth/login")
    suspend fun login(@Body loginRequest: SecureApiManager.LoginRequest): Response<SecureApiManager.AuthResponse>
    
    @POST("auth/refresh")
    suspend fun refreshToken(@Body refreshRequest: SecureApiManager.RefreshTokenRequest): Response<SecureApiManager.AuthResponse>
} 