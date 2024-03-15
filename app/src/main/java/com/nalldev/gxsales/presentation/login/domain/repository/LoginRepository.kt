package com.nalldev.gxsales.presentation.login.domain.repository

import com.nalldev.gxsales.presentation.login.domain.model.LoginRequest
import com.nalldev.gxsales.presentation.login.domain.model.LoginResponse
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    suspend fun doLogin(loginRequest: LoginRequest) : Flow<LoginResponse>
}