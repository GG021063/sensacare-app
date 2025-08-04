package com.sensacare.veepoo.rpm;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0006X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2 = {"Lcom/sensacare/veepoo/rpm/RPMConfig;", "", "()V", "BASE_URL", "", "HEARTBEAT_INTERVAL_MS", "", "MAX_SYNC_QUEUE_SIZE", "", "SYNC_RETRY_ATTEMPTS", "SYNC_RETRY_DELAY_MS", "app_debug"})
public final class RPMConfig {
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String BASE_URL = "https://api.sensacare.com/rpm/v1/";
    public static final long HEARTBEAT_INTERVAL_MS = 900000L;
    public static final int SYNC_RETRY_ATTEMPTS = 3;
    public static final long SYNC_RETRY_DELAY_MS = 5000L;
    public static final int MAX_SYNC_QUEUE_SIZE = 1000;
    @org.jetbrains.annotations.NotNull
    public static final com.sensacare.veepoo.rpm.RPMConfig INSTANCE = null;
    
    private RPMConfig() {
        super();
    }
}