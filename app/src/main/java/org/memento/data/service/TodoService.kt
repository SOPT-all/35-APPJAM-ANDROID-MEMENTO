package org.memento.data.service

import org.memento.data.dto.BaseResponse
import org.memento.data.dto.response.ResponseTodoDto
import retrofit2.http.GET


interface TodoService {
    @GET("/api/v1/todos")
    suspend fun getTodoList(
    ): BaseResponse<ResponseTodoDto>
}
