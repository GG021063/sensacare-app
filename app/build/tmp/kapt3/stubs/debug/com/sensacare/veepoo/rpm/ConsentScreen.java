package com.sensacare.veepoo.rpm;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 $2\u00020\u0001:\u0001$B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0010\u001a\u00020\u0011H\u0002J$\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0016J\b\u0010\u001a\u001a\u00020\u0011H\u0016J\u001a\u0010\u001b\u001a\u00020\u00112\u0006\u0010\u001c\u001a\u00020\u00132\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0016J\b\u0010\u001d\u001a\u00020\u0011H\u0002J\b\u0010\u001e\u001a\u00020\u0011H\u0002J\b\u0010\u001f\u001a\u00020\u0011H\u0002J\b\u0010 \u001a\u00020\u0011H\u0002J\u0010\u0010!\u001a\u00020\u00112\u0006\u0010\"\u001a\u00020#H\u0002R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\u00020\u00048BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006%"}, d2 = {"Lcom/sensacare/veepoo/rpm/ConsentScreen;", "Landroidx/fragment/app/Fragment;", "()V", "_binding", "Lcom/sensacare/veepoo/databinding/FragmentConsentBinding;", "binding", "getBinding", "()Lcom/sensacare/veepoo/databinding/FragmentConsentBinding;", "clientId", "", "clientName", "consentManager", "Lcom/sensacare/veepoo/rpm/ConsentManager;", "rpmIntegrationManager", "Lcom/sensacare/veepoo/rpm/RPMIntegrationManager;", "userToken", "captureConsent", "", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onDestroyView", "onViewCreated", "view", "proceedWithoutConsent", "setupButtons", "setupConsentPolicy", "setupConsentTypeSelector", "showConsentSummary", "consentRecord", "Lcom/sensacare/veepoo/rpm/ConsentRecord;", "Companion", "app_debug"})
public final class ConsentScreen extends androidx.fragment.app.Fragment {
    @org.jetbrains.annotations.Nullable
    private com.sensacare.veepoo.databinding.FragmentConsentBinding _binding;
    private com.sensacare.veepoo.rpm.ConsentManager consentManager;
    private com.sensacare.veepoo.rpm.RPMIntegrationManager rpmIntegrationManager;
    @org.jetbrains.annotations.NotNull
    private java.lang.String clientId = "";
    @org.jetbrains.annotations.NotNull
    private java.lang.String clientName = "";
    @org.jetbrains.annotations.NotNull
    private java.lang.String userToken = "";
    @org.jetbrains.annotations.NotNull
    public static final com.sensacare.veepoo.rpm.ConsentScreen.Companion Companion = null;
    
    public ConsentScreen() {
        super();
    }
    
    private final com.sensacare.veepoo.databinding.FragmentConsentBinding getBinding() {
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
    
    private final void setupConsentPolicy() {
    }
    
    private final void setupConsentTypeSelector() {
    }
    
    private final void setupButtons() {
    }
    
    private final void captureConsent() {
    }
    
    private final void proceedWithoutConsent() {
    }
    
    private final void showConsentSummary(com.sensacare.veepoo.rpm.ConsentRecord consentRecord) {
    }
    
    @java.lang.Override
    public void onDestroyView() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u0006\u00a8\u0006\t"}, d2 = {"Lcom/sensacare/veepoo/rpm/ConsentScreen$Companion;", "", "()V", "newInstance", "Lcom/sensacare/veepoo/rpm/ConsentScreen;", "clientId", "", "clientName", "userToken", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.sensacare.veepoo.rpm.ConsentScreen newInstance(@org.jetbrains.annotations.NotNull
        java.lang.String clientId, @org.jetbrains.annotations.NotNull
        java.lang.String clientName, @org.jetbrains.annotations.NotNull
        java.lang.String userToken) {
            return null;
        }
    }
}