package com.nalldev.gxsales.presentation.add_edit_lead.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nalldev.gxsales.core.util.ErrorExtractor
import com.nalldev.gxsales.core.util.UiState
import com.nalldev.gxsales.presentation.add_edit_lead.domain.model.LeadModel
import com.nalldev.gxsales.presentation.add_edit_lead.domain.repository.AddEditLeadRepository
import com.nalldev.gxsales.presentation.main.domain.model.LeadResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class AddEditLeadViewModel @Inject constructor(
    private val addEditLeadRepository: AddEditLeadRepository
) : ViewModel() {
    private val _stateCreateLead : MutableLiveData<UiState<LeadResponse>> = MutableLiveData()
    val stateCreateLead : LiveData<UiState<LeadResponse>> = _stateCreateLead

    private val _stateUpdateLead : MutableLiveData<UiState<LeadResponse>> = MutableLiveData()
    val stateUpdateLead : LiveData<UiState<LeadResponse>> = _stateUpdateLead

    private val _branchOfficeId = MutableStateFlow("")
    val branchOfficeId: StateFlow<String> = _branchOfficeId.asStateFlow()

    private val _statusId = MutableStateFlow("")
    val statusId: StateFlow<String> = _statusId.asStateFlow()

    private val _probabilityId = MutableStateFlow("")
    val probabilityId: StateFlow<String> = _probabilityId.asStateFlow()

    private val _typeId = MutableStateFlow("")
    val typeId: StateFlow<String> = _typeId.asStateFlow()

    private val _channelId = MutableStateFlow("")
    val channelId: StateFlow<String> = _channelId.asStateFlow()

    private val _mediaId = MutableStateFlow("")
    val mediaId: StateFlow<String> = _mediaId.asStateFlow()

    private val _sourceId = MutableStateFlow("")
    val sourceId: StateFlow<String> = _sourceId.asStateFlow()

    private val _fullName = MutableStateFlow("")
    val fullName: StateFlow<String> = _fullName.asStateFlow()

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _phone = MutableStateFlow("")
    val phone: StateFlow<String> = _phone.asStateFlow()

    private val _address = MutableStateFlow("")
    val address: StateFlow<String> = _address.asStateFlow()

    private val _latitude = MutableStateFlow("")
    val latitude: StateFlow<String> = _latitude.asStateFlow()

    private val _longitude = MutableStateFlow("")
    val longitude: StateFlow<String> = _longitude.asStateFlow()

    private val _generalNotes = MutableStateFlow("")
    val generalNotes: StateFlow<String> = _generalNotes.asStateFlow()

    private val _gender = MutableStateFlow("")
    val gender: StateFlow<String> = _gender.asStateFlow()

    private val _idNumber = MutableStateFlow("")
    val idNumber: StateFlow<String> = _idNumber.asStateFlow()

    private val _image : MutableStateFlow<MultipartBody.Part?> = MutableStateFlow(null)

    var idLead : String = "-1"
    var isUpdate : Boolean = false

    val isBranchMandatoryFilled = combine(
        _fullName,
        _phone,
        _latitude,
        _longitude
    ) { fullName, phone, latitude, longitude ->
        fullName.isNotBlank() && phone.isNotBlank() && latitude.isNotBlank() && longitude.isNotBlank()
    }

    val isLeadMandatoryFilled = combine(
        _typeId,
        _channelId,
        _mediaId,
        _statusId,
        _probabilityId
    ) { typeId, channelId, mediaId, statusId, probabilityId ->
        typeId.isNotBlank() && channelId.isNotBlank() && mediaId.isNotBlank() && statusId.isNotBlank() && probabilityId.isNotBlank()
    }

    fun createLead() = viewModelScope.launch(Dispatchers.IO) {
        val leadModel = LeadModel(
            branchOfficeId = _branchOfficeId.value,
            probabilityId = _probabilityId.value,
            typeId = _typeId.value,
            channelId = _channelId.value,
            mediaId = _mediaId.value,
            sourceId = _sourceId.value,
            fullName = _fullName.value,
            email = _email.value,
            phone = _phone.value,
            address = _address.value,
            latitude = _latitude.value,
            longitude = _longitude.value,
            companyName = "Global Xtreme",
            generalNotes = _generalNotes.value,
            gender = _gender.value,
            idNumber = _idNumber.value
        )

        addEditLeadRepository.createLead(leadModel ,_image.value)
            .onStart {
                _stateCreateLead.postValue(UiState.Loading)
            }
            .catch { cause: Throwable ->
                _stateCreateLead.postValue(UiState.Error(ErrorExtractor.errorMessage(cause)))
            }
            .firstOrNull()
            ?.let { data ->
                _stateCreateLead.postValue(UiState.Success(data))
            }
    }

    fun updateLead() = viewModelScope.launch(Dispatchers.IO) {
        val leadModel = LeadModel(
            branchOfficeId = _branchOfficeId.value,
            probabilityId = _probabilityId.value,
            typeId = _typeId.value,
            channelId = _channelId.value,
            mediaId = _mediaId.value,
            sourceId = _sourceId.value,
            fullName = _fullName.value,
            email = _email.value,
            phone = _phone.value,
            address = _address.value,
            latitude = _latitude.value,
            longitude = _longitude.value,
            companyName = "Global Xtreme",
            generalNotes = _generalNotes.value,
            gender = _gender.value,
            idNumber = _idNumber.value
        )

        addEditLeadRepository.updateLead(idLead, leadModel ,_image.value)
            .onStart {
                _stateUpdateLead.postValue(UiState.Loading)
            }
            .catch { cause: Throwable ->
                _stateUpdateLead.postValue(UiState.Error(ErrorExtractor.errorMessage(cause)))
            }
            .firstOrNull()
            ?.let { data ->
                _stateUpdateLead.postValue(UiState.Success(data))
            }
    }

    fun setBranchOfficeId(id: String) = viewModelScope.launch(Dispatchers.IO) {
        _branchOfficeId.value = id
    }

    fun setStatusId(id: String) = viewModelScope.launch(Dispatchers.IO) {
        _statusId.value = id
    }

    fun setProbabilityId(id: String) = viewModelScope.launch(Dispatchers.IO) {
        _probabilityId.value = id
    }

    fun setTypeId(id: String) = viewModelScope.launch(Dispatchers.IO) {
        _typeId.value = id
    }

    fun setChannelId(id: String) = viewModelScope.launch(Dispatchers.IO) {
        _channelId.value = id
    }

    fun setMediaId(id: String) = viewModelScope.launch(Dispatchers.IO) {
        _mediaId.value = id
    }

    fun setSourceId(id: String) = viewModelScope.launch(Dispatchers.IO) {
        _sourceId.value = id
    }

    fun setFullName(name: String) = viewModelScope.launch(Dispatchers.IO) {
        _fullName.value = name
    }

    fun setEmail(email: String) = viewModelScope.launch(Dispatchers.IO) {
        _email.value = email
    }

    fun setPhone(phone: String) = viewModelScope.launch(Dispatchers.IO) {
        _phone.value = phone
    }

    fun setAddress(address: String) = viewModelScope.launch(Dispatchers.IO) {
        _address.value = address
    }

    fun setLatitude(latitude: String) = viewModelScope.launch(Dispatchers.IO) {
        _latitude.value = latitude
    }

    fun setLongitude(longitude: String) = viewModelScope.launch(Dispatchers.IO) {
        _longitude.value = longitude
    }

    fun setGeneralNotes(notes: String) = viewModelScope.launch(Dispatchers.IO) {
        _generalNotes.value = notes
    }

    fun setGender(gender: String) = viewModelScope.launch(Dispatchers.IO) {
        _gender.value = gender
    }

    fun setIdNumber(idNumber: String) = viewModelScope.launch(Dispatchers.IO) {
        _idNumber.value = idNumber
    }

    fun setImage(image: MultipartBody.Part?) = viewModelScope.launch(Dispatchers.IO) {
        _image.value = image
    }
}