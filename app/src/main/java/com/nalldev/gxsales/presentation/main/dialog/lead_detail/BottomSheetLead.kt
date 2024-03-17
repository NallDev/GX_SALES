package com.nalldev.gxsales.presentation.main.dialog.lead_detail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nalldev.gxsales.R
import com.nalldev.gxsales.databinding.BottomSheetLeadBinding
import com.nalldev.gxsales.presentation.add_edit_lead.ui.AddEditActivity
import com.nalldev.gxsales.presentation.main.domain.model.LeadResponse
import com.nalldev.gxsales.presentation.main.viewmodel.HomeViewModel

class BottomSheetLead(private val dataLead : LeadResponse) : BottomSheetDialogFragment() {
    private var _binding : BottomSheetLeadBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel by activityViewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetLeadBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        setupListener()
    }

    private fun setupUI() = with(binding){
        Glide.with(requireContext())
            .load(dataLead.iDNumberPhoto)
            .error(R.drawable.person)
            .into(ivProfile)

        tvName.text = dataLead.fullName
        tvPhone.text = dataLead.phone
    }


    private fun setupListener() = with(binding) {
        ivDelete.setOnClickListener {
            dismiss()
            homeViewModel.deleteLead(dataLead.id.toString())
        }
        ivEdit.setOnClickListener {
            dismiss()
            Intent(requireContext(), AddEditActivity::class.java).let {
                it.putExtra(AddEditActivity.LEAD, dataLead)
                startActivity(it)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}