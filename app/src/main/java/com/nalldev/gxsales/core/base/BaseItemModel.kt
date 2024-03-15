package com.nalldev.gxsales.core.base

import com.google.gson.annotations.SerializedName

data class BaseItemModel(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int
)
