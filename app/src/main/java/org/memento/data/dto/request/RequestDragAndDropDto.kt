package org.memento.data.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestDragAndDropDto(
    @SerialName("previousToDoId")
    val previousToDoId: Int,
    @SerialName("nextToDoId")
    val nextToDoId: Int,
)
