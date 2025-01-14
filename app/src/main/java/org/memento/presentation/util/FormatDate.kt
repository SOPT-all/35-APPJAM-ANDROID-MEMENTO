package org.memento.presentation.util

fun formatDate(timestamp: Long): String {
    val formatter = java.text.SimpleDateFormat("MMM dd, yyyy", java.util.Locale.ENGLISH)
    return formatter.format(java.util.Date(timestamp))
}
