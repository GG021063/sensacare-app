package com.sensacare.veepoo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.sensacare.veepoo.databinding.ActivityLoginBinding
import kotlinx.coroutines.launch
import io.github.jan.supabase.gotrue.user.UserInfo

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var supabaseManager: SupabaseManager
    private val prefs by lazy {
        getSharedPreferences("SensacareAppPrefs", MODE_PRIVATE)
    }

    /**
     * Which authentication flow is currently active
     */
    private enum class AuthMode { PASSWORD, OTP }
    private var authMode: AuthMode = AuthMode.PASSWORD

    companion object {
        private const val TAG = "LoginActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supabaseManager = SupabaseManager(this)

        // Check if user is already authenticated
        if (supabaseManager.getCurrentUserId() != null) {
            Log.d(TAG, "User already authenticated")
            if (hasCompletedOnboarding()) {
                proceedToMainActivity()
            } else {
                goToOnboarding()
            }
            return
        }

        setupLoginButton()
        setupGetStartedButton()
        setupSignUpButton()
        setupOtpToggle()
        setupOtpButtons()
    }

    private fun setupLoginButton() {
        binding.loginButton.setOnClickListener {
            val username = binding.usernameInput.text.toString()
            val password = binding.passwordInput.text.toString()

            if (username.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            performLogin(username, password)
        }
    }

    private fun setupGetStartedButton() {
        binding.getStartedButton.setOnClickListener {
            goToOnboarding()
        }
    }

    /**
     * Initialise the sign-up button click listener
     * Creates a new Supabase user using the same email / password inputs
     */
    private fun setupSignUpButton() {
        binding.signUpButton.setOnClickListener {
            val username = binding.usernameInput.text.toString()
            val password = binding.passwordInput.text.toString()

            if (username.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            performSignUp(username, password)
        }
    }

    /**
     * Toggle group that switches between password-based auth and OTP (magic link)
     */
    private fun setupOtpToggle() {
        binding.authModeToggle.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (!isChecked) return@addOnButtonCheckedListener
            when (checkedId) {
                binding.passwordModeButton.id -> switchAuthMode(AuthMode.PASSWORD)
                binding.otpModeButton.id       -> switchAuthMode(AuthMode.OTP)
            }
        }
    }

    private fun switchAuthMode(mode: AuthMode) {
        authMode = mode
        when (mode) {
            AuthMode.PASSWORD -> {
                binding.passwordModeContainer.visibility = View.VISIBLE
                binding.otpModeContainer.visibility = View.GONE
            }
            AuthMode.OTP -> {
                binding.passwordModeContainer.visibility = View.GONE
                binding.otpModeContainer.visibility = View.VISIBLE
                // Reset OTP UI
                binding.otpInputLayout.visibility = View.GONE
                binding.verifyOtpButton.visibility = View.GONE
                binding.sendMagicLinkButton.isEnabled = true
            }
        }
    }

    /**
     * Wire Send-Magic-Link and Verify-OTP buttons
     */
    private fun setupOtpButtons() {
        // Send magic link
        binding.sendMagicLinkButton.setOnClickListener {
            val email = binding.usernameInput.text.toString()
            if (email.isBlank()) {
                Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            performSendMagicLink(email)
        }

        // Verify code
        binding.verifyOtpButton.setOnClickListener {
            val email = binding.usernameInput.text.toString()
            val code  = binding.otpInput.text.toString()
            if (email.isBlank() || code.isBlank()) {
                Toast.makeText(this, "Enter email and verification code", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            performVerifyOtp(email, code)
        }
    }

    private fun performLogin(username: String, password: String) {
        binding.progressBar.visibility = View.VISIBLE
        binding.loginButton.isEnabled = false
        binding.signUpButton.isEnabled = false

        lifecycleScope.launch {
            try {
                val result = supabaseManager.signIn(username, password)
                result.fold(
                    onSuccess = { userInfo: UserInfo ->
                        Log.d(TAG, "Login successful for user: ${userInfo.email}")
                        Toast.makeText(this@LoginActivity, "Login successful", Toast.LENGTH_SHORT).show()
                        if (hasCompletedOnboarding()) {
                            proceedToMainActivity()
                        } else {
                            goToOnboarding()
                        }
                    },
                    onFailure = { exception ->
                        Log.e(TAG, "Login failed", exception)
                        Toast.makeText(
                            this@LoginActivity,
                            "Login failed: ${exception.message}",
                            Toast.LENGTH_LONG
                        ).show()
                        binding.progressBar.visibility = View.GONE
                        binding.loginButton.isEnabled = true
                        binding.signUpButton.isEnabled = true
                    }
                )
            } catch (e: Exception) {
                Log.e(TAG, "Login error", e)
                Toast.makeText(
                    this@LoginActivity,
                    "Login error: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
                binding.progressBar.visibility = View.GONE
                binding.loginButton.isEnabled = true
                binding.signUpButton.isEnabled = true
            }
        }
    }

    /**
     * Perform a Supabase sign-up and immediately log the user in
     */
    private fun performSignUp(username: String, password: String) {
        binding.progressBar.visibility = View.VISIBLE
        binding.signUpButton.isEnabled = false
        binding.loginButton.isEnabled = false

        lifecycleScope.launch {
            try {
                val result = supabaseManager.signUp(username, password)
                result.fold(
                    onSuccess = { userInfo: UserInfo ->
                        Log.d(TAG, "Sign-up successful for user: ${userInfo.email}")
                        Toast.makeText(this@LoginActivity, "Account created", Toast.LENGTH_SHORT).show()
                        // After sign-up, continue same flow as login
                        if (hasCompletedOnboarding()) {
                            proceedToMainActivity()
                        } else {
                            goToOnboarding()
                        }
                    },
                    onFailure = { exception ->
                        Log.e(TAG, "Sign-up failed", exception)
                        Toast.makeText(
                            this@LoginActivity,
                            "Sign-up failed: ${exception.message}",
                            Toast.LENGTH_LONG
                        ).show()
                        binding.progressBar.visibility = View.GONE
                        binding.signUpButton.isEnabled = true
                        binding.loginButton.isEnabled = true
                    }
                )
            } catch (e: Exception) {
                Log.e(TAG, "Sign-up error", e)
                Toast.makeText(
                    this@LoginActivity,
                    "Sign-up error: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
                binding.progressBar.visibility = View.GONE
                binding.signUpButton.isEnabled = true
                binding.loginButton.isEnabled = true
            }
        }
    }

    /**
     * Send magic link / OTP to the given email address
     */
    private fun performSendMagicLink(email: String) {
        binding.progressBar.visibility = View.VISIBLE
        binding.sendMagicLinkButton.isEnabled = false

        lifecycleScope.launch {
            try {
                val result = supabaseManager.signInWithOtp(email)
                result.fold(
                    onSuccess = {
                        Log.d(TAG, "Magic link sent to: $email")
                        Toast.makeText(
                            this@LoginActivity,
                            "Magic link sent! Check your email.",
                            Toast.LENGTH_LONG
                        ).show()

                        // Show OTP input UI so user can enter the code
                        binding.otpInputLayout.visibility = View.VISIBLE
                        binding.verifyOtpButton.visibility = View.VISIBLE
                        binding.otpInstructions.text = "Enter the 6-digit code sent to $email"

                        binding.progressBar.visibility = View.GONE
                        binding.sendMagicLinkButton.isEnabled = true
                    },
                    onFailure = { exception ->
                        Log.e(TAG, "Send magic link failed", exception)
                        Toast.makeText(
                            this@LoginActivity,
                            "Failed to send magic link: ${exception.message}",
                            Toast.LENGTH_LONG
                        ).show()
                        binding.progressBar.visibility = View.GONE
                        binding.sendMagicLinkButton.isEnabled = true
                    }
                )
            } catch (e: Exception) {
                Log.e(TAG, "Send magic link error", e)
                Toast.makeText(
                    this@LoginActivity,
                    "Error: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
                binding.progressBar.visibility = View.GONE
                binding.sendMagicLinkButton.isEnabled = true
            }
        }
    }

    /**
     * Verify the OTP code that was emailed to the user
     */
    private fun performVerifyOtp(email: String, code: String) {
        binding.progressBar.visibility = View.VISIBLE
        binding.verifyOtpButton.isEnabled = false

        lifecycleScope.launch {
            try {
                val result = supabaseManager.verifyOtp(email, code)
                result.fold(
                    onSuccess = { userInfo: UserInfo ->
                        Log.d(TAG, "OTP verification successful for user: ${userInfo.email}")
                        Toast.makeText(this@LoginActivity, "Login successful!", Toast.LENGTH_SHORT).show()

                        if (hasCompletedOnboarding()) {
                            proceedToMainActivity()
                        } else {
                            goToOnboarding()
                        }
                    },
                    onFailure = { exception ->
                        Log.e(TAG, "OTP verification failed", exception)
                        Toast.makeText(
                            this@LoginActivity,
                            "Invalid code: ${exception.message}",
                            Toast.LENGTH_LONG
                        ).show()
                        binding.progressBar.visibility = View.GONE
                        binding.verifyOtpButton.isEnabled = true
                    }
                )
            } catch (e: Exception) {
                Log.e(TAG, "OTP verification error", e)
                Toast.makeText(
                    this@LoginActivity,
                    "Error: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
                binding.progressBar.visibility = View.GONE
                binding.verifyOtpButton.isEnabled = true
            }
        }
    }

    private fun hasCompletedOnboarding(): Boolean {
        return prefs.getBoolean("onboarding_completed", false)
    }

    private fun goToOnboarding() {
        val intent = Intent(this, OnboardingActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun proceedToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        supabaseManager.cleanup()
    }
} 