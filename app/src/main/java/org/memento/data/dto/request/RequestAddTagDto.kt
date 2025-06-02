package org.memento.data.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestAddTagDto(
    @SerialName("name")
    val name: String,
    @SerialName("hexCode")
    val hexCode: String,
)
