package org.memento.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseScheduleDetailDto(
    @SerialName("id")
    val id: Int,
    @SerialName("description")
    val description: String,
    @SerialName("startDate")
    val startDate: String,
    @SerialName("endDate")
    val endDate: String,
    @SerialName("scheduleType")
    val scheduleType: String,
    @SerialName("tagId")
    val tagId: Int
)