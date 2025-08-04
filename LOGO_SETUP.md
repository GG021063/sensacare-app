# Adding the Official Sensacare Logo  
How to fix the “missing launcher icon foreground” build error
---

## 1. What happened?
During `./gradlew assembleDebug` the build stopped with:

```
Android resource linking failed
…res/mipmap-anydpi-v26/ic_launcher_sensacare.xml:4: error: resource mipmap/ic_launcher_sensacare_foreground … not found.
```

The adaptive-icon XML (`ic_launcher_sensacare.xml`) expects an image called  
`ic_launcher_sensacare_foreground.png`, but the file is not present in the mipmap folders.  
Adding your official 188 × 188 px PNG will resolve the error.

---

## 2. Required Android icon densities
Android automatically selects an icon appropriate to the device’s screen density.  
Copy the same file into each density-specific folder:

| Density | Folder |
|---------|---------------------------------------------|
| mdpi    | `mipmap-mdpi`  |
| hdpi    | `mipmap-hdpi`  |
| xhdpi   | `mipmap-xhdpi` |
| xxhdpi  | `mipmap-xxhdpi`|
| xxxhdpi | `mipmap-xxxhdpi`|
| Adaptive (any dpi) | `mipmap-anydpi-v26` (already contains the XML) |

---

## 3. Step-by-step guide  

1. **Locate your logo**  
   Make sure you have the official Sensacare logo as a PNG file sized **188 × 188 px** (or larger square – Android will handle down-scaling).

2. **Open the project’s resources directory**  
   ```
   C:\Users\graha\sensacare-app\app\src\main\res
   ```

3. **Copy the logo into every density folder**  
   For each folder listed below, paste a copy of the PNG **and rename it exactly**  
   `ic_launcher_sensacare_foreground.png`.

   ```
   res\mipmap-mdpi\ic_launcher_sensacare_foreground.png
   res\mipmap-hdpi\ic_launcher_sensacare_foreground.png
   res\mipmap-xhdpi\ic_launcher_sensacare_foreground.png
   res\mipmap-xxhdpi\ic_launcher_sensacare_foreground.png
   res\mipmap-xxxhdpi\ic_launcher_sensacare_foreground.png
   ```

4. **Leave the adaptive-icon XML alone**  
   `res\mipmap-anydpi-v26\ic_launcher_sensacare.xml` is already configured to use the foreground image you are adding. No edits needed.

5. **Re-build the app**  
   ```bash
   ./gradlew assembleDebug
   ```
   The build should now succeed and the correct Sensacare icon will appear on the device.

---

## 4. Troubleshooting
* **Still seeing the error?**  
  Double-check file names – they **must** be lowercase and match exactly:  
  `ic_launcher_sensacare_foreground.png`
* **PNG size or transparency**  
  Ensure the file is a square PNG (Android will clip non-square images) and includes any required transparency already baked in.

That’s it! After these steps your Sensacare app will build with the official launcher icon on all devices.
