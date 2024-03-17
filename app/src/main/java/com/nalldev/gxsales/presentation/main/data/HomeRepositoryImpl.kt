package com.nalldev.gxsales.presentation.main.data

import android.util.Log
import com.nalldev.gxsales.core.base.BaseApiResponse
import com.nalldev.gxsales.core.remote.ApiService
import com.nalldev.gxsales.core.repository.session.SessionRepository
import com.nalldev.gxsales.presentation.main.domain.model.ProfileResponse
import com.nalldev.gxsales.presentation.main.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val sessionRepository: SessionRepository
) : HomeRepository {
    override suspend fun getLeadsDashboard(fromDate: String, toDate: String) = flow {
        val token = sessionRepository.getToken()
        val request = apiService.getLeadsDashboard(token, fromDate, toDate)
        emit(request.data.statuses)
    }

    override suspend fun getProfile() = flow {
        val token = sessionRepository.getToken()
        val request = apiService.getProfile(token)
        emit(request.data)
    }

    override suspend fun doLogout() = flow {
        val token = sessionRepository.getToken()
        val request = apiService.doLogout(token)
        emit(request)
    }

    override suspend fun getBranchOffices() = flow {
        val token = sessionRepository.getToken()
        val request = apiService.getBranchOffices(token)
        emit(request.data)
    }

    override suspend fun getTypes() = flow {
        val token = sessionRepository.getToken()
        val request = apiService.getTypes(token)
        emit(request.data)
    }

    override suspend fun getStatuses() = flow {
        val token = sessionRepository.getToken()
        val request = apiService.getStatuses(token)
        emit(request.data)
    }

    override suspend fun getChannels() = flow {
        val token = sessionRepository.getToken()
        val request = apiService.getChannels(token)
        emit(request.data)
    }

    override suspend fun getMedias() = flow {
        val token = sessionRepository.getToken()
        val request = apiService.getMedias(token)
        emit(request.data)
    }

    override suspend fun getSources() = flow {
        val token = sessionRepository.getToken()
        val request = apiService.getSources(token)
        emit(request.data)
    }

    override suspend fun getProbabilities() = flow {
        val token = sessionRepository.getToken()
        val request = apiService.getProbabilities(token)
        emit(request.data)
    }
}