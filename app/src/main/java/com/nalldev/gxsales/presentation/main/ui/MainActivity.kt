package com.nalldev.gxsales.presentation.main.ui

import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.nalldev.gxsales.R
import com.nalldev.gxsales.core.base.BaseActivity
import com.nalldev.gxsales.core.util.UiState
import com.nalldev.gxsales.core.util.showErrorToast
import com.nalldev.gxsales.core.util.showWarningToast
import com.nalldev.gxsales.core.util.sweetAlertCancelable
import com.nalldev.gxsales.databinding.ActivityMainBinding
import com.nalldev.gxsales.presentation.login.ui.LoginActivity
import com.nalldev.gxsales.presentation.main.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import taimoor.sultani.sweetalert2.Sweetalert

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val viewModel by viewModels<HomeViewModel>()

    override fun getViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun setupObserver() = with(viewModel){
        stateLeadsDashboard.observe(this@MainActivity) {state ->
            when(state) {
                is UiState.Error -> {
                    dismissLoading()
                    if (state.message.lowercase() == "Unauthenticated!".lowercase()) {
                        sweetAlertCancelable(
                            title = "Uppss.. sorry",
                            content = "Your session has been over",
                            type = Sweetalert.WARNING_TYPE,
                            confirmText = "Login",
                            onConfirm = {
                                clearSession()
                                startActivityTo(LoginActivity::class.java)
                                finish()
                            }
                        )
                        return@observe
                    }
                    showErrorToast(state.message)
                }
                is UiState.Loading -> showLoading()
                is UiState.Success -> {
                    dismissLoading()
                }
            }
        }
    }

    override fun setupUI() = with(binding) {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        bottomBar.setupWithNavController(navController)
    }

    override fun setupListeners() = with(binding){
        fabAdd.setOnClickListener {
            showWarningToast("ADUH")
        }
    }
}