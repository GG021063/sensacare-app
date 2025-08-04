package com.sensacare.veepoo;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\f\u0012\b\u0012\u00060\u0002R\u00020\u00000\u0001:\u0001\u0015B\u0005\u00a2\u0006\u0002\u0010\u0003J\b\u0010\t\u001a\u00020\nH\u0016J\u001c\u0010\u000b\u001a\u00020\f2\n\u0010\r\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\u000e\u001a\u00020\nH\u0016J\u001c\u0010\u000f\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\nH\u0016J\u0014\u0010\u0013\u001a\u00020\f2\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0016"}, d2 = {"Lcom/sensacare/veepoo/AdminAlertsAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/sensacare/veepoo/AdminAlertsAdapter$AlertsViewHolder;", "()V", "alerts", "", "Lcom/sensacare/veepoo/AdminAlertData;", "dateFormatter", "Ljava/text/SimpleDateFormat;", "getItemCount", "", "onBindViewHolder", "", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "updateAlerts", "newAlerts", "AlertsViewHolder", "app_debug"})
public final class AdminAlertsAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<com.sensacare.veepoo.AdminAlertsAdapter.AlertsViewHolder> {
    @org.jetbrains.annotations.NotNull
    private java.util.List<com.sensacare.veepoo.AdminAlertData> alerts;
    @org.jetbrains.annotations.NotNull
    private final java.text.SimpleDateFormat dateFormatter = null;
    
    public AdminAlertsAdapter() {
        super();
    }
    
    public final void updateAlerts(@org.jetbrains.annotations.NotNull
    java.util.List<com.sensacare.veepoo.AdminAlertData> newAlerts) {
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public com.sensacare.veepoo.AdminAlertsAdapter.AlertsViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @java.lang.Override
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull
    com.sensacare.veepoo.AdminAlertsAdapter.AlertsViewHolder holder, int position) {
    }
    
    @java.lang.Override
    public int getItemCount() {
        return 0;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2 = {"Lcom/sensacare/veepoo/AdminAlertsAdapter$AlertsViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "itemView", "Landroid/view/View;", "(Lcom/sensacare/veepoo/AdminAlertsAdapter;Landroid/view/View;)V", "text1", "Landroid/widget/TextView;", "text2", "bind", "", "alert", "Lcom/sensacare/veepoo/AdminAlertData;", "app_debug"})
    public final class AlertsViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        @org.jetbrains.annotations.NotNull
        private final android.widget.TextView text1 = null;
        @org.jetbrains.annotations.NotNull
        private final android.widget.TextView text2 = null;
        
        public AlertsViewHolder(@org.jetbrains.annotations.NotNull
        android.view.View itemView) {
            super(null);
        }
        
        public final void bind(@org.jetbrains.annotations.NotNull
        com.sensacare.veepoo.AdminAlertData alert) {
        }
    }
}