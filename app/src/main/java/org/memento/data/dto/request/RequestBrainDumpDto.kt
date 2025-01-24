package org.memento.data.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestBrainDumpDto(
    @SerialName("content")
    val content: String,
)
