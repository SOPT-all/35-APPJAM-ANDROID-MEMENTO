package org.memento.data.service

import org.memento.data.dto.BaseResponse
import org.memento.data.dto.request.RequestAddTodoDto
import retrofit2.http.Body
import retrofit2.http.POST

interface AddScheduleService {
    @POST("/api/v1/schedules")
    suspend fun postAddToDo(
        @Body requestAddTodoDto: RequestAddTodoDto,
    ): BaseResponse<Unit>
}
