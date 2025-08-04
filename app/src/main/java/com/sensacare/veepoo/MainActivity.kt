package com.sensacare.veepoo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        // Set the title for Sensacare App
        title = "Sensacare App"

        // Check if onboarding is completed
        if (OnboardingActivity.shouldShowOnboarding(this)) {
            startOnboarding()
            return
        }

        // Setup bottom navigation
        setupBottomNavigation()

        // Start the foreground BLE service
        BleForegroundService.startService(this)

        if (savedInstanceState == null) {
            val fragment = VitalsDashboardFragment()
            supportFragmentManager.commit {
                replace(R.id.fragment_container, fragment)
            }
        }
    }

    private fun setupBottomNavigation() {
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_dashboard -> {
                    supportFragmentManager.commit {
                        replace(R.id.fragment_container, VitalsDashboardFragment())
                    }
                    true
                }
                R.id.navigation_timeline -> {
                    supportFragmentManager.commit {
                        replace(R.id.fragment_container, VitalsTimelineFragment())
                    }
                    true
                }
                R.id.navigation_devices -> {
                    // TODO: Implement devices fragment
                    true
                }
                R.id.navigation_settings -> {
                    // TODO: Implement settings fragment
                    true
                }
                else -> false
            }
        }
    }

    private fun startOnboarding() {
        val intent = Intent(this, OnboardingActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        // Note: We don't stop the service here to keep it running in background
        // The service will continue monitoring even when app is closed
    }
}
