package org.memento.domain.entity

data class ScheduleDetail(
    val id: Int,
    val description: String,
    val startDate: String,
    val endDate: String,
    val scheduleType: String,
    val tagId: Int,
)
