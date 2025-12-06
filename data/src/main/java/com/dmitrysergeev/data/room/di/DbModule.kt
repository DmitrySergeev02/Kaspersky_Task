package com.dmitrysergeev.data.room.di

import android.content.Context
import androidx.room.Room
import com.dmitrysergeev.data.history.sources.db.HistoryDao
import com.dmitrysergeev.data.room.TranslateDatabase
import com.dmitrysergeev.data.translation.sources.db.TranslationDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): TranslateDatabase{
        return Room.databaseBuilder(
            context,
            TranslateDatabase::class.java,
            "translation.db"
        )
            .build()
    }

    @Provides
    fun provideTranslationDao(db: TranslateDatabase): TranslationDao{
        return db.translationDao()
    }

    @Provides
    fun provideHistoryDao(db: TranslateDatabase): HistoryDao{
        return db.historyDao()
    }
}