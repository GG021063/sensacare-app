package com.sensacare.veepoo.device

import android.bluetooth.BluetoothDevice
import android.content.Context
import android.util.Log
import com.sensacare.veepoo.VitalsData
import com.sensacare.veepoo.VeepooDataManager
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.resume

/**
 * Veepoo SDK Adapter that wraps the existing Veepoo SDK functionality
 * This maintains compatibility with the current Veepoo implementation
 */
class VeepooSDKAdapter(private val context: Context) : DeviceSDK {
    
    companion object {
        private const val TAG = "VeepooSDKAdapter"
        private const val SDK_VERSION = "veepoo_sdk_1.0"
    }
    
    private val veepooDataManager = VeepooDataManager(context)
    private var isMonitoring = false
    private var onVitalsCallback: ((VitalsData) -> Unit)? = null
    
    override suspend fun connect(device: BluetoothDevice): Boolean {
        return try {
            Log.d(TAG, "Connecting to Veepoo device: ${device.address}")
            
            // Use the existing Veepoo connection logic (no boolean return)
            veepooDataManager.connect(device)
            Log.d(TAG, "Successfully invoked Veepoo connect()")
            true
        } catch (e: Exception) {
            Log.e(TAG, "Error connecting to Veepoo device", e)
            false
        }
    }
    
    override suspend fun disconnect(): Boolean {
        return try {
            Log.d(TAG, "Disconnecting from Veepoo device")
            
            // Stop monitoring if active
            if (isMonitoring) {
                stopVitalsMonitoring()
            }
            
            // Use the existing Veepoo disconnection logic
            veepooDataManager.disconnect()
            Log.d(TAG, "Successfully invoked Veepoo disconnect()")
            true
        } catch (e: Exception) {
            Log.e(TAG, "Error disconnecting from Veepoo device", e)
            false
        }
    }
    
    override suspend fun startVitalsMonitoring(onVitalsReceived: (VitalsData) -> Unit): Boolean {
        return try {
            Log.d(TAG, "Starting Veepoo vitals monitoring")
            
            // Store the callback
            onVitalsCallback = onVitalsReceived
            
            // Observe LiveData from VeepooDataManager
            veepooDataManager.vitalsData.observeForever { vitalsData ->
                Log.d(TAG, "Received vitals data from Veepoo: $vitalsData")
                onVitalsCallback?.invoke(vitalsData)
            }
            
            // Start scan / monitoring using existing logic
            veepooDataManager.startScan()
            isMonitoring = true
            Log.d(TAG, "Successfully started Veepoo vitals monitoring")
            true
        } catch (e: Exception) {
            Log.e(TAG, "Error starting Veepoo vitals monitoring", e)
            false
        }
    }
    
    override suspend fun stopVitalsMonitoring(): Boolean {
        return try {
            Log.d(TAG, "Stopping Veepoo vitals monitoring")
            
            // Stop monitoring using existing logic
            veepooDataManager.stopScan()
            isMonitoring = false
            onVitalsCallback = null
            Log.d(TAG, "Successfully stopped Veepoo vitals monitoring")
            true
        } catch (e: Exception) {
            Log.e(TAG, "Error stopping Veepoo vitals monitoring", e)
            false
        }
    }
    
    override fun getFirmwareVersion(): String? {
        return try {
            // This would need to be implemented in VeepooDataManager
            // For now, return a placeholder
            "1.3.2" // Placeholder - should come from actual device
        } catch (e: Exception) {
            Log.e(TAG, "Error getting firmware version", e)
            null
        }
    }
    
    override fun getBatteryLevel(): Int? {
        return try {
            // This would need to be implemented in VeepooDataManager
            // For now, return a placeholder
            85 // Placeholder - should come from actual device
        } catch (e: Exception) {
            Log.e(TAG, "Error getting battery level", e)
            null
        }
    }
    
    override fun getSDKVersion(): String {
        return SDK_VERSION
    }
    
    override fun getCapabilities(): DeviceCapabilities {
        return DeviceCapabilities(
            supportsHeartRate = true,
            supportsBloodPressure = true,
            supportsSpO2 = true,
            supportsTemperature = true,
            supportsBatteryLevel = true,
            supportsFirmwareVersion = true,
            maxConnectionDistance = 10.0f, // 10 meters typical for BLE
            dataUpdateInterval = 1000L // 1 second typical update interval
        )
    }
    
    override fun cleanup() {
        try {
            Log.d(TAG, "Cleaning up Veepoo SDK adapter")
            
            if (isMonitoring) {
                // cleanup() is not a suspend-function, so invoke the suspend call
                // synchronously using runBlocking to avoid compiler errors.
                runBlocking {
                    stopVitalsMonitoring()
                }
            }
            
            // Ensure we release the active GATT connection even if monitoring
            // is already stopped.  VeepooDataManager exposes disconnect() but
            // not a `cleanup()` helper.
            veepooDataManager.disconnect()
            onVitalsCallback = null
            
            Log.d(TAG, "Veepoo SDK adapter cleanup completed")
        } catch (e: Exception) {
            Log.e(TAG, "Error during Veepoo SDK adapter cleanup", e)
        }
    }
} 