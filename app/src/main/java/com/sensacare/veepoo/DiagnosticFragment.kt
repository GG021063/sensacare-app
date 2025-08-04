package com.sensacare.veepoo

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.sensacare.veepoo.databinding.FragmentDiagnosticBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DiagnosticFragment : Fragment() {
    private var _binding: FragmentDiagnosticBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var diagnosticLogger: DiagnosticLogger
    private lateinit var logAdapter: DiagnosticLogAdapter
    
    private var currentCategoryFilter: DiagnosticLogger.LogCategory? = null
    private var currentLevelFilter: DiagnosticLogger.LogLevel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDiagnosticBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        diagnosticLogger = DiagnosticLogger.getInstance(requireContext())
        setupUI()
        setupFilters()
        startLogUpdates()
    }

    private fun setupUI() {
        // Setup log adapter
        logAdapter = DiagnosticLogAdapter(requireContext())
        binding.logRecyclerView.adapter = logAdapter
        
        // Setup debug mode toggle
        binding.debugModeSwitch.isChecked = diagnosticLogger.isDebugModeEnabled()
        binding.debugModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            diagnosticLogger.setDebugMode(isChecked)
            updateLogDisplay()
        }
        
        // Setup action buttons
        binding.clearLogsButton.setOnClickListener {
            diagnosticLogger.clearLogs()
            updateLogDisplay()
        }
        
        binding.exportLogsButton.setOnClickListener {
            exportLogs()
        }
        
        binding.refreshButton.setOnClickListener {
            updateLogDisplay()
        }
        
        // Initial log display
        updateLogDisplay()
    }

    private fun setupFilters() {
        // Category filter
        val categories = listOf("All Categories") + DiagnosticLogger.LogCategory.values().map { it.name }
        val categoryAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categories)
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.categorySpinner.adapter = categoryAdapter
        
        binding.categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                currentCategoryFilter = if (position == 0) null else DiagnosticLogger.LogCategory.values()[position - 1]
                updateLogDisplay()
            }
            
            override fun onNothingSelected(parent: AdapterView<*>?) {
                currentCategoryFilter = null
                updateLogDisplay()
            }
        }
        
        // Level filter
        val levels = listOf("All Levels") + DiagnosticLogger.LogLevel.values().map { it.name }
        val levelAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, levels)
        levelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.levelSpinner.adapter = levelAdapter
        
        binding.levelSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                currentLevelFilter = if (position == 0) null else DiagnosticLogger.LogLevel.values()[position - 1]
                updateLogDisplay()
            }
            
            override fun onNothingSelected(parent: AdapterView<*>?) {
                currentLevelFilter = null
                updateLogDisplay()
            }
        }
    }

    private fun startLogUpdates() {
        lifecycleScope.launch {
            while (true) {
                updateLogDisplay()
                delay(2000) // Update every 2 seconds
            }
        }
    }

    private fun updateLogDisplay() {
        var logs = diagnosticLogger.getLogs()
        
        // Apply category filter
        currentCategoryFilter?.let { category ->
            logs = logs.filter { it.category == category }
        }
        
        // Apply level filter
        currentLevelFilter?.let { level ->
            logs = logs.filter { it.level == level }
        }
        
        // Update adapter
        logAdapter.updateLogs(logs)
        
        // Update status
        binding.logCountText.text = "Logs: ${logs.size}"
        binding.debugModeText.text = if (diagnosticLogger.isDebugModeEnabled()) "Debug Mode: ON" else "Debug Mode: OFF"
    }

    private fun exportLogs() {
        val logText = diagnosticLogger.exportLogsAsText()
        
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
                            putExtra(Intent.EXTRA_SUBJECT, "Sensacare App Diagnostic Logs")
            putExtra(Intent.EXTRA_TEXT, logText)
        }
        
        startActivity(Intent.createChooser(intent, "Export Logs"))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 