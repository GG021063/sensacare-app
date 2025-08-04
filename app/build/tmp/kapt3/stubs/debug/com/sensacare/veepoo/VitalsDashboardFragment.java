package com.sensacare.veepoo;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0092\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001@B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0016\u001a\u00020\u0017H\u0002J$\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001d2\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u0016J\b\u0010 \u001a\u00020\u0017H\u0016J\u001a\u0010!\u001a\u00020\u00172\u0006\u0010\"\u001a\u00020\u00192\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u0016J\b\u0010#\u001a\u00020\u0017H\u0002J\b\u0010$\u001a\u00020\u0017H\u0002J\b\u0010%\u001a\u00020\u0017H\u0002J\b\u0010&\u001a\u00020\u0017H\u0002J\b\u0010\'\u001a\u00020\u0017H\u0002J\b\u0010(\u001a\u00020\u0017H\u0002J\u0010\u0010)\u001a\u00020\u00172\u0006\u0010*\u001a\u00020\u0011H\u0002J\b\u0010+\u001a\u00020\u0017H\u0002J\u0016\u0010,\u001a\u00020\u00172\f\u0010-\u001a\b\u0012\u0004\u0012\u00020/0.H\u0002J\u0010\u00100\u001a\u00020\u00172\u0006\u00101\u001a\u000202H\u0002J\u0016\u00103\u001a\u00020\u00172\f\u00104\u001a\b\u0012\u0004\u0012\u0002050.H\u0002J\u0010\u00106\u001a\u00020\u00172\u0006\u00107\u001a\u000208H\u0002J\b\u00109\u001a\u00020\u0017H\u0002J\u0010\u0010:\u001a\u00020\u00172\u0006\u0010-\u001a\u00020/H\u0002J\u0010\u0010;\u001a\u00020\u00172\u0006\u0010<\u001a\u00020=H\u0002J\u0010\u0010>\u001a\u00020\u00172\u0006\u00107\u001a\u00020?H\u0002R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\u00020\u00048BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u000e\u0010\b\u001a\u00020\tX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082.\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u000e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\u00100\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006A"}, d2 = {"Lcom/sensacare/veepoo/VitalsDashboardFragment;", "Landroidx/fragment/app/Fragment;", "()V", "_binding", "Lcom/sensacare/veepoo/databinding/FragmentDashboardBinding;", "binding", "getBinding", "()Lcom/sensacare/veepoo/databinding/FragmentDashboardBinding;", "chartManager", "Lcom/sensacare/veepoo/ChartManager;", "connectionManager", "Lcom/sensacare/veepoo/BleConnectionManager;", "preferencesManager", "Lcom/sensacare/veepoo/PreferencesManager;", "requestPermissionLauncher", "Landroidx/activity/result/ActivityResultLauncher;", "", "", "rpmIntegrationManager", "Lcom/sensacare/veepoo/rpm/RPMIntegrationManager;", "viewModel", "Lcom/sensacare/veepoo/VitalsViewModel;", "checkPermissions", "", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onDestroyView", "onViewCreated", "view", "setupBluetooth", "setupCharts", "setupConnectionCallbacks", "setupObservers", "setupRPMObservers", "setupServiceControls", "showConnectionError", "error", "showRPMSetupDialog", "updateCharts", "vitals", "", "Lcom/sensacare/veepoo/VitalsEntity;", "updateConnectionStatus", "state", "Lcom/sensacare/veepoo/BleConnectionManager$ConnectionState;", "updateRPMAlerts", "alerts", "Lcom/sensacare/veepoo/rpm/AlertData;", "updateRPMStatus", "status", "Lcom/sensacare/veepoo/rpm/RPMStatus;", "updateServiceStatus", "updateVitalsDisplay", "updateVitalsDisplayFromRPM", "vitalsData", "Lcom/sensacare/veepoo/rpm/VitalsData;", "updateVitalsStatus", "Lcom/sensacare/veepoo/rpm/VitalsStatus;", "ConnectionStatusInfo", "app_debug"})
public final class VitalsDashboardFragment extends androidx.fragment.app.Fragment {
    @org.jetbrains.annotations.Nullable
    private com.sensacare.veepoo.databinding.FragmentDashboardBinding _binding;
    private com.sensacare.veepoo.VitalsViewModel viewModel;
    private com.sensacare.veepoo.ChartManager chartManager;
    private com.sensacare.veepoo.BleConnectionManager connectionManager;
    private com.sensacare.veepoo.rpm.RPMIntegrationManager rpmIntegrationManager;
    private com.sensacare.veepoo.PreferencesManager preferencesManager;
    @org.jetbrains.annotations.NotNull
    private final androidx.activity.result.ActivityResultLauncher<java.lang.String[]> requestPermissionLauncher = null;
    
    public VitalsDashboardFragment() {
        super();
    }
    
    private final com.sensacare.veepoo.databinding.FragmentDashboardBinding getBinding() {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public android.view.View onCreateView(@org.jetbrains.annotations.NotNull
    android.view.LayoutInflater inflater, @org.jetbrains.annotations.Nullable
    android.view.ViewGroup container, @org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
        return null;
    }
    
    @java.lang.Override
    public void onViewCreated(@org.jetbrains.annotations.NotNull
    android.view.View view, @org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    private final void setupObservers() {
    }
    
    private final void updateVitalsDisplay(com.sensacare.veepoo.VitalsEntity vitals) {
    }
    
    private final void setupCharts() {
    }
    
    private final void updateCharts(java.util.List<com.sensacare.veepoo.VitalsEntity> vitals) {
    }
    
    private final void setupServiceControls() {
    }
    
    private final void updateServiceStatus() {
    }
    
    private final void setupConnectionCallbacks() {
    }
    
    private final void updateConnectionStatus(com.sensacare.veepoo.BleConnectionManager.ConnectionState state) {
    }
    
    private final void setupRPMObservers() {
    }
    
    private final void updateRPMStatus(com.sensacare.veepoo.rpm.RPMStatus status) {
    }
    
    private final void updateVitalsStatus(com.sensacare.veepoo.rpm.VitalsStatus status) {
    }
    
    private final void updateRPMAlerts(java.util.List<com.sensacare.veepoo.rpm.AlertData> alerts) {
    }
    
    private final void updateVitalsDisplayFromRPM(com.sensacare.veepoo.rpm.VitalsData vitalsData) {
    }
    
    private final void showRPMSetupDialog() {
    }
    
    private final void showConnectionError(java.lang.String error) {
    }
    
    private final void checkPermissions() {
    }
    
    private final void setupBluetooth() {
    }
    
    @java.lang.Override
    public void onDestroyView() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0014\b\u0082\b\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ\t\u0010\u0013\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0014\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0015\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0016\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0017\u001a\u00020\tH\u00c6\u0003J;\u0010\u0018\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\tH\u00c6\u0001J\u0013\u0010\u0019\u001a\u00020\t2\b\u0010\u001a\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001b\u001a\u00020\u0005H\u00d6\u0001J\t\u0010\u001c\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0007\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\f\u00a8\u0006\u001d"}, d2 = {"Lcom/sensacare/veepoo/VitalsDashboardFragment$ConnectionStatusInfo;", "", "statusText", "", "statusIcon", "", "bannerColor", "buttonText", "buttonEnabled", "", "(Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;Z)V", "getBannerColor", "()Ljava/lang/String;", "getButtonEnabled", "()Z", "getButtonText", "getStatusIcon", "()I", "getStatusText", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "other", "hashCode", "toString", "app_debug"})
    static final class ConnectionStatusInfo {
        @org.jetbrains.annotations.NotNull
        private final java.lang.String statusText = null;
        private final int statusIcon = 0;
        @org.jetbrains.annotations.NotNull
        private final java.lang.String bannerColor = null;
        @org.jetbrains.annotations.NotNull
        private final java.lang.String buttonText = null;
        private final boolean buttonEnabled = false;
        
        public ConnectionStatusInfo(@org.jetbrains.annotations.NotNull
        java.lang.String statusText, int statusIcon, @org.jetbrains.annotations.NotNull
        java.lang.String bannerColor, @org.jetbrains.annotations.NotNull
        java.lang.String buttonText, boolean buttonEnabled) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getStatusText() {
            return null;
        }
        
        public final int getStatusIcon() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getBannerColor() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getButtonText() {
            return null;
        }
        
        public final boolean getButtonEnabled() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component1() {
            return null;
        }
        
        public final int component2() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component3() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component4() {
            return null;
        }
        
        public final boolean component5() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.sensacare.veepoo.VitalsDashboardFragment.ConnectionStatusInfo copy(@org.jetbrains.annotations.NotNull
        java.lang.String statusText, int statusIcon, @org.jetbrains.annotations.NotNull
        java.lang.String bannerColor, @org.jetbrains.annotations.NotNull
        java.lang.String buttonText, boolean buttonEnabled) {
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