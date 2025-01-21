package org.memento.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(
    val success: Boolean? = null,
    val message: String? = null,
    val data: T? = null,
)
