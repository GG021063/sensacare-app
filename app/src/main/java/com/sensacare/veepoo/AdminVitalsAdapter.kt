package com.sensacare.veepoo

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class AdminVitalsAdapter : RecyclerView.Adapter<AdminVitalsAdapter.VitalsViewHolder>() {
    
    private var vitals: List<AdminVitalsData> = emptyList()
    private val dateFormatter = SimpleDateFormat("MM/dd HH:mm:ss", Locale.getDefault())

    fun updateVitals(newVitals: List<AdminVitalsData>) {
        vitals = newVitals
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VitalsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_2, parent, false)
        return VitalsViewHolder(view)
    }

    override fun onBindViewHolder(holder: VitalsViewHolder, position: Int) {
        val vital = vitals[position]
        holder.bind(vital)
    }

    override fun getItemCount(): Int = vitals.size

    inner class VitalsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val text1: TextView = itemView.findViewById(android.R.id.text1)
        private val text2: TextView = itemView.findViewById(android.R.id.text2)

        fun bind(vital: AdminVitalsData) {
            // Main text - timestamp and vitals
            val mainText = buildString {
                append("[${dateFormatter.format(Date(vital.timestamp))}] ")
                append("HR: ${vital.heartRate ?: "--"} ")
                append("BP: ${vital.bloodPressureSystolic ?: "--"}/${vital.bloodPressureDiastolic ?: "--"} ")
                append("SpO2: ${vital.spo2 ?: "--"}% ")
                append("Temp: ${vital.temperature?.let { "%.1f".format(it) } ?: "--"}°C")
            }
            text1.text = mainText

            // Secondary text - alert level
            val alertText = "Alert Level: ${vital.alertLevel.name}"
            text2.text = alertText

            // Set colors based on alert level
            val backgroundColor = when (vital.alertLevel) {
                VitalsAlertManager.AlertLevel.CRITICAL -> Color.parseColor("#FFEBEE") // Light red
                VitalsAlertManager.AlertLevel.WARNING -> Color.parseColor("#FFF3E0") // Light orange
                VitalsAlertManager.AlertLevel.NORMAL -> Color.WHITE
                VitalsAlertManager.AlertLevel.NONE -> Color.parseColor("#F0F0F0")   // Light gray
            }
            
            val textColor = when (vital.alertLevel) {
                VitalsAlertManager.AlertLevel.CRITICAL -> Color.RED
                VitalsAlertManager.AlertLevel.WARNING -> Color.parseColor("#FF8C00") // Orange
                VitalsAlertManager.AlertLevel.NORMAL -> Color.BLACK
                VitalsAlertManager.AlertLevel.NONE -> Color.parseColor("#808080")   // Gray
            }

            itemView.setBackgroundColor(backgroundColor)
            text1.setTextColor(textColor)
            text2.setTextColor(textColor)
        }
    }
} 