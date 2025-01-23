package org.memento.data.service

import org.memento.data.dto.BaseResponse
import org.memento.data.dto.request.RequestAddScheduleDto
import org.memento.data.dto.request.RequestAddTodoDto
import org.memento.data.dto.request.RequestBrainDumpDto
import org.memento.data.dto.response.ResponseScheduleDetailDto
import org.memento.data.dto.response.ResponseTodoDetailDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface AddPlanService {
    @POST("/api/v1/todos")
    suspend fun postAddToDo(
        @Body requestAddTodoDto: RequestAddTodoDto,
    ): BaseResponse<Unit>

    @POST("/api/v1/schedules")
    suspend fun postAddSchedule(
        @Body requestAddScheduleDto: RequestAddScheduleDto,
    ): BaseResponse<Unit>

    @POST("/api/v1/braindump")
    suspend fun postBrainDump(
        @Body requestBrainDumpDto: RequestBrainDumpDto,
    ): BaseResponse<Unit>

    @PATCH("/api/v1/todos/{toDoId}")
    suspend fun patchAddTodo(
        @Path("toDoId") toDoId: Int,
        @Body requestAddTodoDto: RequestAddTodoDto,
    ): BaseResponse<Unit>

    @PATCH("/api/v1/schedules/{scheduleId}")
    suspend fun patchAddSchedule(
        @Path("scheduleId") scheduleId: Int,
        @Body requestAddScheduleDto: RequestAddScheduleDto,
    ): BaseResponse<Unit>

    @GET("/api/v1/schedules/{scheduleId}")
    suspend fun getScheduleDetail(
        @Path("scheduleId") scheduleId: Int,
    ): BaseResponse<ResponseScheduleDetailDto>

    @GET("/api/v1/todos/{todDoId}")
    suspend fun getTodoDetail(
        @Path("toDoId") toDoId: Int,
    ): BaseResponse<ResponseTodoDetailDto>
}
