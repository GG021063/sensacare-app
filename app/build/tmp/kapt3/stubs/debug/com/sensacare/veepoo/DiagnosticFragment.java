package com.sensacare.veepoo;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0010\u001a\u00020\u0011H\u0002J$\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0016J\b\u0010\u001a\u001a\u00020\u0011H\u0016J\u001a\u0010\u001b\u001a\u00020\u00112\u0006\u0010\u001c\u001a\u00020\u00132\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0016J\b\u0010\u001d\u001a\u00020\u0011H\u0002J\b\u0010\u001e\u001a\u00020\u0011H\u0002J\b\u0010\u001f\u001a\u00020\u0011H\u0002J\b\u0010 \u001a\u00020\u0011H\u0002R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\u00020\u00048BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006!"}, d2 = {"Lcom/sensacare/veepoo/DiagnosticFragment;", "Landroidx/fragment/app/Fragment;", "()V", "_binding", "Lcom/sensacare/veepoo/databinding/FragmentDiagnosticBinding;", "binding", "getBinding", "()Lcom/sensacare/veepoo/databinding/FragmentDiagnosticBinding;", "currentCategoryFilter", "Lcom/sensacare/veepoo/DiagnosticLogger$LogCategory;", "currentLevelFilter", "Lcom/sensacare/veepoo/DiagnosticLogger$LogLevel;", "diagnosticLogger", "Lcom/sensacare/veepoo/DiagnosticLogger;", "logAdapter", "Lcom/sensacare/veepoo/DiagnosticLogAdapter;", "exportLogs", "", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onDestroyView", "onViewCreated", "view", "setupFilters", "setupUI", "startLogUpdates", "updateLogDisplay", "app_debug"})
public final class DiagnosticFragment extends androidx.fragment.app.Fragment {
    @org.jetbrains.annotations.Nullable
    private com.sensacare.veepoo.databinding.FragmentDiagnosticBinding _binding;
    private com.sensacare.veepoo.DiagnosticLogger diagnosticLogger;
    private com.sensacare.veepoo.DiagnosticLogAdapter logAdapter;
    @org.jetbrains.annotations.Nullable
    private com.sensacare.veepoo.DiagnosticLogger.LogCategory currentCategoryFilter;
    @org.jetbrains.annotations.Nullable
    private com.sensacare.veepoo.DiagnosticLogger.LogLevel currentLevelFilter;
    
    public DiagnosticFragment() {
        super();
    }
    
    private final com.sensacare.veepoo.databinding.FragmentDiagnosticBinding getBinding() {
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
    
    private final void setupUI() {
    }
    
    private final void setupFilters() {
    }
    
    private final void startLogUpdates() {
    }
    
    private final void updateLogDisplay() {
    }
    
    private final void exportLogs() {
    }
    
    @java.lang.Override
    public void onDestroyView() {
    }
}