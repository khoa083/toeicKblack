package com.khoa.demotoeictest.di

import android.content.Context
import androidx.room.Room
import com.khoa.demotoeictest.room.AppDatabase
import com.khoa.demotoeictest.room.Migrations
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataLocalModule {

    @Provides
    @Singleton
    fun providesEtsDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, AppDatabase::class.java, AppDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    
//    fun providesEtsDatabase(@ApplicationContext context: Context) =
//        Room.databaseBuilder(context, AppDatabase::class.java, "kblack_toeic_ver1")
//                .addMigrations(Migrations.MIGRATION_1_2)
//                .build()

    @Provides
    @Singleton
    fun providesListTitleParts(appDatabase: AppDatabase) = appDatabase.listPartsDao()

    @Provides
    @Singleton
    fun providesVocabs(appDatabase: AppDatabase) = appDatabase.vocabsDao()
}