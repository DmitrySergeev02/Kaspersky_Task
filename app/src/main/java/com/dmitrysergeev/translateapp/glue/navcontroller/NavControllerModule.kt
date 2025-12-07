package com.dmitrysergeev.translateapp.glue.navcontroller

import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import javax.inject.Singleton

@Module
@InstallIn(FragmentComponent::class)
class NavControllerModule {

    @Provides
    fun provideNavController(fragment: Fragment): NavController{
        return fragment.findNavController()
    }

}