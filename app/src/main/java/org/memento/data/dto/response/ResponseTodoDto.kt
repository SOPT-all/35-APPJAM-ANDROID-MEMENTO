package org.memento.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseTodoDto(
    val toDoGetResponses: List<ToDoGetResponse>,
) {
    @Serializable
    data class ToDoGetResponse(
        @SerialName("description")
        val description: String,
        @SerialName("endDate")
        val endDate: String,
        @SerialName("groupId")
        val groupId: String? = null,
        @SerialName("id")
        val id: Int,
        @SerialName("isCompleted")
        val isCompleted: Boolean,
        @SerialName("orderNum")
        val orderNum: Double,
        @SerialName("priorityType")
        val priorityType: String,
        @SerialName("priorityValue")
        val priorityValue: Double,
        @SerialName("startDate")
        val startDate: String,
        @SerialName("tagColor")
        val tagColor: String,
        @SerialName("tagName")
        val tagName: String,
        @SerialName("toDoType")
        val toDoType: String,
    )
}
