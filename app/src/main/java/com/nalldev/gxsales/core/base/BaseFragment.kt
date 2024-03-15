package com.nalldev.gxsales.core.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.nalldev.gxsales.R

abstract class BaseFragment<B : ViewBinding> : Fragment() {
    private var _binding: B? = null
    protected val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = getViewBinding(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObserver()
        setupUI()
        setupListeners()
    }

    abstract fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): B

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

    private fun enterActivityAnimate() {
        activity?.overridePendingTransition(R.anim.slide_up, R.anim.stay)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}