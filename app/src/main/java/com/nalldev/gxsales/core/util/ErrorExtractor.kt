package com.nalldev.gxsales.core.util

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeoutException

object ErrorExtractor {
    fun errorMessage(throwable: Throwable): String {
        return when (throwable) {
            is HttpException -> {
                try {
                    val errorBody = throwable.response()?.errorBody()?.string()
                    val type = object : TypeToken<ErrorModel>() {}.type
                    val errorResponse: ErrorModel? = Gson().fromJson(errorBody, type)
                    errorResponse?.message ?: "Something error... try again later"
                } catch (e: Exception) {
                    "There is a problem... \nBecause : ${e.localizedMessage}\nPlease wait for a moment"
                }
            }
            is TimeoutException -> {
                "Timeout, please try again"
            }
            is IOException -> {
                "Please check your internet connection and try again later"
            }
            else -> {
                throwable.localizedMessage ?: "Unknown error occurred"
            }
        }
    }

    data class ErrorModel(val message : String)
}