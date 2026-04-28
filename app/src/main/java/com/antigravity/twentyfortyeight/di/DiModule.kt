package com.antigravity.twentyfortyeight.di

import android.content.Context
import androidx.room.Room
import com.antigravity.twentyfortyeight.data.GameRepository
import com.antigravity.twentyfortyeight.data.PreferencesDataStore
import com.antigravity.twentyfortyeight.data.ScoreDao
import com.antigravity.twentyfortyeight.data.ScoreDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DiModule {

    @Provides
    @Singleton
    fun provideScoreDatabase(
        @ApplicationContext context: Context
    ): ScoreDatabase =
        Room.databaseBuilder(
            context,
            ScoreDatabase::class.java,
            "scores.db"
        ).build()

    @Provides
    @Singleton
    fun provideScoreDao(database: ScoreDatabase): ScoreDao =
        database.scoreDao()

    @Provides
    @Singleton
    fun providePreferencesDataStore(
        @ApplicationContext context: Context
    ): PreferencesDataStore =
        PreferencesDataStore(context)

    @Provides
    @Singleton
    fun provideGameRepository(
        preferencesDataStore: PreferencesDataStore,
        scoreDao: ScoreDao
    ): GameRepository =
        GameRepository(preferencesDataStore, scoreDao)
}
