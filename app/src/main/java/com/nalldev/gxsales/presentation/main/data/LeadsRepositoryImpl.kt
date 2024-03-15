package com.nalldev.gxsales.presentation.main.data

import com.nalldev.gxsales.core.remote.ApiService
import com.nalldev.gxsales.core.repository.session.SessionRepository
import com.nalldev.gxsales.presentation.main.domain.repository.LeadsRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LeadsRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    sessionRepository: SessionRepository
) : LeadsRepository {
    private val token = sessionRepository.getToken()
    override suspend fun getListLead() = flow {
        val request = apiService.getListLead(token)
        emit(request.data)
    }
}