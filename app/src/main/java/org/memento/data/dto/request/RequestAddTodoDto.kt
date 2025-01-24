package org.memento.data.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestAddTodoDto(
    @SerialName("description")
    val description: String,
    @SerialName("startDate")
    val startDate: String,
    @SerialName("endDate")
    val endDate: String?,
    @SerialName("tagId")
    val tagId: Int,
    @SerialName("priorityUrgency")
    val priorityUrgency: Double?,
    @SerialName("priorityImportance")
    val priorityImportance: Double?,
)
