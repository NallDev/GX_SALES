package com.nalldev.gxsales.presentation.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nalldev.gxsales.core.base.BaseItemModel
import com.nalldev.gxsales.core.repository.form.FormRepository
import com.nalldev.gxsales.core.repository.session.SessionRepository
import com.nalldev.gxsales.core.util.DateConverter
import com.nalldev.gxsales.core.util.ErrorExtractor
import com.nalldev.gxsales.core.util.UiState
import com.nalldev.gxsales.presentation.main.domain.model.LeadResponse
import com.nalldev.gxsales.presentation.main.domain.model.ProfileFormModel
import com.nalldev.gxsales.presentation.main.domain.model.ProfileResponse
import com.nalldev.gxsales.presentation.main.domain.model.StatusesItem
import com.nalldev.gxsales.presentation.main.domain.repository.HomeRepository
import com.nalldev.gxsales.presentation.main.domain.repository.LeadsRepository
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
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
    private val sessionRepository: SessionRepository,
    private val formRepository: FormRepository,
    private val leadsRepository: LeadsRepository
) : ViewModel() {
    private val _stateLeadsDashboard: MutableLiveData<UiState<List<StatusesItem>>> =
        MutableLiveData()
    val stateLeadsDashboard: LiveData<UiState<List<StatusesItem>>> = _stateLeadsDashboard

    private val _stateDeleteLead: MutableLiveData<UiState<Unit>> =
        MutableLiveData()
    val stateDeleteLead: LiveData<UiState<Unit>> = _stateDeleteLead

    private val _stateListLead: MutableLiveData<UiState<List<LeadResponse>>> =
        MutableLiveData()
    val stateListLead: LiveData<UiState<List<LeadResponse>>> = _stateListLead

    private val _stateLogout: MutableLiveData<UiState<Unit>> =
        MutableLiveData()
    val stateLogout: LiveData<UiState<Unit>> = _stateLogout

    private val _profile: MutableLiveData<ProfileResponse> = MutableLiveData()
    val profile: LiveData<ProfileResponse> = _profile

    private val _rangeDate =
        MutableStateFlow(Pair(DateConverter.getToday(), DateConverter.getToday()))
    val rangeDate: StateFlow<Pair<String, String>> = _rangeDate.asStateFlow()

    private val _listLead: MutableStateFlow<List<LeadResponse>> = MutableStateFlow(emptyList())

    private val _rangeDateFilter: MutableStateFlow<Pair<String, String>> =
        MutableStateFlow(Pair("", ""))
    val rangeDateFilter: StateFlow<Pair<String, String>> = _rangeDateFilter.asStateFlow()

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name.asStateFlow()

    private val _status = MutableStateFlow("")
    val status: StateFlow<String> = _status.asStateFlow()

    private val _channel = MutableStateFlow("")
    val channel: StateFlow<String> = _channel.asStateFlow()

    private val _media = MutableStateFlow("")
    val media: StateFlow<String> = _media.asStateFlow()

    private val _source = MutableStateFlow("")
    val source: StateFlow<String> = _source.asStateFlow()

    @Suppress("UNCHECKED_CAST")
    val filteredLeadsFlow = combine(
        _listLead,
        _name,
        _rangeDateFilter,
        _status,
        _channel,
        _media,
        _source
    ) { results: Array<Any> ->
        val leads = results[0] as List<LeadResponse>
        val nameFilter = results[1] as String
        val dateFilter = results[2] as Pair<String, String>
        val statusFilter = results[3] as String
        val channelFilter = results[4] as String
        val mediaFilter = results[5] as String
        val sourceFilter = results[6] as String

        leads.filter { lead ->
            val isDateFilterActive = dateFilter.first.isNotEmpty() && dateFilter.second.isNotEmpty()
            val createdAtDate = if (isDateFilterActive) LocalDateTime.parse(lead.createdAt, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")).toLocalDate() else null
            val formatter = if (isDateFilterActive) DateTimeFormatter.ofPattern("dd/MM/yyyy") else null
            val startDate = if (isDateFilterActive) LocalDate.parse(dateFilter.first, formatter) else null
            val endDate = if (isDateFilterActive) LocalDate.parse(dateFilter.second, formatter) else null

            (nameFilter.isEmpty() || lead.fullName.contains(nameFilter, ignoreCase = true)) &&
                    (statusFilter.isEmpty() || lead.status.name.equals(
                        statusFilter,
                        ignoreCase = true
                    )) &&
                    (channelFilter.isEmpty() || lead.channel.name.equals(
                        channelFilter,
                        ignoreCase = true
                    )) &&
                    (mediaFilter.isEmpty() || lead.media.name.equals(
                        mediaFilter,
                        ignoreCase = true
                    )) &&
                    (sourceFilter.isEmpty() || lead.source.name.equals(
                        sourceFilter,
                        ignoreCase = true
                    )) &&
                    (!isDateFilterActive || (createdAtDate != null && !createdAtDate.isBefore(startDate) && !createdAtDate.isAfter(endDate)))
        }
    }

    init {
        getDashboardProfile()
    }

    fun getLeadsDashboard(fromDate: String, toDate: String) =
        viewModelScope.launch(Dispatchers.IO) {
            _rangeDate.value = Pair(fromDate, toDate)
            homeRepository.getLeadsDashboard(fromDate, toDate)
                .onStart {
                    _stateLeadsDashboard.postValue(UiState.Loading)
                }
                .catch { cause: Throwable ->
                    _stateLeadsDashboard.postValue(UiState.Error(ErrorExtractor.errorMessage(cause)))
                }
                .firstOrNull()
                ?.let { listData ->
                    _stateLeadsDashboard.postValue(UiState.Success(listData))
                }
        }

    @Suppress("UNCHECKED_CAST")
    fun getDashboardProfile() = viewModelScope.launch(Dispatchers.IO) {
        val getLeadsDashboard =
            homeRepository.getLeadsDashboard(DateConverter.getToday(), DateConverter.getToday())
        val getProfile = homeRepository.getProfile()
        val getBranch = homeRepository.getBranchOffices()
        val getTypes = homeRepository.getTypes()
        val getChannels = homeRepository.getChannels()
        val getMedias = homeRepository.getMedias()
        val getProbabilities = homeRepository.getProbabilities()
        val getStatuses = homeRepository.getStatuses()
        val getSources = homeRepository.getSources()
        val getListLead = leadsRepository.getListLead()

        combine(
            getLeadsDashboard,
            getProfile,
            getBranch,
            getTypes,
            getChannels,
            getMedias,
            getProbabilities,
            getStatuses,
            getSources,
            getListLead
        ) { results: Array<Any> ->
            ProfileFormModel(
                leadsDashboard = results[0] as List<StatusesItem>,
                profile = results[1] as ProfileResponse,
                branch = results[2] as List<BaseItemModel>,
                types = results[3] as List<BaseItemModel>,
                channels = results[4] as List<BaseItemModel>,
                medias = results[5] as List<BaseItemModel>,
                probabilities = results[6] as List<BaseItemModel>,
                statuses = results[7] as List<BaseItemModel>,
                sources = results[8] as List<BaseItemModel>,
                leads = results[9] as List<LeadResponse>
            )
        }
            .onStart {
                _stateLeadsDashboard.postValue(UiState.Loading)
            }
            .catch { cause: Throwable ->
                _stateLeadsDashboard.postValue(UiState.Error(ErrorExtractor.errorMessage(cause)))
            }
            .collect { data ->
                _stateLeadsDashboard.postValue(UiState.Success(data.leadsDashboard))
                _profile.postValue(data.profile)
                _listLead.value = data.leads

                formRepository.setBranchOffices(data.branch)
                formRepository.setChannels(data.channels)
                formRepository.setMedias(data.medias)
                formRepository.setSources(data.sources)
                formRepository.setStatuses(data.statuses)
                formRepository.setTypes(data.types)
                formRepository.setProbabilities(data.probabilities)
            }
    }

    fun getListLead() = viewModelScope.launch(Dispatchers.IO) {
        leadsRepository.getListLead()
            .onStart {
                _stateListLead.postValue(UiState.Loading)
            }
            .catch { cause: Throwable ->
                _stateListLead.postValue(UiState.Error(ErrorExtractor.errorMessage(cause)))
            }
            .firstOrNull()
            ?.let { listData ->
                _stateListLead.postValue(UiState.Success(listData))
                _listLead.value = listData
            }
    }

    fun doLogout() = viewModelScope.launch(Dispatchers.IO) {
        homeRepository.doLogout()
            .onStart {
                _stateLogout.postValue(UiState.Loading)
            }
            .catch { cause: Throwable ->
                _stateLogout.postValue(UiState.Error(ErrorExtractor.errorMessage(cause)))
            }
            .firstOrNull()
            ?.let {
                _stateLogout.postValue(UiState.Success(Unit))
            }
    }

    fun deleteLead(id : String) = viewModelScope.launch(Dispatchers.IO) {
        leadsRepository.deleteLead(id)
            .onStart {
                _stateDeleteLead.postValue(UiState.Loading)
            }
            .catch { cause: Throwable ->
                _stateDeleteLead.postValue(UiState.Error(ErrorExtractor.errorMessage(cause)))
            }
            .firstOrNull()
            ?.let {
                _stateDeleteLead.postValue(UiState.Success(Unit))
            }
    }

    fun setName(name: String) {
        if (_name.value != name) {
            _name.value = name
        }
    }

    fun setRangeDate(fromDate: String, toDate: String) {
        _rangeDateFilter.value = Pair(fromDate, toDate)
    }

    fun setStatus(text: String) {
        _status.value = text
    }

    fun setChannel(text: String) {
        _channel.value = text
    }

    fun setMedia(text: String) {
        _media.value = text
    }

    fun setSource(text: String) {
        _source.value = text
    }

    fun clearSession() {
        sessionRepository.setToken("")
    }
}