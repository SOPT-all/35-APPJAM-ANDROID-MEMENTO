package org.memento.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseAllDayDto(
    @SerialName("allDaySchedulesList")
    val allDaySchedulesList: List<AllDaySchedules>,
) {
    @Serializable
    data class AllDaySchedules(
        @SerialName("description")
        val description: String,
        @SerialName("endDate")
        val endDate: String,
        @SerialName("id")
        val id: Int,
        @SerialName("isAllDay")
        val isAllDay: Boolean,
        @SerialName("scheduleType")
        val scheduleType: String,
        @SerialName("startDate")
        val startDate: String,
        @SerialName("tagColorCode")
        val tagColorCode: String,
        @SerialName("tagName")
        val tagName: String,
    )
}
