package com.chetan.jobnepal.utils

import java.text.SimpleDateFormat
import java.time.Duration
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit
import kotlin.math.abs

object MyDate {

    fun convertStringToDate(timestamp: String): String{
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val date = Date(timestamp.toLong())
        return dateFormat.format(date)
    }

    fun differenceOfDates(before: String, now: String) : String {
        val duration = abs(1000 * before.toLong() - now.toLong())
        val days = TimeUnit.MILLISECONDS.toDays(duration)
        val hours = TimeUnit.MILLISECONDS.toHours(duration)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(duration)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(duration)

        return seconds.toString()+"   " + minutes.toString()
    }
}