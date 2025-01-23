package org.memento.data.datasource

import org.memento.data.dto.BaseResponse
import org.memento.data.dto.response.ResponseTodoCompleteDto
import org.memento.data.dto.response.ResponseTodoDto

interface TodoDataSource {
    suspend fun getTodoList(): BaseResponse<ResponseTodoDto>
    suspend fun getTodoDateList(date: String): BaseResponse<ResponseTodoDto>
    suspend fun patchTodoComplete(toDoId: Int): BaseResponse<ResponseTodoCompleteDto>

}
