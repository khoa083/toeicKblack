package com.khoa.demotoeictest.di

import com.khoa.demotoeictest.db.DataRemoteAPI
import com.khoa.demotoeictest.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal object AppModule {

    @Singleton
    @Provides
    fun provideAppRepository(dataRemoteAPI: DataRemoteAPI): Repository =
        Repository(dataRemoteAPI)
}