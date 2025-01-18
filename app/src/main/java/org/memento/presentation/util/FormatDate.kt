package org.memento.presentation.util

import java.text.SimpleDateFormat
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

fun parseDateTime(
    date: String,
    time: String,
): Date {
    val formatter = SimpleDateFormat("MMM dd, yyyy hh:mm a", Locale.ENGLISH)
    return formatter.parse("$date $time") ?: Date() // Sun Jan 01 15:00 UTC 2023
}
