package com.sensacare.veepoo;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u000f\u0018\u0000 ,2\u00020\u0001:\u0001,B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0016\u001a\u00020\u0017H\u0002J\b\u0010\u0018\u001a\u00020\u0017H\u0002J\b\u0010\u0019\u001a\u00020\u0017H\u0002J\b\u0010\u001a\u001a\u00020\u0017H\u0002J\b\u0010\u001b\u001a\u00020\u0017H\u0002J\u0012\u0010\u001c\u001a\u00020\u00172\b\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u0014J\b\u0010\u001f\u001a\u00020\u0017H\u0002J\b\u0010 \u001a\u00020\u0017H\u0002J\b\u0010!\u001a\u00020\u0017H\u0002J\b\u0010\"\u001a\u00020\u0017H\u0002J\b\u0010#\u001a\u00020\u0017H\u0002J\b\u0010$\u001a\u00020\u0017H\u0002J\b\u0010%\u001a\u00020\u0017H\u0002J\b\u0010&\u001a\u00020\u0017H\u0002J\u0010\u0010\'\u001a\u00020\u00172\u0006\u0010(\u001a\u00020\rH\u0002J\b\u0010)\u001a\u00020\u0017H\u0002J\b\u0010*\u001a\u00020\u0017H\u0002J\b\u0010+\u001a\u00020\u0017H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u001c\u0010\u0007\u001a\u0010\u0012\f\u0012\n \n*\u0004\u0018\u00010\t0\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\f0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\f0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001c\u0010\u0011\u001a\u0010\u0012\f\u0012\n \n*\u0004\u0018\u00010\r0\r0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0012\u001a\u0004\u0018\u00010\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0013\u001a\u0004\u0018\u00010\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006-"}, d2 = {"Lcom/sensacare/veepoo/OnboardingActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "binding", "Lcom/sensacare/veepoo/databinding/ActivityOnboardingBinding;", "bluetoothAdapter", "Landroid/bluetooth/BluetoothAdapter;", "bluetoothEnableLauncher", "Landroidx/activity/result/ActivityResultLauncher;", "Landroid/content/Intent;", "kotlin.jvm.PlatformType", "bluetoothPermissionLauncher", "", "", "currentStep", "", "locationPermissionLauncher", "notificationPermissionLauncher", "selectedDeviceAddress", "selectedDeviceName", "sharedPreferences", "Landroid/content/SharedPreferences;", "checkBluetoothEnabled", "", "checkBluetoothPermissions", "checkLocationPermissions", "checkNotificationPermissions", "completeOnboarding", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "proceedToNextStep", "setupOnboardingFlow", "showBluetoothPermissionStep", "showCurrentStep", "showDevicePairingStep", "showLocationPermissionStep", "showNotificationPermissionStep", "showNotificationSettingsDialog", "showPermissionDeniedMessage", "message", "showThresholdSetupStep", "showWelcomeStep", "startDeviceDiscovery", "Companion", "app_debug"})
public final class OnboardingActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.sensacare.veepoo.databinding.ActivityOnboardingBinding binding;
    private android.content.SharedPreferences sharedPreferences;
    private android.bluetooth.BluetoothAdapter bluetoothAdapter;
    private int currentStep = 0;
    @org.jetbrains.annotations.Nullable
    private java.lang.String selectedDeviceAddress;
    @org.jetbrains.annotations.Nullable
    private java.lang.String selectedDeviceName;
    @org.jetbrains.annotations.NotNull
    private final androidx.activity.result.ActivityResultLauncher<java.lang.String[]> bluetoothPermissionLauncher = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.activity.result.ActivityResultLauncher<java.lang.String[]> locationPermissionLauncher = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.activity.result.ActivityResultLauncher<java.lang.String> notificationPermissionLauncher = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.activity.result.ActivityResultLauncher<android.content.Intent> bluetoothEnableLauncher = null;
    @org.jetbrains.annotations.NotNull
    public static final com.sensacare.veepoo.OnboardingActivity.Companion Companion = null;
    
    public OnboardingActivity() {
        super();
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    private final void setupOnboardingFlow() {
    }
    
    private final void showCurrentStep() {
    }
    
    private final void showWelcomeStep() {
    }
    
    private final void showBluetoothPermissionStep() {
    }
    
    private final void showLocationPermissionStep() {
    }
    
    private final void showNotificationPermissionStep() {
    }
    
    private final void showDevicePairingStep() {
    }
    
    private final void showThresholdSetupStep() {
    }
    
    private final void checkBluetoothEnabled() {
    }
    
    private final void checkBluetoothPermissions() {
    }
    
    private final void checkLocationPermissions() {
    }
    
    private final void checkNotificationPermissions() {
    }
    
    private final void showNotificationSettingsDialog() {
    }
    
    private final void startDeviceDiscovery() {
    }
    
    private final void completeOnboarding() {
    }
    
    private final void proceedToNextStep() {
    }
    
    private final void showPermissionDeniedMessage(java.lang.String message) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/sensacare/veepoo/OnboardingActivity$Companion;", "", "()V", "shouldShowOnboarding", "", "context", "Landroid/content/Context;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        public final boolean shouldShowOnboarding(@org.jetbrains.annotations.NotNull
        android.content.Context context) {
            return false;
        }
    }
}