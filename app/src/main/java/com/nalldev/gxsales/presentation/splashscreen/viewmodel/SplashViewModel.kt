package com.nalldev.gxsales.presentation.splashscreen.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nalldev.gxsales.core.repository.session.SessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val sessionRepository: SessionRepository
) : ViewModel() {
    private val _hasSession : MutableLiveData<String> = MutableLiveData()
    val hasSession: LiveData<String> = _hasSession

    init {
        checkSession()
    }

    private fun checkSession() = viewModelScope.launch(Dispatchers.IO) {
        val token = sessionRepository.getToken()
        delay(3000)
        _hasSession.postValue(token)
    }
}