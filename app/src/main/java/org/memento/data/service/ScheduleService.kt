package org.memento.data.service

import org.memento.data.dto.BaseResponse
import org.memento.data.dto.response.ResponseScheduleDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ScheduleService {
    @GET("/api/v1/schedules")
    suspend fun getScheduleLists(
        @Query("date") date: String,
    ): BaseResponse<ResponseScheduleDto>

}
