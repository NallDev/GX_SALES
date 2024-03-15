package com.nalldev.gxsales.core.repository.session

import android.content.SharedPreferences
import android.util.Log
import com.nalldev.gxsales.core.util.Constant.AUTH_KEY
import javax.inject.Inject

class SessionRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : SessionRepository {
    override fun getToken(): String {
        return sharedPreferences.getString(AUTH_KEY, "") ?: ""
    }

    override fun setToken(token: String) {
        Log.e("SESSION IN REPOSITORY CLEAR", "PROCESS")
        sharedPreferences.edit().remove(AUTH_KEY).apply()
        sharedPreferences.edit().putString(AUTH_KEY, token).apply()
        Log.e("SESSION IN REPOSITORY2 CLEAR", "${sharedPreferences.getString(AUTH_KEY, "")}")
    }
}