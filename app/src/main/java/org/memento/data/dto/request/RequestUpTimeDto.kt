package org.memento.data.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestUpTimeDto(
    @SerialName("wakeUpTime")
    val wakeUpTime: String,
)
