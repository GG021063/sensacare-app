package com.sensacare.veepoo.rpm

import com.sensacare.veepoo.VitalsData
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class VitalsStatusEvaluatorTest {

    private val evaluator = VitalsStatusEvaluator()

    @Test
    fun `test normal vitals status`() {
        // Given
        val vitalsData = VitalsData(
            heartRate = 75,
            spo2 = 98,
            bloodPressureSystolic = 120,
            bloodPressureDiastolic = 80,
            temperature = 36.8
        )
        val lastDataTimestamp = System.currentTimeMillis()
        val isConnected = true

        // When
        val status = evaluator.evaluateStatus(vitalsData, lastDataTimestamp, isConnected)

        // Then
        assertEquals(VitalsStatus.NORMAL, status)
    }

    @Test
    fun `test no data status`() {
        // Given
        val vitalsData: VitalsData? = null
        val lastDataTimestamp: Long? = null
        val isConnected = false

        // When
        val status = evaluator.evaluateStatus(vitalsData, lastDataTimestamp, isConnected)

        // Then
        assertEquals(VitalsStatus.NO_DATA, status)
    }

    @Test
    fun `test stale data status`() {
        // Given
        val vitalsData = VitalsData(
            heartRate = 75,
            spo2 = 98,
            bloodPressureSystolic = 120,
            bloodPressureDiastolic = 80,
            temperature = 36.8
        )
        val lastDataTimestamp = System.currentTimeMillis() - (2 * 60 * 60 * 1000) // 2 hours ago
        val isConnected = true

        // When
        val status = evaluator.evaluateStatus(vitalsData, lastDataTimestamp, isConnected)

        // Then
        assertEquals(VitalsStatus.NO_DATA, status)
    }

    @Test
    fun `test critical sensor error status`() {
        // Given
        val vitalsData = VitalsData(
            heartRate = 0, // Critical error - heart rate cannot be 0
            spo2 = 98,
            bloodPressureSystolic = 120,
            bloodPressureDiastolic = 80,
            temperature = 36.8
        )
        val lastDataTimestamp = System.currentTimeMillis()
        val isConnected = true

        // When
        val status = evaluator.evaluateStatus(vitalsData, lastDataTimestamp, isConnected)

        // Then
        assertEquals(VitalsStatus.CRITICAL_ERROR, status)
    }

    @Test
    fun `test BLE error status`() {
        // Given
        val vitalsData = VitalsData(
            heartRate = 75,
            spo2 = 98,
            bloodPressureSystolic = 120,
            bloodPressureDiastolic = 80,
            temperature = 36.8
        )
        val lastDataTimestamp = System.currentTimeMillis()
        val isConnected = false

        // When
        val status = evaluator.evaluateStatus(vitalsData, lastDataTimestamp, isConnected)

        // Then
        assertEquals(VitalsStatus.BLE_ERROR, status)
    }

    @Test
    fun `test status description for normal`() {
        // When
        val description = evaluator.getStatusDescription(VitalsStatus.NORMAL)

        // Then
        assertEquals("Vitals monitoring normal", description)
    }

    @Test
    fun `test status description for no data`() {
        // When
        val description = evaluator.getStatusDescription(VitalsStatus.NO_DATA)

        // Then
        assertEquals("No recent vitals data", description)
    }

    @Test
    fun `test status description for critical error`() {
        // When
        val description = evaluator.getStatusDescription(VitalsStatus.CRITICAL_ERROR)

        // Then
        assertEquals("Critical sensor error detected", description)
    }

    @Test
    fun `test status description for BLE error`() {
        // When
        val description = evaluator.getStatusDescription(VitalsStatus.BLE_ERROR)

        // Then
        assertEquals("Bluetooth connection error", description)
    }

    @Test
    fun `test status color for normal`() {
        // When
        val color = evaluator.getStatusColor(VitalsStatus.NORMAL)

        // Then
        assertEquals("#4CAF50", color) // Green
    }

    @Test
    fun `test status color for no data`() {
        // When
        val color = evaluator.getStatusColor(VitalsStatus.NO_DATA)

        // Then
        assertEquals("#FF9800", color) // Orange
    }

    @Test
    fun `test status color for critical error`() {
        // When
        val color = evaluator.getStatusColor(VitalsStatus.CRITICAL_ERROR)

        // Then
        assertEquals("#F44336", color) // Red
    }

    @Test
    fun `test status color for BLE error`() {
        // When
        val color = evaluator.getStatusColor(VitalsStatus.BLE_ERROR)

        // Then
        assertEquals("#F44336", color) // Red
    }

    @Test
    fun `test requires attention for normal status`() {
        // When
        val requiresAttention = evaluator.requiresAttention(VitalsStatus.NORMAL)

        // Then
        assertFalse("Normal status should not require attention", requiresAttention)
    }

    @Test
    fun `test requires attention for critical error`() {
        // When
        val requiresAttention = evaluator.requiresAttention(VitalsStatus.CRITICAL_ERROR)

        // Then
        assertTrue("Critical error should require attention", requiresAttention)
    }

    @Test
    fun `test requires attention for BLE error`() {
        // When
        val requiresAttention = evaluator.requiresAttention(VitalsStatus.BLE_ERROR)

        // Then
        assertTrue("BLE error should require attention", requiresAttention)
    }

    @Test
    fun `test recommended action for normal status`() {
        // When
        val action = evaluator.getRecommendedAction(VitalsStatus.NORMAL)

        // Then
        assertNull("Normal status should not have recommended action", action)
    }

    @Test
    fun `test recommended action for critical error`() {
        // When
        val action = evaluator.getRecommendedAction(VitalsStatus.CRITICAL_ERROR)

        // Then
        assertEquals("Check device sensors and restart if needed", action)
    }

    @Test
    fun `test recommended action for BLE error`() {
        // When
        val action = evaluator.getRecommendedAction(VitalsStatus.BLE_ERROR)

        // Then
        assertEquals("Check Bluetooth connection and device proximity", action)
    }

    @Test
    fun `test recommended action for no data`() {
        // When
        val action = evaluator.getRecommendedAction(VitalsStatus.NO_DATA)

        // Then
        assertEquals("Check device connection and wait for data", action)
    }
} 