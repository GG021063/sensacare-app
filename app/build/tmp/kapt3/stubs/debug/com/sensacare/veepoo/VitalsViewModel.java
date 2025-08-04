package com.sensacare.veepoo;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\b\u0010\u0014\u001a\u00020\u0015H\u0002J\b\u0010\u0016\u001a\u00020\u0015H\u0002J\u0006\u0010\u0017\u001a\u00020\u0015R\u001a\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\rR\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0018"}, d2 = {"Lcom/sensacare/veepoo/VitalsViewModel;", "Landroidx/lifecycle/AndroidViewModel;", "application", "Landroid/app/Application;", "(Landroid/app/Application;)V", "_chartData", "Landroidx/lifecycle/MutableLiveData;", "", "Lcom/sensacare/veepoo/VitalsEntity;", "_recentVitals", "chartData", "Landroidx/lifecycle/LiveData;", "getChartData", "()Landroidx/lifecycle/LiveData;", "database", "Lcom/sensacare/veepoo/AppDatabase;", "recentVitals", "getRecentVitals", "vitalsDao", "Lcom/sensacare/veepoo/VitalsDao;", "loadChartData", "", "loadRecentVitals", "refreshVitals", "app_debug"})
public final class VitalsViewModel extends androidx.lifecycle.AndroidViewModel {
    @org.jetbrains.annotations.NotNull
    private final com.sensacare.veepoo.AppDatabase database = null;
    @org.jetbrains.annotations.NotNull
    private final com.sensacare.veepoo.VitalsDao vitalsDao = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.MutableLiveData<java.util.List<com.sensacare.veepoo.VitalsEntity>> _recentVitals = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.LiveData<java.util.List<com.sensacare.veepoo.VitalsEntity>> recentVitals = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.MutableLiveData<java.util.List<com.sensacare.veepoo.VitalsEntity>> _chartData = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.LiveData<java.util.List<com.sensacare.veepoo.VitalsEntity>> chartData = null;
    
    public VitalsViewModel(@org.jetbrains.annotations.NotNull
    android.app.Application application) {
        super(null);
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.LiveData<java.util.List<com.sensacare.veepoo.VitalsEntity>> getRecentVitals() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.LiveData<java.util.List<com.sensacare.veepoo.VitalsEntity>> getChartData() {
        return null;
    }
    
    private final void loadRecentVitals() {
    }
    
    private final void loadChartData() {
    }
    
    public final void refreshVitals() {
    }
}