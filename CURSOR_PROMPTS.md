# 🎯 Cursor Prompts for Sensacare App

## 🚀 Primary Setup Prompt

```
Read the README.md and implement the full flow:
- Connect BLE via VeepooDataManager
- Parse HR, BP, SpO2, temp
- Insert VitalsData into Room
- After each insert, call SensacareApiClient to upload it
- Display latest vitals in VitalsDashboardFragment
- Handle BLE + location permission requests at runtime
```

---

## 🟡 Optional Follow-up Prompts

### 1. LiveData Observer Enhancement
```
Add a LiveData observer to VitalsDashboardFragment to display latest vitals from Room
```

### 2. Background Sync Service
```
Set up a foreground BLE scanning service that:
- Starts when the app opens and runs in the background
- Continues scanning and receiving data even when the app is minimized
- Automatically reconnects to the last known Veepoo device if disconnected
- Buffers data in memory and pushes it to Room and the API every 30 seconds
```

### 3. Upload Throttling
```
Create a coroutine in VeepooDataManager to throttle and buffer uploads (1 per 5 seconds max)
```

### 4. Reconnection Logic ✅ COMPLETED
```
Add BLE reconnection logic if the device disconnects unexpectedly:
- Monitor BLE connection state in BleConnectionManager
- If disconnected unexpectedly, scan and reconnect to the last known device
- Retry up to 3 times before showing a connection error in the UI
- Auto-reconnect with configurable delays and attempt limits
```

### 5. Secure API Layer ✅ COMPLETED
```
Secure API communication:
- Add a Retrofit Interceptor to include bearer tokens in headers
- Store tokens in EncryptedSharedPreferences
- Retry failed API requests up to 3 times with exponential backoff
- If network is unavailable, queue data locally and sync when online
```

### 6. Charting UI ✅ COMPLETED
```
Add charting UI with MPAndroidChart to show last 24 hours of vitals
- Line charts for heart rate, SpO2, and temperature
- Bar chart for blood pressure (systolic/diastolic)
- Real-time updates from Room database
- Interactive charts with zoom and touch support
```

### 7. Permissions Setup ✅ COMPLETED
```
Create a one-time onboarding flow that:
- Requests BLE, location, and notification permissions
- Guides the user through pairing a Veepoo device
- Saves the device MAC address for automatic reconnect
- Includes optional vitals threshold setup
```

### 8. Vitals Alerts ✅ COMPLETED
```
Add vitals flagging logic:
- Heart rate > 120 or < 40: Show red alert
- BP > 140/90 or SpO2 < 92%: Show warning icon on vitals card
- Display alerts in VitalsDashboardFragment with status color
- Individual vital alerts with warning icons
- Overall alert status with background highlighting
```

### 9. Diagnostic Logs ✅ COMPLETED
```
Create a debug mode toggle:
- Log BLE device scan results
- Log characteristic UUIDs and values
- Log insertions to Room and API POST responses
- Display logs in a scrollable developer diagnostics fragment
```

### 10. Connection UI ✅ COMPLETED
```
Add a persistent connection status banner:
- Display whether the Veepoo device is connected or disconnected
- Use green (connected), yellow (connecting), and red (disconnected) indicators
- Place the banner at the top of the dashboard fragment
```

---

## 🔧 Quick Commands

### Build & Test
```bash
# Sync dependencies
./gradlew build

# Run on device
./gradlew installDebug

# Clean build
./gradlew clean build
```

### Debug Commands
```bash
# View logs
adb logcat | grep "VeepooDataManager\|VitalsDashboard"

# Check BLE permissions
adb shell dumpsys bluetooth_manager
```

---

## 📱 Testing Checklist

- [ ] Android device connected via USB
- [ ] USB debugging enabled
- [ ] Bluetooth enabled on device
- [ ] Location services enabled
- [ ] Veepoo device in pairing mode
- [ ] App permissions granted
- [ ] BLE scan discovers device
- [ ] Connection established
- [ ] Vitals data received
- [ ] Room database populated
- [ ] API upload successful

---

## 🐛 Common Issues & Solutions

| Issue | Solution |
|-------|----------|
| BLE not scanning | Check location permissions |
| Device not found | Verify device is in pairing mode |
| Connection fails | Update UUIDs in VeepooDataManager |
| API upload fails | Check network and base URL |
| Build errors | Sync Gradle dependencies |

---

## 📚 Additional Resources

- [Android BLE Guide](https://developer.android.com/guide/topics/connectivity/bluetooth-le)
- [Room Database](https://developer.android.com/training/data-storage/room)
- [Retrofit Documentation](https://square.github.io/retrofit/)
- [LiveData Guide](https://developer.android.com/topic/libraries/architecture/livedata) 