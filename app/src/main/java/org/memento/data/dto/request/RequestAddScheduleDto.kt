package org.memento.data.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestAddScheduleDto(
    @SerialName("description")
    val description: String,
    @SerialName("startDate")
    val startDate: String,
    @SerialName("endDate")
    val endDate: String,
    @SerialName("isAllDay")
    val isAllDay: Boolean,
    @SerialName("tagId")
    val tagId: Long? = null,
)
