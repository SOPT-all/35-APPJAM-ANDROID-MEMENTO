package org.memento.data.util

import org.memento.data.dto.BaseResponse

fun <T> BaseResponse<T>.handleBaseResponse(): Result<T> {
    return when {
        this.success == false -> {
            Result.failure(Exception("Unexpected error"))
        }

        this.data != null -> Result.success(this.data)

        else -> {
            Result.failure(Exception("Data is null"))
        }
    }
}
