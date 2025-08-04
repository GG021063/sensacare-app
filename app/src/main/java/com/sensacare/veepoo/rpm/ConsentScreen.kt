package com.sensacare.veepoo.rpm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.sensacare.veepoo.databinding.FragmentConsentBinding
import kotlinx.coroutines.launch

class ConsentScreen : Fragment() {
    
    private var _binding: FragmentConsentBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var consentManager: ConsentManager
    private lateinit var rpmIntegrationManager: RPMIntegrationManager
    
    private var clientId: String = ""
    private var clientName: String = ""
    private var userToken: String = ""
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConsentBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        consentManager = ConsentManager(requireContext())
        rpmIntegrationManager = RPMIntegrationManager(requireContext())
        
        // Get arguments passed to this fragment
        arguments?.let { args ->
            clientId = args.getString("client_id", "")
            clientName = args.getString("client_name", "")
            userToken = args.getString("user_token", "")
        }
        
        setupConsentPolicy()
        setupConsentTypeSelector()
        setupButtons()
        
        // Pre-fill client information
        binding.clientIdEditText.setText(clientId)
        binding.clientNameEditText.setText(clientName)
    }
    
    private fun setupConsentPolicy() {
        val policyText = consentManager.getConsentPolicyText()
        binding.consentPolicyText.text = policyText
    }
    
    private fun setupConsentTypeSelector() {
        val consentTypes = ConsentType.values().map { it.displayName }.toTypedArray()
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, consentTypes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        
        binding.consentTypeSpinner.adapter = adapter
        binding.consentTypeSpinner.setSelection(0) // Default to Digital Signature
    }
    
    private fun setupButtons() {
        binding.captureConsentButton.setOnClickListener {
            captureConsent()
        }
        
        binding.skipConsentButton.setOnClickListener {
            // Skip consent and proceed with setup
            proceedWithoutConsent()
        }
    }
    
    private fun captureConsent() {
        val clientIdInput = binding.clientIdEditText.text.toString().trim()
        val clientNameInput = binding.clientNameEditText.text.toString().trim()
        val carerNameInput = binding.carerNameEditText.text.toString().trim()
        
        if (clientIdInput.isEmpty() || clientNameInput.isEmpty()) {
            Toast.makeText(context, "Please fill in all required fields", Toast.LENGTH_SHORT).show()
            return
        }
        
        val selectedConsentType = ConsentType.values()[binding.consentTypeSpinner.selectedItemPosition]
        
        lifecycleScope.launch {
            try {
                binding.captureConsentButton.isEnabled = false
                binding.progressIndicator.visibility = View.VISIBLE
                
                val consentResult = consentManager.captureConsent(
                    clientId = clientIdInput,
                    clientName = clientNameInput,
                    carerName = if (carerNameInput.isNotEmpty()) carerNameInput else null,
                    consentType = selectedConsentType
                )
                
                when (consentResult) {
                    is ConsentResult.Success -> {
                        val consentRecord = consentResult.consentRecord
                        
                        // Show consent summary
                        showConsentSummary(consentRecord)
                        
                        // Setup RPM with consent
                        val setupSuccess = rpmIntegrationManager.setupWithConsent(
                            clientId = clientIdInput,
                            clientName = clientNameInput,
                            userToken = userToken
                        )
                        
                        if (setupSuccess) {
                            Toast.makeText(context, "Consent captured and RPM setup completed", Toast.LENGTH_LONG).show()
                            // Navigate back or to main dashboard
                            parentFragmentManager.popBackStack()
                        } else {
                            Toast.makeText(context, "Failed to setup RPM", Toast.LENGTH_LONG).show()
                        }
                    }
                    is ConsentResult.Failure -> {
                        Toast.makeText(context, "Failed to capture consent: ${consentResult.errorMessage}", Toast.LENGTH_LONG).show()
                    }
                }
                
            } catch (e: Exception) {
                Toast.makeText(context, "Error capturing consent: ${e.message}", Toast.LENGTH_LONG).show()
            } finally {
                binding.captureConsentButton.isEnabled = true
                binding.progressIndicator.visibility = View.GONE
            }
        }
    }
    
    private fun proceedWithoutConsent() {
        val clientIdInput = binding.clientIdEditText.text.toString().trim()
        val clientNameInput = binding.clientNameEditText.text.toString().trim()
        
        if (clientIdInput.isEmpty() || clientNameInput.isEmpty()) {
            Toast.makeText(context, "Please fill in client ID and name", Toast.LENGTH_SHORT).show()
            return
        }
        
        lifecycleScope.launch {
            try {
                binding.skipConsentButton.isEnabled = false
                binding.progressIndicator.visibility = View.VISIBLE
                
                // Initialize RPM without consent (for testing/development)
                val setupSuccess = rpmIntegrationManager.initializeRPM(
                    clientId = clientIdInput,
                    userToken = userToken
                )
                
                if (setupSuccess) {
                    Toast.makeText(context, "RPM setup completed (no consent)", Toast.LENGTH_LONG).show()
                    parentFragmentManager.popBackStack()
                } else {
                    Toast.makeText(context, "Failed to setup RPM", Toast.LENGTH_LONG).show()
                }
                
            } catch (e: Exception) {
                Toast.makeText(context, "Error setting up RPM: ${e.message}", Toast.LENGTH_LONG).show()
            } finally {
                binding.skipConsentButton.isEnabled = true
                binding.progressIndicator.visibility = View.GONE
            }
        }
    }
    
    private fun showConsentSummary(consentRecord: ConsentRecord) {
        val summary = consentManager.getConsentSummary(consentRecord)
        
        binding.consentSummaryContainer.visibility = View.VISIBLE
        binding.consentSummaryText.text = summary
        
        Toast.makeText(context, "Consent captured successfully", Toast.LENGTH_SHORT).show()
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    
    companion object {
        fun newInstance(clientId: String, clientName: String, userToken: String): ConsentScreen {
            return ConsentScreen().apply {
                arguments = Bundle().apply {
                    putString("client_id", clientId)
                    putString("client_name", clientName)
                    putString("user_token", userToken)
                }
            }
        }
    }
} 