package com.nalldev.gxsales.presentation.main.domain.model

import com.google.gson.annotations.SerializedName

data class LeadsDashboardResponse(

	@field:SerializedName("total")
	val total: Int,

	@field:SerializedName("statuses")
	val statuses: List<StatusesItem>
)

data class StatusesItem(

	@field:SerializedName("total")
	val total: Int,

	@field:SerializedName("name")
	val name: String
)
