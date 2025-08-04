package com.sensacare.veepoo

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.sensacare.veepoo.databinding.ActivityOnboardingBinding

class OnboardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var bluetoothAdapter: BluetoothAdapter

    private var currentStep = 0
    private var selectedDeviceAddress: String? = null
    private var selectedDeviceName: String? = null

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

        setupOnboardingFlow()
        showCurrentStep()
    }

    private fun setupOnboardingFlow() {
        binding.nextButton.setOnClickListener {
            when (currentStep) {
                0 -> checkBluetoothEnabled()
                1 -> checkBluetoothPermissions()
                2 -> checkLocationPermissions()
                3 -> checkNotificationPermissions()
                4 -> startDeviceDiscovery()
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
        binding.stepDescription.text = "This app connects to your Veepoo device to monitor your vital signs. Let's get you set up!"
        binding.stepImage.setImageResource(R.drawable.ic_welcome)
        binding.nextButton.text = "Get Started"
        binding.skipButton.visibility = View.VISIBLE
        binding.backButton.visibility = View.GONE
    }

    private fun showBluetoothPermissionStep() {
        binding.stepTitle.text = "Enable Bluetooth"
        binding.stepDescription.text = "Bluetooth is required to connect to your Veepoo device. Please enable Bluetooth to continue."
        binding.stepImage.setImageResource(R.drawable.ic_bluetooth)
        binding.nextButton.text = "Enable Bluetooth"
        binding.skipButton.visibility = View.GONE
        binding.backButton.visibility = View.VISIBLE
    }

    private fun showLocationPermissionStep() {
        binding.stepTitle.text = "Location Permission"
        binding.stepDescription.text = "Location permission is required for Bluetooth Low Energy scanning to find your device."
        binding.stepImage.setImageResource(R.drawable.ic_location)
        binding.nextButton.text = "Grant Permission"
        binding.skipButton.visibility = View.GONE
        binding.backButton.visibility = View.VISIBLE
    }

    private fun showNotificationPermissionStep() {
        binding.stepTitle.text = "Notifications"
        binding.stepDescription.text = "Allow notifications to receive alerts about your vital signs and background monitoring status."
        binding.stepImage.setImageResource(R.drawable.ic_notification)
        binding.nextButton.text = "Allow Notifications"
        binding.skipButton.visibility = View.GONE
        binding.backButton.visibility = View.VISIBLE
    }

    private fun showDevicePairingStep() {
        binding.stepTitle.text = "Connect Your Device"
        binding.stepDescription.text = "Let's find and connect to your Veepoo device. Make sure your device is turned on and in pairing mode."
        binding.stepImage.setImageResource(R.drawable.ic_device)
        binding.nextButton.text = "Scan for Devices"
        binding.skipButton.visibility = View.GONE
        binding.backButton.visibility = View.VISIBLE
    }

    private fun showThresholdSetupStep() {
        binding.stepTitle.text = "Set Alert Thresholds"
        binding.stepDescription.text = "Configure your personal vital sign thresholds for alerts. You can change these later in settings."
        binding.stepImage.setImageResource(R.drawable.ic_threshold)
        binding.nextButton.text = "Complete Setup"
        binding.skipButton.visibility = View.GONE
        binding.backButton.visibility = View.VISIBLE
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

    private fun startDeviceDiscovery() {
        // Start BLE scanning for Veepoo devices
        binding.stepDescription.text = "Scanning for Veepoo devices..."
        binding.nextButton.isEnabled = false
        
        // Simulate device discovery (in real implementation, this would use BleForegroundService)
        binding.stepDescription.postDelayed({
            // Simulate finding a device
            selectedDeviceName = "Veepoo VP-01"
            selectedDeviceAddress = "AA:BB:CC:DD:EE:FF"
            
            binding.stepDescription.text = "Found device: $selectedDeviceName\nTap to connect"
            binding.nextButton.isEnabled = true
            binding.nextButton.text = "Connect Device"
        }, 3000)
    }

    private fun completeOnboarding() {
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

    companion object {
        fun shouldShowOnboarding(context: Context): Boolean {
            val prefs = context.getSharedPreferences("SensacareAppPrefs", Context.MODE_PRIVATE)
            return !prefs.getBoolean("onboarding_completed", false)
        }
    }
} 