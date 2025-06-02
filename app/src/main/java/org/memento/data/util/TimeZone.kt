package org.memento.data.util

import android.icu.util.TimeZone

fun getTimeZoneOffsetString(): String {
    val offsetMillis = TimeZone.getDefault().rawOffset
    val totalMinutes = offsetMillis / 60000

    val sign = if (totalMinutes >= 0) "+" else "-"
    val absMinutes = kotlin.math.abs(totalMinutes)
    val hours = absMinutes / 60
    val minutes = absMinutes % 60

    return String.format("%s%02d:%02d", sign, hours, minutes)
}
