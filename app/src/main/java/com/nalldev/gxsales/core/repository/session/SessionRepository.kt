package com.nalldev.gxsales.core.repository.session

interface SessionRepository {
    fun getToken() : String
    fun setToken(token : String)
}