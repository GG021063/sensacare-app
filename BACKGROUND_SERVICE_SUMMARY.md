# 🔄 Background Sync Service Implementation

## ✅ **IMPLEMENTED FEATURES**

### **BleForegroundService.kt**
- **Foreground Service**: Runs continuously in background with notification
- **Continuous BLE Scanning**: 10-second scan cycles with 5-second pauses
- **Auto-Reconnection**: Remembers last known device and reconnects automatically
- **Data Buffering**: Collects vitals data in memory buffer
- **Batch Upload**: Uploads data to Room and API every 30 seconds
- **Error Handling**: Retries failed uploads and handles disconnections

### **Key Features**
1. **🔍 Continuous Scanning**
   - Scans for Veepoo devices every 10 seconds
   - Automatically connects to discovered devices
   - Maintains connection state

2. **🔄 Auto-Reconnection**
   - Remembers last connected device
   - Attempts reconnection when disconnected
   - Handles connection failures gracefully

3. **📊 Data Buffering**
   - Buffers vitals data in memory
   - Prevents data loss during network issues
   - Batch processing for efficiency

4. **⏰ Scheduled Uploads**
   - Uploads data every 30 seconds
   - Stores in Room database first
   - Then uploads to Sensacare API
   - Retries failed uploads

5. **🔔 User Feedback**
   - Persistent notification showing service status
   - Updates notification with connection status
   - Shows upload progress

---

## 🛠 **TECHNICAL IMPLEMENTATION**

### **Service Lifecycle**
```kotlin
// Service starts when app launches
BleForegroundService.startService(context)

// Runs continuously in background
// Handles app minimization and system restarts
// Stops only when explicitly called
BleForegroundService.stopService(context)
```

### **Data Flow**
```
[BLE Device] → [Service Scan] → [Connect] → [Data Buffer] 
     ↓
[30s Timer] → [Room DB] → [API Upload] → [Notification Update]
```

### **Permission Requirements**
```xml
<uses-permission android:name="android.permission.FOREGROUND_SERVICE" />
<uses-permission android:name="android.permission.FOREGROUND_SERVICE_LOCATION" />
<uses-permission android:name="android.permission.WAKE_LOCK" />
```

---

## 📱 **USER INTERFACE**

### **Service Controls**
- **Start Service Button**: Manually start background monitoring
- **Stop Service Button**: Stop background monitoring
- **Service Status**: Real-time status indicator
- **Notification**: Persistent system notification

### **Service States**
- **Running**: Service is active and monitoring
- **Stopped**: Service is inactive
- **Connected**: Connected to Veepoo device
- **Disconnected**: Searching for devices

---

## 🔧 **CONFIGURATION**

### **Timing Settings**
```kotlin
private const val UPLOAD_INTERVAL = 30_000L // 30 seconds
// Scan for 10 seconds, pause for 5 seconds
delay(10_000) // Scan duration
delay(5_000)  // Pause duration
```

### **Device Filtering**
```kotlin
// Filter for Veepoo devices
if (device.name?.contains("Veepoo", ignoreCase = true) == true || 
    device.name?.contains("VP", ignoreCase = true) == true) {
    // Connect to device
}
```

### **UUID Configuration**
```kotlin
// Update these for your specific Veepoo device
private val VEEPOO_SERVICE_UUID = UUID.fromString("0000fff0-0000-1000-8000-00805f9b34fb")
private val VEEPOO_CHARACTERISTIC_UUID = UUID.fromString("0000fff1-0000-1000-8000-00805f9b34fb")
```

---

## 🧪 **TESTING**

### **Service Testing**
1. **Start Service**: Tap "Start Service" button
2. **Check Notification**: Verify persistent notification appears
3. **Minimize App**: Service should continue running
4. **Check Logs**: Monitor Logcat for service activity
5. **Test Reconnection**: Disconnect device, verify auto-reconnection

### **Data Flow Testing**
1. **Connect Device**: Ensure Veepoo device is paired
2. **Monitor Buffer**: Check logs for data buffering
3. **Verify Uploads**: Confirm 30-second upload cycles
4. **Check Database**: Verify data is stored in Room
5. **API Uploads**: Confirm data reaches Sensacare API

---

## 🚀 **BENEFITS**

### **Reliability**
- ✅ **Continuous Monitoring**: No data loss when app is minimized
- ✅ **Auto-Reconnection**: Handles device disconnections automatically
- ✅ **Data Buffering**: Prevents data loss during network issues
- ✅ **Error Recovery**: Retries failed operations

### **User Experience**
- ✅ **Background Operation**: Works without user intervention
- ✅ **Status Feedback**: Clear indication of service state
- ✅ **Battery Efficient**: Optimized scanning cycles
- ✅ **Non-Intrusive**: Low-priority notification

### **Data Integrity**
- ✅ **Local Storage**: All data stored in Room database
- ✅ **Cloud Sync**: Regular uploads to Sensacare API
- ✅ **Batch Processing**: Efficient data handling
- ✅ **Retry Logic**: Failed uploads are retried

---

## 📋 **USAGE INSTRUCTIONS**

### **For Developers**
1. **Start Service**: `BleForegroundService.startService(context)`
2. **Stop Service**: `BleForegroundService.stopService(context)`
3. **Check Status**: `ServiceStatusChecker.isServiceRunning(context, BleForegroundService::class.java)`

### **For Users**
1. **Launch App**: Service starts automatically
2. **Grant Permissions**: Allow BLE and location access
3. **Monitor Status**: Check service status in UI
4. **Minimize App**: Service continues in background
5. **Check Notification**: Persistent notification shows status

---

## 🔮 **FUTURE ENHANCEMENTS**

- **Battery Optimization**: Implement adaptive scanning based on battery level
- **Multi-Device Support**: Handle multiple Veepoo devices simultaneously
- **Advanced Filtering**: More sophisticated device detection
- **Data Compression**: Compress data before upload
- **Offline Mode**: Enhanced offline data handling
- **Analytics**: Service performance metrics

---

*The background service provides reliable, continuous BLE monitoring with automatic reconnection, data buffering, and scheduled uploads to ensure no vitals data is lost.* 