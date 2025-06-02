package org.memento.data.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestEditTagDto(
    @SerialName("name")
    val name: String,
    @SerialName("color")
    val color: String,
)
