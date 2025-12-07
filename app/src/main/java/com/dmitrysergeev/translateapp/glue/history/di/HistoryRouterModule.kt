package com.dmitrysergeev.translateapp.glue.history.di

import com.dmitrysergeev.history.HistoryRouter
import com.dmitrysergeev.translateapp.glue.history.AdapterHistoryRouter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
interface HistoryRouterModule {

    @Binds
    fun bindAdapterHistoryRouter(adapterHistoryRouter: AdapterHistoryRouter): HistoryRouter

}