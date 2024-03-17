package com.nalldev.gxsales.presentation.add_edit_lead.ui

import androidx.activity.viewModels
import com.nalldev.gxsales.core.base.BaseActivity
import com.nalldev.gxsales.core.util.UiState
import com.nalldev.gxsales.core.util.showErrorToast
import com.nalldev.gxsales.core.util.sweetAlertCancelable
import com.nalldev.gxsales.core.viewmodel.FormViewModel
import com.nalldev.gxsales.databinding.ActivityAddEditBinding
import com.nalldev.gxsales.presentation.add_edit_lead.viewmodel.AddEditLeadViewModel
import com.nalldev.gxsales.presentation.main.domain.model.LeadResponse
import com.nalldev.gxsales.presentation.main.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import taimoor.sultani.sweetalert2.Sweetalert

@AndroidEntryPoint
class AddEditActivity : BaseActivity<ActivityAddEditBinding>() {

    private val viewModel by viewModels<AddEditLeadViewModel>()
    private val formViewModel by viewModels<FormViewModel>()

    override fun getViewBinding(): ActivityAddEditBinding {
        return ActivityAddEditBinding.inflate(layoutInflater)
    }

    override fun setupObserver() = with(viewModel) {
        stateCreateLead.observe(this@AddEditActivity) { state ->
            when(state) {
                is UiState.Error -> {
                    dismissLoading()
                    showErrorToast(state.message)
                }
                is UiState.Loading -> showLoading()
                is UiState.Success -> {
                    dismissLoading()
                    sweetAlertCancelable(
                        title = "Success",
                        content = "Create lead success",
                        type = Sweetalert.SUCCESS_TYPE,
                        confirmText = "Retry",
                        onConfirm = {
                            startActivityTo(MainActivity::class.java)
                            finishAffinity()
                        }
                    )
                }
            }
        }

        stateUpdateLead.observe(this@AddEditActivity) { state ->
            when(state) {
                is UiState.Error -> {
                    dismissLoading()
                    showErrorToast(state.message)
                }
                is UiState.Loading -> showLoading("Updating...")
                is UiState.Success -> {
                    dismissLoading()
                    sweetAlertCancelable(
                        title = "Success",
                        content = "Update lead success",
                        type = Sweetalert.SUCCESS_TYPE,
                        confirmText = "Retry",
                        onConfirm = {
                            startActivityTo(MainActivity::class.java)
                            finishAffinity()
                        }
                    )
                }
            }
        }
    }

    override fun setupUI() {
        formViewModel.fetchForm()
        val data = intent.getParcelableExtra<LeadResponse>(LEAD)
        setupLeadData(data)
    }

    private fun setupLeadData(data: LeadResponse?) = with(viewModel) {
        data?.let {
            binding.tvTitle.text = String.format("Update Lead")
            isUpdate = true
            idLead = it.id.toString()
            setBranchOfficeId(it.branchOffice.id.toString())
            setProbabilityId(it.probability.id.toString())
            setTypeId(it.type.id.toString())
            setChannelId(it.channel.id.toString())
            setMediaId(it.media.id.toString())
            setStatusId(it.status.id.toString())
            setSourceId(it.source.id.toString())
            setFullName(it.fullName)
            setEmail(it.email)
            setPhone(it.phone)
            setAddress(it.address)
            setLatitude(it.latitude)
            setLongitude(it.longitude)
            setGeneralNotes(it.generalNotes)
            setGender(it.gender)
            setIdNumber(it.iDNumber)
        }
    }

    override fun setupListeners() {
    }

    companion object {
        const val LEAD = "lead"
    }

}