package org.memento.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(
    val data: T? = null,
    val message: String? = null,
)