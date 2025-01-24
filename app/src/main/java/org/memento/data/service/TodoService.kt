package org.memento.data.service

import org.memento.data.dto.BaseResponse
import org.memento.data.dto.request.RequestPriorityDto
import org.memento.data.dto.response.ResponsePriorityTodoDto
import org.memento.data.dto.response.ResponseTodoCompleteDto
import org.memento.data.dto.response.ResponseTodoDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface TodoService {
    @GET("/api/v1/todos")
    suspend fun getTodoList(): BaseResponse<ResponseTodoDto>

    @GET("/api/v1/todos")
    suspend fun getTodoDateList(
        @Query("date") date: String,
    ): BaseResponse<ResponseTodoDto>

    @PATCH("/api/v1/todos/{toDoId}/completion")
    suspend fun patchTodoCompleted(
        @Path("toDoId") toDoId: Int,
    ): BaseResponse<ResponseTodoCompleteDto>

    @POST("/api/v1/todos/prioritization/weekly")
    suspend fun postPriorityTodo(
        @Body requestPriorityDto: RequestPriorityDto,
    ): BaseResponse<ResponsePriorityTodoDto>
}
