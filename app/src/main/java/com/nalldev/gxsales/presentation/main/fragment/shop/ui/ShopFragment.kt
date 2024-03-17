package com.nalldev.gxsales.presentation.main.fragment.shop.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.nalldev.gxsales.core.util.GridSpacingItemDecoration
import com.nalldev.gxsales.databinding.FragmentShopBinding
import com.nalldev.gxsales.presentation.main.fragment.shop.adapter.ShopAdapter
import com.nalldev.gxsales.presentation.main.viewmodel.ShopViewModel
import kotlinx.coroutines.launch

class ShopFragment : Fragment() {
    private var _binding : FragmentShopBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<ShopViewModel>()

    private val shopAdapter by lazy {
        ShopAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShopBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObserver()
        setupUI()
        setupListeners()
    }

    private fun setupObserver() = with(viewModel) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    listItemShop.collect { listData ->
                        shopAdapter.submitList(listData)
                    }
                }
            }
        }
    }

    private fun setupUI() = with(binding) {
        rvShop.apply {
            addItemDecoration(GridSpacingItemDecoration(1, 48, true))
            adapter = shopAdapter
        }
    }

    private fun setupListeners() = with(binding) {
        etSearch.doOnTextChanged { text, _, _, _ ->
            viewModel.setTitle(text.toString())
        }
    }

}