package com.dmitrysergeev.translateapp.di

import android.content.Context
import androidx.room.Room
import com.dmitrysergeev.translateapp.data.translation.api.ApiTranslationRepository
import com.dmitrysergeev.translateapp.data.translation.api.SkyEngApi
import com.dmitrysergeev.translateapp.data.translation.api.TranslationRepository
import com.dmitrysergeev.translateapp.data.translation.db.RoomTranslationDbRepository
import com.dmitrysergeev.translateapp.data.translation.db.TranslateDatabase
import com.dmitrysergeev.translateapp.data.translation.db.TranslationDbRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class TranslationBindsModule {

    @Binds
    @Singleton
    abstract fun bindTranslationRepository(
        apiTranslationRepository: ApiTranslationRepository
    ): TranslationRepository

    @Binds
    @Singleton
    abstract fun bindTranslationDbRepository(
        roomTranslationDbRepository: RoomTranslationDbRepository
    ): TranslationDbRepository

}

@Module
@InstallIn(SingletonComponent::class)
object TranslationProvidesModule {

    @Provides
    @Singleton
    fun provideSkyEngApi(): SkyEngApi{
        val okHttpClient = OkHttpClient.Builder()
            .build()

        return Retrofit.Builder()
            .baseUrl("https://dictionary.skyeng.ru/api/public/v1/")
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create<SkyEngApi>()
    }

    @Provides
    @Singleton
    fun providesTranslateDatabase(
        @ApplicationContext context: Context
    ): TranslateDatabase {
        val DATABASE_NAME = "translation_database"
        return Room
            .databaseBuilder(
                context,
                TranslateDatabase::class.java,
                DATABASE_NAME
            )
            .build()
    }

}