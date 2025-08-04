package com.sensacare.veepoo.device

import android.bluetooth.BluetoothDevice
import android.content.Context
import android.util.Log
import com.sensacare.veepoo.VitalsData
import com.sensacare.veepoo.rpm.DeviceMetadata
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Device SDK Manager that abstracts different device SDKs
 * Currently supports Veepoo SDK with extensibility for other SDKs
 */
class DeviceSDKManager(private val context: Context) {
    
    companion object {
        private const val TAG = "DeviceSDKManager"
        
        // Supported device types
        const val DEVICE_TYPE_VEEPOO_RING = "veepoo_ring"
        const val DEVICE_TYPE_VEEPOO_WATCH = "veepoo_watch"
        const val DEVICE_TYPE_GENERIC_BLE = "generic_ble"
    }
    
    // Current device SDK
    private var currentSDK: DeviceSDK? = null
    private var currentDeviceType: String? = null
    
    // State flows
    private val _isConnected = MutableStateFlow(false)
    val isConnected: StateFlow<Boolean> = _isConnected.asStateFlow()
    
    private val _deviceMetadata = MutableStateFlow<DeviceMetadata?>(null)
    val deviceMetadata: StateFlow<DeviceMetadata?> = _deviceMetadata.asStateFlow()
    
    private val _lastVitalsData = MutableStateFlow<VitalsData?>(null)
    val lastVitalsData: StateFlow<VitalsData?> = _lastVitalsData.asStateFlow()
    
    /**
     * Initialize device SDK for the specified device type
     */
    fun initializeSDK(deviceType: String): Boolean {
        return try {
            val sdk = createSDKForDeviceType(deviceType)
            if (sdk != null) {
                currentSDK = sdk
                currentDeviceType = deviceType
                Log.d(TAG, "Initialized SDK for device type: $deviceType")
                true
            } else {
                Log.e(TAG, "Failed to create SDK for device type: $deviceType")
                false
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error initializing SDK for device type: $deviceType", e)
            false
        }
    }
    
    /**
     * Create appropriate SDK for device type
     */
    private fun createSDKForDeviceType(deviceType: String): DeviceSDK? {
        return when (deviceType) {
            DEVICE_TYPE_VEEPOO_RING, DEVICE_TYPE_VEEPOO_WATCH -> {
                VeepooSDKAdapter(context)
            }
            DEVICE_TYPE_GENERIC_BLE -> {
                GenericBLESDKAdapter(context)
            }
            else -> {
                Log.w(TAG, "Unsupported device type: $deviceType")
                null
            }
        }
    }
    
    /**
     * Connect to device
     */
    suspend fun connectToDevice(device: BluetoothDevice): Boolean {
        return try {
            val sdk = currentSDK ?: throw IllegalStateException("SDK not initialized")
            val success = sdk.connect(device)
            if (success) {
                _isConnected.value = true
                updateDeviceMetadata(device)
                Log.d(TAG, "Connected to device: ${device.address}")
            }
            success
        } catch (e: Exception) {
            Log.e(TAG, "Error connecting to device", e)
            false
        }
    }
    
    /**
     * Disconnect from device
     */
    suspend fun disconnectFromDevice(): Boolean {
        return try {
            val sdk = currentSDK ?: return false
            val success = sdk.disconnect()
            if (success) {
                _isConnected.value = false
                Log.d(TAG, "Disconnected from device")
            }
            success
        } catch (e: Exception) {
            Log.e(TAG, "Error disconnecting from device", e)
            false
        }
    }
    
    /**
     * Start vitals monitoring
     */
    suspend fun startVitalsMonitoring(): Boolean {
        return try {
            val sdk = currentSDK ?: throw IllegalStateException("SDK not initialized")
            val success = sdk.startVitalsMonitoring { vitalsData ->
                _lastVitalsData.value = vitalsData
                Log.d(TAG, "Received vitals data: $vitalsData")
            }
            Log.d(TAG, "Started vitals monitoring: $success")
            success
        } catch (e: Exception) {
            Log.e(TAG, "Error starting vitals monitoring", e)
            false
        }
    }
    
    /**
     * Stop vitals monitoring
     */
    suspend fun stopVitalsMonitoring(): Boolean {
        return try {
            val sdk = currentSDK ?: return false
            val success = sdk.stopVitalsMonitoring()
            Log.d(TAG, "Stopped vitals monitoring: $success")
            success
        } catch (e: Exception) {
            Log.e(TAG, "Error stopping vitals monitoring", e)
            false
        }
    }
    
    /**
     * Get device metadata
     */
    fun getDeviceMetadata(): DeviceMetadata? {
        return _deviceMetadata.value
    }
    
    /**
     * Update device metadata
     */
    private fun updateDeviceMetadata(device: BluetoothDevice) {
        val sdk = currentSDK ?: return
        
        val metadata = DeviceMetadata(
            type = currentDeviceType ?: DEVICE_TYPE_VEEPOO_RING,
            mac = device.address,
            firmware = sdk.getFirmwareVersion() ?: "unknown",
            battery = sdk.getBatteryLevel() ?: 0,
            sdkVersion = sdk.getSDKVersion()
        )
        
        _deviceMetadata.value = metadata
        Log.d(TAG, "Updated device metadata: $metadata")
    }
    
    /**
     * Get current device type
     */
    fun getCurrentDeviceType(): String? {
        return currentDeviceType
    }
    
    /**
     * Check if SDK is initialized
     */
    fun isSDKInitialized(): Boolean {
        return currentSDK != null
    }
    
    /**
     * Get SDK capabilities
     */
    fun getSDKCapabilities(): DeviceCapabilities? {
        return currentSDK?.getCapabilities()
    }
    
    /**
     * Cleanup
     */
    fun cleanup() {
        try {
            currentSDK?.cleanup()
            currentSDK = null
            currentDeviceType = null
            _isConnected.value = false
            _deviceMetadata.value = null
            _lastVitalsData.value = null
            Log.d(TAG, "Device SDK Manager cleaned up")
        } catch (e: Exception) {
            Log.e(TAG, "Error during cleanup", e)
        }
    }
}

/**
 * Device SDK interface for abstraction
 */
interface DeviceSDK {
    suspend fun connect(device: BluetoothDevice): Boolean
    suspend fun disconnect(): Boolean
    suspend fun startVitalsMonitoring(onVitalsReceived: (VitalsData) -> Unit): Boolean
    suspend fun stopVitalsMonitoring(): Boolean
    fun getFirmwareVersion(): String?
    fun getBatteryLevel(): Int?
    fun getSDKVersion(): String
    fun getCapabilities(): DeviceCapabilities
    fun cleanup()
}

/**
 * Device capabilities
 */
data class DeviceCapabilities(
    val supportsHeartRate: Boolean,
    val supportsBloodPressure: Boolean,
    val supportsSpO2: Boolean,
    val supportsTemperature: Boolean,
    val supportsBatteryLevel: Boolean,
    val supportsFirmwareVersion: Boolean,
    val maxConnectionDistance: Float? = null,
    val dataUpdateInterval: Long? = null
) 