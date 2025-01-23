package org.memento.data.dto.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseTodoCompleteDto(
    @SerialName("id")
    val id: Int,
    @SerialName("isCompleted")
    val isCompleted: Boolean
)

