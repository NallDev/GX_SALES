package com.nalldev.gxsales.core.repository.form

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nalldev.gxsales.core.base.BaseItemModel
import com.nalldev.gxsales.core.util.Constant.BRANCH_KEY
import com.nalldev.gxsales.core.util.Constant.CHANNELS_KEY
import com.nalldev.gxsales.core.util.Constant.MEDIAS_KEY
import com.nalldev.gxsales.core.util.Constant.PROBABILITIES_KEY
import com.nalldev.gxsales.core.util.Constant.SOURCES_KEY
import com.nalldev.gxsales.core.util.Constant.STATUSES_KEY
import com.nalldev.gxsales.core.util.Constant.TYPES_KEY
import javax.inject.Inject

class FormRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : FormRepository {
    override fun getBranchOffices(): List<BaseItemModel> {
        val json = sharedPreferences.getString(BRANCH_KEY, null)
        val type = object : TypeToken<List<BaseItemModel>>() {}.type
        return Gson().fromJson(json, type) ?: emptyList()
    }

    override fun getTypes(): List<BaseItemModel> {
        val json = sharedPreferences.getString(TYPES_KEY, null)
        val type = object : TypeToken<List<BaseItemModel>>() {}.type
        return Gson().fromJson(json, type) ?: emptyList()
    }

    override fun getStatuses(): List<BaseItemModel> {
        val json = sharedPreferences.getString(STATUSES_KEY, null)
        val type = object : TypeToken<List<BaseItemModel>>() {}.type
        return Gson().fromJson(json, type) ?: emptyList()
    }

    override fun getChannels(): List<BaseItemModel> {
        val json = sharedPreferences.getString(CHANNELS_KEY, null)
        val type = object : TypeToken<List<BaseItemModel>>() {}.type
        return Gson().fromJson(json, type) ?: emptyList()
    }

    override fun getMedias(): List<BaseItemModel> {
        val json = sharedPreferences.getString(MEDIAS_KEY, null)
        val type = object : TypeToken<List<BaseItemModel>>() {}.type
        return Gson().fromJson(json, type) ?: emptyList()
    }

    override fun getSources(): List<BaseItemModel> {
        val json = sharedPreferences.getString(SOURCES_KEY, null)
        val type = object : TypeToken<List<BaseItemModel>>() {}.type
        return Gson().fromJson(json, type) ?: emptyList()
    }

    override fun getProbabilities(): List<BaseItemModel> {
        val json = sharedPreferences.getString(PROBABILITIES_KEY, null)
        val type = object : TypeToken<List<BaseItemModel>>() {}.type
        return Gson().fromJson(json, type) ?: emptyList()
    }

    override fun setBranchOffices(items: List<BaseItemModel>) {
        val dataToJson = Gson().toJson(items)
        sharedPreferences.edit().putString(BRANCH_KEY, dataToJson).apply()
    }

    override fun setTypes(items: List<BaseItemModel>) {
        val dataToJson = Gson().toJson(items)
        sharedPreferences.edit().putString(TYPES_KEY, dataToJson).apply()
    }

    override fun setStatuses(items: List<BaseItemModel>) {
        val dataToJson = Gson().toJson(items)
        sharedPreferences.edit().putString(STATUSES_KEY, dataToJson).apply()
    }

    override fun setChannels(items: List<BaseItemModel>) {
        val dataToJson = Gson().toJson(items)
        sharedPreferences.edit().putString(CHANNELS_KEY, dataToJson).apply()
    }

    override fun setMedias(items: List<BaseItemModel>) {
        val dataToJson = Gson().toJson(items)
        sharedPreferences.edit().putString(MEDIAS_KEY, dataToJson).apply()
    }

    override fun setSources(items: List<BaseItemModel>) {
        val dataToJson = Gson().toJson(items)
        sharedPreferences.edit().putString(SOURCES_KEY, dataToJson).apply()
    }

    override fun setProbabilities(items: List<BaseItemModel>) {
        val dataToJson = Gson().toJson(items)
        sharedPreferences.edit().putString(PROBABILITIES_KEY, dataToJson).apply()
    }
}