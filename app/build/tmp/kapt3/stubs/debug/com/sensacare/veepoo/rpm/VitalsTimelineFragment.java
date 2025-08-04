package com.sensacare.veepoo.rpm;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u000e\u001a\u00020\u000f2\b\b\u0002\u0010\u0010\u001a\u00020\u0011H\u0002J$\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0016J\b\u0010\u001a\u001a\u00020\u000fH\u0016J\u001a\u0010\u001b\u001a\u00020\u000f2\u0006\u0010\u001c\u001a\u00020\u00132\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0016J\b\u0010\u001d\u001a\u00020\u000fH\u0002J\b\u0010\u001e\u001a\u00020\u000fH\u0002J\b\u0010\u001f\u001a\u00020\u000fH\u0002J\u0016\u0010 \u001a\u00020\u000f2\f\u0010!\u001a\b\u0012\u0004\u0012\u00020#0\"H\u0002R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\u00020\u00048BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006$"}, d2 = {"Lcom/sensacare/veepoo/rpm/VitalsTimelineFragment;", "Landroidx/fragment/app/Fragment;", "()V", "_binding", "Lcom/sensacare/veepoo/databinding/FragmentVitalsTimelineBinding;", "binding", "getBinding", "()Lcom/sensacare/veepoo/databinding/FragmentVitalsTimelineBinding;", "dateFormatter", "Ljava/text/SimpleDateFormat;", "rpmIntegrationManager", "Lcom/sensacare/veepoo/rpm/RPMIntegrationManager;", "timelineAdapter", "Lcom/sensacare/veepoo/rpm/VitalsTimelineAdapter;", "loadTimelineData", "", "range", "", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onDestroyView", "onViewCreated", "view", "setupRangeSelector", "setupRecyclerView", "setupRefreshButton", "updateTimelineSummary", "timeline", "", "Lcom/sensacare/veepoo/rpm/VitalsTimelineEntry;", "app_debug"})
public final class VitalsTimelineFragment extends androidx.fragment.app.Fragment {
    @org.jetbrains.annotations.Nullable
    private com.sensacare.veepoo.databinding.FragmentVitalsTimelineBinding _binding;
    private com.sensacare.veepoo.rpm.VitalsTimelineAdapter timelineAdapter;
    private com.sensacare.veepoo.rpm.RPMIntegrationManager rpmIntegrationManager;
    @org.jetbrains.annotations.NotNull
    private final java.text.SimpleDateFormat dateFormatter = null;
    
    public VitalsTimelineFragment() {
        super();
    }
    
    private final com.sensacare.veepoo.databinding.FragmentVitalsTimelineBinding getBinding() {
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
    
    private final void setupRecyclerView() {
    }
    
    private final void setupRangeSelector() {
    }
    
    private final void setupRefreshButton() {
    }
    
    private final void loadTimelineData(java.lang.String range) {
    }
    
    private final void updateTimelineSummary(java.util.List<com.sensacare.veepoo.rpm.VitalsTimelineEntry> timeline) {
    }
    
    @java.lang.Override
    public void onDestroyView() {
    }
}