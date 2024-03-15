package com.nalldev.gxsales.presentation.main.domain.model

import com.google.gson.annotations.SerializedName

data class LeadResponse(

	@field:SerializedName("address")
	val address: String,

	@field:SerializedName("gender")
	val gender: String,

	@field:SerializedName("probability")
	val probability: Probability,

	@field:SerializedName("latitude")
	val latitude: String,

	@field:SerializedName("companyName")
	val companyName: String,

	@field:SerializedName("channel")
	val channel: Channel,

	@field:SerializedName("fullName")
	val fullName: String,

	@field:SerializedName("media")
	val media: Media,

	@field:SerializedName("source")
	val source: Source,

	@field:SerializedName("type")
	val type: Type,

	@field:SerializedName("number")
	val number: String,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("branchOffice")
	val branchOffice: BranchOffice,

	@field:SerializedName("phone")
	val phone: String,

	@field:SerializedName("IDNumberPhoto")
	val iDNumberPhoto: String,

	@field:SerializedName("generalNotes")
	val generalNotes: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("longitude")
	val longitude: String,

	@field:SerializedName("IDNumber")
	val iDNumber: String,

	@field:SerializedName("status")
	val status: Status
)

data class Media(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int
)

data class Status(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int
)

data class Probability(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int
)

data class BranchOffice(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int
)

data class Source(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int
)

data class Channel(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int
)

data class Type(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int
)
