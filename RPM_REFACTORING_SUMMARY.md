# Sensacare App - RPM Integration Refactoring Summary

## Overview

This document summarizes the comprehensive refactoring of the Sensacare App to transform it from a standalone monitoring application into a clean data capture node for the Sensacare RPM (Remote Patient Monitoring) system.

## Key Architectural Changes

### 1. **RPM-Centric Data Flow**
- **Before**: App handled clinical logic, long-term storage, and alert management
- **After**: App focuses solely on data capture and secure transmission to RPM
- **Benefit**: Clean separation of concerns, RPM handles all clinical logic

### 2. **Device SDK Abstraction Layer**
- **Before**: Tightly coupled to Veepoo SDK only
- **After**: Abstracted device SDK interface supporting multiple device types
- **Benefit**: Extensible for future device support while maintaining Veepoo compatibility

### 3. **Secure Client Context Management**
- **Before**: No client identity or consent management
- **After**: Multi-profile client management with secure credential storage
- **Benefit**: Enterprise-grade security and multi-user support

## New Components Implemented

### Core RPM Components

#### 1. **RPM Data Models** (`rpm/VitalsUploadPayload.kt`)
```kotlin
data class VitalsUploadPayload(
    val timestamp: String,        // ISO 8601 with timezone
    val clientId: String,         // RPM client identifier
    val device: DeviceMetadata,   // Device information
    val vitals: VitalsData,       // Vital signs data
    val confidence: Double,       // Data quality score
    val consentToken: String      // Consent validation
)
```

#### 2. **RPM API Service** (`rpm/RPMApiService.kt`)
- Complete REST API interface for RPM communication
- Endpoints for vitals upload, alerts, device status, thresholds
- Proper error handling and response types

#### 3. **RPM Client Context Manager** (`rpm/RPMClientContextManager.kt`)
- Secure storage of client credentials using EncryptedSharedPreferences
- Multi-profile support for carers with multiple patients
- Dynamic threshold configuration from RPM
- Consent token management

#### 4. **Vitals Payload Builder** (`rpm/VitalsPayloadBuilder.kt`)
- Constructs RPM-compliant JSON payloads
- Calculates confidence scores based on signal quality
- Validates vitals data before upload
- Includes device metadata and timestamps

### Data Management Components

#### 5. **Sync Queue System** (`rpm/SyncQueueEntity.kt`, `rpm/SyncQueueDao.kt`)
- Local storage for failed uploads
- Automatic retry with exponential backoff
- Priority-based queue management
- Cleanup of old items

#### 6. **RPM API Manager** (`rpm/RPMApiManager.kt`)
- Centralized API communication
- Authentication and retry interceptors
- Heartbeat reporting every 15 minutes
- Queue processing and sync gap reporting

### Device Abstraction Layer

#### 7. **Device SDK Manager** (`device/DeviceSDKManager.kt`)
- Abstract interface for different device SDKs
- State management for device connections
- Metadata tracking and capabilities reporting

#### 8. **Veepoo SDK Adapter** (`device/VeepooSDKAdapter.kt`)
- Wraps existing Veepoo SDK functionality
- Maintains backward compatibility
- Implements DeviceSDK interface

#### 9. **Generic BLE SDK Adapter** (`device/GenericBLESDKAdapter.kt`)
- Template for future device SDK implementations
- Extensible architecture for other device types
- Placeholder for generic BLE device support

### Security & Compliance

#### 10. **Consent Manager** (`rpm/ConsentManager.kt`)
- Digital signature capture
- Consent token generation and validation
- RPM policy compliance
- Audit trail support

#### 11. **Vitals Status Evaluator** (`rpm/VitalsStatusEvaluator.kt`)
- Non-clinical device-side status checking
- Sensor error detection
- Data quality assessment
- Connection health monitoring

### Integration & Orchestration

#### 12. **RPM Integration Manager** (`rpm/RPMIntegrationManager.kt`)
- Central orchestrator for all RPM functionality
- Unified interface for app integration
- Background sync and queue processing
- Alert polling and timeline management

## Database Schema Updates

### New Tables Added
```sql
-- Sync queue for failed uploads
CREATE TABLE sync_queue (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    payload TEXT NOT NULL,           -- JSON serialized VitalsUploadPayload
    timestamp INTEGER NOT NULL,      -- When the item was queued
    retry_count INTEGER DEFAULT 0,   -- Number of retry attempts
    last_attempt INTEGER DEFAULT 0,  -- Last retry timestamp
    error_message TEXT,              -- Error details
    priority INTEGER DEFAULT 1       -- Queue priority (1=normal, 2=high, 3=critical)
);
```

### Database Version Update
- Updated `AppDatabase.kt` to version 2
- Added `SyncQueueDao` to database interface
- Implemented destructive migration for development

## API Integration

### RPM Endpoints Implemented
```kotlin
// Vitals upload
POST /clients/{clientId}/vitals

// Recent alerts
GET /clients/{clientId}/alerts/recent

// Alert acknowledgment
POST /clients/{clientId}/alerts/{alertId}/acknowledge

// Device status heartbeat
POST /clients/{clientId}/device-status

// Client thresholds
GET /clients/{clientId}/thresholds

// Vitals timeline
GET /clients/{clientId}/vitals?range=24h

// Sync gap reporting
POST /clients/{clientId}/sync-gaps
```

### Authentication & Security
- Bearer token authentication
- Encrypted credential storage
- Consent token validation
- Secure API communication

## Key Features Implemented

### 1. **Clean Data Ingestion**
- ✅ ISO 8601 timestamps with timezone
- ✅ Device metadata (MAC, firmware, battery)
- ✅ Confidence rating per reading
- ✅ Offline buffering with retry logic
- ✅ Structured source field tagging

### 2. **Carer Context & Multi-Profile Support**
- ✅ Secure client ID and user token storage
- ✅ Multi-profile support for carers
- ✅ Dynamic thresholds from RPM
- ✅ Profile switching and management

### 3. **Minimal On-Device Alerts**
- ✅ Non-clinical status evaluation
- ✅ Device-side error detection
- ✅ Connection health monitoring
- ✅ Data quality indicators

### 4. **Structured Upload to RPM**
- ✅ RPM-compliant JSON payloads
- ✅ Device metadata inclusion
- ✅ Confidence scoring
- ✅ Consent token validation

### 5. **Security & Compliance**
- ✅ Consent capture and validation
- ✅ Encrypted credential storage
- ✅ Audit trail support
- ✅ RPM policy compliance

### 6. **Device Sync Health Reporting**
- ✅ Heartbeat reporting every 15 minutes
- ✅ Sync gap detection and reporting
- ✅ Device status monitoring
- ✅ Connection health tracking

## Device SDK Extensibility

### Current Support
- **Veepoo SDK**: Fully implemented with adapter pattern
- **Generic BLE**: Template implementation for future devices

### Future Device Support
The abstraction layer allows easy addition of new device SDKs:
1. Implement `DeviceSDK` interface
2. Add device type to `DeviceSDKManager`
3. Configure device-specific capabilities
4. Update payload builder for device metadata

## Integration Points

### Existing Components Updated
- **AppDatabase**: Added sync queue table
- **VeepooDataManager**: Wrapped by VeepooSDKAdapter
- **VitalsDao**: Enhanced with date range queries

### New Integration Points
- **RPMIntegrationManager**: Main entry point for RPM functionality
- **DeviceSDKManager**: Device abstraction layer
- **ConsentManager**: Consent capture and validation
- **VitalsStatusEvaluator**: Device-side status checking

## Benefits Achieved

### 1. **Clean Architecture**
- Clear separation between data capture and clinical logic
- Modular design for easy maintenance and extension
- Abstraction layers for device independence

### 2. **Enterprise Security**
- Encrypted credential storage
- Consent management and validation
- Secure API communication
- Audit trail support

### 3. **Reliability**
- Offline data buffering
- Automatic retry with exponential backoff
- Sync gap detection and reporting
- Heartbeat monitoring

### 4. **Extensibility**
- Device SDK abstraction layer
- Multi-profile support
- Configurable thresholds
- Future device support ready

### 5. **RPM Compliance**
- Structured data payloads
- ISO 8601 timestamps
- Device metadata tracking
- Consent token validation

## Next Steps

### Phase 2 Implementation
1. **UI Updates**: Update dashboard for RPM alerts and status
2. **Timeline View**: Implement vitals timeline fragment
3. **Consent Screens**: Add consent capture UI
4. **Multi-Profile UI**: Profile switching interface

### Phase 3 Testing
1. **Integration Testing**: End-to-end RPM communication
2. **Offline Testing**: Queue processing and retry logic
3. **Security Testing**: Credential storage and API security
4. **Performance Testing**: Sync performance and reliability

### Phase 4 Cleanup
1. **Remove Legacy Components**: Delete unused clinical logic
2. **Update Documentation**: Complete API documentation
3. **Production Migration**: Implement proper database migrations
4. **Monitoring**: Add comprehensive logging and monitoring

## Conclusion

The RPM refactoring successfully transforms the Sensacare App into a focused, reliable data capture node for the Sensacare RPM system. The implementation maintains backward compatibility with the existing Veepoo SDK while providing a clean, extensible architecture for future device support.

Key achievements:
- ✅ **Clean Data Flow**: BLE → Validation → RPM Upload
- ✅ **Device Independence**: Abstracted SDK layer
- ✅ **Enterprise Security**: Encrypted storage and consent management
- ✅ **Reliability**: Offline buffering and retry logic
- ✅ **RPM Compliance**: Structured payloads and metadata

The app now excels at its core mission: **"Capture Clean, Hand Off Fast"** - ensuring that RPM receives high-quality, structured, and reliable data while maintaining minimal local complexity and maximum security compliance. 