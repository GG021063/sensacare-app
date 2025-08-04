package com.sensacare.veepoo

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DiagnosticLogAdapter(private val context: Context) : RecyclerView.Adapter<DiagnosticLogAdapter.LogViewHolder>() {
    
    private var logs: List<DiagnosticLogger.LogEntry> = emptyList()

    fun updateLogs(newLogs: List<DiagnosticLogger.LogEntry>) {
        logs = newLogs
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogViewHolder {
        val view = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_2, parent, false)
        return LogViewHolder(view)
    }

    override fun onBindViewHolder(holder: LogViewHolder, position: Int) {
        val log = logs[position]
        holder.bind(log)
    }

    override fun getItemCount(): Int = logs.size

    inner class LogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val text1: TextView = itemView.findViewById(android.R.id.text1)
        private val text2: TextView = itemView.findViewById(android.R.id.text2)

        fun bind(log: DiagnosticLogger.LogEntry) {
            // Set the main log message
            val mainText = buildString {
                append("[${log.timestampFormatted}] ")
                append("[${log.level.name}] ")
                append("[${log.category.name}] ")
                append(log.message)
            }
            text1.text = mainText
            
            // Set the details if available
            text2.text = log.details ?: ""
            text2.visibility = if (log.details != null) View.VISIBLE else View.GONE
            
            // Set colors based on log level
            val color = when (log.level) {
                DiagnosticLogger.LogLevel.DEBUG -> Color.GRAY
                DiagnosticLogger.LogLevel.INFO -> Color.BLACK
                DiagnosticLogger.LogLevel.WARNING -> Color.parseColor("#FF8C00") // Orange
                DiagnosticLogger.LogLevel.ERROR -> Color.RED
            }
            
            text1.setTextColor(color)
            text2.setTextColor(Color.DKGRAY)
            
            // Set background color based on category
            val backgroundColor = when (log.category) {
                DiagnosticLogger.LogCategory.BLE_SCAN -> Color.parseColor("#F0F8FF") // Light blue
                DiagnosticLogger.LogCategory.BLE_CONNECTION -> Color.parseColor("#F0FFF0") // Light green
                DiagnosticLogger.LogCategory.BLE_CHARACTERISTIC -> Color.parseColor("#FFF8DC") // Light yellow
                DiagnosticLogger.LogCategory.ROOM_DATABASE -> Color.parseColor("#F5F5DC") // Light beige
                DiagnosticLogger.LogCategory.API_CALL -> Color.parseColor("#F0F0F0") // Light gray
                DiagnosticLogger.LogCategory.SECURITY -> Color.parseColor("#FFF0F5") // Light pink
                DiagnosticLogger.LogCategory.GENERAL -> Color.WHITE
            }
            
            itemView.setBackgroundColor(backgroundColor)
        }
    }
} 