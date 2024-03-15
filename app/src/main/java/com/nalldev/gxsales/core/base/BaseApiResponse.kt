package com.nalldev.gxsales.core.base

import com.google.gson.annotations.SerializedName

data class BaseApiResponse<T>(
    @field:SerializedName("data")
    val data: T,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("status")
    val status: String
)