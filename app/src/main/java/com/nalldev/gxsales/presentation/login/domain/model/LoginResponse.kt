package com.nalldev.gxsales.presentation.login.domain.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("type")
	val type: String,

	@field:SerializedName("status")
	val status: String,

	@field:SerializedName("token")
	val token: String
)
