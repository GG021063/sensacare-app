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
import com.sensacare.veepoo.databinding.ActivityLoginBinding
import kotlinx.coroutines.delay
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
        
        // Initialize session restoration
        initializeSession()
        
        setupUI()
        setupOTPInputs()
    }
    
    private fun initializeSession() {
        lifecycleScope.launch {
            try {
                val result = supabaseManager.initializeSession()
                if (result.isSuccess) {
                    // Check if user is already authenticated
                    val currentUser = supabaseManager.getCurrentUser()
                    if (currentUser != null) {
                        Log.d(TAG, "User already authenticated: ${currentUser.email}")
                        navigateToMain()
                        return@launch
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Session initialization failed", e)
            }
        }
    }
    
    private fun setupUI() {
        // Get Code Button
        binding.getCodeButton.setOnClickListener {
            val email = binding.emailInput.text.toString().trim()
            if (email.isEmpty()) {
                Toast.makeText(this, "Please enter your email address", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show()
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
        lifecycleScope.launch {
            try {
                binding.getCodeButton.isEnabled = false
                binding.getCodeButton.text = "Sending..."
                
                // Send actual OTP through Supabase
                val result = supabaseManager.sendOTP(email)
                
                if (result.isSuccess) {
                    // Show success message with the actual email entered
                    Toast.makeText(this@LoginActivity, "Code sent to $email", Toast.LENGTH_LONG).show()
                    
                    // Update the OTP message with the actual email
                    binding.otpMessage.text = "Code sent to $email"
                    
                    // Show OTP input section
                    binding.emailSection.visibility = View.GONE
                    binding.otpSection.visibility = View.VISIBLE
                    
                    // Auto-focus to first OTP input
                    binding.otp1.requestFocus()
                } else {
                    Toast.makeText(this@LoginActivity, "Failed to send code: ${result.exceptionOrNull()?.message}", Toast.LENGTH_LONG).show()
                }
                
            } catch (e: Exception) {
                Log.e(TAG, "Failed to send OTP", e)
                Toast.makeText(this@LoginActivity, "Failed to send code: ${e.message}", Toast.LENGTH_LONG).show()
            } finally {
                binding.getCodeButton.isEnabled = true
                binding.getCodeButton.text = "GET CODE"
            }
        }
    }
    
    private fun verifyOTPCode(otpCode: String) {
        lifecycleScope.launch {
            try {
                binding.verifyCodeButton.isEnabled = false
                binding.verifyCodeButton.text = "Verifying..."
                
                // Get the email that was used to send the OTP
                val email = binding.emailInput.text.toString().trim()
                
                // Verify OTP with Supabase
                val result = supabaseManager.verifyOTP(email, otpCode)
                if (result.isSuccess) {
                    val user = result.getOrNull()
                    Toast.makeText(this@LoginActivity, "Login successful! Welcome ${user?.email}", Toast.LENGTH_SHORT).show()
                    navigateToMain()
                } else {
                    Toast.makeText(this@LoginActivity, "Invalid code. Please try again.", Toast.LENGTH_LONG).show()
                    clearOTPInputs()
                }
                
            } catch (e: Exception) {
                Log.e(TAG, "Failed to verify OTP", e)
                Toast.makeText(this@LoginActivity, "Verification failed: ${e.message}", Toast.LENGTH_LONG).show()
                clearOTPInputs()
            } finally {
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
