package com.sensacare.veepoo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class VitalsViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AppDatabase.getInstance(application)
    private val vitalsDao = database.vitalsDao()

    private val _recentVitals = MutableLiveData<List<VitalsEntity>>()
    val recentVitals: LiveData<List<VitalsEntity>> = _recentVitals

    private val _chartData = MutableLiveData<List<VitalsEntity>>()
    val chartData: LiveData<List<VitalsEntity>> = _chartData

    init {
        loadRecentVitals()
        loadChartData()
    }

    private fun loadRecentVitals() {
        viewModelScope.launch {
            try {
                val vitals = vitalsDao.getRecentVitals()
                _recentVitals.postValue(vitals)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    private fun loadChartData() {
        viewModelScope.launch {
            try {
                // Get last 24 hours of data
                val startTime = System.currentTimeMillis() - TimeUnit.HOURS.toMillis(24)
                val vitals = vitalsDao.getVitalsFromTime(startTime)
                _chartData.postValue(vitals)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun refreshVitals() {
        loadRecentVitals()
        loadChartData()
    }
} 