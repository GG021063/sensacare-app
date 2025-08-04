# Sensacare App

A comprehensive Android/Kotlin application for connecting to BLE medical devices and capturing real-time vitals. Designed to integrate with the Sensacare RPM platform and serve as a reliable data capture node for remote patient monitoring.

---

## 🔧 Features

- 🛰️ **BLE Device Discovery**  
  Scans for medical devices using filtered BLE logic with extensible SDK support

- ❤️ **Vitals Monitoring**  
  Real-time heart rate, blood pressure, SpO₂, and temperature with confidence scoring

- 💾 **Room Database**  
  Local storage of vitals for offline access and secure sync queue management

- ☁️ **RPM Integration**  
  Secure transmission of structured health data to Sensacare's RPM backend

- 🔐 **Permission Manager**  
  Automatic handling of BLE + location permission requests with user consent

- 🔄 **Background Service**  
  Foreground service for continuous BLE monitoring even when app is minimized

- 📊 **Live Charting**  
  Real-time vitals trends with MPAndroidChart showing last 24 hours of data

- 🏥 **RPM Status Monitoring**  
  Real-time connection status and alert management from RPM system

- 📋 **Consent Management**  
  Digital consent capture and management for healthcare compliance

- 📈 **Timeline View**  
  Historical vitals data with sync gap detection and visualization

---

## 🧱 Project Configuration

| Property         | Value            |
|------------------|------------------|
| **Target SDK**   | 34               |
| **Min SDK**      | 24               |
| **Language**     | Kotlin           |
| **Build System** | Gradle           |
| **View Binding** | Enabled          |
| **Cursor Ready** | ✅               |

---

## 🧩 Dependencies

- `androidx.lifecycle:lifecycle-livedata-ktx`
- `androidx.room:room-runtime`, `room-compiler`
- `com.squareup.retrofit2:retrofit` & `converter-gson`
- `androidx.navigation:navigation-fragment-ktx`
- `kotlinx.coroutines` for background execution
- `com.github.PhilJay:MPAndroidChart` for vitals visualization
- `androidx.security:security-crypto` for encrypted storage

---

## ⚙️ Permissions Required

Add the following to `AndroidManifest.xml`:

```xml
<uses-permission android:name="android.permission.BLUETOOTH" />
<uses-permission android:name="android.permission.BLUETOOTH_ADMIN" />
<uses-permission android:name="android.permission.BLUETOOTH_CONNECT" />
<uses-permission android:name="android.permission.BLUETOOTH_SCAN" />
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.INTERNET" />
```

---

## 🛠 Cursor Setup Instructions

1. **Open Cursor** → New Project → Import from ZIP
2. **Use** `sensacare-veepoo-ble-full.zip`
3. **Set project type** manually in `.cursor/config.json` if needed:

```json
{
  "project_type": "android",
  "language": "kotlin",
  "build_system": "gradle"
}
```

4. **Paste this Cursor prompt** to wire it all together:

```
Wire VeepooDataManager to emit updates to VitalsDashboardFragment. Store each update in Room via AppDatabase, and call SensacareApiClient.uploadVitals() after each insert. Implement BLE permission request and lifecycle handling.
```

5. **Build and Run** — Connect your Android device and observe vitals updates.

---

## 📲 Usage Flow

1. **Launch app** → VitalsDashboardFragment is shown
2. **Grant permissions** when prompted
3. **RPM Setup** → Configure client credentials and consent
4. **Background service starts** automatically for continuous monitoring
5. **App scans & connects** to nearest medical device

---

## 🧠 Architecture Breakdown

| Component | Role |
|-----------|------|
| `MainActivity.kt` | Entry point, hosts fragments |
| `VitalsDashboardFragment.kt` | Displays real-time vitals |
| `BleForegroundService.kt` | Background BLE monitoring service |
| `VeepooDataManager.kt` | BLE scan/connect/data handler |
| `VitalsData.kt` | Kotlin model for structured vitals |
| `AppDatabase.kt` | Room database for local storage |
| `VitalsDao.kt` | Insert/query vitals |
| `SensacareApiClient.kt` | Retrofit interface to your API |
| `ChartManager.kt` | MPAndroidChart configuration and data updates |

---

## 🔍 Data Flow

```
[ BLE Characteristic Change ]
          ↓
[ VeepooDataManager.parseData() ]
          ↓
[ VitalsData instance ]
          ↓
[ Room: insert via VitalsDao ]
          ↓
[ Retrofit: POST to /vitals/upload ]
          ↓
[ LiveData: UI refresh ]
```

---

## 🧪 Testing Tips

- Run on a **real Android device** (API 24+)
- Ensure **Bluetooth is enabled** and **location is ON**
- Place **Veepoo device into pairing/broadcast mode**
- Watch **Logcat** for connection and sync messages

---

## ⚠️ Dev Notes

- Replace **UUIDs** in `VeepooDataManager` with known values from your device
- Use **Cursor's AI** to help implement characteristic parsing in `onCharacteristicChanged`
- Adjust **SensacareApiClient base URL** to point to staging or prod
- Enhance the **dashboard UI** with real-time graphs or alerts if needed

---

## 📍 Future Enhancements

- Battery & firmware status view
- Advanced alert logic with Sensacare thresholds
- Multi-device pairing
- Enhanced chart customization and analytics

---

## 🔒 License

This module is part of the Sensacare platform and subject to its IP and licensing agreements. 