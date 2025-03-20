package org.memento.data.service

import org.memento.data.dto.BaseResponse
import org.memento.data.dto.response.ResponseScheduleDto
import org.memento.data.dto.response.ResponseUpTimeDto
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ScheduleService {
    @GET("api/v1/schedules")
    suspend fun getScheduleLists(
        @Query("date") date: String,
    ): BaseResponse<ResponseScheduleDto>

    @DELETE("api/v1/schedules/{scheduleId}")
    suspend fun deleteSchedule(
        @Path("scheduleId") scheduleId: Int,
    ): BaseResponse<Unit>

    @GET("api/v1/members/personal-info/uptime")
    suspend fun getUpTime(): BaseResponse<ResponseUpTimeDto>
}
