package com.nalldev.gxsales.presentation.add_edit_lead.fragment

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.nalldev.gxsales.R
import com.nalldev.gxsales.core.base.BaseFragment
import com.nalldev.gxsales.core.util.createMultipartFromUri
import com.nalldev.gxsales.databinding.FragmentBranchOfficeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BranchOfficeFragment : BaseFragment<FragmentBranchOfficeBinding>() {
    private val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            val data = requireContext().createMultipartFromUri(uri, requireActivity().contentResolver)

            Log.e("IMAGESSS", data.toString())
        }
    }
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

    override fun setupListeners() = with(binding) {
        btnNext.setOnClickListener {
            findNavController().navigate(R.id.action_branchOfficeFragment_to_leadOfficeFragment)
        }

        containerAddImage.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
    }

}