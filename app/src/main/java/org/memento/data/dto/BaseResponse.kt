package org.memento.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(
    val errorCode: String? = null,
    val message: String? = null,
    val data: T? = null,
)
