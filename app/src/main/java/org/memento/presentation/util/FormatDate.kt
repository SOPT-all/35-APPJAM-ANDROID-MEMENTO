package org.memento.presentation.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

fun formatDate(timestamp: Long): String {
    val formatter = java.text.SimpleDateFormat("MMM dd, yyyy", java.util.Locale.ENGLISH)
    return formatter.format(java.util.Date(timestamp))
}

fun todoFormatDate(localDate: LocalDate): String {
    val formatter = DateTimeFormatter.ofPattern("MMM d", Locale.ENGLISH)
    return localDate.format(formatter)
}
