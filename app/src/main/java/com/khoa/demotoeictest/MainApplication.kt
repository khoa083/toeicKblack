package com.khoa.demotoeictest

import android.app.Application
import com.khoa.demotoeictest.room.DatabaseToeic
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication : Application() {
    val database: DatabaseToeic by lazy { DatabaseToeic.getDatabase(this) }
}