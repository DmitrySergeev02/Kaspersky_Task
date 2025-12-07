package com.dmitrysergeev.translateapp.glue.translation.di

import com.dmitrysergeev.translateapp.domain.translation.TranslationRepository
import com.dmitrysergeev.translateapp.glue.translation.repositories.AdapterTranslationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface TranslationRepositoriesModule{

    @Binds
    @Singleton
    fun bindAdapterTranslationRepository(adapterTranslationRepository: AdapterTranslationRepository): TranslationRepository
}