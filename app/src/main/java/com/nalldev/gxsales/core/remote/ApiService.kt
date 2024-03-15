package com.nalldev.gxsales.core.remote

import com.nalldev.gxsales.core.base.BaseApiResponse
import com.nalldev.gxsales.core.base.BaseItemModel
import com.nalldev.gxsales.presentation.main.domain.model.LeadsDashboardResponse
import com.nalldev.gxsales.presentation.main.domain.model.ProfileResponse
import com.nalldev.gxsales.presentation.login.domain.model.LoginRequest
import com.nalldev.gxsales.presentation.login.domain.model.LoginResponse
import com.nalldev.gxsales.presentation.main.domain.model.LeadResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @POST("login")
    suspend fun doLogin(
        @Body loginRequest: LoginRequest
    ) : LoginResponse

    @GET("leads/dashboard")
    suspend fun getLeadsDashboard(
        @Header("Authorization") token : String,
        @Query("fromDate") fromDate : String,
        @Query("toDate") toDate : String,
    ) : BaseApiResponse<LeadsDashboardResponse>

    @GET("profile")
    suspend fun getProfile(
        @Header("Authorization") token : String
    ) : BaseApiResponse<ProfileResponse>

    @GET("settings/branch-offices")
    suspend fun getBranchOffices(
        @Header("Authorization") token : String
    ) : BaseApiResponse<List<BaseItemModel>>

    @GET("settings/types")
    suspend fun getTypes(
        @Header("Authorization") token : String
    ) : BaseApiResponse<List<BaseItemModel>>

    @GET("settings/statuses")
    suspend fun getStatuses(
        @Header("Authorization") token : String
    ) : BaseApiResponse<List<BaseItemModel>>

    @GET("settings/channels")
    suspend fun getChannels(
        @Header("Authorization") token : String
    ) : BaseApiResponse<List<BaseItemModel>>

    @GET("settings/medias")
    suspend fun getMedias(
        @Header("Authorization") token : String
    ) : BaseApiResponse<List<BaseItemModel>>

    @GET("settings/sources")
    suspend fun getSources(
        @Header("Authorization") token : String
    ) : BaseApiResponse<List<BaseItemModel>>

    @GET("settings/probabilities")
    suspend fun getProbabilities(
        @Header("Authorization") token : String
    ) : BaseApiResponse<List<BaseItemModel>>

    @GET("leads")
    suspend fun getListLead(
        @Header("Authorization") token : String
    ) : BaseApiResponse<List<LeadResponse>>
}