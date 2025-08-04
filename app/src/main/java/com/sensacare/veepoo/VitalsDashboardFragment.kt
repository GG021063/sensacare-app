package com.sensacare.veepoo

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.sensacare.veepoo.databinding.FragmentDashboardBinding
import com.sensacare.veepoo.rpm.RPMIntegrationManager
import com.sensacare.veepoo.rpm.RPMStatus
import com.sensacare.veepoo.rpm.VitalsStatus
import com.sensacare.veepoo.rpm.AlertData
import kotlinx.coroutines.launch

class VitalsDashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var viewModel: VitalsViewModel
    private lateinit var chartManager: ChartManager
    private lateinit var connectionManager: BleConnectionManager
    private lateinit var rpmIntegrationManager: RPMIntegrationManager
    private lateinit var preferencesManager: PreferencesManager

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allGranted = permissions.entries.all { it.value }
        if (allGranted) {
            setupBluetooth()
        } else {
            Toast.makeText(context, "BLE permissions required", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        viewModel = ViewModelProvider(this)[VitalsViewModel::class.java]
        chartManager = ChartManager(requireContext())
        
        preferencesManager = PreferencesManager(requireContext())
        connectionManager = BleConnectionManager(requireContext(), preferencesManager)
        rpmIntegrationManager = RPMIntegrationManager(requireContext())
        
        setupCharts()
        setupObservers()
        setupServiceControls()
        setupConnectionCallbacks()
        setupRPMObservers()
        checkPermissions()
    }

    private fun setupObservers() {
        viewModel.recentVitals.observe(viewLifecycleOwner) { vitalsList ->
            // Update recent vitals display if needed
            Log.d("VitalsDashboard", "Recent vitals updated: ${vitalsList.size} records")
            
            // Update vitals display with latest data
            vitalsList.firstOrNull()?.let { latestVitals ->
                updateVitalsDisplay(latestVitals)
                // Removed updateAlertStatus call as it's not defined
            }
        }

        viewModel.chartData.observe(viewLifecycleOwner) { vitalsList ->
            updateCharts(vitalsList)
        }
    }

    private fun updateVitalsDisplay(vitals: VitalsEntity) {
        binding.heartRateText.text = "HR: ${vitals.heartRate ?: "--"} bpm"
        binding.bloodPressureText.text = "BP: ${vitals.bloodPressureSystolic ?: "--"}/${vitals.bloodPressureDiastolic ?: "--"} mmHg"
        binding.spo2Text.text = "SpO2: ${vitals.spo2 ?: "--"}%"
        binding.temperatureText.text = "Temp: ${vitals.temperature ?: "--"}°C"
    }

    // Note: Alert handling is now managed by RPM system
    // Local clinical alert logic has been removed in favor of RPM-based alerts

    private fun setupCharts() {
        chartManager.setupHeartRateChart(binding.heartRateChart)
        chartManager.setupSpO2Chart(binding.spo2Chart)
        chartManager.setupTemperatureChart(binding.temperatureChart)
        chartManager.setupBloodPressureChart(binding.bloodPressureChart)
    }

    private fun updateCharts(vitals: List<VitalsEntity>) {
        chartManager.updateHeartRateChart(binding.heartRateChart, vitals)
        chartManager.updateSpO2Chart(binding.spo2Chart, vitals)
        chartManager.updateTemperatureChart(binding.temperatureChart, vitals)
        chartManager.updateBloodPressureChart(binding.bloodPressureChart, vitals)
    }

    private fun setupServiceControls() {
        // Check initial service status
        updateServiceStatus()
        
        binding.startServiceButton.setOnClickListener {
            BleForegroundService.startService(requireContext())
            updateServiceStatus()
            Toast.makeText(context, "Background service started", Toast.LENGTH_SHORT).show()
        }

        binding.stopServiceButton.setOnClickListener {
            BleForegroundService.stopService(requireContext())
            updateServiceStatus()
            Toast.makeText(context, "Background service stopped", Toast.LENGTH_SHORT).show()
        }

        binding.diagnosticButton.setOnClickListener {
            // Navigate to diagnostic fragment
            val diagnosticFragment = DiagnosticFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, diagnosticFragment)
                .addToBackStack(null)
                .commit()
        }

        binding.adminButton.setOnClickListener {
            // Navigate to admin fragment
            val adminFragment = AdminFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, adminFragment)
                .addToBackStack(null)
                .commit()
        }

        // RPM Navigation buttons
        binding.timelineButton.setOnClickListener {
            // Navigate to vitals timeline fragment
            val timelineFragment = com.sensacare.veepoo.rpm.VitalsTimelineFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, timelineFragment)
                .addToBackStack(null)
                .commit()
        }

        binding.consentButton.setOnClickListener {
            // Navigate to consent screen
            val consentFragment = com.sensacare.veepoo.rpm.ConsentScreen.newInstance(
                clientId = "demo_client_123",
                clientName = "Demo Client",
                userToken = "demo_token_456"
            )
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, consentFragment)
                .addToBackStack(null)
                .commit()
        }

        binding.rpmSetupButton.setOnClickListener {
            // Show RPM setup dialog or navigate to setup screen
            showRPMSetupDialog()
        }

        // Setup connection action button
        binding.connectionActionButton.setOnClickListener {
            val currentState = connectionManager.getConnectionState()
            when (currentState) {
                BleConnectionManager.ConnectionState.CONNECTED -> {
                    // Disconnect from device
                    connectionManager.disconnect()
                    Toast.makeText(context, "Disconnecting from device", Toast.LENGTH_SHORT).show()
                }
                BleConnectionManager.ConnectionState.CONNECTING, BleConnectionManager.ConnectionState.RECONNECTING -> {
                    // Cancel connection attempt
                    connectionManager.disconnect()
                    Toast.makeText(context, "Connection cancelled", Toast.LENGTH_SHORT).show()
                }
                BleConnectionManager.ConnectionState.DISCONNECTED, BleConnectionManager.ConnectionState.ERROR -> {
                    // Connect to device
                    if (preferencesManager.isDevicePaired()) {
                        connectionManager.connectToLastKnownDevice()
                        Toast.makeText(context, "Connecting to device", Toast.LENGTH_SHORT).show()
                    } else {
                        // Start device discovery
                        setupBluetooth()
                        Toast.makeText(context, "Starting device discovery", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun updateServiceStatus() {
        val isRunning = ServiceStatusChecker.isServiceRunning(requireContext(), BleForegroundService::class.java)
        binding.serviceStatusText.text = "Service Status: ${if (isRunning) "Running" else "Stopped"}"
    }

    private fun setupConnectionCallbacks() {
        connectionManager.setConnectionStateCallback { state ->
            activity?.runOnUiThread {
                updateConnectionStatus(state)
            }
        }

        connectionManager.setErrorCallback { error ->
            activity?.runOnUiThread {
                showConnectionError(error)
            }
        }
    }

    private fun updateConnectionStatus(state: BleConnectionManager.ConnectionState) {
        val (statusText, statusIcon, bannerColor, buttonText, buttonEnabled) = when (state) {
            BleConnectionManager.ConnectionState.CONNECTED -> ConnectionStatusInfo(
                "Connected to Veepoo device",
                R.drawable.ic_bluetooth_connected,
                "#28A745", // Green
                "Disconnect",
                true
            )
            BleConnectionManager.ConnectionState.CONNECTING -> ConnectionStatusInfo(
                "Connecting to Veepoo device...",
                R.drawable.ic_bluetooth_connecting,
                "#FFC107", // Yellow
                "Cancel",
                true
            )
            BleConnectionManager.ConnectionState.RECONNECTING -> ConnectionStatusInfo(
                "Reconnecting to Veepoo device...",
                R.drawable.ic_bluetooth_connecting,
                "#FFC107", // Yellow
                "Cancel",
                true
            )
            BleConnectionManager.ConnectionState.DISCONNECTED -> ConnectionStatusInfo(
                "Disconnected",
                R.drawable.ic_bluetooth_disconnected,
                "#DC3545", // Red
                "Connect",
                true
            )
            BleConnectionManager.ConnectionState.ERROR -> ConnectionStatusInfo(
                "Connection Error",
                R.drawable.ic_bluetooth_disconnected,
                "#DC3545", // Red
                "Retry",
                true
            )
        }

        // Update banner background color
        val gradientDrawable = binding.connectionStatusBanner.background as android.graphics.drawable.GradientDrawable
        gradientDrawable.setColor(android.graphics.Color.parseColor(bannerColor))

        // Update status text
        binding.connectionStatusText.text = statusText

        // Update status icon
        binding.connectionStatusIcon.setImageResource(statusIcon)

        // Update action button
        binding.connectionActionButton.text = buttonText
        binding.connectionActionButton.isEnabled = buttonEnabled
    }

    private data class ConnectionStatusInfo(
        val statusText: String,
        val statusIcon: Int,
        val bannerColor: String,
        val buttonText: String,
        val buttonEnabled: Boolean
    )

    private fun setupRPMObservers() {
        // Observe RPM status
        lifecycleScope.launch {
            rpmIntegrationManager.rpmStatus.collect { status ->
                updateRPMStatus(status)
            }
        }

        // Observe vitals status
        lifecycleScope.launch {
            rpmIntegrationManager.currentVitalsStatus.collect { status ->
                updateVitalsStatus(status)
            }
        }

        // Observe recent alerts from RPM
        lifecycleScope.launch {
            rpmIntegrationManager.recentAlerts.collect { alerts ->
                updateRPMAlerts(alerts)
            }
        }

        // Observe last vitals data
        lifecycleScope.launch {
            rpmIntegrationManager.lastVitalsData.collect { vitalsData ->
                vitalsData?.let { updateVitalsDisplayFromRPM(it) }
            }
        }
    }

    private fun updateRPMStatus(status: RPMStatus) {
        when (status) {
            RPMStatus.NOT_INITIALIZED -> {
                binding.rpmStatusText.text = "RPM Status: Not Initialized"
                binding.rpmStatusText.setTextColor(android.graphics.Color.GRAY)
            }
            RPMStatus.INITIALIZING -> {
                binding.rpmStatusText.text = "RPM Status: Initializing..."
                binding.rpmStatusText.setTextColor(android.graphics.Color.YELLOW)
            }
            RPMStatus.READY -> {
                binding.rpmStatusText.text = "RPM Status: Ready"
                binding.rpmStatusText.setTextColor(android.graphics.Color.GREEN)
            }
            RPMStatus.MONITORING -> {
                binding.rpmStatusText.text = "RPM Status: Monitoring"
                binding.rpmStatusText.setTextColor(android.graphics.Color.GREEN)
            }
            RPMStatus.ERROR -> {
                binding.rpmStatusText.text = "RPM Status: Error"
                binding.rpmStatusText.setTextColor(android.graphics.Color.RED)
            }
            RPMStatus.DISCONNECTED -> {
                binding.rpmStatusText.text = "RPM Status: Disconnected"
                binding.rpmStatusText.setTextColor(android.graphics.Color.RED)
            }
        }
    }

    private fun updateVitalsStatus(status: VitalsStatus) {
        when (status) {
            VitalsStatus.NORMAL -> {
                binding.vitalsStatusText.text = "Vitals Status: Normal"
                binding.vitalsStatusText.setTextColor(android.graphics.Color.GREEN)
            }
            VitalsStatus.NO_DATA -> {
                binding.vitalsStatusText.text = "Vitals Status: No Recent Data"
                binding.vitalsStatusText.setTextColor(android.graphics.Color.YELLOW)
            }
            VitalsStatus.CRITICAL_ERROR -> {
                binding.vitalsStatusText.text = "Vitals Status: Sensor Error"
                binding.vitalsStatusText.setTextColor(android.graphics.Color.RED)
            }
            VitalsStatus.BLE_ERROR -> {
                binding.vitalsStatusText.text = "Vitals Status: Connection Error"
                binding.vitalsStatusText.setTextColor(android.graphics.Color.RED)
            }
        }
    }

    private fun updateRPMAlerts(alerts: List<AlertData>) {
        if (alerts.isNotEmpty()) {
            binding.rpmAlertsContainer.visibility = View.VISIBLE
            val latestAlert = alerts.first()
            binding.rpmAlertText.text = "${latestAlert.severity.uppercase()}: ${latestAlert.message}"
            
            // Set color based on severity
            val color = when (latestAlert.severity.lowercase()) {
                "critical" -> android.graphics.Color.RED
                "warning" -> android.graphics.Color.YELLOW
                else -> android.graphics.Color.BLUE
            }
            binding.rpmAlertText.setTextColor(color)
        } else {
            binding.rpmAlertsContainer.visibility = View.GONE
        }
    }

    private fun updateVitalsDisplayFromRPM(vitalsData: com.sensacare.veepoo.rpm.VitalsData) {
        // Convert rpm.VitalsData to regular VitalsData for display
        binding.heartRateText.text = "HR: ${vitalsData.heartRate ?: "--"} bpm"
        binding.bloodPressureText.text = "BP: ${vitalsData.bloodPressureSystolic ?: "--"}/${vitalsData.bloodPressureDiastolic ?: "--"} mmHg"
        binding.spo2Text.text = "SpO2: ${vitalsData.spo2 ?: "--"}%"
        binding.temperatureText.text = "Temp: ${vitalsData.temperature ?: "--"}°C"
    }

    private fun showRPMSetupDialog() {
        val dialog = android.app.AlertDialog.Builder(requireContext())
            .setTitle("RPM Setup")
            .setMessage("Initialize RPM with demo credentials?")
            .setPositiveButton("Yes") { _, _ ->
                lifecycleScope.launch {
                    try {
                        val success = rpmIntegrationManager.initializeRPM(
                            clientId = "demo_client_123",
                            userToken = "demo_token_456"
                        )
                        if (success) {
                            Toast.makeText(context, "RPM initialized successfully", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "Failed to initialize RPM", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            .setNegativeButton("Cancel", null)
            .create()
        dialog.show()
    }

    private fun showConnectionError(error: String) {
        // Show error message to user
        Toast.makeText(requireContext(), "Connection Error: $error", Toast.LENGTH_LONG).show()
        
        // Update connection status
        binding.connectionStatusText.text = "Connection Error"
        binding.connectionStatusText.setTextColor(android.graphics.Color.RED)
    }



    private fun checkPermissions() {
        val permissions = arrayOf(
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.ACCESS_FINE_LOCATION
        )

        val permissionsToRequest = permissions.filter {
            ContextCompat.checkSelfPermission(requireContext(), it) != PackageManager.PERMISSION_GRANTED
        }.toTypedArray()

        if (permissionsToRequest.isNotEmpty()) {
            requestPermissionLauncher.launch(permissionsToRequest)
        } else {
            setupBluetooth()
        }
    }

    private fun setupBluetooth() {
        val bluetoothManager = requireContext().getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        val bluetoothAdapter = bluetoothManager.adapter

        if (!bluetoothAdapter.isEnabled) {
            Toast.makeText(context, "Please enable Bluetooth", Toast.LENGTH_LONG).show()
            return
        }

        // Start the background service
        BleForegroundService.startService(requireContext())
        binding.serviceStatusText.text = "Service Status: Running"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        connectionManager.cleanup()
        _binding = null
    }
}
