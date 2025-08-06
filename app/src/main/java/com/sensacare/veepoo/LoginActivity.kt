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