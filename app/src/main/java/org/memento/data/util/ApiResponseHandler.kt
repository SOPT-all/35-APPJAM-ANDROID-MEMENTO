package org.memento.data.util

import org.memento.data.dto.BaseResponse

fun <T> BaseResponse<T>.handleBaseResponse(): Result<T?> {
    return when {
        errorCode != null -> {
            // 실패: errorCode가 존재
            Result.failure(Exception("Error occurred: $errorCode"))
        }

        data != null -> {
            // 성공: data가 존재
            Result.success(data)
        }

        else -> {
            // 성공이지만 data가 null인 경우
            Result.success(null)
        }
    }
}

