package com.nalldev.gxsales.presentation.add_edit_lead.domain.model

data class LeadModel(
    val branchOfficeId: String,
    val probabilityId: String,
    val typeId: String,
    val channelId: String,
    val mediaId: String,
    val sourceId: String,
    val fullName: String,
    val email: String,
    val phone: String,
    val address: String,
    val latitude: String,
    val longitude: String,
    val companyName: String,
    val generalNotes: String,
    val gender: String,
    val idNumber: String
)
