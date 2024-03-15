package com.nalldev.gxsales.core.repository.form

import com.nalldev.gxsales.core.base.BaseItemModel

interface FormRepository {
    fun getBranchOffices() : List<BaseItemModel>
    fun getTypes() : List<BaseItemModel>
    fun getStatuses() : List<BaseItemModel>
    fun getChannels() : List<BaseItemModel>
    fun getMedias() : List<BaseItemModel>
    fun getSources() : List<BaseItemModel>
    fun getProbabilities() : List<BaseItemModel>
    fun setBranchOffices(items : List<BaseItemModel>)
    fun setTypes(items : List<BaseItemModel>)
    fun setStatuses(items : List<BaseItemModel>)
    fun setChannels(items : List<BaseItemModel>)
    fun setMedias(items : List<BaseItemModel>)
    fun setSources(items : List<BaseItemModel>)
    fun setProbabilities(items : List<BaseItemModel>)
}