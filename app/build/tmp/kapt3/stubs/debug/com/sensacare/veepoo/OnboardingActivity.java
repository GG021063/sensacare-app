package com.sensacare.veepoo;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0012\u0018\u0000 52\u00020\u0001:\u000256B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u001c\u001a\u00020\u001dH\u0002J\b\u0010\u001e\u001a\u00020\u001dH\u0002J\b\u0010\u001f\u001a\u00020\u001dH\u0002J\b\u0010 \u001a\u00020\u001dH\u0002J\b\u0010!\u001a\u00020\u001dH\u0002J\b\u0010\"\u001a\u00020\u001dH\u0002J\u0012\u0010#\u001a\u00020\u001d2\b\u0010$\u001a\u0004\u0018\u00010%H\u0014J\b\u0010&\u001a\u00020\u001dH\u0014J\b\u0010\'\u001a\u00020\u001dH\u0002J\b\u0010(\u001a\u00020\u001dH\u0002J\b\u0010)\u001a\u00020\u001dH\u0002J\b\u0010*\u001a\u00020\u001dH\u0002J\b\u0010+\u001a\u00020\u001dH\u0002J\b\u0010,\u001a\u00020\u001dH\u0002J\b\u0010-\u001a\u00020\u001dH\u0002J\b\u0010.\u001a\u00020\u001dH\u0002J\b\u0010/\u001a\u00020\u001dH\u0002J\u0010\u00100\u001a\u00020\u001d2\u0006\u00101\u001a\u00020\u000fH\u0002J\b\u00102\u001a\u00020\u001dH\u0002J\b\u00103\u001a\u00020\u001dH\u0002J\b\u00104\u001a\u00020\u001dH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u001c\u0010\t\u001a\u0010\u0012\f\u0012\n \f*\u0004\u0018\u00010\u000b0\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u000e0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0012\u0010\u0012\u001a\u00060\u0013R\u00020\u0000X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0016\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u000e0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001c\u0010\u0017\u001a\u0010\u0012\f\u0012\n \f*\u0004\u0018\u00010\u000f0\u000f0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0018\u001a\u0004\u0018\u00010\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0019\u001a\u0004\u0018\u00010\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u00067"}, d2 = {"Lcom/sensacare/veepoo/OnboardingActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "binding", "Lcom/sensacare/veepoo/databinding/ActivityOnboardingBinding;", "bleConnectionManager", "Lcom/sensacare/veepoo/BleConnectionManager;", "bluetoothAdapter", "Landroid/bluetooth/BluetoothAdapter;", "bluetoothEnableLauncher", "Landroidx/activity/result/ActivityResultLauncher;", "Landroid/content/Intent;", "kotlin.jvm.PlatformType", "bluetoothPermissionLauncher", "", "", "currentStep", "", "deviceAdapter", "Lcom/sensacare/veepoo/OnboardingActivity$DeviceAdapter;", "isScanning", "", "locationPermissionLauncher", "notificationPermissionLauncher", "selectedDeviceAddress", "selectedDeviceName", "sharedPreferences", "Landroid/content/SharedPreferences;", "checkBluetoothEnabled", "", "checkBluetoothPermissions", "checkLocationPermissions", "checkNotificationPermissions", "completeOnboarding", "handleDeviceDiscoveryAction", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "proceedToNextStep", "setupBleCallbacks", "setupOnboardingFlow", "showBluetoothPermissionStep", "showCurrentStep", "showDevicePairingStep", "showLocationPermissionStep", "showNotificationPermissionStep", "showNotificationSettingsDialog", "showPermissionDeniedMessage", "message", "showThresholdSetupStep", "showWelcomeStep", "startDeviceDiscovery", "Companion", "DeviceAdapter", "app_debug"})
public final class OnboardingActivity extends androidx.appcompat.app.AppCompatActivity {
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String TAG = "OnboardingActivity";
    private com.sensacare.veepoo.databinding.ActivityOnboardingBinding binding;
    private android.content.SharedPreferences sharedPreferences;
    private android.bluetooth.BluetoothAdapter bluetoothAdapter;
    private com.sensacare.veepoo.BleConnectionManager bleConnectionManager;
    private com.sensacare.veepoo.OnboardingActivity.DeviceAdapter deviceAdapter;
    private int currentStep = 0;
    @org.jetbrains.annotations.Nullable
    private java.lang.String selectedDeviceAddress;
    @org.jetbrains.annotations.Nullable
    private java.lang.String selectedDeviceName;
    private boolean isScanning = false;
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
    
    private final void setupBleCallbacks() {
    }
    
    @java.lang.Override
    protected void onDestroy() {
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
    
    private final void handleDeviceDiscoveryAction() {
    }
    
    private final void startDeviceDiscovery() {
    }
    
    private final void completeOnboarding() {
    }
    
    private final void proceedToNextStep() {
    }
    
    private final void showPermissionDeniedMessage(java.lang.String message) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2 = {"Lcom/sensacare/veepoo/OnboardingActivity$Companion;", "", "()V", "TAG", "", "shouldShowOnboarding", "", "context", "Landroid/content/Context;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        public final boolean shouldShowOnboarding(@org.jetbrains.annotations.NotNull
        android.content.Context context) {
            return false;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0082\u0004\u0018\u00002\u0010\u0012\f\u0012\n0\u0002R\u00060\u0000R\u00020\u00030\u0001:\u0001\u0017B\'\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\t0\b\u00a2\u0006\u0002\u0010\nJ\b\u0010\r\u001a\u00020\fH\u0016J \u0010\u000e\u001a\u00020\t2\u000e\u0010\u000f\u001a\n0\u0002R\u00060\u0000R\u00020\u00032\u0006\u0010\u0010\u001a\u00020\fH\u0016J \u0010\u0011\u001a\n0\u0002R\u00060\u0000R\u00020\u00032\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\fH\u0016J\u0014\u0010\u0015\u001a\u00020\t2\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0018"}, d2 = {"Lcom/sensacare/veepoo/OnboardingActivity$DeviceAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/sensacare/veepoo/OnboardingActivity$DeviceAdapter$DeviceViewHolder;", "Lcom/sensacare/veepoo/OnboardingActivity;", "devices", "", "Landroid/bluetooth/BluetoothDevice;", "onDeviceSelected", "Lkotlin/Function1;", "", "(Lcom/sensacare/veepoo/OnboardingActivity;Ljava/util/List;Lkotlin/jvm/functions/Function1;)V", "selectedPosition", "", "getItemCount", "onBindViewHolder", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "updateDevices", "newDevices", "DeviceViewHolder", "app_debug"})
    final class DeviceAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<com.sensacare.veepoo.OnboardingActivity.DeviceAdapter.DeviceViewHolder> {
        @org.jetbrains.annotations.NotNull
        private java.util.List<android.bluetooth.BluetoothDevice> devices;
        @org.jetbrains.annotations.NotNull
        private final kotlin.jvm.functions.Function1<android.bluetooth.BluetoothDevice, kotlin.Unit> onDeviceSelected = null;
        private int selectedPosition = -1;
        
        public DeviceAdapter(@org.jetbrains.annotations.NotNull
        java.util.List<android.bluetooth.BluetoothDevice> devices, @org.jetbrains.annotations.NotNull
        kotlin.jvm.functions.Function1<? super android.bluetooth.BluetoothDevice, kotlin.Unit> onDeviceSelected) {
            super();
        }
        
        @java.lang.Override
        @org.jetbrains.annotations.NotNull
        public com.sensacare.veepoo.OnboardingActivity.DeviceAdapter.DeviceViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull
        android.view.ViewGroup parent, int viewType) {
            return null;
        }
        
        @java.lang.Override
        public void onBindViewHolder(@org.jetbrains.annotations.NotNull
        com.sensacare.veepoo.OnboardingActivity.DeviceAdapter.DeviceViewHolder holder, int position) {
        }
        
        @java.lang.Override
        public int getItemCount() {
            return 0;
        }
        
        public final void updateDevices(@org.jetbrains.annotations.NotNull
        java.util.List<android.bluetooth.BluetoothDevice> newDevices) {
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\bR\u0011\u0010\u000b\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\bR\u0011\u0010\r\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0011\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\b\u00a8\u0006\u0013"}, d2 = {"Lcom/sensacare/veepoo/OnboardingActivity$DeviceAdapter$DeviceViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "view", "Landroid/view/View;", "(Lcom/sensacare/veepoo/OnboardingActivity$DeviceAdapter;Landroid/view/View;)V", "deviceAddress", "Landroid/widget/TextView;", "getDeviceAddress", "()Landroid/widget/TextView;", "deviceName", "getDeviceName", "deviceRssi", "getDeviceRssi", "signalStrength", "Landroid/widget/ProgressBar;", "getSignalStrength", "()Landroid/widget/ProgressBar;", "signalStrengthText", "getSignalStrengthText", "app_debug"})
        public final class DeviceViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
            @org.jetbrains.annotations.NotNull
            private final android.widget.TextView deviceName = null;
            @org.jetbrains.annotations.NotNull
            private final android.widget.TextView deviceAddress = null;
            @org.jetbrains.annotations.NotNull
            private final android.widget.TextView deviceRssi = null;
            @org.jetbrains.annotations.NotNull
            private final android.widget.ProgressBar signalStrength = null;
            @org.jetbrains.annotations.NotNull
            private final android.widget.TextView signalStrengthText = null;
            
            public DeviceViewHolder(@org.jetbrains.annotations.NotNull
            android.view.View view) {
                super(null);
            }
            
            @org.jetbrains.annotations.NotNull
            public final android.widget.TextView getDeviceName() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull
            public final android.widget.TextView getDeviceAddress() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull
            public final android.widget.TextView getDeviceRssi() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull
            public final android.widget.ProgressBar getSignalStrength() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull
            public final android.widget.TextView getSignalStrengthText() {
                return null;
            }
        }
    }
}