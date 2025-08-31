package org.memento.presentation.util

import timber.log.Timber
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
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

fun createLocalDate(dateString: String): LocalDate {
    val formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy", Locale.ENGLISH)
    return LocalDate.parse(dateString, formatter)
}

fun formatDateString(dateString: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    val outputFormat = SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH)

    return try {
        val date = inputFormat.parse(dateString)

        if (date != null) {
            outputFormat.format(date)
        } else {
            dateString
        }
    } catch (e: Exception) {
        dateString
    }
}

fun formatDateTime(dateString: String): String {
    val inputFormat = SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH) // "Jan 25, 2025" 형식
    val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH) // "2025-01-25" 형식
    return try {
        val date = inputFormat.parse(dateString)
        outputFormat.format(date ?: Date())
    } catch (e: Exception) {
        dateString
    }
}

fun formatEditString(dateString: String): String {
    val inputFormatWithMillis = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.ENGLISH)
    val inputFormatWithoutMillis = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
    val outputFormat = SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH)

    return try {
        val date =
            when {
                dateString.contains(".") -> inputFormatWithMillis.parse(dateString)
                else -> inputFormatWithoutMillis.parse(dateString)
            }
        outputFormat.format(date ?: Date())
    } catch (e: Exception) {
        dateString
    }
}

fun formatEditTime(dateString: String): String {
    val inputFormatWithoutMillis = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
    val outputFormat = SimpleDateFormat("h:mm a", Locale.ENGLISH)

    return try {
        val date = inputFormatWithoutMillis.parse(dateString)
        val calendar = Calendar.getInstance().apply { time = date ?: Date() }

        val minute = calendar.get(Calendar.MINUTE)
        val roundedMinute = if (minute in 0..15) 0 else 30
        calendar.set(Calendar.MINUTE, roundedMinute)

        outputFormat.format(calendar.time)
    } catch (e: Exception) {
        dateString
    }
}

private val isoFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
private val hourOnlyFormatter = DateTimeFormatter.ofPattern("h a", Locale.ENGLISH)
private val fullTimeFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH)

fun formatTimeTo12Hour(timeString: String): String {
    return try {
        val time = LocalDateTime.parse(timeString, isoFormatter)
        if (time.minute == 0) {
            time.format(hourOnlyFormatter)
        } else {
            time.format(fullTimeFormatter)
        }
    } catch (e: Exception) {
        timeString
    }
}

fun formatTimeRangeWithDuration(
    start: String,
    end: String,
): String {
    return try {
        val startTime = LocalDateTime.parse(start, isoFormatter)
        val endTime = LocalDateTime.parse(end, isoFormatter)
        val duration = Duration.between(startTime, endTime)

        val formattedStart =
            if (startTime.minute == 0) {
                startTime.format(DateTimeFormatter.ofPattern("h a", Locale.ENGLISH))
            } else {
                startTime.format(DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH))
            }

        val formattedEnd =
            if (endTime.minute == 0) {
                endTime.format(DateTimeFormatter.ofPattern("h a", Locale.ENGLISH))
            } else {
                endTime.format(DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH))
            }

        val durationString =
            buildString {
                val hours = duration.toHours()
                val minutes = duration.toMinutes() % 60
                if (hours > 0) append("${hours}h")
                if (minutes > 0) {
                    if (isNotEmpty()) append(" ")
                    append("${minutes}m")
                }
            }
        "$formattedStart - $formattedEnd${if (durationString.isNotEmpty()) " ($durationString)" else ""}"
    } catch (e: Exception) {
        "$start - $end"
    }
}

fun formatTextLocalDateTime(
    dateString: String,
    timeString: String,
): LocalDateTime {
    val dateTimeString = "$dateString $timeString"
    val formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy h:mm a", Locale.ENGLISH)
    return LocalDateTime.parse(dateTimeString, formatter)
}

// localDate -> long 파싱 함수
fun LocalDate.toMillis(): Long {
    return this.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
}

// 08:00 -> 08:00 AM
fun String.to12HourFormat(): String {
    return try {
        val inputFormat = SimpleDateFormat("HH:mm", Locale.ENGLISH)
        val outputFormat = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
        val date = inputFormat.parse(this)
        outputFormat.format(date ?: Date())
    } catch (e: Exception) {
        this
    }
}
