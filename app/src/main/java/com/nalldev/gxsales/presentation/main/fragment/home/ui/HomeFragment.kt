package com.nalldev.gxsales.presentation.main.fragment.home.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.nalldev.gxsales.core.util.DateConverter
import com.nalldev.gxsales.core.util.GridSpacingItemDecoration
import com.nalldev.gxsales.core.util.UiState
import com.nalldev.gxsales.core.util.createDateRangeBuilder
import com.nalldev.gxsales.databinding.FragmentHomeBinding
import com.nalldev.gxsales.presentation.main.fragment.home.adapter.HomeAdapter
import com.nalldev.gxsales.presentation.main.domain.model.ProfileResponse
import com.nalldev.gxsales.presentation.main.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<HomeViewModel>()
    private val homeAdapter by lazy {
        HomeAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObserver()
        setupUI()
        setupListeners()
    }

    private fun setupListeners() = with(binding) {
        tvDate.setOnClickListener {
            requireContext()
                .createDateRangeBuilder()
                .setOnChoose("Save") { start, end ->
                    viewModel.getLeadsDashboard(
                        DateConverter.convertLongToDateString(start),
                        DateConverter.convertLongToDateString(end)
                    )
                }
                .build()
                .show()
        }
    }

    private fun setupObserver() = with(viewModel) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    rangeDate.collect { date ->
                        binding.tvDate.text = String.format(
                            "%s - %s",
                            DateConverter.convertDDMMYYYYToDMMMYYYY(date.first),
                            DateConverter.convertDDMMYYYYToDMMMYYYY(date.second)
                        )
                    }
                }
            }
        }

        stateLeadsDashboard.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Error -> {}

                is UiState.Loading -> {}

                is UiState.Success -> {
                    homeAdapter.submitList(state.data)
                }
            }
        }

        profile.observe(viewLifecycleOwner) {
            updateProfile(it)
        }
    }

    private fun setupUI() = with(binding) {
        rvHome.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            addItemDecoration(GridSpacingItemDecoration(2, 48, true))
            adapter = homeAdapter
        }
    }

    private fun updateProfile(data: ProfileResponse) = with(binding) {
        tvName.text = data.name
        tvEmail.text = data.email
    }
}