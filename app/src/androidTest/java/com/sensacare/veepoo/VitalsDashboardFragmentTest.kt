package com.sensacare.veepoo

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.sensacare.veepoo.rpm.RPMIntegrationManager
import com.sensacare.veepoo.rpm.RPMStatus
import com.sensacare.veepoo.rpm.VitalsStatus
import io.mockk.*
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class VitalsDashboardFragmentTest {

    private lateinit var mockRpmIntegrationManager: RPMIntegrationManager
    private val rpmStatusFlow = MutableStateFlow(RPMStatus.NOT_INITIALIZED)
    private val vitalsStatusFlow = MutableStateFlow(VitalsStatus.NO_DATA)
    private val recentAlertsFlow = MutableStateFlow<List<com.sensacare.veepoo.rpm.AlertData>>(emptyList())

    @Before
    fun setUp() {
        mockRpmIntegrationManager = mockk<RPMIntegrationManager>(relaxed = true)
        
        // Mock the flows
        every { mockRpmIntegrationManager.rpmStatus } returns rpmStatusFlow
        every { mockRpmIntegrationManager.currentVitalsStatus } returns vitalsStatusFlow
        every { mockRpmIntegrationManager.recentAlerts } returns recentAlertsFlow
        
        // Mock the context
        val context = InstrumentationRegistry.getInstrumentation().targetContext
    }

    @Test
    fun testRPMStatusDisplay_NotInitialized() {
        // Given
        rpmStatusFlow.value = RPMStatus.NOT_INITIALIZED

        // When
        launchFragmentInContainer<VitalsDashboardFragment>()

        // Then
        onView(withId(R.id.rpmStatusText))
            .check(matches(withText("RPM Status: Not Initialized")))
    }

    @Test
    fun testRPMStatusDisplay_Ready() {
        // Given
        rpmStatusFlow.value = RPMStatus.READY

        // When
        launchFragmentInContainer<VitalsDashboardFragment>()

        // Then
        onView(withId(R.id.rpmStatusText))
            .check(matches(withText("RPM Status: Ready")))
    }

    @Test
    fun testRPMStatusDisplay_Error() {
        // Given
        rpmStatusFlow.value = RPMStatus.ERROR

        // When
        launchFragmentInContainer<VitalsDashboardFragment>()

        // Then
        onView(withId(R.id.rpmStatusText))
            .check(matches(withText("RPM Status: Error")))
    }

    @Test
    fun testVitalsStatusDisplay_Normal() {
        // Given
        vitalsStatusFlow.value = VitalsStatus.NORMAL

        // When
        launchFragmentInContainer<VitalsDashboardFragment>()

        // Then
        onView(withId(R.id.vitalsStatusText))
            .check(matches(withText("Vitals Status: Normal")))
    }

    @Test
    fun testVitalsStatusDisplay_NoData() {
        // Given
        vitalsStatusFlow.value = VitalsStatus.NO_DATA

        // When
        launchFragmentInContainer<VitalsDashboardFragment>()

        // Then
        onView(withId(R.id.vitalsStatusText))
            .check(matches(withText("Vitals Status: No Recent Data")))
    }

    @Test
    fun testVitalsStatusDisplay_CriticalError() {
        // Given
        vitalsStatusFlow.value = VitalsStatus.CRITICAL_ERROR

        // When
        launchFragmentInContainer<VitalsDashboardFragment>()

        // Then
        onView(withId(R.id.vitalsStatusText))
            .check(matches(withText("Vitals Status: Sensor Error")))
    }

    @Test
    fun testNavigationButtonsAreDisplayed() {
        // When
        launchFragmentInContainer<VitalsDashboardFragment>()

        // Then
        onView(withId(R.id.timelineButton))
            .check(matches(isDisplayed()))
            .check(matches(withText("Timeline")))

        onView(withId(R.id.consentButton))
            .check(matches(isDisplayed()))
            .check(matches(withText("Consent")))

        onView(withId(R.id.rpmSetupButton))
            .check(matches(isDisplayed()))
            .check(matches(withText("RPM Setup")))
    }

    @Test
    fun testTimelineButtonClick() {
        // Given
        launchFragmentInContainer<VitalsDashboardFragment>()

        // When
        onView(withId(R.id.timelineButton)).perform(click())

        // Then
        // Verify navigation occurred (this would require navigation testing setup)
        // For now, we just verify the button is clickable
        onView(withId(R.id.timelineButton))
            .check(matches(isClickable()))
    }

    @Test
    fun testConsentButtonClick() {
        // Given
        launchFragmentInContainer<VitalsDashboardFragment>()

        // When
        onView(withId(R.id.consentButton)).perform(click())

        // Then
        // Verify navigation occurred (this would require navigation testing setup)
        // For now, we just verify the button is clickable
        onView(withId(R.id.consentButton))
            .check(matches(isClickable()))
    }

    @Test
    fun testRPMSetupButtonClick() {
        // Given
        launchFragmentInContainer<VitalsDashboardFragment>()

        // When
        onView(withId(R.id.rpmSetupButton)).perform(click())

        // Then
        // Verify dialog appears (this would require dialog testing setup)
        // For now, we just verify the button is clickable
        onView(withId(R.id.rpmSetupButton))
            .check(matches(isClickable()))
    }

    @Test
    fun testRPMAlertsContainer_NoAlerts() {
        // Given
        recentAlertsFlow.value = emptyList()

        // When
        launchFragmentInContainer<VitalsDashboardFragment>()

        // Then
        onView(withId(R.id.rpmAlertsContainer))
            .check(matches(withEffectiveVisibility(Visibility.GONE)))
    }

    @Test
    fun testRPMAlertsContainer_WithAlerts() {
        // Given
        val alerts = listOf(
            com.sensacare.veepoo.rpm.AlertData(
                id = "alert_1",
                severity = "warning",
                message = "Test alert message",
                timestamp = "2024-01-01T12:00:00Z"
            )
        )
        recentAlertsFlow.value = alerts

        // When
        launchFragmentInContainer<VitalsDashboardFragment>()

        // Then
        onView(withId(R.id.rpmAlertsContainer))
            .check(matches(withEffectiveVisibility(Visibility.VISIBLE)))

        onView(withId(R.id.rpmAlertText))
            .check(matches(withText("WARNING: Test alert message")))
    }

    @Test
    fun testServiceControlButtonsAreDisplayed() {
        // When
        launchFragmentInContainer<VitalsDashboardFragment>()

        // Then
        onView(withId(R.id.startServiceButton))
            .check(matches(isDisplayed()))
            .check(matches(withText("Start Service")))

        onView(withId(R.id.stopServiceButton))
            .check(matches(isDisplayed()))
            .check(matches(withText("Stop Service")))
    }

    @Test
    fun testDiagnosticButtonIsDisplayed() {
        // When
        launchFragmentInContainer<VitalsDashboardFragment>()

        // Then
        onView(withId(R.id.diagnosticButton))
            .check(matches(isDisplayed()))
            .check(matches(withText("Diagnostics")))
    }

    @Test
    fun testAdminButtonIsDisplayed() {
        // When
        launchFragmentInContainer<VitalsDashboardFragment>()

        // Then
        onView(withId(R.id.adminButton))
            .check(matches(isDisplayed()))
            .check(matches(withText("Admin")))
    }

    @Test
    fun testVitalsDisplayFieldsArePresent() {
        // When
        launchFragmentInContainer<VitalsDashboardFragment>()

        // Then
        onView(withId(R.id.heartRateText))
            .check(matches(isDisplayed()))

        onView(withId(R.id.bloodPressureText))
            .check(matches(isDisplayed()))

        onView(withId(R.id.spo2Text))
            .check(matches(isDisplayed()))

        onView(withId(R.id.temperatureText))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testConnectionStatusBannerIsDisplayed() {
        // When
        launchFragmentInContainer<VitalsDashboardFragment>()

        // Then
        onView(withId(R.id.connectionStatusBanner))
            .check(matches(isDisplayed()))

        onView(withId(R.id.connectionStatusText))
            .check(matches(isDisplayed()))

        onView(withId(R.id.connectionActionButton))
            .check(matches(isDisplayed()))
    }
} 