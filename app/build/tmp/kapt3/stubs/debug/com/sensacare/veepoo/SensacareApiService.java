package com.sensacare.veepoo;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J!\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0007J!\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\t\u001a\u00020\nH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000bJ!\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\u00032\b\b\u0001\u0010\u000e\u001a\u00020\u000fH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0010\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0011"}, d2 = {"Lcom/sensacare/veepoo/SensacareApiService;", "", "login", "Lretrofit2/Response;", "Lcom/sensacare/veepoo/SecureApiManager$AuthResponse;", "loginRequest", "Lcom/sensacare/veepoo/SecureApiManager$LoginRequest;", "(Lcom/sensacare/veepoo/SecureApiManager$LoginRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "refreshToken", "refreshRequest", "Lcom/sensacare/veepoo/SecureApiManager$RefreshTokenRequest;", "(Lcom/sensacare/veepoo/SecureApiManager$RefreshTokenRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "uploadVitals", "", "vitalsData", "Lcom/sensacare/veepoo/VitalsData;", "(Lcom/sensacare/veepoo/VitalsData;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public abstract interface SensacareApiService {
    
    @retrofit2.http.POST(value = "vitals/upload")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object uploadVitals(@retrofit2.http.Body
    @org.jetbrains.annotations.NotNull
    com.sensacare.veepoo.VitalsData vitalsData, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super retrofit2.Response<kotlin.Unit>> $completion);
    
    @retrofit2.http.POST(value = "auth/login")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object login(@retrofit2.http.Body
    @org.jetbrains.annotations.NotNull
    com.sensacare.veepoo.SecureApiManager.LoginRequest loginRequest, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.sensacare.veepoo.SecureApiManager.AuthResponse>> $completion);
    
    @retrofit2.http.POST(value = "auth/refresh")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object refreshToken(@retrofit2.http.Body
    @org.jetbrains.annotations.NotNull
    com.sensacare.veepoo.SecureApiManager.RefreshTokenRequest refreshRequest, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.sensacare.veepoo.SecureApiManager.AuthResponse>> $completion);
}