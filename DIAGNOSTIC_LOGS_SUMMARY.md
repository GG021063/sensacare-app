# 🔍 Diagnostic Logs Implementation

## ✅ **IMPLEMENTED FEATURES**

### **DiagnosticLogger.kt**
- **Comprehensive Logging**: BLE scanning, connection, characteristics, Room database, API calls, and security events
- **Debug Mode Toggle**: Enable/disable logging with runtime control
- **Categorized Logging**: Separate categories for different types of events
- **Log Levels**: DEBUG, INFO, WARNING, ERROR with color coding
- **Real-time Updates**: Auto-refresh every 2 seconds
- **Export Functionality**: Share logs via email or other apps

### **Key Features**
1. **🔍 BLE Diagnostics**
   - Device scan results with RSSI and scan records
   - Connection state changes and attempts
   - Service discovery and characteristic operations
   - Vitals data parsing and validation

2. **💾 Database Diagnostics**
   - Room database insertions and queries
   - Error handling and recovery
   - Data validation and integrity checks

3. **🌐 API Diagnostics**
   - Request/response logging with headers and bodies
   - Authentication and token management
   - Offline queue operations
   - Error handling and retry logic

4. **🔐 Security Diagnostics**
   - Authentication success/failure events
   - Token refresh operations
   - Encrypted storage operations

---

## 🛠 **TECHNICAL IMPLEMENTATION**

### **DiagnosticLogger Singleton**
```kotlin
class DiagnosticLogger private constructor(private val context: Context) {
    companion object {
        @Volatile
        private var INSTANCE: DiagnosticLogger? = null
        
        fun getInstance(context: Context): DiagnosticLogger {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: DiagnosticLogger(context).also { INSTANCE = it }
            }
        }
    }
    
    private val logQueue = ConcurrentLinkedQueue<LogEntry>()
    private var isDebugModeEnabled = false
}
```

### **Log Entry Structure**
```kotlin
data class LogEntry(
    val timestamp: Long,
    val timestampFormatted: String,
    val level: LogLevel,
    val category: LogCategory,
    val message: String,
    val details: String? = null
)

enum class LogLevel {
    DEBUG, INFO, WARNING, ERROR
}

enum class LogCategory {
    BLE_SCAN, BLE_CONNECTION, BLE_CHARACTERISTIC, 
    ROOM_DATABASE, API_CALL, SECURITY, GENERAL
}
```

### **BLE Scanning Logs**
```kotlin
fun logBleScan(deviceName: String?, deviceAddress: String, rssi: Int, scanRecord: ByteArray?) {
    if (!isDebugModeEnabled) return
    
    val message = "BLE Scan Result"
    val details = buildString {
        appendLine("Device: ${deviceName ?: "Unknown"} ($deviceAddress)")
        appendLine("RSSI: $rssi dBm")
        if (scanRecord != null) {
            appendLine("Scan Record: ${bytesToHex(scanRecord)}")
        }
    }
    
    log(LogLevel.DEBUG, LogCategory.BLE_SCAN, message, details)
}
```

### **Characteristic Logs**
```kotlin
fun logBleCharacteristicChanged(deviceAddress: String, serviceUuid: String, characteristicUuid: String, value: ByteArray) {
    if (!isDebugModeEnabled) return
    
    val message = "BLE Characteristic Changed"
    val details = buildString {
        appendLine("Device: $deviceAddress")
        appendLine("Service: $serviceUuid")
        appendLine("Characteristic: $characteristicUuid")
        appendLine("Value: ${bytesToHex(value)}")
        appendLine("Value (ASCII): ${String(value)}")
    }
    
    log(LogLevel.DEBUG, LogCategory.BLE_CHARACTERISTIC, message, details)
}
```

---

## 📱 **USER INTERFACE**

### **DiagnosticFragment.kt**
- **Real-time Log Display**: Auto-refreshing log viewer
- **Filter Controls**: Category and level filtering
- **Debug Mode Toggle**: Enable/disable logging
- **Export Functionality**: Share logs via email
- **Clear and Refresh**: Manage log data

### **DiagnosticLogAdapter.kt**
- **Color-coded Logs**: Different colors for levels and categories
- **Expandable Details**: Show/hide detailed information
- **Timestamp Formatting**: Human-readable timestamps
- **Background Colors**: Visual category differentiation

### **UI Features**
- **Category Filter**: Filter by BLE, Database, API, Security, etc.
- **Level Filter**: Filter by DEBUG, INFO, WARNING, ERROR
- **Auto-refresh**: Updates every 2 seconds
- **Export**: Share logs as text file
- **Clear**: Remove all logs
- **Status Display**: Show log count and debug mode status

---

## 🔄 **INTEGRATION**

### **BleConnectionManager Integration**
```kotlin
override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {
    val deviceAddress = gatt.device.address
    val diagnosticLogger = DiagnosticLogger.getInstance(context)
    
    // Log diagnostic information
    diagnosticLogger.logBleConnectionStateChange(deviceAddress, 
        if (isConnected.get()) BluetoothProfile.STATE_CONNECTED else BluetoothProfile.STATE_DISCONNECTED, 
        newState)
    
    when (newState) {
        BluetoothProfile.STATE_CONNECTED -> {
            diagnosticLogger.logBleConnectionSuccess(deviceAddress)
            // ... connection logic
        }
        BluetoothProfile.STATE_DISCONNECTED -> {
            diagnosticLogger.logBleDisconnection(deviceAddress, "Status: $status")
            // ... disconnection logic
        }
    }
}
```

### **BleForegroundService Integration**
```kotlin
private val scanCallback = object : ScanCallback() {
    override fun onScanResult(callbackType: Int, result: ScanResult) {
        val device = result.device
        val diagnosticLogger = DiagnosticLogger.getInstance(this@BleForegroundService)
        
        // Log diagnostic information
        diagnosticLogger.logBleScan(device.name, device.address, result.rssi, result.scanRecord)
        
        // ... scan logic
    }

    override fun onScanFailed(errorCode: Int) {
        val diagnosticLogger = DiagnosticLogger.getInstance(this@BleForegroundService)
        diagnosticLogger.logBleScanFailed(errorCode)
        // ... error handling
    }
}
```

### **Navigation Integration**
```kotlin
binding.diagnosticButton.setOnClickListener {
    // Navigate to diagnostic fragment
    val diagnosticFragment = DiagnosticFragment()
    parentFragmentManager.commit {
        replace(R.id.fragment_container, diagnosticFragment)
        addToBackStack(null)
    }
}
```

---

## 🎨 **VISUAL DESIGN**

### **Color Scheme**
- **DEBUG**: Gray text
- **INFO**: Black text
- **WARNING**: Orange text (#FF8C00)
- **ERROR**: Red text

### **Category Backgrounds**
- **BLE_SCAN**: Light blue (#F0F8FF)
- **BLE_CONNECTION**: Light green (#F0FFF0)
- **BLE_CHARACTERISTIC**: Light yellow (#FFF8DC)
- **ROOM_DATABASE**: Light beige (#F5F5DC)
- **API_CALL**: Light gray (#F0F0F0)
- **SECURITY**: Light pink (#FFF0F5)
- **GENERAL**: White

### **UI Layout**
- **Header**: Title, debug mode status, and toggle
- **Controls**: Category/level filters and action buttons
- **Status Bar**: Log count and auto-refresh indicator
- **Log Display**: Scrollable RecyclerView with color-coded entries

---

## ⚙️ **CONFIGURATION**

### **Log Settings**
```kotlin
companion object {
    private const val MAX_LOG_ENTRIES = 1000
    private const val DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS"
}
```

### **Auto-refresh Settings**
- **Update Interval**: 2 seconds
- **Background Processing**: Coroutine-based updates
- **Memory Management**: Automatic log cleanup

### **Export Settings**
- **Format**: Plain text with timestamps
- **Content**: All logs with categories and levels
- **Sharing**: Intent-based sharing to other apps

---

## 🧪 **TESTING**

### **BLE Diagnostics Testing**
1. **Scan Results**: Test device discovery logging
2. **Connection Events**: Test connection state changes
3. **Characteristic Operations**: Test read/write/notify logging
4. **Error Handling**: Test connection failures and timeouts

### **Database Diagnostics Testing**
1. **Insert Operations**: Test vitals data insertion logging
2. **Query Operations**: Test database query logging
3. **Error Handling**: Test database error logging
4. **Data Validation**: Test data integrity checks

### **API Diagnostics Testing**
1. **Request Logging**: Test API request details
2. **Response Logging**: Test API response handling
3. **Authentication**: Test token management logging
4. **Offline Queue**: Test queue operations logging

### **UI Testing**
1. **Filter Functionality**: Test category and level filters
2. **Real-time Updates**: Test auto-refresh functionality
3. **Export Feature**: Test log export and sharing
4. **Debug Toggle**: Test enable/disable functionality

---

## 🚀 **BENEFITS**

### **Development Benefits**
- ✅ **Real-time Debugging**: Live monitoring of BLE operations
- ✅ **Error Tracking**: Detailed error logging and analysis
- ✅ **Performance Monitoring**: Track API and database performance
- ✅ **Data Validation**: Verify data integrity and parsing
- ✅ **Security Auditing**: Monitor authentication and token operations

### **Troubleshooting Benefits**
- ✅ **Issue Isolation**: Quickly identify problem areas
- ✅ **Data Flow Tracking**: Follow data from BLE to database to API
- ✅ **Error Reproduction**: Detailed logs for bug reproduction
- ✅ **Performance Analysis**: Identify bottlenecks and slow operations
- ✅ **Security Monitoring**: Track authentication and authorization

### **User Experience Benefits**
- ✅ **Transparent Operations**: Users can see what the app is doing
- ✅ **Problem Reporting**: Easy log export for support
- ✅ **Debug Mode Control**: Users can enable/disable logging
- ✅ **Visual Feedback**: Color-coded logs for easy reading
- ✅ **Filtering Options**: Focus on specific areas of interest

---

## 📋 **USAGE INSTRUCTIONS**

### **For Developers**
1. **Enable Debug Mode**: Toggle the debug switch in the diagnostic fragment
2. **Monitor Logs**: Watch real-time log updates
3. **Filter Logs**: Use category and level filters to focus on specific areas
4. **Export Logs**: Share logs for analysis or support
5. **Clear Logs**: Remove old logs to free memory

### **For Users**
1. **Access Diagnostics**: Tap the "Diagnostics" button in the main screen
2. **Enable Debug Mode**: Toggle the debug switch to start logging
3. **View Logs**: Scroll through the log entries
4. **Filter Logs**: Use dropdowns to filter by category or level
5. **Export Logs**: Share logs with support team if needed

### **For Support**
1. **Collect Logs**: Ask users to export logs when issues occur
2. **Analyze Logs**: Review timestamps, categories, and error messages
3. **Identify Issues**: Look for error patterns and failed operations
4. **Reproduce Problems**: Use log details to recreate issues
5. **Monitor Performance**: Check for slow operations or bottlenecks

---

## 🔮 **FUTURE ENHANCEMENTS**

### **Advanced Logging**
- **Log Persistence**: Save logs to file for long-term storage
- **Log Compression**: Compress old logs to save space
- **Log Rotation**: Automatic log file rotation
- **Remote Logging**: Send logs to remote server for analysis
- **Log Analytics**: Statistical analysis of log patterns

### **Enhanced UI**
- **Search Functionality**: Search logs by text content
- **Log Highlighting**: Highlight specific log entries
- **Log Bookmarks**: Save important log entries
- **Custom Filters**: User-defined filter combinations
- **Log Comparison**: Compare logs from different sessions

### **Performance Monitoring**
- **Performance Metrics**: Track response times and throughput
- **Resource Usage**: Monitor memory and CPU usage
- **Network Monitoring**: Track network performance
- **Battery Impact**: Monitor battery usage from logging
- **Storage Usage**: Track log storage requirements

---

## 📚 **RESOURCES**

### **Implementation Files**
- `DiagnosticLogger.kt` - Core logging functionality
- `DiagnosticFragment.kt` - UI for log display
- `DiagnosticLogAdapter.kt` - RecyclerView adapter for logs
- `fragment_diagnostic.xml` - Layout for diagnostic UI
- `control_background.xml` - Background drawable for controls
- `log_background.xml` - Background drawable for log area

### **Integration Points**
- `BleConnectionManager.kt` - BLE connection logging
- `BleForegroundService.kt` - BLE scanning logging
- `VitalsDashboardFragment.kt` - Navigation to diagnostics
- `fragment_dashboard.xml` - Diagnostic button in main UI

### **Log Categories**
- **BLE_SCAN**: Device discovery and scanning
- **BLE_CONNECTION**: Connection state management
- **BLE_CHARACTERISTIC**: Data transfer operations
- **ROOM_DATABASE**: Local database operations
- **API_CALL**: Network API communications
- **SECURITY**: Authentication and authorization
- **GENERAL**: General application events

---

*The diagnostic logging implementation provides comprehensive debugging capabilities with real-time monitoring, filtering, and export functionality for the Sensacare App.* 