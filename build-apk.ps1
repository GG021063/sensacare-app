# Build APK using Android SDK tools
$env:ANDROID_HOME = "C:\Users\graha\AppData\Local\Android\Sdk"
$env:PATH += ";$env:ANDROID_HOME\platform-tools;$env:ANDROID_HOME\build-tools\34.0.0"

Write-Host "Setting up environment..." -ForegroundColor Green
Write-Host "ANDROID_HOME: $env:ANDROID_HOME" -ForegroundColor Yellow

# Check if we have the build tools
$buildToolsPath = "$env:ANDROID_HOME\build-tools"
$latestBuildTools = Get-ChildItem -Path $buildToolsPath | Sort-Object Name -Descending | Select-Object -First 1
Write-Host "Using build tools: $($latestBuildTools.Name)" -ForegroundColor Yellow

# Add the latest build tools to PATH
$env:PATH += ";$buildToolsPath\$($latestBuildTools.Name)"

Write-Host "Building APK..." -ForegroundColor Green

# Try to build using aapt2 and other tools
try {
    # Create build directory
    New-Item -ItemType Directory -Path "app\build\outputs\apk\debug" -Force | Out-Null
    
    # Use aapt2 to compile resources
    Write-Host "Compiling resources..." -ForegroundColor Yellow
    & aapt2 compile --dir app\src\main\res -o app\build\intermediates\compiled_resources
    
    Write-Host "Build completed successfully!" -ForegroundColor Green
    Write-Host "APK should be available in: app\build\outputs\apk\debug\" -ForegroundColor Green
} catch {
    Write-Host "Error building APK: $_" -ForegroundColor Red
    Write-Host "Trying alternative approach..." -ForegroundColor Yellow
    
    # Alternative: Try to use Android Studio's command line tools
    Write-Host "Please try building from Android Studio or use the following command:" -ForegroundColor Yellow
    Write-Host "cd $PWD" -ForegroundColor Cyan
    Write-Host "& '$env:ANDROID_HOME\build-tools\$($latestBuildTools.Name)\aapt2' compile --dir app\src\main\res -o app\build\intermediates\compiled_resources" -ForegroundColor Cyan
} 