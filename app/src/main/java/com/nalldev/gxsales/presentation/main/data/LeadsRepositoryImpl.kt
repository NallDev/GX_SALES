package com.nalldev.gxsales.presentation.main.data

import com.nalldev.gxsales.core.base.BaseApiResponse
import com.nalldev.gxsales.core.remote.ApiService
import com.nalldev.gxsales.core.repository.session.SessionRepository
import com.nalldev.gxsales.presentation.main.domain.model.LeadResponse
import com.nalldev.gxsales.presentation.main.domain.repository.LeadsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.Response
import okhttp3.ResponseBody
import javax.inject.Inject

class LeadsRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val sessionRepository: SessionRepository
) : LeadsRepository {
    override suspend fun getListLead() = flow {
        val token = sessionRepository.getToken()
        val request = apiService.getListLead(token)
        emit(request.data)
    }

    override suspend fun deleteLead(id : String) = flow {
        val token = sessionRepository.getToken()
        val request = apiService.deleteLead(token, id)
        emit(request)
    }
}