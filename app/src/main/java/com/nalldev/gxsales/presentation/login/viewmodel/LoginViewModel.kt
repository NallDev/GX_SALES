package com.nalldev.gxsales.presentation.login.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nalldev.gxsales.core.repository.session.SessionRepository
import com.nalldev.gxsales.core.util.ErrorExtractor
import com.nalldev.gxsales.core.util.UiState
import com.nalldev.gxsales.presentation.login.domain.model.LoginRequest
import com.nalldev.gxsales.presentation.login.domain.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val sessionRepository: SessionRepository
) : ViewModel() {
    private val _stateLogin: MutableLiveData<UiState<Unit>> = MutableLiveData()
    val stateLogin: LiveData<UiState<Unit>> = _stateLogin

    private val _isInputFilled: MutableLiveData<Boolean> = MutableLiveData()
    val isInputFilled: LiveData<Boolean> = _isInputFilled

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    init {
        checkInputField()
    }

    private fun checkInputField() = viewModelScope.launch(Dispatchers.IO) {
        val inputState = email.value.isNotBlank() && password.value.isNotBlank()
        _isInputFilled.postValue(inputState)
    }

    fun setEmail(email: String) {
        if (_email.value != email) {
            _email.value = email
            checkInputField()
        }
    }

    fun setPassword(password: String) {
        if (_password.value != password) {
            _password.value = password
            checkInputField()
        }
    }

    fun doLogin() = viewModelScope.launch {
        val requestBody = LoginRequest(
            email = _email.value,
            password = _password.value
        )

        loginRepository.doLogin(requestBody)
            .onStart {
                _stateLogin.postValue(UiState.Loading)
            }
            .catch { cause: Throwable ->
                _stateLogin.postValue(UiState.Error(ErrorExtractor.errorMessage(cause)))
            }
            .firstOrNull()
            ?.let {
                Log.e("REGISTERED TOKEN", "bearer ${it.token}")
                sessionRepository.setToken("bearer ${it.token}")
                _stateLogin.postValue(UiState.Success(Unit))
            }
    }
}