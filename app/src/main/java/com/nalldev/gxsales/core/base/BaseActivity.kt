package com.nalldev.gxsales.core.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.nalldev.gxsales.R
import taimoor.sultani.sweetalert2.Sweetalert

abstract class BaseActivity<B : ViewBinding> : AppCompatActivity() {

    private var _binding: B? = null
    protected val binding get() = _binding!!

    private lateinit var loadingDialog : Sweetalert

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = getViewBinding()
        setContentView(binding.root)
        loadingDialog = Sweetalert(this, Sweetalert.PROGRESS_TYPE).also {
            it.setCancelable(false)
        }
        setupObserver()
        setupUI()
        setupListeners()
    }

    abstract fun getViewBinding(): B

    abstract fun setupObserver()

    abstract fun setupUI()

    abstract fun setupListeners()

    fun Context.startActivityTo(activity: Class<*>, builderAction: (Intent.() -> Unit)? = null) {
        val intent = Intent(this, activity).apply {
            if (builderAction != null) {
                builderAction()
            }
        }
        startActivity(intent)
        enterActivityAnimate()
    }

    protected fun showLoading(customText : String? = null) {
        if (::loadingDialog.isInitialized) {
            loadingDialog.dismiss()
        }
        loadingDialog.contentText = customText ?: "Loading..."
        loadingDialog.show()
    }

    protected fun dismissLoading() {
        if (::loadingDialog.isInitialized) {
            loadingDialog.dismiss()
        }
    }

    protected fun closeActivity() {
        finish()
        closeActivityAnimate()
    }

    private fun enterActivityAnimate() {
        overridePendingTransition(R.anim.slide_up, R.anim.stay)
    }

    private fun closeActivityAnimate() {
        overridePendingTransition(R.anim.stay, R.anim.slide_down)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        closeActivityAnimate()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        closeActivityAnimate()
        return super.onSupportNavigateUp()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}