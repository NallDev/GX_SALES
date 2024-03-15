package com.nalldev.gxsales.presentation.main.domain.repository

import com.nalldev.gxsales.core.base.BaseItemModel
import com.nalldev.gxsales.presentation.main.domain.model.ProfileResponse
import com.nalldev.gxsales.presentation.main.domain.model.StatusesItem
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    suspend fun getLeadsDashboard(fromDate : String, toDate : String) : Flow<List<StatusesItem>>
    suspend fun getProfile() : Flow<ProfileResponse>

    suspend fun getBranchOffices() : Flow<List<BaseItemModel>>
    suspend fun getTypes() : Flow<List<BaseItemModel>>
    suspend fun getStatuses() : Flow<List<BaseItemModel>>
    suspend fun getChannels() : Flow<List<BaseItemModel>>
    suspend fun getMedias() : Flow<List<BaseItemModel>>
    suspend fun getSources() : Flow<List<BaseItemModel>>
    suspend fun getProbabilities() : Flow<List<BaseItemModel>>
}