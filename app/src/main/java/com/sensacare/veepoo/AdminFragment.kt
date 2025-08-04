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
import androidx.recyclerview.widget.LinearLayoutManager
import com.sensacare.veepoo.databinding.FragmentAdminBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class AdminFragment : Fragment() {
    private var _binding: FragmentAdminBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var vitalsAdapter: AdminVitalsAdapter
    private lateinit var alertsAdapter: AdminAlertsAdapter
    private lateinit var eventsAdapter: AdminEventsAdapter
    
    private val database = AppDatabase.getInstance(requireContext())
    private val vitalsDao = database.vitalsDao()
    private val diagnosticLogger = DiagnosticLogger.getInstance(requireContext())
    
    private var currentPage = 1
    private val pageSize = 20
    private var totalItems = 0
    
    private val dateFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdminBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupRecyclerViews()
        setupFilters()
        setupPagination()
        setupExportButton()
        loadData()
        startAutoRefresh()
    }

    private fun setupRecyclerViews() {
        // Vitals RecyclerView
        vitalsAdapter = AdminVitalsAdapter()
        binding.vitalsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = vitalsAdapter
        }

        // Alerts RecyclerView
        alertsAdapter = AdminAlertsAdapter()
        binding.alertsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = alertsAdapter
        }

        // Events RecyclerView
        eventsAdapter = AdminEventsAdapter()
        binding.eventsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = eventsAdapter
        }
    }

    private fun setupFilters() {
        // Date range filter
        val dateRanges = listOf("Last 24 hours", "Last 7 days", "Last 30 days", "All time")
        val dateAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, dateRanges)
        dateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.dateRangeSpinner.adapter = dateAdapter
        
        binding.dateRangeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                currentPage = 1
                loadData()
            }
            
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        // Alert level filter
        val alertLevels = listOf("All Alerts", "Critical", "Warning", "Normal")
        val alertAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, alertLevels)
        alertAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.alertLevelSpinner.adapter = alertAdapter
        
        binding.alertLevelSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // loadAlerts is a suspend function – launch coroutine from lifecycle scope
                viewLifecycleOwner.lifecycleScope.launch {
                    loadAlerts()
                }
            }
            
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        // Event type filter
        val eventTypes = listOf("All Events", "BLE Connection", "Data Sync", "API Upload", "Error")
        val eventAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, eventTypes)
        eventAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.eventTypeSpinner.adapter = eventAdapter
        
        binding.eventTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // loadEvents is a suspend function – launch coroutine from lifecycle scope
                viewLifecycleOwner.lifecycleScope.launch {
                    loadEvents()
                }
            }
            
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun setupPagination() {
        binding.prevPageButton.setOnClickListener {
            if (currentPage > 1) {
                currentPage--
                loadData()
            }
        }

        binding.nextPageButton.setOnClickListener {
            val maxPages = (totalItems + pageSize - 1) / pageSize
            if (currentPage < maxPages) {
                currentPage++
                loadData()
            }
        }
    }

    private fun setupExportButton() {
        binding.exportButton.setOnClickListener {
            exportToCSV()
        }
    }

    private fun loadData() {
        lifecycleScope.launch {
            loadVitals()
            loadAlerts()
            loadEvents()
            updateStatistics()
        }
    }

    private suspend fun loadVitals() {
        val dateRange = getDateRange()
        val offset = (currentPage - 1) * pageSize
        
        val vitals = vitalsDao.getVitalsWithPagination(dateRange.first, dateRange.second, pageSize, offset)
        totalItems = vitalsDao.getVitalsCount(dateRange.first, dateRange.second)
        
        val adminVitals = vitals.map { entity ->
            AdminVitalsData(
                id = entity.id.toLong(),
                heartRate = entity.heartRate,
                bloodPressureSystolic = entity.bloodPressureSystolic,
                bloodPressureDiastolic = entity.bloodPressureDiastolic,
                spo2 = entity.spo2,
                temperature = entity.temperature,
                timestamp = entity.timestamp,
                alertLevel = calculateAlertLevel(entity)
            )
        }
        
        vitalsAdapter.updateVitals(adminVitals)
        updatePaginationInfo()
    }

    private suspend fun loadAlerts() {
        val alertLevel = getSelectedAlertLevel()
        val dateRange = getDateRange()
        
        val vitals = vitalsDao.getVitalsByDateRange(dateRange.first, dateRange.second)
        val alerts = vitals.mapNotNull { entity ->
            val alertLevel = calculateAlertLevel(entity)
            if (alertLevel != VitalsAlertManager.AlertLevel.NORMAL && 
                (getSelectedAlertLevel() == "All Alerts" || alertLevel.name == getSelectedAlertLevel())) {
                AdminAlertData(
                    id = entity.id.toLong(),
                    vitalType = getAlertVitalType(entity, alertLevel),
                    value = getAlertValue(entity, alertLevel),
                    threshold = getAlertThreshold(entity, alertLevel),
                    alertLevel = alertLevel,
                    timestamp = entity.timestamp
                )
            } else null
        }
        
        alertsAdapter.updateAlerts(alerts)
    }

    private suspend fun loadEvents() {
        val eventType = getSelectedEventType()
        val logs = diagnosticLogger.getLogs()
        
        val events = logs.mapNotNull { logEntry ->
            if (eventType == "All Events" || matchesEventType(logEntry, eventType)) {
                AdminEventData(
                    id = logEntry.timestamp,
                    eventType = getEventType(logEntry),
                    description = logEntry.message,
                    details = logEntry.details,
                    level = logEntry.level,
                    timestamp = logEntry.timestamp
                )
            } else null
        }
        
        eventsAdapter.updateEvents(events)
    }

    private suspend fun updateStatistics() {
        val dateRange = getDateRange()
        val vitals = vitalsDao.getVitalsByDateRange(dateRange.first, dateRange.second)
        
        val totalVitals = vitals.size
        val criticalAlerts = vitals.count { calculateAlertLevel(it) == VitalsAlertManager.AlertLevel.CRITICAL }
        val warningAlerts = vitals.count { calculateAlertLevel(it) == VitalsAlertManager.AlertLevel.WARNING }
        val avgHeartRate = vitals.mapNotNull { it.heartRate }.average().takeIf { !it.isNaN() } ?: 0.0
        val avgSpO2 = vitals.mapNotNull { it.spo2 }.average().takeIf { !it.isNaN() } ?: 0.0
        
        binding.statsText.text = buildString {
            appendLine("📊 Statistics (${getDateRangeText()})")
            appendLine("Total Vitals: $totalVitals")
            appendLine("Critical Alerts: $criticalAlerts")
            appendLine("Warning Alerts: $warningAlerts")
            appendLine("Avg Heart Rate: ${String.format("%.1f", avgHeartRate)} bpm")
            appendLine("Avg SpO2: ${String.format("%.1f", avgSpO2)}%")
        }
    }

    private fun updatePaginationInfo() {
        val maxPages = (totalItems + pageSize - 1) / pageSize
        binding.pageInfoText.text = "Page $currentPage of $maxPages ($totalItems total)"
        
        binding.prevPageButton.isEnabled = currentPage > 1
        binding.nextPageButton.isEnabled = currentPage < maxPages
    }

    private fun exportToCSV() {
        lifecycleScope.launch {
            val csvData = generateCSVData()
            
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/csv"
                putExtra(Intent.EXTRA_SUBJECT, "Sensacare Admin Data Export")
                putExtra(Intent.EXTRA_TEXT, csvData)
            }
            
            startActivity(Intent.createChooser(intent, "Export Data"))
        }
    }

    private suspend fun generateCSVData(): String {
        val dateRange = getDateRange()
        val vitals = vitalsDao.getVitalsByDateRange(dateRange.first, dateRange.second)
        
        return buildString {
            appendLine("ID,Timestamp,Heart Rate,Blood Pressure Systolic,Blood Pressure Diastolic,SpO2,Temperature,Alert Level")
            vitals.forEach { entity ->
                val alertLevel = calculateAlertLevel(entity)
                appendLine("${entity.id},${dateFormatter.format(Date(entity.timestamp))}," +
                    "${entity.heartRate ?: ""},${entity.bloodPressureSystolic ?: ""}," +
                    "${entity.bloodPressureDiastolic ?: ""},${entity.spo2 ?: ""}," +
                    "${entity.temperature ?: ""},${alertLevel.name}")
            }
        }
    }

    private fun startAutoRefresh() {
        lifecycleScope.launch {
            while (true) {
                delay(30000) // Refresh every 30 seconds
                loadData()
            }
        }
    }

    // Helper methods
    private fun getDateRange(): Pair<Long, Long> {
        val now = System.currentTimeMillis()
        val selectedRange = binding.dateRangeSpinner.selectedItem.toString()
        
        return when (selectedRange) {
            "Last 24 hours" -> Pair(now - 24 * 60 * 60 * 1000, now)
            "Last 7 days" -> Pair(now - 7 * 24 * 60 * 60 * 1000, now)
            "Last 30 days" -> Pair(now - 30 * 24 * 60 * 60 * 1000, now)
            else -> Pair(0L, now) // All time
        }
    }

    private fun getDateRangeText(): String {
        return binding.dateRangeSpinner.selectedItem.toString()
    }

    private fun getSelectedAlertLevel(): String {
        return binding.alertLevelSpinner.selectedItem.toString()
    }

    private fun getSelectedEventType(): String {
        return binding.eventTypeSpinner.selectedItem.toString()
    }

    private fun calculateAlertLevel(entity: VitalsEntity): VitalsAlertManager.AlertLevel {
        val alertManager = VitalsAlertManager.getInstance(requireContext())
        return when {
            entity.heartRate != null -> alertManager.calculateAlertLevel(
                "heartrate",
                entity.heartRate.toFloat()
            )
            entity.spo2 != null -> alertManager.calculateAlertLevel(
                "spo2",
                entity.spo2.toFloat()
            )
            entity.bloodPressureSystolic != null -> alertManager.calculateAlertLevel(
                "bloodpressure",
                entity.bloodPressureSystolic.toFloat()
            )
            entity.temperature != null -> alertManager.calculateAlertLevel(
                "temperature",
                entity.temperature
            )
            else -> VitalsAlertManager.AlertLevel.NONE
        }
    }

    private fun getAlertVitalType(entity: VitalsEntity, alertLevel: VitalsAlertManager.AlertLevel): String {
        return when {
            entity.heartRate != null && (entity.heartRate < 40 || entity.heartRate > 120) -> "Heart Rate"
            entity.spo2 != null && entity.spo2 < 92 -> "SpO2"
            entity.bloodPressureSystolic != null && entity.bloodPressureSystolic > 140 -> "Blood Pressure"
            entity.temperature != null && entity.temperature > 38.0 -> "Temperature"
            else -> "Unknown"
        }
    }

    private fun getAlertValue(entity: VitalsEntity, alertLevel: VitalsAlertManager.AlertLevel): String {
        return when {
            entity.heartRate != null && (entity.heartRate < 40 || entity.heartRate > 120) -> "${entity.heartRate} bpm"
            entity.spo2 != null && entity.spo2 < 92 -> "${entity.spo2}%"
            entity.bloodPressureSystolic != null && entity.bloodPressureSystolic > 140 -> "${entity.bloodPressureSystolic}/${entity.bloodPressureDiastolic ?: 0} mmHg"
            entity.temperature != null && entity.temperature > 38.0 -> "${entity.temperature}°C"
            else -> "Unknown"
        }
    }

    private fun getAlertThreshold(entity: VitalsEntity, alertLevel: VitalsAlertManager.AlertLevel): String {
        return when {
            entity.heartRate != null && (entity.heartRate < 40 || entity.heartRate > 120) -> 
                if (entity.heartRate < 40) "< 40 bpm" else "> 120 bpm"
            entity.spo2 != null && entity.spo2 < 92 -> "< 92%"
            entity.bloodPressureSystolic != null && entity.bloodPressureSystolic > 140 -> "> 140/90 mmHg"
            entity.temperature != null && entity.temperature > 38.0 -> "> 38.0°C"
            else -> "Unknown"
        }
    }

    private fun matchesEventType(logEntry: DiagnosticLogger.LogEntry, eventType: String): Boolean {
        return when (eventType) {
            "BLE Connection" -> logEntry.category == DiagnosticLogger.LogCategory.BLE_CONNECTION
            "Data Sync" -> logEntry.category == DiagnosticLogger.LogCategory.ROOM_DATABASE
            "API Upload" -> logEntry.category == DiagnosticLogger.LogCategory.API_CALL
            "Error" -> logEntry.level == DiagnosticLogger.LogLevel.ERROR
            else -> false
        }
    }

    private fun getEventType(logEntry: DiagnosticLogger.LogEntry): String {
        return when (logEntry.category) {
            DiagnosticLogger.LogCategory.BLE_CONNECTION -> "BLE Connection"
            DiagnosticLogger.LogCategory.ROOM_DATABASE -> "Data Sync"
            DiagnosticLogger.LogCategory.API_CALL -> "API Upload"
            DiagnosticLogger.LogCategory.BLE_SCAN -> "BLE Scan"
            DiagnosticLogger.LogCategory.BLE_CHARACTERISTIC -> "BLE Data"
            DiagnosticLogger.LogCategory.SECURITY -> "Security"
            else -> "General"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 