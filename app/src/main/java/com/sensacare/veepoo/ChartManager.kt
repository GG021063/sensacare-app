package com.sensacare.veepoo

import android.content.Context
import android.graphics.Color
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.SimpleDateFormat
import java.util.*

class ChartManager(private val context: Context) {

    private val timeFormatter = SimpleDateFormat("HH:mm", Locale.getDefault())

    fun setupHeartRateChart(chart: LineChart) {
        chart.apply {
            description.isEnabled = false
            legend.isEnabled = true
            setTouchEnabled(true)
            setScaleEnabled(true)
            setPinchZoom(true)
            
            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                setDrawGridLines(false)
                valueFormatter = TimeAxisFormatter()
                labelRotationAngle = -45f
            }
            
            axisLeft.apply {
                setDrawGridLines(true)
                axisMinimum = 40f
                axisMaximum = 200f
                setDrawZeroLine(false)
            }
            
            axisRight.isEnabled = false
            
            data = LineData()
            invalidate()
        }
    }

    fun setupSpO2Chart(chart: LineChart) {
        chart.apply {
            description.isEnabled = false
            legend.isEnabled = true
            setTouchEnabled(true)
            setScaleEnabled(true)
            setPinchZoom(true)
            
            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                setDrawGridLines(false)
                valueFormatter = TimeAxisFormatter()
                labelRotationAngle = -45f
            }
            
            axisLeft.apply {
                setDrawGridLines(true)
                axisMinimum = 80f
                axisMaximum = 100f
                setDrawZeroLine(false)
            }
            
            axisRight.isEnabled = false
            
            data = LineData()
            invalidate()
        }
    }

    fun setupTemperatureChart(chart: LineChart) {
        chart.apply {
            description.isEnabled = false
            legend.isEnabled = true
            setTouchEnabled(true)
            setScaleEnabled(true)
            setPinchZoom(true)
            
            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                setDrawGridLines(false)
                valueFormatter = TimeAxisFormatter()
                labelRotationAngle = -45f
            }
            
            axisLeft.apply {
                setDrawGridLines(true)
                axisMinimum = 35f
                axisMaximum = 42f
                setDrawZeroLine(false)
            }
            
            axisRight.isEnabled = false
            
            data = LineData()
            invalidate()
        }
    }

    fun setupBloodPressureChart(chart: BarChart) {
        chart.apply {
            description.isEnabled = false
            legend.isEnabled = true
            setTouchEnabled(true)
            setScaleEnabled(true)
            setPinchZoom(true)
            
            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                setDrawGridLines(false)
                valueFormatter = TimeAxisFormatter()
                labelRotationAngle = -45f
            }
            
            axisLeft.apply {
                setDrawGridLines(true)
                axisMinimum = 60f
                axisMaximum = 200f
                setDrawZeroLine(false)
            }
            
            axisRight.isEnabled = false
            
            data = BarData()
            invalidate()
        }
    }

    fun updateHeartRateChart(chart: LineChart, vitals: List<VitalsEntity>) {
        val entries = vitals.mapIndexedNotNull { index, vital ->
            vital.heartRate?.let { hr ->
                Entry(index.toFloat(), hr.toFloat())
            }
        }

        val dataSet = LineDataSet(entries, "Heart Rate (bpm)").apply {
            color = Color.RED
            setCircleColor(Color.RED)
            lineWidth = 2f
            circleRadius = 4f
            setDrawCircleHole(true)
            setDrawValues(false)
            mode = LineDataSet.Mode.CUBIC_BEZIER
        }

        chart.data = LineData(dataSet)
        chart.invalidate()
    }

    fun updateSpO2Chart(chart: LineChart, vitals: List<VitalsEntity>) {
        val entries = vitals.mapIndexedNotNull { index, vital ->
            vital.spo2?.let { spo2 ->
                Entry(index.toFloat(), spo2.toFloat())
            }
        }

        val dataSet = LineDataSet(entries, "SpO2 (%)").apply {
            color = Color.BLUE
            setCircleColor(Color.BLUE)
            lineWidth = 2f
            circleRadius = 4f
            setDrawCircleHole(true)
            setDrawValues(false)
            mode = LineDataSet.Mode.CUBIC_BEZIER
        }

        chart.data = LineData(dataSet)
        chart.invalidate()
    }

    fun updateTemperatureChart(chart: LineChart, vitals: List<VitalsEntity>) {
        val entries = vitals.mapIndexedNotNull { index, vital ->
            vital.temperature?.let { temp ->
                Entry(index.toFloat(), temp)
            }
        }

        val dataSet = LineDataSet(entries, "Temperature (°C)").apply {
            color = Color.GREEN
            setCircleColor(Color.GREEN)
            lineWidth = 2f
            circleRadius = 4f
            setDrawCircleHole(true)
            setDrawValues(false)
            mode = LineDataSet.Mode.CUBIC_BEZIER
        }

        chart.data = LineData(dataSet)
        chart.invalidate()
    }

    fun updateBloodPressureChart(chart: BarChart, vitals: List<VitalsEntity>) {
        val systolicEntries = vitals.mapIndexedNotNull { index, vital ->
            vital.bloodPressureSystolic?.let { systolic ->
                BarEntry(index.toFloat(), systolic.toFloat())
            }
        }

        val diastolicEntries = vitals.mapIndexedNotNull { index, vital ->
            vital.bloodPressureDiastolic?.let { diastolic ->
                BarEntry(index.toFloat(), diastolic.toFloat())
            }
        }

        val systolicDataSet = BarDataSet(systolicEntries, "Systolic").apply {
            color = Color.RED
            setDrawValues(false)
        }

        val diastolicDataSet = BarDataSet(diastolicEntries, "Diastolic").apply {
            color = Color.BLUE
            setDrawValues(false)
        }

        chart.data = BarData(systolicDataSet, diastolicDataSet)
        chart.invalidate()
    }

    private inner class TimeAxisFormatter : ValueFormatter() {
        override fun getFormattedValue(value: Float): String {
            // This is a simplified version - in a real app you'd map the index to actual timestamps
            return timeFormatter.format(Date())
        }
    }
} 