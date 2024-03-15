package com.nalldev.gxsales.presentation.add_edit_lead.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import com.nalldev.gxsales.R
import com.nalldev.gxsales.core.base.BaseActivity
import com.nalldev.gxsales.databinding.ActivityAddEditBinding

class AddEditActivity : BaseActivity<ActivityAddEditBinding>() {
    override fun getViewBinding(): ActivityAddEditBinding {
        return ActivityAddEditBinding.inflate(layoutInflater)
    }

    override fun setupObserver() {
    }

    override fun setupUI() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_fragment) as NavHostFragment
        val navController = navHostFragment.navController

    }

    override fun setupListeners() {
    }

}