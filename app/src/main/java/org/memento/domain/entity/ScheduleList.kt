package org.memento.domain.entity

data class ScheduleList(
    val scheduleWithOrderInfos: List<ScheduleWithOrderInfo>,
) {
    data class ScheduleWithOrderInfo(
        val description: String,
        val endDate: String,
        val id: Int,
        val isAllDay: Boolean,
        val order: Int,
        val scheduleType: String,
        val startDate: String,
        val tagColorCode: String,
        val tagName: String,
        val timeDuration: String
    )
}
