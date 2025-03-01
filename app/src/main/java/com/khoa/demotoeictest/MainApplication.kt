package com.khoa.demotoeictest

import android.app.Application
import android.content.Intent
import android.util.Log
import com.khoa.demotoeictest.room.AppDatabase
import com.khoa.demotoeictest.screen.BugHandlerActivity
import dagger.hilt.android.HiltAndroidApp
import kotlin.system.exitProcess

@HiltAndroidApp
class MainApplication : Application(), Thread.UncaughtExceptionHandler {
//    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
init {
    Thread.setDefaultUncaughtExceptionHandler(this)
}
companion object {
    private const val TAG = "KblackApplication"
}

override fun uncaughtException(t: Thread, e: Throwable) {
    val exceptionMessage = Log.getStackTraceString(e)
    val threadName = Thread.currentThread().name
    Log.e(TAG, "Error on thread $threadName:\n $exceptionMessage")
    val intent = Intent(this, BugHandlerActivity::class.java)
    intent.putExtra("exception_message", exceptionMessage)
    intent.putExtra("thread", threadName)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    startActivity(intent)
    exitProcess(10)
}
}