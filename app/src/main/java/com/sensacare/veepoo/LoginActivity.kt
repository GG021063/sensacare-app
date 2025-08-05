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

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var secureApiManager: SecureApiManager
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

        secureApiManager = SecureApiManager(this)

        // Check if user is already authenticated
        if (secureApiManager.isAuthenticated()) {
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

    private fun performLogin(username: String, password: String) {
        binding.progressBar.visibility = View.VISIBLE
        binding.loginButton.isEnabled = false

        lifecycleScope.launch {
            try {
                val result = secureApiManager.login(username, password)
                result.fold(
                    onSuccess = { authResponse ->
                        Log.d(TAG, "Login successful for user: ${authResponse.userId}")
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
        secureApiManager.cleanup()
    }
} 