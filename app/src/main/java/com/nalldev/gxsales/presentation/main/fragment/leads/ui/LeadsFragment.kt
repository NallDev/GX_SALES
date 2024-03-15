package com.nalldev.gxsales.presentation.main.fragment.leads.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.nalldev.gxsales.core.base.BaseFragment
import com.nalldev.gxsales.core.util.GridSpacingItemDecoration
import com.nalldev.gxsales.databinding.FragmentLeadsBinding
import com.nalldev.gxsales.presentation.main.dialog.filter_leads.ui.BottomSheetFilterLeads
import com.nalldev.gxsales.presentation.main.fragment.leads.adapter.LeadsAdapter
import com.nalldev.gxsales.presentation.main.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LeadsFragment : BaseFragment<FragmentLeadsBinding>() {

    private val viewModel by activityViewModels<HomeViewModel>()

    private val leadsAdapter by lazy {
        LeadsAdapter()
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLeadsBinding {
        return FragmentLeadsBinding.inflate(inflater, container, false)
    }

    override fun setupObserver(): Unit = with(viewModel) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    filteredLeadsFlow.collect {
                        leadsAdapter.submitList(it)
                    }
                }
            }
        }
    }

    override fun setupUI(): Unit = with(binding) {
        rvLeads.apply {
            addItemDecoration(GridSpacingItemDecoration(1, 48, true))
            adapter = leadsAdapter
        }
    }

    override fun setupListeners(): Unit = with(binding) {
        etName.doOnTextChanged { text, _, _, _ ->
            viewModel.setName(text.toString())
        }

        btnFilter.setOnClickListener {
            BottomSheetFilterLeads()
                .show(requireActivity().supportFragmentManager, "filter")
        }
    }
}