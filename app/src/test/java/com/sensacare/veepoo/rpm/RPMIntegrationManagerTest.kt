package com.sensacare.veepoo.rpm

import android.content.Context
import com.sensacare.veepoo.VitalsData
import com.sensacare.veepoo.VitalsEntity
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
class RPMIntegrationManagerTest {

    @Mock
    private lateinit var mockContext: Context

    private lateinit var rpmIntegrationManager: RPMIntegrationManager
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        
        // Mock the database and other dependencies
        rpmIntegrationManager = RPMIntegrationManager(mockContext)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test RPM initialization with valid credentials`() = runTest {
        // Given
        val clientId = "test_client_123"
        val userToken = "test_token_456"

        // When
        val result = rpmIntegrationManager.initializeRPM(clientId, userToken)

        // Then
        assertTrue("RPM should initialize successfully", result)
        assertEquals(RPMStatus.READY, rpmIntegrationManager.rpmStatus.value)
    }

    @Test
    fun `test RPM initialization with invalid credentials`() = runTest {
        // Given
        val clientId = ""
        val userToken = ""

        // When
        val result = rpmIntegrationManager.initializeRPM(clientId, userToken)

        // Then
        assertFalse("RPM should fail to initialize with invalid credentials", result)
        assertEquals(RPMStatus.ERROR, rpmIntegrationManager.rpmStatus.value)
    }

    @Test
    fun `test vitals data processing`() = runTest {
        // Given
        val vitalsData = VitalsData(
            heartRate = 75,
            spo2 = 98,
            bloodPressureSystolic = 120,
            bloodPressureDiastolic = 80,
            temperature = 36.8f
        )

        // When
        val result = rpmIntegrationManager.processVitalsData(vitalsData)

        // Then
        assertTrue("Vitals data should be processed successfully", result)
        assertEquals(VitalsStatus.NORMAL, rpmIntegrationManager.currentVitalsStatus.value)
    }

    @Test
    fun `test vitals data processing with invalid data`() = runTest {
        // Given
        val vitalsData = VitalsData(
            heartRate = 0, // Invalid heart rate
            spo2 = 98,
            bloodPressureSystolic = 120,
            bloodPressureDiastolic = 80,
            temperature = 36.8f
        )

        // When
        val result = rpmIntegrationManager.processVitalsData(vitalsData)

        // Then
        assertFalse("Vitals data processing should fail with invalid data", result)
        assertEquals(VitalsStatus.CRITICAL_ERROR, rpmIntegrationManager.currentVitalsStatus.value)
    }

    @Test
    fun `test RPM setup with consent`() = runTest {
        // Given
        val clientId = "test_client_123"
        val clientName = "Test Client"
        val userToken = "test_token_456"

        // When
        val result = rpmIntegrationManager.setupWithConsent(clientId, clientName, userToken)

        // Then
        assertTrue("RPM setup with consent should succeed", result)
        assertEquals(RPMStatus.READY, rpmIntegrationManager.rpmStatus.value)
    }

    @Test
    fun `test alerts refresh`() = runTest {
        // Given
        rpmIntegrationManager.initializeRPM("test_client", "test_token")

        // When
        val result = rpmIntegrationManager.refreshAlerts()

        // Then
        assertTrue("Alerts refresh should succeed", result)
        // Verify that alerts are loaded (empty list is expected in test environment)
        assertNotNull(rpmIntegrationManager.recentAlerts.value)
    }

    @Test
    fun `test alert acknowledgment`() = runTest {
        // Given
        val alertId = "test_alert_123"
        rpmIntegrationManager.initializeRPM("test_client", "test_token")

        // When
        val result = rpmIntegrationManager.acknowledgeAlert(alertId)

        // Then
        assertTrue("Alert acknowledgment should succeed", result)
    }

    @Test
    fun `test vitals timeline retrieval`() = runTest {
        // Given
        rpmIntegrationManager.initializeRPM("test_client", "test_token")

        // When
        val timeline = rpmIntegrationManager.getVitalsTimeline()

        // Then
        assertNotNull("Timeline should not be null", timeline)
        // In test environment, timeline might be empty but should not be null
    }

    @Test
    fun `test RPM ready state check`() = runTest {
        // Given
        rpmIntegrationManager.initializeRPM("test_client", "test_token")

        // When
        val isReady = rpmIntegrationManager.isRPMReady()

        // Then
        assertTrue("RPM should be ready after initialization", isReady)
    }

    @Test
    fun `test RPM not ready state`() {
        // Given - RPM not initialized

        // When
        val isReady = rpmIntegrationManager.isRPMReady()

        // Then
        assertFalse("RPM should not be ready when not initialized", isReady)
    }

    @Test
    fun `test queue stats retrieval`() = runTest {
        // Given
        rpmIntegrationManager.initializeRPM("test_client", "test_token")

        // When
        val stats = rpmIntegrationManager.getQueueStats()

        // Then
        assertNotNull("Queue stats should not be null", stats)
        assertTrue("Queue size should be non-negative", stats?.totalItems ?: 0 >= 0)
    }

    @Test
    fun `test cleanup functionality`() = runTest {
        // Given
        rpmIntegrationManager.initializeRPM("test_client", "test_token")

        // When
        rpmIntegrationManager.cleanup()

        // Then
        assertEquals(RPMStatus.NOT_INITIALIZED, rpmIntegrationManager.rpmStatus.value)
    }
} 