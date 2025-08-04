package com.sensacare.veepoo;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001:\u0001\u0016B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nJ\u000e\u0010\u000b\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\fJ\u000e\u0010\r\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\fJ\u000e\u0010\u000e\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\fJ\u001c\u0010\u000f\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011J\u001c\u0010\u0013\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011J\u001c\u0010\u0014\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011J\u001c\u0010\u0015\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0017"}, d2 = {"Lcom/sensacare/veepoo/ChartManager;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "timeFormatter", "Ljava/text/SimpleDateFormat;", "setupBloodPressureChart", "", "chart", "Lcom/github/mikephil/charting/charts/BarChart;", "setupHeartRateChart", "Lcom/github/mikephil/charting/charts/LineChart;", "setupSpO2Chart", "setupTemperatureChart", "updateBloodPressureChart", "vitals", "", "Lcom/sensacare/veepoo/VitalsEntity;", "updateHeartRateChart", "updateSpO2Chart", "updateTemperatureChart", "TimeAxisFormatter", "app_debug"})
public final class ChartManager {
    @org.jetbrains.annotations.NotNull
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull
    private final java.text.SimpleDateFormat timeFormatter = null;
    
    public ChartManager(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
        super();
    }
    
    public final void setupHeartRateChart(@org.jetbrains.annotations.NotNull
    com.github.mikephil.charting.charts.LineChart chart) {
    }
    
    public final void setupSpO2Chart(@org.jetbrains.annotations.NotNull
    com.github.mikephil.charting.charts.LineChart chart) {
    }
    
    public final void setupTemperatureChart(@org.jetbrains.annotations.NotNull
    com.github.mikephil.charting.charts.LineChart chart) {
    }
    
    public final void setupBloodPressureChart(@org.jetbrains.annotations.NotNull
    com.github.mikephil.charting.charts.BarChart chart) {
    }
    
    public final void updateHeartRateChart(@org.jetbrains.annotations.NotNull
    com.github.mikephil.charting.charts.LineChart chart, @org.jetbrains.annotations.NotNull
    java.util.List<com.sensacare.veepoo.VitalsEntity> vitals) {
    }
    
    public final void updateSpO2Chart(@org.jetbrains.annotations.NotNull
    com.github.mikephil.charting.charts.LineChart chart, @org.jetbrains.annotations.NotNull
    java.util.List<com.sensacare.veepoo.VitalsEntity> vitals) {
    }
    
    public final void updateTemperatureChart(@org.jetbrains.annotations.NotNull
    com.github.mikephil.charting.charts.LineChart chart, @org.jetbrains.annotations.NotNull
    java.util.List<com.sensacare.veepoo.VitalsEntity> vitals) {
    }
    
    public final void updateBloodPressureChart(@org.jetbrains.annotations.NotNull
    com.github.mikephil.charting.charts.BarChart chart, @org.jetbrains.annotations.NotNull
    java.util.List<com.sensacare.veepoo.VitalsEntity> vitals) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0007\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016\u00a8\u0006\u0007"}, d2 = {"Lcom/sensacare/veepoo/ChartManager$TimeAxisFormatter;", "Lcom/github/mikephil/charting/formatter/ValueFormatter;", "(Lcom/sensacare/veepoo/ChartManager;)V", "getFormattedValue", "", "value", "", "app_debug"})
    final class TimeAxisFormatter extends com.github.mikephil.charting.formatter.ValueFormatter {
        
        public TimeAxisFormatter() {
            super();
        }
        
        @java.lang.Override
        @org.jetbrains.annotations.NotNull
        public java.lang.String getFormattedValue(float value) {
            return null;
        }
    }
}