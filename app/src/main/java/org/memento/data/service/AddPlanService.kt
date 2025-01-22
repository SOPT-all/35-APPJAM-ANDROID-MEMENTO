package org.memento.data.service

import org.memento.data.dto.BaseResponse
import org.memento.data.dto.request.RequestAddScheduleDto
import retrofit2.http.Body
import retrofit2.http.POST

interface AddPlanService {
    @POST("/api/v1/todos")
    suspend fun postAddToDo(
        @Body requestAddScheduleDto: RequestAddScheduleDto,
    ): BaseResponse<Unit>

    @POST("/api/v1/schedules")
    suspend fun postAddSchedule(
        @Body requestAddScheduleDto: RequestAddScheduleDto,
    ): BaseResponse<Unit>
}
