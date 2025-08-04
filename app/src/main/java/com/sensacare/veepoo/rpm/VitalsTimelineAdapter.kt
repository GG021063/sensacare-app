package com.sensacare.veepoo.rpm

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class VitalsTimelineAdapter(private val context: Context) : RecyclerView.Adapter<VitalsTimelineAdapter.TimelineViewHolder>() {
    
    private var timelineEntries: List<VitalsTimelineEntry> = emptyList()
    private val dateFormatter = SimpleDateFormat("MMM dd, HH:mm", Locale.getDefault())
    
    fun updateTimeline(entries: List<VitalsTimelineEntry>) {
        timelineEntries = entries
        notifyDataSetChanged()
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimelineViewHolder {
        val view = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_2, parent, false)
        return TimelineViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: TimelineViewHolder, position: Int) {
        holder.bind(timelineEntries[position])
    }
    
    override fun getItemCount(): Int = timelineEntries.size
    
    inner class TimelineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val text1: TextView = itemView.findViewById(android.R.id.text1)
        private val text2: TextView = itemView.findViewById(android.R.id.text2)
        
        fun bind(entry: VitalsTimelineEntry) {
            // Parse timestamp
            val timestamp = try {
                val isoFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
                isoFormatter.timeZone = TimeZone.getTimeZone("UTC")
                isoFormatter.parse(entry.timestamp)?.time ?: 0L
            } catch (e: Exception) {
                0L
            }
            
            val formattedTime = if (timestamp > 0) {
                dateFormatter.format(Date(timestamp))
            } else {
                "Unknown time"
            }
            
            // Create status indicator
            val statusIndicator = when (entry.status) {
                "synced" -> "✅"
                "gap" -> "⏸️"
                "alert" -> "⚠️"
                else -> "❓"
            }
            
            // Set primary text (timestamp + status)
            text1.text = "$statusIndicator $formattedTime"
            
            // Set secondary text (vitals data or alert message)
            val secondaryText = when {
                entry.status == "alert" && entry.alertMessage != null -> {
                    "ALERT: ${entry.alertMessage}"
                }
                entry.vitals != null -> {
                    buildVitalsText(entry.vitals)
                }
                entry.status == "gap" -> {
                    "No data available"
                }
                else -> {
                    "No vitals data"
                }
            }
            
            text2.text = secondaryText
            
            // Set colors based on status
            when (entry.status) {
                "synced" -> {
                    text1.setTextColor(Color.BLACK)
                    text2.setTextColor(Color.DKGRAY)
                    itemView.setBackgroundColor(Color.WHITE)
                }
                "gap" -> {
                    text1.setTextColor(Color.GRAY)
                    text2.setTextColor(Color.LTGRAY)
                    itemView.setBackgroundColor(Color.parseColor("#F5F5F5"))
                }
                "alert" -> {
                    text1.setTextColor(Color.RED)
                    text2.setTextColor(Color.RED)
                    itemView.setBackgroundColor(Color.parseColor("#FFEBEE"))
                }
                else -> {
                    text1.setTextColor(Color.BLACK)
                    text2.setTextColor(Color.DKGRAY)
                    itemView.setBackgroundColor(Color.WHITE)
                }
            }
        }
        
        private fun buildVitalsText(vitals: VitalsData): String {
            val parts = mutableListOf<String>()
            
            vitals.heartRate?.let { parts.add("HR: ${it}bpm") }
            vitals.spo2?.let { parts.add("SpO2: ${it}%") }
            vitals.bloodPressureSystolic?.let { systolic ->
                vitals.bloodPressureDiastolic?.let { diastolic ->
                    parts.add("BP: ${systolic}/${diastolic}mmHg")
                }
            }
            vitals.temperature?.let { parts.add("Temp: ${it}°C") }
            
            return if (parts.isNotEmpty()) {
                parts.joinToString(" | ")
            } else {
                "No vitals data"
            }
        }
    }
} 