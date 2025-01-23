package org.memento.presentation.util

import timber.log.Timber
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

fun formatDate(timestamp: Long): String {
    val formatter = SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH)
    return formatter.format(Date(timestamp)) // Jan 01, 2023
}

fun formatTime(
    hour: Int,
    roundedMinute: Int,
): String {
    val formatter = String.format(Locale.ENGLISH, "%02d:%02d %s", if (hour % 12 == 0) 12 else hour % 12, roundedMinute, if (hour < 12) "AM" else "PM")

    return formatter // 03:30 PM
}

fun String.to24HourFormat(): String {
    val regex = """(\d{2}):(\d{2})\s?(AM|PM)""".toRegex()
    val matchResult = regex.find(this) ?: return this

    val (hourStr, minuteStr, period) = matchResult.destructured
    var hour = hourStr.toInt()
    val minute = minuteStr.toInt()

    hour =
        when {
            period == "PM" && hour != 12 -> hour + 12
            period == "AM" && hour == 12 -> 0
            else -> hour
        }

    return "%02d:%02d".format(hour, minute)
}

fun parseDateTime(
    date: String,
    time: String,
): Date {
    val formatter = SimpleDateFormat("MMM dd, yyyy hh:mm a", Locale.ENGLISH)
    return formatter.parse("$date $time") ?: Date() // Sun Jan 01 15:00 UTC 2023
}

fun todoFormatDate(localDate: LocalDate): String {
    val formatter = DateTimeFormatter.ofPattern("MMM d", Locale.ENGLISH)
    return localDate.format(formatter)
}

fun String.toLocalDate(): LocalDate {
    return LocalDate.parse(this, DateTimeFormatter.ISO_DATE)
}

fun createLocalDateTime(
    dateText: String,
    timeText: String,
): LocalDateTime {
    return try {
        val formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy hh:mm a", Locale.ENGLISH)
        LocalDateTime.parse("$dateText $timeText", formatter)
    } catch (e: Exception) {
        Timber.e(e, "Failed to parse LocalDateTime for input: $dateText $timeText")
        throw e
    }
}

fun createLocalDate(dateText: String): LocalDate {
    return try {
        val formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy", Locale.ENGLISH)
        LocalDate.parse(dateText, formatter)
    } catch (e: Exception) {
        Timber.e(e, "Failed to parse LocalDate for input: $dateText")
        throw e
    }
}

fun formatDateString(dateString: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
    val outputFormat = SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH)
    return try {
        val date = inputFormat.parse(dateString)
        outputFormat.format(date ?: Date())
    } catch (e: Exception) {
        dateString
    }
}

fun formatTimeTo12Hour(timeString: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.ENGLISH)
    val outputFormat = SimpleDateFormat("h a", Locale.ENGLISH)

    return try {
        val date = inputFormat.parse(timeString)
        outputFormat.format(date ?: Date())
    } catch (e: Exception) {
        timeString
    }
}
