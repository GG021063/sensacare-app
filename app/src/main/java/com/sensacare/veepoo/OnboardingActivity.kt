package com.sensacare.veepoo

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sensacare.veepoo.databinding.ActivityOnboardingBinding
import kotlinx.coroutines.launch
import android.widget.ProgressBar

class OnboardingActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "OnboardingActivity"
        
        fun shouldShowOnboarding(context: Context): Boolean {
            val prefs = context.getSharedPreferences("SensacareAppPrefs", Context.MODE_PRIVATE)
            return !prefs.getBoolean("onboarding_completed", false)
        }
    }

    private lateinit var binding: ActivityOnboardingBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var bluetoothAdapter: BluetoothAdapter
    private lateinit var bleConnectionManager: BleConnectionManager
    private lateinit var deviceAdapter: DeviceAdapter

    private var currentStep = 0
    private var selectedDeviceAddress: String? = null
    private var selectedDeviceName: String? = null
    private var isScanning = false

    // Permission launchers
    private val bluetoothPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allGranted = permissions.values.all { it }
        if (allGranted) {
            proceedToNextStep()
        } else {
            showPermissionDeniedMessage("Bluetooth permissions are required for device connectivity")
        }
    }

    private val locationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allGranted = permissions.values.all { it }
        if (allGranted) {
            proceedToNextStep()
        } else {
            showPermissionDeniedMessage("Location permission is required for BLE scanning")
        }
    }

    private val notificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            proceedToNextStep()
        } else {
            showPermissionDeniedMessage("Notification permission is recommended for background monitoring")
            // Continue anyway as notification permission is not critical
            proceedToNextStep()
        }
    }

    private val bluetoothEnableLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            proceedToNextStep()
        } else {
            showPermissionDeniedMessage("Bluetooth must be enabled to use this app")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("SensacareAppPrefs", Context.MODE_PRIVATE)
        bluetoothAdapter = (getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager).adapter
        
        // Initialize BLE Connection Manager
        val preferencesManager = PreferencesManager(this)
        bleConnectionManager = BleConnectionManager(this, preferencesManager, this)
        
        // Initialize device adapter for the RecyclerView
        deviceAdapter = DeviceAdapter(emptyList()) { device ->
            // Device selection callback
            selectedDeviceName = device.name ?: "Unknown Device"
            selectedDeviceAddress = device.address
            binding.nextButton.isEnabled = true
            binding.nextButton.text = "Connect to ${device.name ?: "Selected Device"}"
        }

        // Setup RecyclerView
        binding.deviceRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@OnboardingActivity)
            adapter = deviceAdapter
            visibility = View.GONE // Initially hidden
        }

        setupBleCallbacks()
        setupOnboardingFlow()
        showCurrentStep()
    }
    
    private fun setupBleCallbacks() {
        // Set up BLE scan results callback
        bleConnectionManager.setScanResultCallback { devices ->
            lifecycleScope.launch {
                if (devices.isEmpty()) {
                    binding.stepDescription.text = "No vitals monitoring devices found.\nMake sure your device is turned on and in pairing mode."
                    binding.nextButton.text = "Scan Again"
                    binding.nextButton.isEnabled = true
                } else {
                    binding.stepDescription.text = "${devices.size} device(s) found. Select one to connect:"
                    deviceAdapter.updateDevices(devices)
                    binding.nextButton.text = "Connect Device"
                    binding.nextButton.isEnabled = selectedDeviceAddress != null
                }
                binding.progressScan.visibility = View.GONE
            }
        }
        
        // Set up error callback
        bleConnectionManager.setErrorCallback { message, _ ->
            lifecycleScope.launch {
                Toast.makeText(this@OnboardingActivity, message, Toast.LENGTH_SHORT).show()
                binding.progressScan.visibility = View.GONE
                binding.nextButton.isEnabled = true
                binding.nextButton.text = "Scan Again"
                isScanning = false
            }
        }
        
        // Set up connection state callback
        bleConnectionManager.setConnectionStateCallback { state ->
            lifecycleScope.launch {
                when (state) {
                    BleConnectionManager.ConnectionState.SCANNING -> {
                        binding.stepDescription.text = "Scanning for vitals monitoring devices..."
                        binding.progressScan.visibility = View.VISIBLE
                        isScanning = true
                    }
                    BleConnectionManager.ConnectionState.DISCONNECTED -> {
                        if (isScanning) {
                            binding.progressScan.visibility = View.GONE
                            isScanning = false
                        }
                    }
                    else -> { /* Handle other states if needed */ }
                }
            }
        }
    }

    override fun onDestroy() {
        // Clean up resources
        bleConnectionManager.cleanup()
        super.onDestroy()
    }

    private fun setupOnboardingFlow() {
        binding.nextButton.setOnClickListener {
            when (currentStep) {
                0 -> checkBluetoothEnabled()
                1 -> checkBluetoothPermissions()
                2 -> checkLocationPermissions()
                3 -> checkNotificationPermissions()
                4 -> handleDeviceDiscoveryAction()
                5 -> completeOnboarding()
            }
        }

        binding.skipButton.setOnClickListener {
            completeOnboarding()
        }

        binding.backButton.setOnClickListener {
            if (currentStep > 0) {
                currentStep--
                showCurrentStep()
            }
        }
    }

    private fun showCurrentStep() {
        when (currentStep) {
            0 -> showWelcomeStep()
            1 -> showBluetoothPermissionStep()
            2 -> showLocationPermissionStep()
            3 -> showNotificationPermissionStep()
            4 -> showDevicePairingStep()
            5 -> showThresholdSetupStep()
        }
    }

    private fun showWelcomeStep() {
        binding.stepTitle.text = "Welcome to Sensacare"
        binding.stepDescription.text = "This app connects to your vitals monitoring device to track your vital signs. Let's get you set up!"
        binding.stepImage.setImageResource(R.drawable.ic_welcome)
        binding.stepImage.visibility = View.VISIBLE
        binding.nextButton.text = "Get Started"
        binding.skipButton.visibility = View.VISIBLE
        binding.backButton.visibility = View.GONE
        binding.deviceRecyclerView.visibility = View.GONE
        binding.progressScan.visibility = View.GONE
    }

    private fun showBluetoothPermissionStep() {
        binding.stepTitle.text = "Enable Bluetooth"
        binding.stepDescription.text = "Bluetooth is required to connect to your vitals monitoring device. Please enable Bluetooth to continue."
        binding.stepImage.setImageResource(R.drawable.ic_bluetooth)
        binding.stepImage.visibility = View.VISIBLE
        binding.nextButton.text = "Enable Bluetooth"
        binding.skipButton.visibility = View.GONE
        binding.backButton.visibility = View.VISIBLE
        binding.deviceRecyclerView.visibility = View.GONE
        binding.progressScan.visibility = View.GONE
    }

    private fun showLocationPermissionStep() {
        binding.stepTitle.text = "Location Permission"
        binding.stepDescription.text = "Location permission is required for Bluetooth Low Energy scanning to find your device."
        binding.stepImage.setImageResource(R.drawable.ic_location)
        binding.stepImage.visibility = View.VISIBLE
        binding.nextButton.text = "Grant Permission"
        binding.skipButton.visibility = View.GONE
        binding.backButton.visibility = View.VISIBLE
        binding.deviceRecyclerView.visibility = View.GONE
        binding.progressScan.visibility = View.GONE
    }

    private fun showNotificationPermissionStep() {
        binding.stepTitle.text = "Notifications"
        binding.stepDescription.text = "Allow notifications to receive alerts about your vital signs and background monitoring status."
        binding.stepImage.setImageResource(R.drawable.ic_notification)
        binding.stepImage.visibility = View.VISIBLE
        binding.nextButton.text = "Allow Notifications"
        binding.skipButton.visibility = View.GONE
        binding.backButton.visibility = View.VISIBLE
        binding.deviceRecyclerView.visibility = View.GONE
        binding.progressScan.visibility = View.GONE
    }

    private fun showDevicePairingStep() {
        binding.stepTitle.text = "Connect Your Device"
        binding.stepDescription.text = "Let's find and connect to your vitals monitoring device. Make sure your device is turned on and in pairing mode."
        binding.stepImage.setImageResource(R.drawable.ic_device)
        binding.stepImage.visibility = View.VISIBLE
        binding.nextButton.text = "Scan for Devices"
        binding.skipButton.visibility = View.GONE
        binding.backButton.visibility = View.VISIBLE
        binding.deviceRecyclerView.visibility = View.GONE
        binding.progressScan.visibility = View.GONE
        
        // Reset device selection
        selectedDeviceName = null
        selectedDeviceAddress = null
    }

    private fun showThresholdSetupStep() {
        binding.stepTitle.text = "Set Alert Thresholds"
        binding.stepDescription.text = "Configure your personal vital sign thresholds for alerts. You can change these later in settings."
        binding.stepImage.setImageResource(R.drawable.ic_threshold)
        binding.stepImage.visibility = View.VISIBLE
        binding.nextButton.text = "Complete Setup"
        binding.skipButton.visibility = View.GONE
        binding.backButton.visibility = View.VISIBLE
        binding.deviceRecyclerView.visibility = View.GONE
        binding.progressScan.visibility = View.GONE
    }

    private fun checkBluetoothEnabled() {
        if (!bluetoothAdapter.isEnabled) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            bluetoothEnableLauncher.launch(enableBtIntent)
        } else {
            proceedToNextStep()
        }
    }

    private fun checkBluetoothPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val permissions = arrayOf(
                Manifest.permission.BLUETOOTH_CONNECT,
                Manifest.permission.BLUETOOTH_SCAN
            )
            
            val allGranted = permissions.all {
                ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
            }
            
            if (allGranted) {
                proceedToNextStep()
            } else {
                bluetoothPermissionLauncher.launch(permissions)
            }
        } else {
            // For older Android versions, these permissions are granted at install time
            proceedToNextStep()
        }
    }

    private fun checkLocationPermissions() {
        val permissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        
        val allGranted = permissions.all {
            ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
        }
        
        if (allGranted) {
            proceedToNextStep()
        } else {
            locationPermissionLauncher.launch(permissions)
        }
    }

    private fun checkNotificationPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                proceedToNextStep()
            } else {
                notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        } else {
            // For older versions, check if notifications are enabled
            if (NotificationManagerCompat.from(this).areNotificationsEnabled()) {
                proceedToNextStep()
            } else {
                // Show dialog to enable notifications in settings
                showNotificationSettingsDialog()
            }
        }
    }

    private fun showNotificationSettingsDialog() {
        // Show a dialog explaining how to enable notifications
        android.app.AlertDialog.Builder(this)
            .setTitle("Enable Notifications")
            .setMessage("Please enable notifications in your device settings to receive vital sign alerts.")
            .setPositiveButton("Open Settings") { _, _ ->
                val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
                    putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
                }
                startActivity(intent)
                proceedToNextStep()
            }
            .setNegativeButton("Skip") { _, _ ->
                proceedToNextStep()
            }
            .show()
    }
    
    private fun handleDeviceDiscoveryAction() {
        // Check the current button text to determine the action
        when (binding.nextButton.text.toString()) {
            "Scan for Devices" -> startDeviceDiscovery()
            "Scan Again" -> startDeviceDiscovery()
            "Connect Device" -> {
                if (selectedDeviceAddress != null) {
                    // Stop scanning if it's still active
                    if (isScanning) {
                        bleConnectionManager.stopScan()
                        isScanning = false
                    }
                    proceedToNextStep()
                } else {
                    Toast.makeText(this, "Please select a device first", Toast.LENGTH_SHORT).show()
                }
            }
            else -> {
                if (selectedDeviceAddress != null) {
                    proceedToNextStep()
                } else {
                    startDeviceDiscovery()
                }
            }
        }
    }

    private fun startDeviceDiscovery() {
        // Hide the device image and show the RecyclerView
        binding.stepImage.visibility = View.GONE
        binding.deviceRecyclerView.visibility = View.VISIBLE
        binding.progressScan.visibility = View.VISIBLE
        
        // Update UI
        binding.stepDescription.text = "Scanning for vitals monitoring devices..."
        binding.nextButton.isEnabled = false
        
        // Clear any previous devices
        deviceAdapter.updateDevices(emptyList())
        
        // Start real BLE scanning
        isScanning = true
        bleConnectionManager.startScan()
    }

    private fun completeOnboarding() {
        // Stop scanning if it's still active
        if (isScanning) {
            bleConnectionManager.stopScan()
            isScanning = false
        }
        
        // Save onboarding completion and device preferences
        sharedPreferences.edit().apply {
            putBoolean("onboarding_completed", true)
            putString("paired_device_name", selectedDeviceName)
            putString("paired_device_address", selectedDeviceAddress)
            putLong("onboarding_timestamp", System.currentTimeMillis())
            
            // Save default thresholds
            putInt("hr_min_threshold", 60)
            putInt("hr_max_threshold", 100)
            putInt("spo2_min_threshold", 95)
            putInt("bp_systolic_max_threshold", 140)
            putInt("bp_diastolic_max_threshold", 90)
            putFloat("temp_max_threshold", 37.5f)
        }.apply()

        // Start the main activity
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    private fun proceedToNextStep() {
        currentStep++
        if (currentStep < 6) {
            showCurrentStep()
        } else {
            completeOnboarding()
        }
    }

    private fun showPermissionDeniedMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        
        // Show dialog with explanation and settings option
        android.app.AlertDialog.Builder(this)
            .setTitle("Permission Required")
            .setMessage("$message\n\nWould you like to open settings to grant this permission?")
            .setPositiveButton("Open Settings") { _, _ ->
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = android.net.Uri.fromParts("package", packageName, null)
                }
                startActivity(intent)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    // Device adapter for the RecyclerView
    private inner class DeviceAdapter(
        private var devices: List<BluetoothDevice>,
        private val onDeviceSelected: (BluetoothDevice) -> Unit
    ) : RecyclerView.Adapter<DeviceAdapter.DeviceViewHolder>() {

        private var selectedPosition = -1

        inner class DeviceViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val deviceName: TextView = view.findViewById(R.id.tv_device_name)
            val deviceAddress: TextView = view.findViewById(R.id.tv_device_address)
            val deviceRssi: TextView = view.findViewById(R.id.tv_rssi)
            val signalStrength: ProgressBar = view.findViewById(R.id.progress_signal_strength)
            val signalStrengthText: TextView = view.findViewById(R.id.tv_signal_strength)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_ble_device, parent, false)
            return DeviceViewHolder(view)
        }

        override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
            val device = devices[position]
            val scanResult = bleConnectionManager.getDeviceScanResult(device.address)
            
            // Set device details
            holder.deviceName.text = device.name ?: "Unknown Device"
            holder.deviceAddress.text = device.address
            
            // Set RSSI and signal strength
            val rssi = scanResult?.rssi ?: -100
            holder.deviceRssi.text = "$rssi dBm"
            
            // Calculate signal strength percentage (RSSI typically ranges from -100 to -30)
            val signalPercentage = ((rssi + 100) * 100 / 70).coerceIn(0, 100)
            holder.signalStrength.progress = signalPercentage
            
            // Set signal strength text
            holder.signalStrengthText.text = when {
                signalPercentage > 70 -> "Excellent signal"
                signalPercentage > 50 -> "Good signal"
                signalPercentage > 30 -> "Fair signal"
                else -> "Weak signal"
            }
            
            // Highlight selected device
            holder.itemView.isSelected = position == selectedPosition
            
            // Set click listener
            holder.itemView.setOnClickListener {
                // Update selection
                val previousSelected = selectedPosition
                selectedPosition = position
                
                // Update UI
                notifyItemChanged(previousSelected)
                notifyItemChanged(selectedPosition)
                
                // Trigger callback
                onDeviceSelected(device)
            }
        }

        override fun getItemCount() = devices.size

        fun updateDevices(newDevices: List<BluetoothDevice>) {
            devices = newDevices
            selectedPosition = -1
            notifyDataSetChanged()
        }
    }
}
