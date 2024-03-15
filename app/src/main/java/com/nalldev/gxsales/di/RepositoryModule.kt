package com.nalldev.gxsales.di

import android.content.SharedPreferences
import com.nalldev.gxsales.core.remote.ApiService
import com.nalldev.gxsales.core.repository.form.FormRepository
import com.nalldev.gxsales.core.repository.form.FormRepositoryImpl
import com.nalldev.gxsales.core.repository.session.SessionRepository
import com.nalldev.gxsales.core.repository.session.SessionRepositoryImpl
import com.nalldev.gxsales.presentation.add_edit_lead.data.AddEditLeadRepositoryImpl
import com.nalldev.gxsales.presentation.add_edit_lead.domain.repository.AddEditLeadRepository
import com.nalldev.gxsales.presentation.main.data.HomeRepositoryImpl
import com.nalldev.gxsales.presentation.main.domain.repository.HomeRepository
import com.nalldev.gxsales.presentation.login.data.LoginRepositoryImpl
import com.nalldev.gxsales.presentation.login.domain.repository.LoginRepository
import com.nalldev.gxsales.presentation.main.data.LeadsRepositoryImpl
import com.nalldev.gxsales.presentation.main.domain.repository.LeadsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideLoginRepository(apiService: ApiService) : LoginRepository = LoginRepositoryImpl(apiService)

    @Provides
    @Singleton
    fun provideSessionRepository(sharedPreferences: SharedPreferences) : SessionRepository = SessionRepositoryImpl(sharedPreferences)

    @Provides
    @Singleton
    fun providesFormRepository(sharedPreferences: SharedPreferences) : FormRepository = FormRepositoryImpl(sharedPreferences)

    @Provides
    @Singleton
    fun provideHomeRepository(apiService: ApiService, sessionRepository: SessionRepository) : HomeRepository = HomeRepositoryImpl(apiService, sessionRepository)

    @Provides
    @Singleton
    fun provideLeadsRepository(apiService: ApiService, sessionRepository: SessionRepository) : LeadsRepository = LeadsRepositoryImpl(apiService, sessionRepository)

    @Provides
    @Singleton
    fun provideAddEditLeadRepository(apiService: ApiService, sessionRepository: SessionRepository) : AddEditLeadRepository = AddEditLeadRepositoryImpl(apiService, sessionRepository)
}