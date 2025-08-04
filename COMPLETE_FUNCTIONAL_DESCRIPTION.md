# Sensacare App - Complete Functional Description

## Overview

The **Sensacare App** is a comprehensive Android application designed as a **Smart Data Capture Node** for the Sensacare Remote Patient Monitoring (RPM) system. The app serves as a bridge between BLE (Bluetooth Low Energy) medical devices and the Sensacare RPM backend, providing real-time vitals monitoring, secure data transmission, and user-friendly interfaces for healthcare professionals and caregivers.

## Core Architecture

### **RPM-Centric Design**
- **Smart Capture Node**: Focuses on clean data ingestion and transmission to RPM
- **Clinical Logic Separation**: All clinical decision-making resides in the RPM backend
- **Device Extensibility**: Supports multiple BLE device types through abstracted SDK layer
- **Secure Data Flow**: End-to-end encrypted communication with RPM system

### **Key Components**
- **Device SDK Manager**: Abstracted interface for multiple BLE device types
- **RPM Integration Manager**: Central orchestrator for all RPM operations
- **Secure Context Management**: Encrypted storage for sensitive client data
- **Offline Sync Queue**: Robust data buffering for network interruptions
- **Comprehensive Logging**: Production-ready monitoring and debugging

## Primary Features

### 1. **BLE Device Management**

#### **Device Discovery & Connection**
- **Automatic Scanning**: Continuous BLE device discovery in background
- **Device Filtering**: Smart filtering for supported medical devices
- **Connection Management**: Robust connection handling with auto-reconnect
- **Multi-Device Support**: Framework ready for various BLE device types

#### **Current Device Support**
- **Veepoo Ring**: Primary supported device with full SDK integration
- **Generic BLE Devices**: Template implementation for future device support
- **Device Abstraction**: Clean interface for adding new device types

#### **Connection Features**
- **Persistent Connection**: Background service maintains device connection
- **Connection Status**: Real-time connection status with visual indicators
- **Auto-Reconnect**: Automatic reconnection on connection loss
- **Connection Health**: Continuous monitoring of connection quality

### 2. **Vitals Data Capture**

#### **Real-Time Monitoring**
- **Continuous Data Stream**: Real-time vitals data from connected devices
- **Data Validation**: On-device sanity checks for data quality
- **Confidence Scoring**: Automatic confidence rating for each data point
- **Timestamp Precision**: ISO 8601 formatted timestamps for all data

#### **Supported Vitals**
- **Heart Rate**: Real-time heart rate monitoring (bpm)
- **Blood Oxygen (SpO2)**: Oxygen saturation levels (%)
- **Blood Pressure**: Systolic and diastolic pressure (mmHg)
- **Temperature**: Body temperature monitoring (°C)
- **Battery Level**: Device battery status monitoring
- **Signal Strength**: BLE signal quality indicators

#### **Data Quality Features**
- **Sensor Error Detection**: Automatic detection of sensor malfunctions
- **Data Completeness**: Validation of required vitals fields
- **Freshness Checking**: Detection of stale or outdated data
- **Signal Quality**: RSSI-based signal strength assessment

### 3. **RPM Integration**

#### **Secure Data Transmission**
- **Encrypted Payloads**: All data encrypted before transmission
- **Bearer Token Authentication**: Secure API authentication
- **Retry Logic**: Exponential backoff for failed transmissions
- **Offline Queuing**: Local storage for network interruptions

#### **API Endpoints**
- **Vitals Upload**: `POST /clients/{id}/vitals` - Upload vitals data
- **Alerts Polling**: `GET /clients/{id}/alerts/recent` - Fetch RPM alerts
- **Device Status**: `POST /clients/{id}/device-status` - Report device health
- **Thresholds**: `GET /clients/{id}/thresholds` - Fetch client thresholds
- **Timeline Data**: `GET /clients/{id}/vitals` - Fetch historical data
- **Sync Gaps**: `POST /clients/{id}/sync-gaps` - Report data gaps

#### **Data Flow**
1. **Capture**: Vitals data captured from BLE device
2. **Validate**: On-device validation and confidence scoring
3. **Queue**: Data queued for transmission (with offline buffering)
4. **Transmit**: Secure transmission to RPM backend
5. **Confirm**: Transmission confirmation and error handling

### 4. **User Interface**

#### **Main Dashboard**
- **RPM Status Display**: Real-time RPM connection status
- **Vitals Status**: Device-side vitals monitoring status
- **Current Vitals**: Live display of latest vitals data
- **RPM Alerts**: Display of alerts from RPM system
- **Connection Banner**: Persistent connection status indicator

#### **Navigation Features**
- **Timeline View**: Historical vitals data with sync gaps
- **Consent Management**: Digital consent capture and management
- **RPM Setup**: Client configuration and initialization
- **Diagnostics**: System health and troubleshooting
- **Service Controls**: Background service management

#### **Status Indicators**
- **RPM Status**: Not Initialized, Initializing, Ready, Monitoring, Error, Disconnected
- **Vitals Status**: Normal, No Data, Critical Error, BLE Error
- **Connection Status**: Connected, Connecting, Disconnected, Error, Reconnecting
- **Alert Severity**: Critical (Red), Warning (Yellow), Info (Blue)

### 5. **Consent Management**

#### **Digital Consent Capture**
- **Client Information**: Client ID, name, and carer details
- **Consent Types**: Digital Signature, Verbal Confirmation, Carer Proxy
- **Policy Display**: Complete RPM consent policy text
- **Token Generation**: Unique consent tokens for audit trail

#### **Consent Features**
- **Multi-Client Support**: Multiple client profiles management
- **Consent Validation**: Token-based consent verification
- **Audit Trail**: Complete consent history and timestamps
- **Revocation Support**: Consent revocation and management

#### **Compliance Features**
- **GDPR Compliance**: Privacy and data protection compliance
- **HIPAA Compliance**: Healthcare data protection standards
- **Audit Logging**: Complete audit trail for compliance
- **Data Minimization**: Only necessary data collection

### 6. **Timeline & Historical Data**

#### **Vitals Timeline View**
- **Time Range Selection**: 1h, 6h, 12h, 24h, 48h, 7d ranges
- **Status Indicators**: Synced (✅), Gap (⏸️), Alert (⚠️) entries
- **Data Visualization**: Color-coded timeline entries
- **Summary Statistics**: Total, synced, gaps, and alerts count

#### **Performance Features**
- **Pagination**: 50 entries per page for large datasets
- **Caching**: 5-minute cache with automatic cleanup
- **Preloading**: Background data preloading for smooth UX
- **Compression**: Data compression for storage efficiency

#### **Sync Gap Detection**
- **Gap Identification**: Automatic detection of missing data periods
- **Gap Reporting**: Automatic reporting of sync gaps to RPM
- **Gap Visualization**: Clear visual indicators for data gaps
- **Gap Resolution**: Support for gap resolution workflows

### 7. **Alert Management**

#### **RPM-Based Alerts**
- **Alert Polling**: Regular polling for RPM-generated alerts
- **Alert Display**: Real-time alert display with severity colors
- **Alert Acknowledgment**: User acknowledgment of alerts
- **Alert History**: Historical alert tracking and management

#### **Device-Side Alerts**
- **Critical Errors**: Sensor malfunctions and device errors
- **Connection Issues**: BLE connection problems
- **Data Quality**: Poor signal quality or incomplete data
- **Battery Warnings**: Low device battery notifications

#### **Alert Features**
- **Severity Levels**: Critical, Warning, Info classifications
- **Visual Indicators**: Color-coded alert displays
- **Action Recommendations**: Suggested actions for each alert
- **Alert Escalation**: Automatic escalation for critical alerts

### 8. **Background Services**

#### **BLE Foreground Service**
- **Continuous Monitoring**: 24/7 device monitoring
- **Battery Optimization**: Efficient battery usage
- **Notification Management**: Persistent notification for service status
- **Service Controls**: Start/stop service management

#### **Sync Queue Processing**
- **Offline Buffering**: Local storage for network interruptions
- **Retry Logic**: Exponential backoff for failed uploads
- **Queue Management**: Priority-based queue processing
- **Queue Statistics**: Real-time queue status and metrics

#### **Heartbeat Reporting**
- **Device Status**: Regular device health reporting to RPM
- **Connection Health**: Continuous connection quality monitoring
- **Performance Metrics**: Response time and throughput tracking
- **Error Reporting**: Automatic error reporting and diagnostics

### 9. **Security & Privacy**

#### **Data Protection**
- **Encrypted Storage**: All sensitive data encrypted at rest
- **Secure Communication**: HTTPS for all API communications
- **Token Management**: Secure token storage and rotation
- **Access Control**: Proper permission handling and validation

#### **Privacy Features**
- **Consent Management**: Structured consent capture and validation
- **Data Minimization**: Only necessary data collection
- **User Control**: User can clear data and revoke consent
- **Audit Trail**: Complete audit trail for compliance

#### **Security Measures**
- **Encrypted SharedPreferences**: Secure storage for sensitive data
- **Certificate Pinning**: API certificate validation
- **Input Validation**: Comprehensive input sanitization
- **Error Handling**: Secure error handling without data leakage

### 10. **Diagnostics & Monitoring**

#### **System Diagnostics**
- **Connection Diagnostics**: BLE connection health monitoring
- **API Diagnostics**: RPM API communication status
- **Device Diagnostics**: Connected device health and status
- **Performance Metrics**: Response times and throughput

#### **Logging System**
- **Structured Logging**: Categorized log entries for different components
- **File Logging**: Daily log files with automatic rotation
- **Log Categories**: RPM_INIT, VITALS_PROCESSING, API_COMMUNICATION, etc.
- **Debug Mode**: Configurable debug logging for development

#### **Performance Monitoring**
- **Response Times**: API call response time tracking
- **Memory Usage**: Application memory usage monitoring
- **Cache Performance**: Timeline cache hit/miss ratios
- **Network Efficiency**: Data transmission efficiency metrics

### 11. **Multi-Profile Support**

#### **Client Profiles**
- **Multiple Clients**: Support for multiple client profiles
- **Profile Switching**: Easy switching between client profiles
- **Profile Management**: Add, remove, and edit client profiles
- **Profile Isolation**: Secure isolation between client data

#### **Profile Features**
- **Client Context**: Client-specific settings and preferences
- **Threshold Management**: Client-specific vitals thresholds
- **Alert Preferences**: Client-specific alert configurations
- **Data Segregation**: Complete data isolation between clients

### 12. **Offline Capabilities**

#### **Offline Data Storage**
- **Local Database**: Room database for offline data storage
- **Sync Queue**: Offline queue for failed API calls
- **Data Persistence**: Persistent storage of vitals and settings
- **Cache Management**: Intelligent cache management for offline access

#### **Offline Features**
- **Data Collection**: Continuous data collection when offline
- **Queue Processing**: Automatic queue processing when online
- **Conflict Resolution**: Smart conflict resolution for data conflicts
- **Sync Status**: Clear indication of sync status and progress

## Technical Specifications

### **Platform Requirements**
- **Android Version**: API 24+ (Android 7.0+)
- **Target SDK**: API 34 (Android 14)
- **Minimum RAM**: 2GB recommended
- **Storage**: 100MB+ available storage
- **Bluetooth**: BLE 4.0+ support required

### **Performance Characteristics**
- **Startup Time**: < 3 seconds cold start
- **Memory Usage**: < 150MB typical usage
- **Battery Impact**: < 5% additional battery usage
- **Network Usage**: Optimized for minimal data usage

### **Security Standards**
- **Encryption**: AES-256 encryption for data at rest
- **Transport**: TLS 1.3 for data in transit
- **Authentication**: Bearer token with automatic refresh
- **Compliance**: GDPR, HIPAA, and healthcare data standards

## User Workflows

### **Initial Setup**
1. **App Installation**: Install and launch application
2. **Permissions**: Grant BLE and location permissions
3. **RPM Setup**: Configure RPM client credentials
4. **Device Pairing**: Pair with BLE medical device
5. **Consent Capture**: Complete digital consent process
6. **Service Start**: Start background monitoring service

### **Daily Operation**
1. **Service Monitoring**: Background service maintains device connection
2. **Data Collection**: Continuous vitals data collection
3. **Data Transmission**: Automatic secure transmission to RPM
4. **Alert Monitoring**: Real-time alert monitoring and display
5. **Status Checking**: Regular status checks and health monitoring

### **Troubleshooting**
1. **Diagnostic Tools**: Built-in diagnostic tools for troubleshooting
2. **Log Analysis**: Comprehensive logging for issue identification
3. **Connection Recovery**: Automatic connection recovery mechanisms
4. **Error Reporting**: Automatic error reporting to support team

## Integration Points

### **RPM Backend Integration**
- **RESTful APIs**: Standard REST API communication
- **Real-time Updates**: WebSocket support for real-time updates
- **Batch Processing**: Efficient batch data processing
- **Error Handling**: Comprehensive error handling and recovery

### **Device SDK Integration**
- **Veepoo SDK**: Full integration with Veepoo device SDK
- **Generic BLE**: Framework for additional BLE device support
- **SDK Abstraction**: Clean abstraction layer for device management
- **Extensibility**: Easy addition of new device types

### **Healthcare System Integration**
- **HL7 FHIR**: Support for healthcare data standards
- **EHR Integration**: Electronic Health Record system integration
- **Care Team Communication**: Integration with care team workflows
- **Clinical Decision Support**: Integration with clinical decision systems

## Future Enhancements

### **Planned Features**
- **Additional Device Support**: Support for more BLE medical devices
- **Advanced Analytics**: Enhanced reporting and analytics capabilities
- **Mobile Companion App**: Companion mobile application
- **Web Dashboard**: Web-based dashboard for remote monitoring

### **Scalability Features**
- **Cloud Integration**: Enhanced cloud integration capabilities
- **Multi-Platform Support**: iOS and web platform support
- **Advanced Security**: Enhanced security and compliance features
- **AI Integration**: Machine learning for predictive analytics

## Conclusion

The Sensacare App represents a comprehensive, production-ready solution for remote patient monitoring data capture. With its robust architecture, extensive feature set, and focus on security and compliance, the application provides a solid foundation for healthcare providers to implement effective remote patient monitoring solutions.

The modular design and extensible architecture ensure that the application can evolve with changing healthcare needs while maintaining the highest standards of security, reliability, and user experience. 