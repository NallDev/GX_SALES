package com.nalldev.gxsales.presentation.main.domain.repository

import com.nalldev.gxsales.presentation.main.domain.model.LeadResponse
import kotlinx.coroutines.flow.Flow

interface LeadsRepository {
    suspend fun getListLead() : Flow<List<LeadResponse>>
}