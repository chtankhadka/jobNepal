package com.chetan.jobnepal.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.util.concurrent.TimeUnit
import kotlin.math.abs

object MyDate {

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertStringToDate(timestamp: String): String {
        val instant = Instant.ofEpochMilli(timestamp.toLong())
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            .withLocale(Locale.getDefault())
            .withZone(ZoneId.systemDefault())
        return formatter.format(instant)
    }

    fun differenceOfDates(before: String, now: String): String {
        val duration = abs(1000 * before.toLong() - now.toLong())
        val days = TimeUnit.MILLISECONDS.toDays(duration)
        val hours = TimeUnit.MILLISECONDS.toHours(duration) % 24
        val minutes = TimeUnit.MILLISECONDS.toMinutes(duration) % 60
        val seconds = TimeUnit.MILLISECONDS.toSeconds(duration) % 60

        return if (days != 0L) {
            "$days days ago"
        } else if (hours != 0L) {
            "$hours hours ago"
        } else if (minutes != 0L) {
            "$minutes minutes ago"
        } else {
            "just a moment ago"
        }
    }
}