package com.nalldev.gxsales.presentation.add_edit_lead.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nalldev.gxsales.R
import com.nalldev.gxsales.core.base.BaseFragment
import com.nalldev.gxsales.databinding.FragmentBranchOfficeBinding

class BranchOfficeFragment : BaseFragment<FragmentBranchOfficeBinding>() {
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentBranchOfficeBinding {
        return FragmentBranchOfficeBinding.inflate(inflater, container, false)
    }

    override fun setupObserver() {
    }

    override fun setupUI() {
    }

    override fun setupListeners() {
    }

}