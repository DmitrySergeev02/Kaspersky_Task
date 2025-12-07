package com.dmitrysergeev.translateapp.glue.translation.di

import com.dmitrysergeev.translateapp.glue.translation.AdapterTranslationRouter
import com.dmitrysergeev.translation.TranslationRouter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
interface TranslationRouterModule {

    @Binds
    fun bindAdapterTranslationRouter(adapterTranslationRouter: AdapterTranslationRouter): TranslationRouter
}