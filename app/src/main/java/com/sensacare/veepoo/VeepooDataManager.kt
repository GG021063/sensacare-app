package com.sensacare.veepoo

import android.bluetooth.*
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class VeepooDataManager(private val context: Context) {

    private var bluetoothGatt: BluetoothGatt? = null
    private val database = AppDatabase.getInstance(context)
    private val vitalsDao = database.vitalsDao()
    private val secureApiManager = SecureApiManager(context)
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    // LiveData for UI updates
    private val _vitalsData = MutableLiveData<VitalsData>()
    val vitalsData: LiveData<VitalsData> = _vitalsData

    private val _connectionStatus = MutableLiveData<Boolean>()
    val connectionStatus: LiveData<Boolean> = _connectionStatus

    private val _discoveredDevices = MutableLiveData<List<BluetoothDevice>>()
    val discoveredDevices: LiveData<List<BluetoothDevice>> = _discoveredDevices

    private val bluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    private val bluetoothAdapter = bluetoothManager.adapter
    private val bluetoothLeScanner = bluetoothAdapter.bluetoothLeScanner
    private val discoveredDevicesList = mutableListOf<BluetoothDevice>()

    companion object {
        private const val TAG = "VeepooDataManager"
        // Veepoo device service UUIDs (these may need to be updated based on actual device)
        private val VEEPOO_SERVICE_UUID = UUID.fromString("0000fff0-0000-1000-8000-00805f9b34fb")
        private val VEEPOO_CHARACTERISTIC_UUID = UUID.fromString("0000fff1-0000-1000-8000-00805f9b34fb")
    }

    fun startScan() {
        Log.d(TAG, "Starting BLE scan for Veepoo devices")
        if (bluetoothLeScanner != null) {
            discoveredDevicesList.clear()
            bluetoothLeScanner.startScan(scanCallback)
        } else {
            Log.e(TAG, "BluetoothLeScanner is null")
        }
    }

    fun stopScan() {
        bluetoothLeScanner?.stopScan(scanCallback)
    }

    private val scanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            val device = result.device
            Log.d(TAG, "Found device: ${device.name ?: "Unknown"} - ${device.address}")
            
            // Filter for Veepoo devices (you may need to adjust this filter)
            if (device.name?.contains("Veepoo", ignoreCase = true) == true || 
                device.name?.contains("VP", ignoreCase = true) == true) {
                
                if (!discoveredDevicesList.contains(device)) {
                    discoveredDevicesList.add(device)
                    _discoveredDevices.postValue(discoveredDevicesList.toList())
                }
            }
        }

        override fun onScanFailed(errorCode: Int) {
            Log.e(TAG, "Scan failed with error: $errorCode")
        }
    }

    fun connect(device: BluetoothDevice) {
        Log.d(TAG, "Connecting to device: ${device.name}")
        bluetoothGatt = device.connectGatt(context, false, gattCallback)
    }

    private val gattCallback = object : BluetoothGattCallback() {
        override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {
            when (newState) {
                BluetoothProfile.STATE_CONNECTED -> {
                    Log.d(TAG, "Connected to GATT server")
                    _connectionStatus.postValue(true)
                    gatt.discoverServices()
                }
                BluetoothProfile.STATE_DISCONNECTED -> {
                    Log.d(TAG, "Disconnected from GATT server")
                    _connectionStatus.postValue(false)
                }
            }
        }

        override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                Log.d(TAG, "Services discovered")
                val service = gatt.getService(VEEPOO_SERVICE_UUID)
                val characteristic = service?.getCharacteristic(VEEPOO_CHARACTERISTIC_UUID)
                
                characteristic?.let {
                    gatt.setCharacteristicNotification(it, true)
                    Log.d(TAG, "Characteristic notification enabled")
                }
            }
        }

        override fun onCharacteristicChanged(gatt: BluetoothGatt, characteristic: BluetoothGattCharacteristic) {
            val data = characteristic.value
            Log.d(TAG, "Characteristic changed: ${data?.size} bytes")
            
            // Parse vitals data (simplified - actual parsing depends on device protocol)
            parseVitalsData(data)?.let { vitals ->
                _vitalsData.postValue(vitals)
                
                // Store in database and upload to API
                coroutineScope.launch {
                    storeAndUploadVitals(vitals)
                }
            }
        }
    }

    private fun parseVitalsData(data: ByteArray?): VitalsData? {
        data ?: return null
        
        // Simplified parsing - replace with actual Veepoo device protocol
        // This is a placeholder implementation
        return VitalsData(
            heartRate = if (data.size >= 1) data[0].toInt() and 0xFF else null,
            bloodPressureSystolic = if (data.size >= 2) data[1].toInt() and 0xFF else null,
            bloodPressureDiastolic = if (data.size >= 3) data[2].toInt() and 0xFF else null,
            spo2 = if (data.size >= 4) data[3].toInt() and 0xFF else null,
            temperature = if (data.size >= 5) data[4].toFloat() / 10 else null
        )
    }

    private suspend fun storeAndUploadVitals(vitals: VitalsData) {
        try {
            // Store in Room database
            val entity = VitalsEntity(
                heartRate = vitals.heartRate,
                bloodPressureSystolic = vitals.bloodPressureSystolic,
                bloodPressureDiastolic = vitals.bloodPressureDiastolic,
                spo2 = vitals.spo2,
                temperature = vitals.temperature,
                timestamp = vitals.timestamp
            )
            vitalsDao.insert(entity)
            Log.d(TAG, "Vitals stored in database")

            // Upload to API
            try {
                val uploadResult = secureApiManager.uploadVitals(vitals)
                if (uploadResult.isSuccess) {
                    Log.d(TAG, "Vitals uploaded to API")
                } else {
                    Log.w(TAG, "API upload failed: ${uploadResult.exceptionOrNull()?.message}")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Failed to upload vitals to API", e)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to store vitals", e)
        }
    }

    fun disconnect() {
        bluetoothGatt?.disconnect()
        bluetoothGatt?.close()
        bluetoothGatt = null
        _connectionStatus.postValue(false)
    }
}
