package com.nalldev.gxsales.core.remote

import com.nalldev.gxsales.core.base.BaseApiResponse
import com.nalldev.gxsales.core.base.BaseItemModel
import com.nalldev.gxsales.presentation.add_edit_lead.domain.model.LeadModel
import com.nalldev.gxsales.presentation.main.domain.model.LeadsDashboardResponse
import com.nalldev.gxsales.presentation.main.domain.model.ProfileResponse
import com.nalldev.gxsales.presentation.login.domain.model.LoginRequest
import com.nalldev.gxsales.presentation.login.domain.model.LoginResponse
import com.nalldev.gxsales.presentation.main.domain.model.LeadResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @POST("login")
    suspend fun doLogin(
        @Body loginRequest: LoginRequest
    ) : LoginResponse

    @POST("logout")
    suspend fun doLogout(
        @Header("Authorization") token : String,
    ) : BaseApiResponse<ProfileResponse>

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

    @DELETE("leads/{id}")
    suspend fun deleteLead(
        @Header("Authorization") token : String,
        @Path("id") id: String
    ) : BaseApiResponse<LeadResponse>

    @Multipart
    @POST("leads")
    suspend fun createLead(
        @Header("Authorization") token : String,
        @Part("branchOfficeId") branchOfficeId: RequestBody,
        @Part("probabilityId") probabilityId: RequestBody,
        @Part("typeId") typeId: RequestBody,
        @Part("channelId") channelId: RequestBody,
        @Part("mediaId") mediaId: RequestBody,
        @Part("sourceId") sourceId: RequestBody,
        @Part("fullName") fullName: RequestBody,
        @Part("email") email: RequestBody,
        @Part("phone") phone: RequestBody,
        @Part("address") address: RequestBody,
        @Part("latitude") latitude: RequestBody,
        @Part("longitude") longitude: RequestBody,
        @Part("companyName") companyName: RequestBody,
        @Part("generalNotes") generalNotes: RequestBody,
        @Part("gender") gender: RequestBody,
        @Part("IDNumber") idNumber: RequestBody,
        @Part image: MultipartBody.Part?
    ): BaseApiResponse<LeadResponse>

    @Multipart
    @POST("leads/{id}/update")
    suspend fun updateLead(
        @Header("Authorization") token : String,
        @Path("id") id: String,
        @Part("branchOfficeId") branchOfficeId: RequestBody,
        @Part("probabilityId") probabilityId: RequestBody,
        @Part("typeId") typeId: RequestBody,
        @Part("channelId") channelId: RequestBody,
        @Part("mediaId") mediaId: RequestBody,
        @Part("sourceId") sourceId: RequestBody,
        @Part("fullName") fullName: RequestBody,
        @Part("email") email: RequestBody,
        @Part("phone") phone: RequestBody,
        @Part("address") address: RequestBody,
        @Part("latitude") latitude: RequestBody,
        @Part("longitude") longitude: RequestBody,
        @Part("companyName") companyName: RequestBody,
        @Part("generalNotes") generalNotes: RequestBody,
        @Part("gender") gender: RequestBody,
        @Part("IDNumber") idNumber: RequestBody,
        @Part image: MultipartBody.Part?
    ): BaseApiResponse<LeadResponse>
}