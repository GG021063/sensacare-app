package com.sensacare.veepoo.rpm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.sensacare.veepoo.databinding.FragmentVitalsTimelineBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class VitalsTimelineFragment : Fragment() {
    
    private var _binding: FragmentVitalsTimelineBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var timelineAdapter: VitalsTimelineAdapter
    private lateinit var rpmIntegrationManager: RPMIntegrationManager
    
    private val dateFormatter = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault())
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVitalsTimelineBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        rpmIntegrationManager = RPMIntegrationManager(requireContext())
        
        setupRecyclerView()
        setupRangeSelector()
        setupRefreshButton()
        
        // Load initial timeline data
        loadTimelineData()
    }
    
    private fun setupRecyclerView() {
        timelineAdapter = VitalsTimelineAdapter(requireContext())
        binding.timelineRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = timelineAdapter
        }
    }
    
    private fun setupRangeSelector() {
        val ranges = arrayOf("1h", "6h", "12h", "24h", "48h", "7d")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, ranges)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        
        binding.rangeSpinner.adapter = adapter
        binding.rangeSpinner.setSelection(3) // Default to 24h
        
        binding.rangeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedRange = ranges[position]
                loadTimelineData(selectedRange)
            }
            
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }
    
    private fun setupRefreshButton() {
        binding.refreshButton.setOnClickListener {
            val selectedRange = binding.rangeSpinner.selectedItem as String
            loadTimelineData(selectedRange)
        }
    }
    
    private fun loadTimelineData(range: String = "24h") {
        lifecycleScope.launch {
            try {
                binding.loadingIndicator.visibility = View.VISIBLE
                binding.timelineRecyclerView.visibility = View.GONE
                
                val timeline = rpmIntegrationManager.getVitalsTimeline(range = range)
                
                if (timeline != null) {
                    timelineAdapter.updateTimeline(timeline)
                    binding.timelineRecyclerView.visibility = View.VISIBLE
                    
                    // Update summary
                    updateTimelineSummary(timeline)
                } else {
                    binding.emptyStateText.visibility = View.VISIBLE
                    binding.emptyStateText.text = "No timeline data available for the selected range"
                }
                
            } catch (e: Exception) {
                Toast.makeText(context, "Error loading timeline: ${e.message}", Toast.LENGTH_SHORT).show()
                binding.emptyStateText.visibility = View.VISIBLE
                binding.emptyStateText.text = "Error loading timeline data"
            } finally {
                binding.loadingIndicator.visibility = View.GONE
            }
        }
    }
    
    private fun updateTimelineSummary(timeline: List<VitalsTimelineEntry>) {
        val totalEntries = timeline.size
        val syncedEntries = timeline.count { it.status == "synced" }
        val gapEntries = timeline.count { it.status == "gap" }
        val alertEntries = timeline.count { it.status == "alert" }
        
        binding.summaryText.text = "Total: $totalEntries | Synced: $syncedEntries | Gaps: $gapEntries | Alerts: $alertEntries"
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 