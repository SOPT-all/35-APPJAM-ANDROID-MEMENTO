package org.memento.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseSettingTagDto(
    @SerialName("colorCode")
    val colorCode: String,
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
)
