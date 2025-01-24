package org.memento.data.dto.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseUpTimeDto(
    @SerialName("wakeUpTime")
    val wakeUpTime: String,
    @SerialName("windDownTime")
    val windDownTime: String
)
