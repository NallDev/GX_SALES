package com.nalldev.gxsales.presentation.main.data

import android.util.Log
import com.nalldev.gxsales.core.remote.ApiService
import com.nalldev.gxsales.core.repository.session.SessionRepository
import com.nalldev.gxsales.presentation.main.domain.repository.HomeRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    sessionRepository: SessionRepository
) : HomeRepository {
    private val token = sessionRepository.getToken()
    override suspend fun getLeadsDashboard(fromDate: String, toDate: String) = flow {
        Log.e("TOKEN", token)
        val request = apiService.getLeadsDashboard(token, fromDate, toDate)
        emit(request.data.statuses)
    }

    override suspend fun getProfile() = flow {
        val request = apiService.getProfile(token)
        emit(request.data)
    }

    override suspend fun getBranchOffices() = flow {
        val request = apiService.getBranchOffices(token)
        emit(request.data)
    }

    override suspend fun getTypes() = flow {
        val request = apiService.getTypes(token)
        emit(request.data)
    }

    override suspend fun getStatuses() = flow {
        val request = apiService.getStatuses(token)
        emit(request.data)
    }

    override suspend fun getChannels() = flow {
        val request = apiService.getChannels(token)
        emit(request.data)
    }

    override suspend fun getMedias() = flow {
        val request = apiService.getMedias(token)
        emit(request.data)
    }

    override suspend fun getSources() = flow {
        val request = apiService.getSources(token)
        emit(request.data)
    }

    override suspend fun getProbabilities() = flow {
        val request = apiService.getProbabilities(token)
        emit(request.data)
    }
}