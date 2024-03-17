package com.nalldev.gxsales.presentation.add_edit_lead.data

import com.nalldev.gxsales.core.remote.ApiService
import com.nalldev.gxsales.core.repository.session.SessionRepository
import com.nalldev.gxsales.presentation.add_edit_lead.domain.model.LeadModel
import com.nalldev.gxsales.presentation.add_edit_lead.domain.repository.AddEditLeadRepository
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class AddEditLeadRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val sessionRepository: SessionRepository
) : AddEditLeadRepository {
    override suspend fun createLead(leadModel: LeadModel, image : MultipartBody.Part?) = flow {
        val token = sessionRepository.getToken()
        val branchOfficeIdBody = leadModel.branchOfficeId.toRequestBody("text/plain".toMediaTypeOrNull())
        val probabilityId = leadModel.probabilityId.toRequestBody("text/plain".toMediaTypeOrNull())
        val typeId = leadModel.typeId.toRequestBody("text/plain".toMediaTypeOrNull())
        val channelId = leadModel.channelId.toRequestBody("text/plain".toMediaTypeOrNull())
        val mediaId = leadModel.mediaId.toRequestBody("text/plain".toMediaTypeOrNull())
        val sourceId = leadModel.sourceId.toRequestBody("text/plain".toMediaTypeOrNull())
        val fullName = leadModel.fullName.toRequestBody("text/plain".toMediaTypeOrNull())
        val email = leadModel.email.toRequestBody("text/plain".toMediaTypeOrNull())
        val phone = leadModel.phone.toRequestBody("text/plain".toMediaTypeOrNull())
        val address = leadModel.address.toRequestBody("text/plain".toMediaTypeOrNull())
        val latitude = leadModel.latitude.toRequestBody("text/plain".toMediaTypeOrNull())
        val longitude = leadModel.longitude.toRequestBody("text/plain".toMediaTypeOrNull())
        val companyName = leadModel.companyName.toRequestBody("text/plain".toMediaTypeOrNull())
        val generalNotes = leadModel.generalNotes.toRequestBody("text/plain".toMediaTypeOrNull())
        val gender = leadModel.gender.toRequestBody("text/plain".toMediaTypeOrNull())
        val idNumber = leadModel.idNumber.toRequestBody("text/plain".toMediaTypeOrNull())

        val request = apiService.createLead(
            token,
            branchOfficeIdBody,
            probabilityId,
            typeId,
            channelId,
            mediaId,
            sourceId,
            fullName,
            email,
            phone,
            address,
            latitude,
            longitude,
            companyName,
            generalNotes,
            gender,
            idNumber,
            image)
        emit(request.data)
    }

    override suspend fun updateLead(id : String, leadModel: LeadModel, image : MultipartBody.Part?) = flow {
        val token = sessionRepository.getToken()
        val branchOfficeIdBody = leadModel.branchOfficeId.toRequestBody("text/plain".toMediaTypeOrNull())
        val probabilityId = leadModel.probabilityId.toRequestBody("text/plain".toMediaTypeOrNull())
        val typeId = leadModel.typeId.toRequestBody("text/plain".toMediaTypeOrNull())
        val channelId = leadModel.channelId.toRequestBody("text/plain".toMediaTypeOrNull())
        val mediaId = leadModel.mediaId.toRequestBody("text/plain".toMediaTypeOrNull())
        val sourceId = leadModel.sourceId.toRequestBody("text/plain".toMediaTypeOrNull())
        val fullName = leadModel.fullName.toRequestBody("text/plain".toMediaTypeOrNull())
        val email = leadModel.email.toRequestBody("text/plain".toMediaTypeOrNull())
        val phone = leadModel.phone.toRequestBody("text/plain".toMediaTypeOrNull())
        val address = leadModel.address.toRequestBody("text/plain".toMediaTypeOrNull())
        val latitude = leadModel.latitude.toRequestBody("text/plain".toMediaTypeOrNull())
        val longitude = leadModel.longitude.toRequestBody("text/plain".toMediaTypeOrNull())
        val companyName = leadModel.companyName.toRequestBody("text/plain".toMediaTypeOrNull())
        val generalNotes = leadModel.generalNotes.toRequestBody("text/plain".toMediaTypeOrNull())
        val gender = leadModel.gender.toRequestBody("text/plain".toMediaTypeOrNull())
        val idNumber = leadModel.idNumber.toRequestBody("text/plain".toMediaTypeOrNull())

        val request = apiService.updateLead(
            token,
            id,
            branchOfficeIdBody,
            probabilityId,
            typeId,
            channelId,
            mediaId,
            sourceId,
            fullName,
            email,
            phone,
            address,
            latitude,
            longitude,
            companyName,
            generalNotes,
            gender,
            idNumber,
            image)
        emit(request.data)
    }
}