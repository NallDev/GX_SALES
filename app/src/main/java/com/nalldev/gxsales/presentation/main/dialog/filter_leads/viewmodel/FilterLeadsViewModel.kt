package com.nalldev.gxsales.presentation.main.dialog.filter_leads.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nalldev.gxsales.core.base.BaseItemModel
import com.nalldev.gxsales.core.repository.form.FormRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilterLeadsViewModel @Inject constructor(
    private val formRepository: FormRepository
) : ViewModel() {
    private val _listStatus : MutableLiveData<List<BaseItemModel>> = MutableLiveData()
    val listStatus : LiveData<List<BaseItemModel>> = _listStatus

    private val _listChannel : MutableLiveData<List<BaseItemModel>> = MutableLiveData()
    val listChannel : LiveData<List<BaseItemModel>> = _listChannel

    private val _listMedia : MutableLiveData<List<BaseItemModel>> = MutableLiveData()
    val listMedia : LiveData<List<BaseItemModel>> = _listMedia

    private val _listSource : MutableLiveData<List<BaseItemModel>> = MutableLiveData()
    val listSource : LiveData<List<BaseItemModel>> = _listSource

    fun fetchForm() = viewModelScope.launch {
        _listStatus.postValue(formRepository.getStatuses())
        _listChannel.postValue(formRepository.getChannels())
        _listMedia.postValue(formRepository.getMedias())
        _listSource.postValue(formRepository.getSources())
    }
}