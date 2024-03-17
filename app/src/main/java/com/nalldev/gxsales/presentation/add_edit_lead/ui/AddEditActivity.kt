package com.nalldev.gxsales.presentation.add_edit_lead.ui

import androidx.activity.viewModels
import com.nalldev.gxsales.core.base.BaseActivity
import com.nalldev.gxsales.core.util.UiState
import com.nalldev.gxsales.core.util.showErrorToast
import com.nalldev.gxsales.core.viewmodel.FormViewModel
import com.nalldev.gxsales.databinding.ActivityAddEditBinding
import com.nalldev.gxsales.presentation.add_edit_lead.viewmodel.AddEditLeadViewModel
import com.nalldev.gxsales.presentation.main.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint

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
                    startActivityTo(MainActivity::class.java)
                    finishAffinity()
                }
            }
        }
    }

    override fun setupUI() {
        formViewModel.fetchForm()
    }

    override fun setupListeners() {
    }

}