package com.dmitrysergeev.data.translation.di

import com.dmitrysergeev.data.TranslationDataRepository
import com.dmitrysergeev.data.translation.RealTranslationDataRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface TranslationDataRepositoryModule {

    @Binds
    @Singleton
    fun bindRealTranslationDataRepository(realTranslationDataRepository: RealTranslationDataRepository): TranslationDataRepository

}