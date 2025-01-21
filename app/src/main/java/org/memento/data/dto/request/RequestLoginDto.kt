package org.memento.data.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestLoginDto(
    @SerialName("provider")
    val provider: String,
    @SerialName("idToken")
    val idToken: String,
)
