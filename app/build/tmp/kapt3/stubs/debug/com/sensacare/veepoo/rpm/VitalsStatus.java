package com.sensacare.veepoo.rpm;

/**
 * Vitals status enum for device-side display
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/sensacare/veepoo/rpm/VitalsStatus;", "", "(Ljava/lang/String;I)V", "NORMAL", "NO_DATA", "CRITICAL_ERROR", "BLE_ERROR", "app_debug"})
public enum VitalsStatus {
    /*public static final*/ NORMAL /* = new NORMAL() */,
    /*public static final*/ NO_DATA /* = new NO_DATA() */,
    /*public static final*/ CRITICAL_ERROR /* = new CRITICAL_ERROR() */,
    /*public static final*/ BLE_ERROR /* = new BLE_ERROR() */;
    
    VitalsStatus() {
    }
    
    @org.jetbrains.annotations.NotNull
    public static kotlin.enums.EnumEntries<com.sensacare.veepoo.rpm.VitalsStatus> getEntries() {
        return null;
    }
}