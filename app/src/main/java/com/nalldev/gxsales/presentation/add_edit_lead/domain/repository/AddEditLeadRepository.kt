package com.nalldev.gxsales.presentation.add_edit_lead.domain.repository

import com.nalldev.gxsales.presentation.add_edit_lead.domain.model.LeadModel
import com.nalldev.gxsales.presentation.main.domain.model.LeadResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface AddEditLeadRepository {
    suspend fun createLead(leadModel: LeadModel, image : MultipartBody.Part?) : Flow<LeadResponse>
    suspend fun updateLead(id: String, leadModel: LeadModel, image : MultipartBody.Part?) : Flow<LeadResponse>
}