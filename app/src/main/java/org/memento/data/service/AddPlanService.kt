package org.memento.data.service

import org.memento.data.dto.BaseResponse
import org.memento.data.dto.request.RequestAddScheduleDto
import org.memento.data.dto.request.RequestAddTodoDto
import org.memento.data.dto.response.ResponseTagDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AddPlanService {
    @POST("/api/v1/todos")
    suspend fun postAddToDo(
        @Body requestAddTodoDto: RequestAddTodoDto,
    ): BaseResponse<Unit>

    @POST("/api/v1/schedules")
    suspend fun postAddSchedule(
        @Body requestAddScheduleDto: RequestAddScheduleDto,
    ): BaseResponse<Unit>

    @GET("/api/v1/tags")
    suspend fun getTagList(): BaseResponse<List<ResponseTagDto>>
}
