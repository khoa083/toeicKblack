package com.khoa.demotoeictest

import android.app.Application
import com.khoa.demotoeictest.room.AppDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication : Application() {
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
}