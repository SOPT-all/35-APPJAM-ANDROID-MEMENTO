package org.memento.data.datasource

import org.memento.data.dto.BaseResponse
import org.memento.data.dto.response.ResponseTodoDto

interface TodoDataSource {
    suspend fun getTodoList(): BaseResponse<ResponseTodoDto>
}
