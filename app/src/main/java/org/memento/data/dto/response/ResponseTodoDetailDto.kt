package org.memento.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseTodoDetailDto(
    @SerialName("id")
    val id: Int,
    @SerialName("description")
    val description: String,
    @SerialName("startDate")
    val startDate: String,
    @SerialName("endDate")
    val endDate: String,
    @SerialName("isCompleted")
    val isCompleted: Boolean,
    @SerialName("priorityType")
    val priorityType: String,
    @SerialName("tagId")
    val tagId: Int,
    @SerialName("tagName")
    val tagName: String,
    @SerialName("tagColor")
    val tagColor: String,
    @SerialName("toDoType")
    val toDoType: String,
)
