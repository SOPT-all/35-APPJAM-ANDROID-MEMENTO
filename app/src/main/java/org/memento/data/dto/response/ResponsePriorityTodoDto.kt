package org.memento.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponsePriorityTodoDto(
    @SerialName("todos")
    val todos: List<ResponseTodos>,
)

@Serializable
data class ResponseTodos(
    @SerialName("id")
    val id: Int,
    @SerialName("groupId")
    val groupId: String,
    @SerialName("description")
    val description: String,
    @SerialName("startDate")
    val startDate: String,
    @SerialName("endDate")
    val endDate: String,
    @SerialName("isCompleted")
    val isCompleted: Boolean,
    @SerialName("priorityValue")
    val priorityValue: Double,
    @SerialName("priorityType")
    val priorityType: String,
    @SerialName("tagName")
    val tagName: String,
    @SerialName("tagColor")
    val tagColor: String,
    @SerialName("toDoType")
    val toDoType: String,
    @SerialName("orderNum")
    val orderNum: Double,
)
