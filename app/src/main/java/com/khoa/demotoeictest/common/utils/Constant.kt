package com.khoa.demotoeictest.common.utils

import com.khoa.demotoeictest.R
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

    val iconParts = mapOf(
        "part_1" to R.drawable.ic_part1_photographs,
        "part_2" to R.drawable.ic_part2_questions,
        "part_3" to R.drawable.ic_part3_convertions,
        "part_4" to R.drawable.ic_part4_short_talks,
        "part_5" to R.drawable.ic_part5_incomplete_centences,
        "part_6" to R.drawable.ic_part6_text_completion,
        "part_7" to R.drawable.ic_part7_reading,
        "listening" to R.drawable.ic_practice_listening,
        "reading" to R.drawable.ic_practice_reading
    )
}