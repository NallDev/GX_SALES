package com.nalldev.gxsales.presentation.main.dialog.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nalldev.gxsales.databinding.BottomSheetProfileBinding
import com.nalldev.gxsales.presentation.main.viewmodel.HomeViewModel

class BottomSheetProfile(private val name : String, private val email : String) : BottomSheetDialogFragment() {
    private var _binding : BottomSheetProfileBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel by activityViewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            tvName.text = name
            tvEmail.text = email
            btnLogout.setOnClickListener {
                dismiss()
                homeViewModel.doLogout()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}