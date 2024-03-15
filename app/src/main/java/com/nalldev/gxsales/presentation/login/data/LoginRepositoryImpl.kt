package com.nalldev.gxsales.presentation.login.data

import com.nalldev.gxsales.core.remote.ApiService
import com.nalldev.gxsales.presentation.login.domain.model.LoginRequest
import com.nalldev.gxsales.presentation.login.domain.model.LoginResponse
import com.nalldev.gxsales.presentation.login.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : LoginRepository {
    override suspend fun doLogin(loginRequest: LoginRequest): Flow<LoginResponse> = flow {
        val doLogin =  apiService.doLogin(loginRequest)
        emit(doLogin)
    }
}