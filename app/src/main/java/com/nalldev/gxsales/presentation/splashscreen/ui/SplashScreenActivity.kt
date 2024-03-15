package com.nalldev.gxsales.presentation.splashscreen.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.nalldev.gxsales.R
import com.nalldev.gxsales.core.base.BaseActivity
import com.nalldev.gxsales.databinding.ActivitySplashScreenBinding
import com.nalldev.gxsales.presentation.main.ui.MainActivity
import com.nalldev.gxsales.presentation.login.ui.LoginActivity
import com.nalldev.gxsales.presentation.splashscreen.viewmodel.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashScreenActivity : BaseActivity<ActivitySplashScreenBinding>() {

    private val viewModel by viewModels<SplashViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun getViewBinding(): ActivitySplashScreenBinding {
        return ActivitySplashScreenBinding.inflate(layoutInflater)
    }

    override fun setupObserver() = with(viewModel){
        hasSession.observe(this@SplashScreenActivity) { token ->
            if (token.isNotBlank()) {
                startActivityTo(MainActivity::class.java)
                finish()
            } else {
                startActivityTo(LoginActivity::class.java)
                finish()
            }
        }
    }

    override fun setupUI() {
    }

    override fun setupListeners() {
    }
}