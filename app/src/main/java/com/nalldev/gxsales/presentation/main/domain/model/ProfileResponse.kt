package com.nalldev.gxsales.presentation.main.domain.model

import com.google.gson.annotations.SerializedName

data class ProfileResponse(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("email")
	val email: String
)
