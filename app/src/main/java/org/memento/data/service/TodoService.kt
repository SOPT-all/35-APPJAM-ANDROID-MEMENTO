package org.memento.data.service

import org.memento.data.dto.BaseResponse
import org.memento.data.dto.response.ResponseTodoCompleteDto
import org.memento.data.dto.response.ResponseTodoDto
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface TodoService {
    @GET("/api/v1/todos")
    suspend fun getTodoList(): BaseResponse<ResponseTodoDto>

    @PATCH("/api/v1/todos/{toDoId}/completion")
    suspend fun patchTodoCompleted(
        @Path("toDoId") toDoId: Int,
    ): BaseResponse<ResponseTodoCompleteDto>
}
