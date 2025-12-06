package com.dmitrysergeev.data.history.di

import com.dmitrysergeev.data.HistoryDataRepository
import com.dmitrysergeev.data.history.RealHistoryDataRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface HistoryDataRepositoryModule {

    @Binds
    @Singleton
    fun bindRealHistoryDataRepository(realHistoryDataRepository: RealHistoryDataRepository): HistoryDataRepository

}