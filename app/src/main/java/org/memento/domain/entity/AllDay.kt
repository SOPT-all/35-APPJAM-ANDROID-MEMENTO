package org.memento.domain.entity

data class AllDay(
    val allDaySchedulesList: List<AllDaySchedules>
) {
    data class AllDaySchedules(
        val description: String,
        val endDate: String,
        val id: Int,
        val isAllDay: Boolean,
        val scheduleType: String,
        val startDate: String,
        val tagColorCode: String,
        val tagName: String
    )
}
