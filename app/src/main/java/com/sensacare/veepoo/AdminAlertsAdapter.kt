package com.sensacare.veepoo

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class AdminAlertsAdapter : RecyclerView.Adapter<AdminAlertsAdapter.AlertsViewHolder>() {
    
    private var alerts: List<AdminAlertData> = emptyList()
    private val dateFormatter = SimpleDateFormat("MM/dd HH:mm:ss", Locale.getDefault())

    fun updateAlerts(newAlerts: List<AdminAlertData>) {
        alerts = newAlerts
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlertsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_2, parent, false)
        return AlertsViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlertsViewHolder, position: Int) {
        val alert = alerts[position]
        holder.bind(alert)
    }

    override fun getItemCount(): Int = alerts.size

    inner class AlertsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val text1: TextView = itemView.findViewById(android.R.id.text1)
        private val text2: TextView = itemView.findViewById(android.R.id.text2)

        fun bind(alert: AdminAlertData) {
            // Main text - alert type and value
            val mainText = buildString {
                append("[${dateFormatter.format(Date(alert.timestamp))}] ")
                append("${alert.vitalType}: ${alert.value} ")
                append("(Threshold: ${alert.threshold})")
            }
            text1.text = mainText

            // Secondary text - alert level
            val alertText = "Alert Level: ${alert.alertLevel.name}"
            text2.text = alertText

            // Set colors based on alert level
            val backgroundColor = when (alert.alertLevel) {
                VitalsAlertManager.AlertLevel.CRITICAL -> Color.parseColor("#FFEBEE") // Light red
                VitalsAlertManager.AlertLevel.WARNING -> Color.parseColor("#FFF3E0") // Light orange
                VitalsAlertManager.AlertLevel.NORMAL -> Color.WHITE
                VitalsAlertManager.AlertLevel.NONE -> Color.parseColor("#F0F0F0")   // Light gray
            }
            
            val textColor = when (alert.alertLevel) {
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