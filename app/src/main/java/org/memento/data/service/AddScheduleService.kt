package org.memento.data.service

import org.memento.data.dto.BaseResponse
import org.memento.data.dto.request.RequestAddPlanDto
import retrofit2.http.Body
import retrofit2.http.POST

interface AddScheduleService {
    @POST("/api/v1/todos")
    suspend fun postAddToDo(
        @Body requestAddPlanDto: RequestAddPlanDto,
    ): BaseResponse<Unit>

    @POST("/api/v1/schedules")
    suspend fun postAddPlan(
        @Body requestAddPlanDto: RequestAddPlanDto,
    ): BaseResponse<Unit>
}
