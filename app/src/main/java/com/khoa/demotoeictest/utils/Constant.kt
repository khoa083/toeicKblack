package com.khoa.demotoeictest.utils

object Constant {
    fun milliSecondsToTimer(milliseconds: Long): String {
        var finalTimerString = ""
        var secondsString = ""
        var minuteString = ""
        val hours = (milliseconds / (1000 * 60 * 60)).toInt()
        val minutes = (milliseconds % (1000 * 60 * 60)).toInt() / (1000 * 60)
        val seconds = (milliseconds % (1000 * 60 * 60) % (1000 * 60) / 1000).toInt()
        if (hours > 0) {
            finalTimerString = "$hours:"
        }
        secondsString = if (seconds < 10) "0$seconds" else "" + seconds
        minuteString = if (minutes < 10) "0$minutes" else "" + minutes
        finalTimerString = "$finalTimerString$minuteString:$secondsString"
        return finalTimerString
    }

    fun getProgressPercentage(currentDuration: Long, totalDuration: Long): Int {
        val percentage: Double
        val currentSeconds = (currentDuration / 1000).toInt().toLong()
        val totalSeconds = (totalDuration / 1000).toInt().toLong()
        percentage = currentSeconds.toDouble() / totalSeconds * 100
        return percentage.toInt()
    }

    fun progressToTimer(progress: Int, totalDuration: Int): Int {
        var totalDuration = totalDuration
        var currentDuration = 0
        totalDuration /= 1000
        currentDuration = (progress.toDouble() / 100 * totalDuration).toInt()

        return currentDuration * 1000
    }

    const val BASE_URL = "https://kblack.dev/"
    const val KEY_DATA = "KEY DATA"
}