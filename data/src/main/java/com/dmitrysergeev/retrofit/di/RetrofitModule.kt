package com.dmitrysergeev.retrofit.di

import com.dmitrysergeev.data.translation.sources.api.TranslateApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @Singleton
    fun provideTranslationApi(): TranslateApi{
        val client = OkHttpClient.Builder()
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://clients5.google.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build()

        return retrofit.create(TranslateApi::class.java)
    }


}