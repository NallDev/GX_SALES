package com.nalldev.gxsales.presentation.main.dialog.filter_leads.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nalldev.gxsales.core.util.DateConverter
import com.nalldev.gxsales.core.util.createDateRangeBuilder
import com.nalldev.gxsales.databinding.BottomSheetFilterBinding
import com.nalldev.gxsales.presentation.main.dialog.filter_leads.viewmodel.FilterLeadsViewModel
import com.nalldev.gxsales.presentation.main.viewmodel.HomeViewModel
import kotlinx.coroutines.launch

class BottomSheetFilterLeads : BottomSheetDialogFragment() {
    private var _binding : BottomSheetFilterBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<FilterLeadsViewModel>()
    private val homeViewModel by activityViewModels<HomeViewModel>()

    private val statusArrayAdapter by lazy {
        ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, arrayListOf<String>())
    }

    private val channelArrayAdapter by lazy {
        ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, arrayListOf<String>())
    }

    private val mediaArrayAdapter by lazy {
        ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, arrayListOf<String>())
    }

    private val sourceArrayAdapter by lazy {
        ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, arrayListOf<String>())
    }

    private var tempFromDate : String = ""
    private var tempToDate : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.fetchForm()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObserver()
        setupUI()
        setupListener()
    }

    private fun setupUI() = with(binding) {
        tvStatus.setAdapter(statusArrayAdapter)
        tvChannel.setAdapter(channelArrayAdapter)
        tvMedia.setAdapter(mediaArrayAdapter)
        tvSource.setAdapter(sourceArrayAdapter)
    }

    private fun setupObserver() = with(viewModel) {
        listStatus.observe(viewLifecycleOwner) { data ->
            val listStatus = data.map { it.name }
            statusArrayAdapter.clear()
            statusArrayAdapter.addAll(listStatus)
            statusArrayAdapter.notifyDataSetChanged()
        }

        listChannel.observe(viewLifecycleOwner) {data ->
            val listChannel = data.map { it.name }
            channelArrayAdapter.clear()
            channelArrayAdapter.addAll(listChannel)
            channelArrayAdapter.notifyDataSetChanged()
        }

        listMedia.observe(viewLifecycleOwner) {data ->
            val listMedia = data.map { it.name }
            mediaArrayAdapter.clear()
            mediaArrayAdapter.addAll(listMedia)
            mediaArrayAdapter.notifyDataSetChanged()
        }

        listSource.observe(viewLifecycleOwner) {data ->
            val listSource = data.map { it.name }
            sourceArrayAdapter.clear()
            sourceArrayAdapter.addAll(listSource)
            sourceArrayAdapter.notifyDataSetChanged()
        }

        homeViewModel.apply {
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    launch {
                        status.collect { status ->
                            if (status.isNotBlank()) {
                                binding.tvStatus.setText(status)
                            } else {
                                binding.tvStatus.text.clear()
                            }
                        }
                    }

                    launch {
                        rangeDateFilter.collect{ rangeDate ->
                            if (rangeDate.first.isNotBlank()) {
                                binding.tvDate.text = String.format("%s - %s", rangeDate.first, rangeDate.second)
                                tempFromDate = rangeDate.first
                                tempToDate = rangeDate.second
                            }
                        }
                    }

                    launch {
                        channel.collect { channel ->
                            if (channel.isNotBlank()) {
                                binding.tvChannel.setText(channel)
                            }
                        }
                    }

                    launch {
                        media.collect { media ->
                            if (media.isNotBlank()) {
                                binding.tvMedia.setText(media)
                            }
                        }
                    }

                    launch {
                        source.collect { source ->
                            if (source.isNotBlank()) {
                                binding.tvSource.setText(source)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setupListener() = with(binding){
        tvDate.setOnClickListener {
            requireContext()
                .createDateRangeBuilder()
                .setOnChoose("Save") { start, end ->
                    tempFromDate = DateConverter.convertLongToDateString(start)
                    tempToDate = DateConverter.convertLongToDateString(end)
                    tvDate.text = String.format("%s - %s", tempFromDate, tempToDate)
                }
                .build()
                .show()
        }

        btnReset.setOnClickListener {
            resetFilter()
        }

        btnSearch.setOnClickListener {
            doSearch()
        }
    }

    private fun doSearch() = with(homeViewModel) {
        binding.apply {
            setRangeDate(tempFromDate, tempToDate)
            setStatus(tvStatus.text.toString())
            setChannel(tvChannel.text.toString())
            setMedia(tvMedia.text.toString())
            setSource(tvSource.text.toString())
        }

        dismiss()
    }

    private fun resetFilter() = with(homeViewModel) {
        setRangeDate("", "")
        setStatus("")
        setChannel("")
        setMedia("")
        setSource("")

        dismiss()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}