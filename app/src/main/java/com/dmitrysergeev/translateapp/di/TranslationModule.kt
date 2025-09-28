package com.dmitrysergeev.translateapp.di

import android.content.Context
import androidx.room.Room
import com.dmitrysergeev.translateapp.data.translation.TranslationRepository
import com.dmitrysergeev.translateapp.data.translation.TranslationRepositoryImpl
import com.dmitrysergeev.translateapp.data.translation.network.ApiNetworkTranslationRepository
import com.dmitrysergeev.translateapp.data.translation.network.SkyEngApi
import com.dmitrysergeev.translateapp.data.translation.network.NetworkTranslationRepository
import com.dmitrysergeev.translateapp.data.translation.db.RoomDbTranslationRepository
import com.dmitrysergeev.translateapp.data.translation.db.TranslateDatabase
import com.dmitrysergeev.translateapp.data.translation.db.DbTranslationRepository
import com.dmitrysergeev.translateapp.domain.addfavouritewordtranslationusecase.AddFavouriteWordTranslationUseCase
import com.dmitrysergeev.translateapp.domain.addfavouritewordtranslationusecase.AddFavouriteWordTranslationUseCaseImpl
import com.dmitrysergeev.translateapp.domain.addhistoryitemusecase.AddHistoryItemUseCase
import com.dmitrysergeev.translateapp.domain.addhistoryitemusecase.AddHistoryItemUseCaseImpl
import com.dmitrysergeev.translateapp.domain.deletefavouritebybasewordandtranslationusecase.DeleteFavouriteByBaseWordAndTranslationUseCase
import com.dmitrysergeev.translateapp.domain.deletefavouritebybasewordandtranslationusecase.DeleteFavouriteByBaseWordAndTranslationUseCaseImpl
import com.dmitrysergeev.translateapp.domain.deletehistoryitemusecase.DeleteHistoryItemUseCase
import com.dmitrysergeev.translateapp.domain.deletehistoryitemusecase.DeleteHistoryItemUseCaseImpl
import com.dmitrysergeev.translateapp.domain.getfavouritebybasewordandtranslationusecase.GetFavouriteByBaseWordAndTranslationUseCase
import com.dmitrysergeev.translateapp.domain.getfavouritebybasewordandtranslationusecase.GetFavouriteByBaseWordAndTranslationUseCaseImpl
import com.dmitrysergeev.translateapp.domain.getfavouritetranslationsusecase.GetFavouriteTranslationsUseCase
import com.dmitrysergeev.translateapp.domain.getfavouritetranslationsusecase.GetFavouriteTranslationsUseCaseImpl
import com.dmitrysergeev.translateapp.domain.gethistoryusecase.GetHistoryUseCase
import com.dmitrysergeev.translateapp.domain.gethistoryusecase.GetHistoryUseCaseImpl
import com.dmitrysergeev.translateapp.domain.gettranslationsforqueryusecase.GetTranslationsForQueryUseCase
import com.dmitrysergeev.translateapp.domain.gettranslationsforqueryusecase.GetTranslationsForQueryUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class TranslationBindsModule {

    @Binds
    @Singleton
    abstract fun bindNetworkTranslationRepository(
        apiTranslationRepository: ApiNetworkTranslationRepository
    ): NetworkTranslationRepository

    @Binds
    @Singleton
    abstract fun bindTranslationDbRepository(
        roomTranslationDbRepository: RoomDbTranslationRepository
    ): DbTranslationRepository

    @Binds
    @Singleton
    abstract fun bindTranslationRepository(
        translationRepositoryImpl: TranslationRepositoryImpl
    ): TranslationRepository

    @Binds
    abstract fun bindGetHistoryUseCase(
        getHistoryUseCase: GetHistoryUseCaseImpl
    ): GetHistoryUseCase

    @Binds
    abstract fun bindAddFavouriteWordTranslationUseCase(
        addFavouriteWordTranslationUseCase: AddFavouriteWordTranslationUseCaseImpl
    ): AddFavouriteWordTranslationUseCase

    @Binds
    abstract fun bindAddHistoryItemUseCase(
        addHistoryItemUseCase: AddHistoryItemUseCaseImpl
    ): AddHistoryItemUseCase

    @Binds
    abstract fun bindDeleteFavouriteByBaseWordAndTranslationUseCase(
        deleteFavouriteByBaseWordAndTranslationUseCase: DeleteFavouriteByBaseWordAndTranslationUseCaseImpl
    ): DeleteFavouriteByBaseWordAndTranslationUseCase

    @Binds
    abstract fun bindDeleteHistoryItemUseCase(
        deleteHistoryItemUseCase: DeleteHistoryItemUseCaseImpl
    ): DeleteHistoryItemUseCase

    @Binds
    abstract fun bindGetFavouriteByBaseWordAndTranslationUseCase(
        getFavouriteByBaseWordAndTranslationUseCase: GetFavouriteByBaseWordAndTranslationUseCaseImpl
    ): GetFavouriteByBaseWordAndTranslationUseCase

    @Binds
    abstract fun bindGetFavouriteTranslationsUseCase(
        getFavouriteTranslationsUseCase: GetFavouriteTranslationsUseCaseImpl
    ): GetFavouriteTranslationsUseCase

    @Binds
    abstract fun bindGetTranslationsForQueryUseCase(
        getTranslationsForQueryUseCase: GetTranslationsForQueryUseCaseImpl
    ): GetTranslationsForQueryUseCase


}

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class IoDispatcher

@Module
@InstallIn(SingletonComponent::class)
object TranslationProvidesModule {

    @Provides
    @IoDispatcher
    fun provideIoDispatcher() : CoroutineDispatcher = Dispatchers.IO

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