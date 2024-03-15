package com.nalldev.gxsales.presentation.login.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.nalldev.gxsales.R
import com.nalldev.gxsales.core.base.BaseActivity
import com.nalldev.gxsales.core.util.UiState
import com.nalldev.gxsales.core.util.showErrorToast
import com.nalldev.gxsales.databinding.ActivityLoginBinding
import com.nalldev.gxsales.presentation.main.ui.MainActivity
import com.nalldev.gxsales.presentation.login.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun getViewBinding(): ActivityLoginBinding {
        return ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun setupObserver() = with(viewModel) {
        lifecycleScope.launch(Dispatchers.IO) {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    email.collect { email ->
                        if (binding.etEmail.text.toString() != email) {
                            binding.etEmail.setText(email)
                        }
                    }
                }

                launch {
                    password.collect { password ->
                        if (binding.etPassword.text.toString() != password) {
                            binding.etPassword.setText(password)
                        }
                    }
                }

            }
        }

        stateLogin.observe(this@LoginActivity) {state ->
            when(state) {
                is UiState.Error -> {
                    dismissLoading()
                    showErrorToast(state.message)
                }
                is UiState.Loading -> showLoading()
                is UiState.Success -> {
                    dismissLoading()
                    startActivityTo(MainActivity::class.java)
                    finish()
                }
            }
        }

        isInputFilled.observe(this@LoginActivity) {isFilled ->
            stateLoginButton(isFilled)
        }
    }

    private fun stateLoginButton(filled: Boolean) = with(binding.btnLogin) {
        if (filled) {
            isEnabled = true
            backgroundTintList = ContextCompat.getColorStateList(this@LoginActivity, R.color.yellow_btn)
        } else {
            isEnabled = false
            backgroundTintList = ContextCompat.getColorStateList(this@LoginActivity, R.color.outline_stroke)
        }
    }

    override fun setupUI() {
    }

    override fun setupListeners() = with(binding) {
        etEmail.doOnTextChanged { text, _, _, _ ->
            viewModel.setEmail(text.toString())
        }

        etPassword.doOnTextChanged { text, _, _, _ ->
            viewModel.setPassword(text.toString())
        }

        btnLogin.setOnClickListener {
            viewModel.doLogin()
        }
    }
}