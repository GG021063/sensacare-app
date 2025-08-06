package com.sensacare.veepoo

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.Lifecycle
import com.sensacare.veepoo.databinding.ActivityLoginBinding
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    
    companion object {
        private const val TAG = "LoginActivity"
    }
    
    private lateinit var binding: ActivityLoginBinding
    private lateinit var supabaseManager: SupabaseManager
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        supabaseManager = SupabaseManager(this)
        
        setupUI()
        setupOTPInputs()
        observeAuthState()
    }
    
    private fun observeAuthState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                supabaseManager.authState.collect { authState ->
                    when (authState) {
                        is SupabaseManager.AuthState.Authenticated -> {
                            Log.d(TAG, "User authenticated: ${authState.user.email}")
                            navigateToMain()
                        }
                        is SupabaseManager.AuthState.Unauthenticated -> {
                            Log.d(TAG, "User not authenticated")
                            // Stay on login screen
                        }
                    }
                }
            }
        }
    }
    
    private fun setupUI() {
        // Get Code Button
        binding.getCodeButton.setOnClickListener {
            val email = binding.emailInput.text.toString().trim()
            if (email.isEmpty()) {
                Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            
            sendOTPCode(email)
        }
        
        // Verify Code Button
        binding.verifyCodeButton.setOnClickListener {
            val otpCode = buildString {
                append(binding.otp1.text.toString())
                append(binding.otp2.text.toString())
                append(binding.otp3.text.toString())
                append(binding.otp4.text.toString())
                append(binding.otp5.text.toString())
                append(binding.otp6.text.toString())
            }
            
            if (otpCode.length != 6) {
                Toast.makeText(this, "Please enter the complete 6-digit code", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            
            verifyOTPCode(otpCode)
        }
    }
    
    private fun setupOTPInputs() {
        val otpInputs = listOf(
            binding.otp1, binding.otp2, binding.otp3,
            binding.otp4, binding.otp5, binding.otp6
        )
        
        // Auto-focus to next input when digit is entered
        for (i in otpInputs.indices) {
            otpInputs[i].addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    if (s?.length == 1 && i < otpInputs.size - 1) {
                        otpInputs[i + 1].requestFocus()
                    }
                }
            })
        }
    }
    
    private fun sendOTPCode(email: String) {
        binding.getCodeButton.isEnabled = false
        binding.getCodeButton.text = "Sending..."
        
        supabaseManager.sendOtpToEmail(email) { success, errorMessage ->
            runOnUiThread {
                if (success) {
                    Toast.makeText(this@LoginActivity, "Code sent to $email", Toast.LENGTH_LONG).show()
                    
                    // Update the OTP message with the actual email
                    binding.otpMessage.text = "Code sent to $email"
                    
                    // Show OTP input section
                    binding.emailSection.visibility = View.GONE
                    binding.otpSection.visibility = View.VISIBLE
                    
                    // Auto-focus to first OTP input
                    binding.otp1.requestFocus()
                } else {
                    Toast.makeText(this@LoginActivity, "Failed to send code: $errorMessage", Toast.LENGTH_LONG).show()
                }
                
                binding.getCodeButton.isEnabled = true
                binding.getCodeButton.text = "GET CODE"
            }
        }
    }
    
    private fun verifyOTPCode(otpCode: String) {
        binding.verifyCodeButton.isEnabled = false
        binding.verifyCodeButton.text = "Verifying..."
        
        // Get the email that was used to send the OTP
        val email = binding.emailInput.text.toString().trim()
        
        supabaseManager.verifyOtp(email, otpCode) { success, errorMessage ->
            runOnUiThread {
                if (success) {
                    Toast.makeText(this@LoginActivity, "Login successful!", Toast.LENGTH_SHORT).show()
                    // Navigation will be handled by authState observer
                } else {
                    Toast.makeText(this@LoginActivity, "Invalid code: $errorMessage", Toast.LENGTH_LONG).show()
                    clearOTPInputs()
                }
                
                binding.verifyCodeButton.isEnabled = true
                binding.verifyCodeButton.text = "VERIFY CODE"
            }
        }
    }
    
    private fun getOTPCode(): String {
        return binding.otp1.text.toString() +
               binding.otp2.text.toString() +
               binding.otp3.text.toString() +
               binding.otp4.text.toString() +
               binding.otp5.text.toString() +
               binding.otp6.text.toString()
    }
    
    private fun clearOTPInputs() {
        binding.otp1.setText("")
        binding.otp2.setText("")
        binding.otp3.setText("")
        binding.otp4.setText("")
        binding.otp5.setText("")
        binding.otp6.setText("")
        binding.otp1.requestFocus()
    }
    
    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
    
    override fun onDestroy() {
        super.onDestroy()
    }
}
