package com.dmitrysergeev.data.translation.di

import com.dmitrysergeev.data.translation.sources.MutableTranslationDataSource
import com.dmitrysergeev.data.translation.sources.TranslationDataSource
import com.dmitrysergeev.data.translation.sources.api.ApiTranslationDataSource
import com.dmitrysergeev.data.translation.sources.db.DbTranslationDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface TranslationDataSourceModule {

    @Binds
    @Singleton
    fun bindApiTranslationDataSource(apiTranslationDataSource: ApiTranslationDataSource): TranslationDataSource

    @Binds
    @Singleton
    fun bindDbTranslationDataSource(dbTranslationDataSource: DbTranslationDataSource): MutableTranslationDataSource

}