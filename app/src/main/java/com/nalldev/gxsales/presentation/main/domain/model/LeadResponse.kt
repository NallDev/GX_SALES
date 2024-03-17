package com.nalldev.gxsales.presentation.main.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class LeadResponse(

	@field:SerializedName("address")
	val address: String,

	@field:SerializedName("gender")
	val gender: String,

	@field:SerializedName("probability")
	val probability: Probability,

	@field:SerializedName("latitude")
	val latitude: String,

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
) : Parcelable

@Parcelize
data class Media(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int
) : Parcelable

@Parcelize
data class Status(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int
) : Parcelable

@Parcelize
data class Probability(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int
) : Parcelable

@Parcelize
data class BranchOffice(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int
) : Parcelable

@Parcelize
data class Source(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int
) : Parcelable

@Parcelize
data class Channel(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int
) : Parcelable

@Parcelize
data class Type(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int
) : Parcelable
