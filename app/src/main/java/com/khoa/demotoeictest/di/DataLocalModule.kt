package com.khoa.demotoeictest.di

import android.content.Context
import androidx.room.Room
import com.khoa.demotoeictest.room.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataLocalModule {

    @Provides
    @Singleton
    fun providesEtsDatabase(context: Context) =
        Room.databaseBuilder(context, AppDatabase::class.java, context.packageName)
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun providesListTitleParts(appDatabase: AppDatabase) = appDatabase.listPartsDao()

    @Provides
    @Singleton
    fun providesVocabs(appDatabase: AppDatabase) = appDatabase.vocabsDao()
}