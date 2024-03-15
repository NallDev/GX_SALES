package com.nalldev.gxsales.presentation.add_edit_lead.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.nalldev.gxsales.R
import com.nalldev.gxsales.core.base.BaseFragment
import com.nalldev.gxsales.databinding.FragmentLeadOfficeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LeadOfficeFragment : BaseFragment<FragmentLeadOfficeBinding>() {
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLeadOfficeBinding {
        return FragmentLeadOfficeBinding.inflate(inflater, container, false)
    }

    override fun setupObserver() {
    }

    override fun setupUI() {
    }

    override fun setupListeners() = with(binding) {
        btnPrev.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}