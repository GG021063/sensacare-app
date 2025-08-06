package com.sensacare.veepoo;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 .2\u00020\u0001:\u0002-.B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0010\u001a\u00020\u0011H\u0002J\b\u0010\u0012\u001a\u00020\u0013H\u0002J\u0012\u0010\u0014\u001a\u00020\u00112\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0014J\b\u0010\u0017\u001a\u00020\u0011H\u0014J\u0018\u0010\u0018\u001a\u00020\u00112\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001aH\u0002J\u0010\u0010\u001c\u001a\u00020\u00112\u0006\u0010\u001d\u001a\u00020\u001aH\u0002J\u0018\u0010\u001e\u001a\u00020\u00112\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001aH\u0002J\u0018\u0010\u001f\u001a\u00020\u00112\u0006\u0010\u001d\u001a\u00020\u001a2\u0006\u0010 \u001a\u00020\u001aH\u0002J\b\u0010!\u001a\u00020\u0011H\u0002J\b\u0010\"\u001a\u00020\u0011H\u0002J\b\u0010#\u001a\u00020\u0011H\u0002J\b\u0010$\u001a\u00020\u0011H\u0002J\b\u0010%\u001a\u00020\u0011H\u0002J\b\u0010&\u001a\u00020\u0011H\u0002J\u0010\u0010\'\u001a\u00020\u00112\u0006\u0010(\u001a\u00020\u0004H\u0002J\u0018\u0010)\u001a\u00020\u00112\u0006\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020\u0013H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R#\u0010\u0007\u001a\n \t*\u0004\u0018\u00010\b0\b8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\f\u0010\r\u001a\u0004\b\n\u0010\u000bR\u000e\u0010\u000e\u001a\u00020\u000fX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006/"}, d2 = {"Lcom/sensacare/veepoo/LoginActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "authMode", "Lcom/sensacare/veepoo/LoginActivity$AuthMode;", "binding", "Lcom/sensacare/veepoo/databinding/ActivityLoginBinding;", "prefs", "Landroid/content/SharedPreferences;", "kotlin.jvm.PlatformType", "getPrefs", "()Landroid/content/SharedPreferences;", "prefs$delegate", "Lkotlin/Lazy;", "supabaseManager", "Lcom/sensacare/veepoo/SupabaseManager;", "goToOnboarding", "", "hasCompletedOnboarding", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "performLogin", "username", "", "password", "performSendMagicLink", "email", "performSignUp", "performVerifyOtp", "code", "proceedToMainActivity", "setupGetStartedButton", "setupLoginButton", "setupOtpButtons", "setupOtpToggle", "setupSignUpButton", "switchAuthMode", "mode", "updateTabAppearance", "tab", "Landroid/widget/TextView;", "isActive", "AuthMode", "Companion", "app_debug"})
public final class LoginActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.sensacare.veepoo.databinding.ActivityLoginBinding binding;
    private com.sensacare.veepoo.SupabaseManager supabaseManager;
    @org.jetbrains.annotations.NotNull
    private final kotlin.Lazy prefs$delegate = null;
    @org.jetbrains.annotations.NotNull
    private com.sensacare.veepoo.LoginActivity.AuthMode authMode = com.sensacare.veepoo.LoginActivity.AuthMode.PASSWORD;
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String TAG = "LoginActivity";
    @org.jetbrains.annotations.NotNull
    public static final com.sensacare.veepoo.LoginActivity.Companion Companion = null;
    
    public LoginActivity() {
        super();
    }
    
    private final android.content.SharedPreferences getPrefs() {
        return null;
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    private final void setupLoginButton() {
    }
    
    private final void setupGetStartedButton() {
    }
    
    /**
     * Initialise the sign-up button click listener
     * Creates a new Supabase user using the same email / password inputs
     */
    private final void setupSignUpButton() {
    }
    
    /**
     * Setup the auth mode tabs (Password vs OTP)
     */
    private final void setupOtpToggle() {
    }
    
    private final void updateTabAppearance(android.widget.TextView tab, boolean isActive) {
    }
    
    private final void switchAuthMode(com.sensacare.veepoo.LoginActivity.AuthMode mode) {
    }
    
    /**
     * Wire Send-Magic-Link and Verify-OTP buttons
     */
    private final void setupOtpButtons() {
    }
    
    private final void performLogin(java.lang.String username, java.lang.String password) {
    }
    
    /**
     * Perform a Supabase sign-up and immediately log the user in
     */
    private final void performSignUp(java.lang.String username, java.lang.String password) {
    }
    
    /**
     * Send magic link / OTP to the given email address
     */
    private final void performSendMagicLink(java.lang.String email) {
    }
    
    /**
     * Verify the OTP code that was emailed to the user
     */
    private final void performVerifyOtp(java.lang.String email, java.lang.String code) {
    }
    
    private final boolean hasCompletedOnboarding() {
        return false;
    }
    
    private final void goToOnboarding() {
    }
    
    private final void proceedToMainActivity() {
    }
    
    @java.lang.Override
    protected void onDestroy() {
    }
    
    /**
     * Which authentication flow is currently active
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0082\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004\u00a8\u0006\u0005"}, d2 = {"Lcom/sensacare/veepoo/LoginActivity$AuthMode;", "", "(Ljava/lang/String;I)V", "PASSWORD", "OTP", "app_debug"})
    static enum AuthMode {
        /*public static final*/ PASSWORD /* = new PASSWORD() */,
        /*public static final*/ OTP /* = new OTP() */;
        
        AuthMode() {
        }
        
        @org.jetbrains.annotations.NotNull
        public static kotlin.enums.EnumEntries<com.sensacare.veepoo.LoginActivity.AuthMode> getEntries() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/sensacare/veepoo/LoginActivity$Companion;", "", "()V", "TAG", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}