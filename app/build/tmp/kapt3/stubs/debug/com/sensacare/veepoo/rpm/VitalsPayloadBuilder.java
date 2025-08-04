package com.sensacare.veepoo.rpm;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0006\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 /2\u00020\u0001:\u0002/0B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0012\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0002J-\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\f2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000e2\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0010\u00a2\u0006\u0002\u0010\u0011J3\u0010\u0012\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0014\u001a\u00020\u00152\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\b\u0010\u0016\u001a\u0004\u0018\u00010\u000e2\b\b\u0002\u0010\u0017\u001a\u00020\u0018\u00a2\u0006\u0002\u0010\u0019J\u001a\u0010\u001a\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u001b\u001a\u00020\u001c2\b\u0010\u0007\u001a\u0004\u0018\u00010\bJ(\u0010\u001d\u001a\u0004\u0018\u00010\u001e2\u0006\u0010\u001f\u001a\u00020\u00182\u0006\u0010 \u001a\u00020\u00182\u0006\u0010!\u001a\u00020\u00102\u0006\u0010\"\u001a\u00020\u000eJ\'\u0010#\u001a\u00020$2\u0006\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u0017\u001a\u00020\u0018H\u0002\u00a2\u0006\u0002\u0010%J\u0010\u0010&\u001a\u00020$2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J\u0010\u0010\'\u001a\u00020$2\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\u0017\u0010(\u001a\u00020$2\b\u0010\u0016\u001a\u0004\u0018\u00010\u000eH\u0002\u00a2\u0006\u0002\u0010)J\u0010\u0010*\u001a\u00020$2\u0006\u0010\u0017\u001a\u00020\u0018H\u0002J\u0010\u0010+\u001a\u00020\u00152\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J\u0010\u0010,\u001a\u00020\u00152\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\u000e\u0010-\u001a\u00020.2\u0006\u0010\u0014\u001a\u00020\u0015R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u00061"}, d2 = {"Lcom/sensacare/veepoo/rpm/VitalsPayloadBuilder;", "", "clientContextManager", "Lcom/sensacare/veepoo/rpm/RPMClientContextManager;", "(Lcom/sensacare/veepoo/rpm/RPMClientContextManager;)V", "buildDeviceMetadata", "Lcom/sensacare/veepoo/rpm/DeviceMetadata;", "device", "Landroid/bluetooth/BluetoothDevice;", "buildDeviceStatusPayload", "Lcom/sensacare/veepoo/rpm/DeviceStatusPayload;", "connected", "", "battery", "", "firmware", "", "(ZLjava/lang/Integer;Ljava/lang/String;)Lcom/sensacare/veepoo/rpm/DeviceStatusPayload;", "buildPayload", "Lcom/sensacare/veepoo/rpm/VitalsUploadPayload;", "vitalsData", "Lcom/sensacare/veepoo/VitalsData;", "rssi", "timestamp", "", "(Lcom/sensacare/veepoo/VitalsData;Landroid/bluetooth/BluetoothDevice;Ljava/lang/Integer;J)Lcom/sensacare/veepoo/rpm/VitalsUploadPayload;", "buildPayloadFromEntity", "entity", "Lcom/sensacare/veepoo/VitalsEntity;", "buildSyncGapReport", "Lcom/sensacare/veepoo/rpm/SyncGapReport;", "startTime", "endTime", "reason", "vitalsCount", "calculateConfidence", "", "(Lcom/sensacare/veepoo/VitalsData;Ljava/lang/Integer;J)D", "calculateConfidenceFromEntity", "calculateDataCompleteness", "calculateRssiConfidence", "(Ljava/lang/Integer;)D", "calculateTimestampFreshness", "convertEntityToRPMVitals", "convertToRPMVitals", "validateVitalsForUpload", "Lcom/sensacare/veepoo/rpm/VitalsPayloadBuilder$ValidationResult;", "Companion", "ValidationResult", "app_debug"})
public final class VitalsPayloadBuilder {
    @org.jetbrains.annotations.NotNull
    private final com.sensacare.veepoo.rpm.RPMClientContextManager clientContextManager = null;
    private static final double DEFAULT_CONFIDENCE = 0.95;
    private static final double MIN_CONFIDENCE = 0.5;
    private static final double MAX_CONFIDENCE = 1.0;
    private static final double RSSI_WEIGHT = 0.3;
    private static final double DATA_COMPLETENESS_WEIGHT = 0.4;
    private static final double TIMESTAMP_FRESHNESS_WEIGHT = 0.3;
    @org.jetbrains.annotations.NotNull
    public static final com.sensacare.veepoo.rpm.VitalsPayloadBuilder.Companion Companion = null;
    
    public VitalsPayloadBuilder(@org.jetbrains.annotations.NotNull
    com.sensacare.veepoo.rpm.RPMClientContextManager clientContextManager) {
        super();
    }
    
    /**
     * Build RPM-ready payload from VitalsData
     */
    @org.jetbrains.annotations.Nullable
    public final com.sensacare.veepoo.rpm.VitalsUploadPayload buildPayload(@org.jetbrains.annotations.NotNull
    com.sensacare.veepoo.VitalsData vitalsData, @org.jetbrains.annotations.Nullable
    android.bluetooth.BluetoothDevice device, @org.jetbrains.annotations.Nullable
    java.lang.Integer rssi, long timestamp) {
        return null;
    }
    
    /**
     * Build RPM-ready payload from VitalsEntity (for sync queue)
     */
    @org.jetbrains.annotations.Nullable
    public final com.sensacare.veepoo.rpm.VitalsUploadPayload buildPayloadFromEntity(@org.jetbrains.annotations.NotNull
    com.sensacare.veepoo.VitalsEntity entity, @org.jetbrains.annotations.Nullable
    android.bluetooth.BluetoothDevice device) {
        return null;
    }
    
    /**
     * Build device status payload for heartbeat
     */
    @org.jetbrains.annotations.Nullable
    public final com.sensacare.veepoo.rpm.DeviceStatusPayload buildDeviceStatusPayload(boolean connected, @org.jetbrains.annotations.Nullable
    java.lang.Integer battery, @org.jetbrains.annotations.Nullable
    java.lang.String firmware) {
        return null;
    }
    
    /**
     * Build sync gap report
     */
    @org.jetbrains.annotations.Nullable
    public final com.sensacare.veepoo.rpm.SyncGapReport buildSyncGapReport(long startTime, long endTime, @org.jetbrains.annotations.NotNull
    java.lang.String reason, int vitalsCount) {
        return null;
    }
    
    private final com.sensacare.veepoo.rpm.DeviceMetadata buildDeviceMetadata(android.bluetooth.BluetoothDevice device) {
        return null;
    }
    
    private final com.sensacare.veepoo.VitalsData convertToRPMVitals(com.sensacare.veepoo.VitalsData vitalsData) {
        return null;
    }
    
    private final com.sensacare.veepoo.VitalsData convertEntityToRPMVitals(com.sensacare.veepoo.VitalsEntity entity) {
        return null;
    }
    
    /**
     * Calculate confidence score based on multiple factors
     */
    private final double calculateConfidence(com.sensacare.veepoo.VitalsData vitalsData, java.lang.Integer rssi, long timestamp) {
        return 0.0;
    }
    
    private final double calculateConfidenceFromEntity(com.sensacare.veepoo.VitalsEntity entity) {
        return 0.0;
    }
    
    /**
     * Calculate confidence based on RSSI signal strength
     */
    private final double calculateRssiConfidence(java.lang.Integer rssi) {
        return 0.0;
    }
    
    /**
     * Calculate confidence based on data completeness
     */
    private final double calculateDataCompleteness(com.sensacare.veepoo.VitalsData vitalsData) {
        return 0.0;
    }
    
    /**
     * Calculate confidence based on timestamp freshness
     */
    private final double calculateTimestampFreshness(long timestamp) {
        return 0.0;
    }
    
    /**
     * Validate vitals data for RPM upload
     */
    @org.jetbrains.annotations.NotNull
    public final com.sensacare.veepoo.rpm.VitalsPayloadBuilder.ValidationResult validateVitalsForUpload(@org.jetbrains.annotations.NotNull
    com.sensacare.veepoo.VitalsData vitalsData) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\n"}, d2 = {"Lcom/sensacare/veepoo/rpm/VitalsPayloadBuilder$Companion;", "", "()V", "DATA_COMPLETENESS_WEIGHT", "", "DEFAULT_CONFIDENCE", "MAX_CONFIDENCE", "MIN_CONFIDENCE", "RSSI_WEIGHT", "TIMESTAMP_FRESHNESS_WEIGHT", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\r\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B)\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\u0002\u0010\bJ\t\u0010\r\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u00c6\u0003J\u000f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u00c6\u0003J3\u0010\u0010\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u00c6\u0001J\u0013\u0010\u0011\u001a\u00020\u00032\b\u0010\u0012\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0013\u001a\u00020\u0014H\u00d6\u0001J\t\u0010\u0015\u001a\u00020\u0006H\u00d6\u0001R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u000bR\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\n\u00a8\u0006\u0016"}, d2 = {"Lcom/sensacare/veepoo/rpm/VitalsPayloadBuilder$ValidationResult;", "", "isValid", "", "errors", "", "", "warnings", "(ZLjava/util/List;Ljava/util/List;)V", "getErrors", "()Ljava/util/List;", "()Z", "getWarnings", "component1", "component2", "component3", "copy", "equals", "other", "hashCode", "", "toString", "app_debug"})
    public static final class ValidationResult {
        private final boolean isValid = false;
        @org.jetbrains.annotations.NotNull
        private final java.util.List<java.lang.String> errors = null;
        @org.jetbrains.annotations.NotNull
        private final java.util.List<java.lang.String> warnings = null;
        
        public ValidationResult(boolean isValid, @org.jetbrains.annotations.NotNull
        java.util.List<java.lang.String> errors, @org.jetbrains.annotations.NotNull
        java.util.List<java.lang.String> warnings) {
            super();
        }
        
        public final boolean isValid() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.List<java.lang.String> getErrors() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.List<java.lang.String> getWarnings() {
            return null;
        }
        
        public final boolean component1() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.List<java.lang.String> component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.List<java.lang.String> component3() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.sensacare.veepoo.rpm.VitalsPayloadBuilder.ValidationResult copy(boolean isValid, @org.jetbrains.annotations.NotNull
        java.util.List<java.lang.String> errors, @org.jetbrains.annotations.NotNull
        java.util.List<java.lang.String> warnings) {
            return null;
        }
        
        @java.lang.Override
        public boolean equals(@org.jetbrains.annotations.Nullable
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override
        @org.jetbrains.annotations.NotNull
        public java.lang.String toString() {
            return null;
        }
    }
}