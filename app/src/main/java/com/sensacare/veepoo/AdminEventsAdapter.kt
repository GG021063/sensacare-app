package com.sensacare.veepoo

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class AdminEventsAdapter : RecyclerView.Adapter<AdminEventsAdapter.EventsViewHolder>() {
    
    private var events: List<AdminEventData> = emptyList()
    private val dateFormatter = SimpleDateFormat("MM/dd HH:mm:ss", Locale.getDefault())

    fun updateEvents(newEvents: List<AdminEventData>) {
        events = newEvents
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_2, parent, false)
        return EventsViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventsViewHolder, position: Int) {
        val event = events[position]
        holder.bind(event)
    }

    override fun getItemCount(): Int = events.size

    inner class EventsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val text1: TextView = itemView.findViewById(android.R.id.text1)
        private val text2: TextView = itemView.findViewById(android.R.id.text2)

        fun bind(event: AdminEventData) {
            // Main text - timestamp, event type, and description
            val mainText = buildString {
                append("[${dateFormatter.format(Date(event.timestamp))}] ")
                append("[${event.eventType}] ")
                append(event.description)
            }
            text1.text = mainText

            // Secondary text - details if available
            val detailsText = event.details ?: ""
            text2.text = detailsText
            text2.visibility = if (detailsText.isNotEmpty()) View.VISIBLE else View.GONE

            // Set colors based on log level
            val backgroundColor = when (event.level) {
                DiagnosticLogger.LogLevel.ERROR -> Color.parseColor("#FFEBEE") // Light red
                DiagnosticLogger.LogLevel.WARNING -> Color.parseColor("#FFF3E0") // Light orange
                DiagnosticLogger.LogLevel.INFO -> Color.parseColor("#E3F2FD") // Light blue
                DiagnosticLogger.LogLevel.DEBUG -> Color.WHITE
            }
            
            val textColor = when (event.level) {
                DiagnosticLogger.LogLevel.ERROR -> Color.RED
                DiagnosticLogger.LogLevel.WARNING -> Color.parseColor("#FF8C00") // Orange
                DiagnosticLogger.LogLevel.INFO -> Color.BLUE
                DiagnosticLogger.LogLevel.DEBUG -> Color.GRAY
            }

            itemView.setBackgroundColor(backgroundColor)
            text1.setTextColor(textColor)
            text2.setTextColor(Color.DKGRAY)
        }
    }
} 