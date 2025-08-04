package com.sensacare.veepoo.rpm;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0010\u000b\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J+\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u00062\b\b\u0001\u0010\u0007\u001a\u00020\u0006H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\bJ!\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000bJ1\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u00062\b\b\u0003\u0010\u000f\u001a\u00020\u0010H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0011J;\u0010\u0012\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00130\r0\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u00062\b\b\u0003\u0010\u0014\u001a\u00020\u00062\b\b\u0003\u0010\u000f\u001a\u00020\u0010H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0015J+\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u00062\b\b\u0001\u0010\u0017\u001a\u00020\u0018H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0019J+\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u00062\b\b\u0001\u0010\u001b\u001a\u00020\u001cH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001dJ+\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u001f0\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u00062\b\b\u0001\u0010 \u001a\u00020!H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\"JC\u0010#\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020%0$0\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u00062\u0014\b\u0001\u0010&\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060$H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\'\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006("}, d2 = {"Lcom/sensacare/veepoo/rpm/RPMApiService;", "", "acknowledgeAlert", "Lretrofit2/Response;", "", "clientId", "", "alertId", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getClientThresholds", "Lcom/sensacare/veepoo/rpm/ClientThresholds;", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getRecentAlerts", "", "Lcom/sensacare/veepoo/rpm/AlertData;", "limit", "", "(Ljava/lang/String;ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getVitalsTimeline", "Lcom/sensacare/veepoo/rpm/VitalsTimelineEntry;", "range", "(Ljava/lang/String;Ljava/lang/String;ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "reportDeviceStatus", "status", "Lcom/sensacare/veepoo/rpm/DeviceStatusPayload;", "(Ljava/lang/String;Lcom/sensacare/veepoo/rpm/DeviceStatusPayload;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "reportSyncGap", "gap", "Lcom/sensacare/veepoo/rpm/SyncGapReport;", "(Ljava/lang/String;Lcom/sensacare/veepoo/rpm/SyncGapReport;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "uploadVitals", "Lcom/sensacare/veepoo/rpm/UploadResponse;", "payload", "Lcom/sensacare/veepoo/rpm/VitalsUploadPayload;", "(Ljava/lang/String;Lcom/sensacare/veepoo/rpm/VitalsUploadPayload;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "validateClient", "", "", "token", "(Ljava/lang/String;Ljava/util/Map;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public abstract interface RPMApiService {
    
    @retrofit2.http.POST(value = "clients/{clientId}/vitals")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object uploadVitals(@retrofit2.http.Path(value = "clientId")
    @org.jetbrains.annotations.NotNull
    java.lang.String clientId, @retrofit2.http.Body
    @org.jetbrains.annotations.NotNull
    com.sensacare.veepoo.rpm.VitalsUploadPayload payload, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.sensacare.veepoo.rpm.UploadResponse>> $completion);
    
    @retrofit2.http.GET(value = "clients/{clientId}/alerts/recent")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getRecentAlerts(@retrofit2.http.Path(value = "clientId")
    @org.jetbrains.annotations.NotNull
    java.lang.String clientId, @retrofit2.http.Query(value = "limit")
    int limit, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super retrofit2.Response<java.util.List<com.sensacare.veepoo.rpm.AlertData>>> $completion);
    
    @retrofit2.http.POST(value = "clients/{clientId}/alerts/{alertId}/acknowledge")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object acknowledgeAlert(@retrofit2.http.Path(value = "clientId")
    @org.jetbrains.annotations.NotNull
    java.lang.String clientId, @retrofit2.http.Path(value = "alertId")
    @org.jetbrains.annotations.NotNull
    java.lang.String alertId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super retrofit2.Response<kotlin.Unit>> $completion);
    
    @retrofit2.http.POST(value = "clients/{clientId}/device-status")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object reportDeviceStatus(@retrofit2.http.Path(value = "clientId")
    @org.jetbrains.annotations.NotNull
    java.lang.String clientId, @retrofit2.http.Body
    @org.jetbrains.annotations.NotNull
    com.sensacare.veepoo.rpm.DeviceStatusPayload status, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super retrofit2.Response<kotlin.Unit>> $completion);
    
    @retrofit2.http.GET(value = "clients/{clientId}/thresholds")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getClientThresholds(@retrofit2.http.Path(value = "clientId")
    @org.jetbrains.annotations.NotNull
    java.lang.String clientId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.sensacare.veepoo.rpm.ClientThresholds>> $completion);
    
    @retrofit2.http.GET(value = "clients/{clientId}/vitals")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getVitalsTimeline(@retrofit2.http.Path(value = "clientId")
    @org.jetbrains.annotations.NotNull
    java.lang.String clientId, @retrofit2.http.Query(value = "range")
    @org.jetbrains.annotations.NotNull
    java.lang.String range, @retrofit2.http.Query(value = "limit")
    int limit, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super retrofit2.Response<java.util.List<com.sensacare.veepoo.rpm.VitalsTimelineEntry>>> $completion);
    
    @retrofit2.http.POST(value = "clients/{clientId}/validate")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object validateClient(@retrofit2.http.Path(value = "clientId")
    @org.jetbrains.annotations.NotNull
    java.lang.String clientId, @retrofit2.http.Body
    @org.jetbrains.annotations.NotNull
    java.util.Map<java.lang.String, java.lang.String> token, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super retrofit2.Response<java.util.Map<java.lang.String, java.lang.Boolean>>> $completion);
    
    @retrofit2.http.POST(value = "clients/{clientId}/sync-gaps")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object reportSyncGap(@retrofit2.http.Path(value = "clientId")
    @org.jetbrains.annotations.NotNull
    java.lang.String clientId, @retrofit2.http.Body
    @org.jetbrains.annotations.NotNull
    com.sensacare.veepoo.rpm.SyncGapReport gap, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super retrofit2.Response<kotlin.Unit>> $completion);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}