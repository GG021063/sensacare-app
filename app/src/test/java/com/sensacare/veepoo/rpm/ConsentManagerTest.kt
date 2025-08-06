package com.sensacare.veepoo.rpm

import android.content.Context
import io.mockk.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ConsentManagerTest {

    @Mock
    private lateinit var mockContext: Context

    private lateinit var consentManager: ConsentManager

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        consentManager = ConsentManager(mockContext)
    }

    @Test
    fun `test consent capture with valid data`() {
        // Given
        val clientId = "test_client_123"
        val clientName = "Test Client"
        val carerName = "Test Carer"
        val consentType = ConsentType.DIGITAL_SIGNATURE

        // When
        val result = consentManager.captureConsent(clientId, clientName, carerName, consentType)

        // Then
        assertTrue("Consent capture should succeed", result is ConsentResult.Success)
        
        val successResult = result as ConsentResult.Success
        val consentRecord = successResult.consentRecord
        
        assertEquals("Client ID should match", clientId, consentRecord.clientId)
        assertEquals("Client name should match", clientName, consentRecord.clientName)
        assertEquals("Carer name should match", carerName, consentRecord.carerName)
        assertEquals("Consent type should match", consentType, consentRecord.consentType)
        assertNotNull("Consent token should not be null", consentRecord.consentToken)
        assertTrue("Consent timestamp should be recent", consentRecord.timestamp > 0)
    }

    @Test
    fun `test consent capture with empty client ID`() {
        // Given
        val clientId = ""
        val clientName = "Test Client"
        val consentType = ConsentType.DIGITAL_SIGNATURE

        // When
        val result = consentManager.captureConsent(clientId, clientName, consentType = consentType)

        // Then
        assertTrue("Consent capture should fail with empty client ID", result is ConsentResult.Failure)
        
        val failureResult = result as ConsentResult.Failure
        assertTrue("Error message should contain client ID validation", 
            failureResult.errorMessage.contains("client ID"))
    }

    @Test
    fun `test consent capture with empty client name`() {
        // Given
        val clientId = "test_client_123"
        val clientName = ""
        val consentType = ConsentType.DIGITAL_SIGNATURE

        // When
        val result = consentManager.captureConsent(clientId, clientName, consentType = consentType)

        // Then
        assertTrue("Consent capture should fail with empty client name", result is ConsentResult.Failure)
        
        val failureResult = result as ConsentResult.Failure
        assertTrue("Error message should contain client name validation", 
            failureResult.errorMessage.contains("client name"))
    }

    @Test
    fun `test consent validation with valid token`() {
        // Given
        val clientId = "test_client_123"
        val clientName = "Test Client"
        val consentType = ConsentType.DIGITAL_SIGNATURE
        
        val captureResult = consentManager.captureConsent(clientId, clientName, consentType = consentType)
        val consentRecord = (captureResult as ConsentResult.Success).consentRecord

        // When
        val isValid = consentManager.validateConsent(consentRecord.consentToken)

        // Then
        assertTrue("Consent token should be valid", isValid)
    }

    @Test
    fun `test consent validation with invalid token`() {
        // Given
        val invalidToken = "invalid_token_123"

        // When
        val isValid = consentManager.validateConsent(invalidToken)

        // Then
        assertFalse("Invalid consent token should not be valid", isValid)
    }

    @Test
    fun `test consent validation with empty token`() {
        // Given
        val emptyToken = ""

        // When
        val isValid = consentManager.validateConsent(emptyToken)

        // Then
        assertFalse("Empty consent token should not be valid", isValid)
    }

    @Test
    fun `test include consent in payload`() {
        // Given
        val payload = VitalsUploadPayload(
            timestamp = "2024-01-01T12:00:00Z",
            clientId = "test_client_123",
            device = DeviceMetadata(
                type = "veepoo_ring",
                mac = "AA:BB:CC:DD:EE:FF",
                firmware = "1.0.0",
                battery = 85
            ),
            vitals = VitalsData(
                heartRate = 75,
                spo2 = 98,
                bloodPressureSystolic = 120,
                bloodPressureDiastolic = 80,
                temperature = 36.8f
            ),
            confidence = 0.95,
            consentToken = "original_token"
        )
        val newConsentToken = "new_consent_token_456"

        // When
        val updatedPayload = consentManager.includeConsentInPayload(payload, newConsentToken)

        // Then
        assertEquals("Consent token should be updated", newConsentToken, updatedPayload.consentToken)
        assertEquals("Other fields should remain unchanged", payload.timestamp, updatedPayload.timestamp)
        assertEquals("Client ID should remain unchanged", payload.clientId, updatedPayload.clientId)
    }

    @Test
    fun `test consent policy text`() {
        // When
        val policyText = consentManager.getConsentPolicyText()

        // Then
        assertNotNull("Policy text should not be null", policyText)
        assertTrue("Policy text should not be empty", policyText.isNotEmpty())
        assertTrue("Policy text should contain RPM information", 
            policyText.contains("Remote Patient Monitoring"))
    }

    @Test
    fun `test consent summary generation`() {
        // Given
        val consentRecord = ConsentRecord(
            consentId = "consent_123",
            clientId = "test_client_123",
            clientName = "Test Client",
            carerName = "Test Carer",
            consentType = ConsentType.DIGITAL_SIGNATURE,
            consentToken = "token_456",
            timestamp = System.currentTimeMillis(),
            version = "1.0"
        )

        // When
        val summary = consentManager.getConsentSummary(consentRecord)

        // Then
        assertNotNull("Consent summary should not be null", summary)
        assertTrue("Summary should contain client name", summary.contains("Test Client"))
        assertTrue("Summary should contain consent type", summary.contains("Digital Signature"))
        assertTrue("Summary should contain timestamp", summary.contains("2024"))
    }

    @Test
    fun `test different consent types`() {
        // Test DIGITAL_SIGNATURE
        val digitalResult = consentManager.captureConsent(
            "client_1", "Client 1", consentType = ConsentType.DIGITAL_SIGNATURE
        )
        assertTrue("Digital signature consent should succeed", digitalResult is ConsentResult.Success)

        // Test VERBAL_CONFIRMATION
        val verbalResult = consentManager.captureConsent(
            "client_2", "Client 2", consentType = ConsentType.VERBAL_CONFIRMATION
        )
        assertTrue("Verbal confirmation consent should succeed", verbalResult is ConsentResult.Success)

        // Test CARER_PROXY
        val proxyResult = consentManager.captureConsent(
            "client_3", "Client 3", consentType = ConsentType.CARER_PROXY
        )
        assertTrue("Carer proxy consent should succeed", proxyResult is ConsentResult.Success)
    }

    @Test
    fun `test consent token uniqueness`() {
        // Given
        val clientId = "test_client_123"
        val clientName = "Test Client"

        // When
        val result1 = consentManager.captureConsent(clientId, clientName, consentType = ConsentType.DIGITAL_SIGNATURE)
        val result2 = consentManager.captureConsent(clientId, clientName, consentType = ConsentType.DIGITAL_SIGNATURE)

        // Then
        assertTrue("Both consent captures should succeed", 
            result1 is ConsentResult.Success && result2 is ConsentResult.Success)
        
        val token1 = (result1 as ConsentResult.Success).consentRecord.consentToken
        val token2 = (result2 as ConsentResult.Success).consentRecord.consentToken
        
        assertNotEquals("Consent tokens should be unique", token1, token2)
    }

    @Test
    fun `test consent timestamp accuracy`() {
        // Given
        val clientId = "test_client_123"
        val clientName = "Test Client"
        val beforeCapture = System.currentTimeMillis()

        // When
        val result = consentManager.captureConsent(clientId, clientName, consentType = ConsentType.DIGITAL_SIGNATURE)
        val afterCapture = System.currentTimeMillis()

        // Then
        assertTrue("Consent capture should succeed", result is ConsentResult.Success)
        
        val consentRecord = (result as ConsentResult.Success).consentRecord
        assertTrue("Consent timestamp should be after before capture", consentRecord.timestamp >= beforeCapture)
        assertTrue("Consent timestamp should be before after capture", consentRecord.timestamp <= afterCapture)
    }
} 