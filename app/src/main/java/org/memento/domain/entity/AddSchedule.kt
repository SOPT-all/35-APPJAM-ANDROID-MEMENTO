package org.memento.domain.entity

data class AddSchedule(
    val description: String,
    val startDate: String,
    val endDate: String,
    val isAllDay: Boolean,
    val tagId: Int,
)
