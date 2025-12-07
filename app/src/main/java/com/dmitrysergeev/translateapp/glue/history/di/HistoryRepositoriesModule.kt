package com.dmitrysergeev.translateapp.glue.history.di

import com.dmitrysergeev.history.domain.HistoryRepository
import com.dmitrysergeev.translateapp.glue.history.repositories.AdapterHistoryRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface HistoryRepositoriesModule {

    @Binds
    @Singleton
    fun bindAdapterHistoryRepository(adapterHistoryRepository: AdapterHistoryRepository): com.dmitrysergeev.history.domain.HistoryRepository
}