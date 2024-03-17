package com.nalldev.gxsales.core.repository.session

import android.content.SharedPreferences
import com.nalldev.gxsales.core.util.Constant.AUTH_KEY
import javax.inject.Inject

class SessionRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : SessionRepository {
    override fun getToken(): String {
        return sharedPreferences.getString(AUTH_KEY, "") ?: ""
    }

    override fun setToken(token: String) {
        sharedPreferences.edit().putString(AUTH_KEY, token).apply()
    }
}