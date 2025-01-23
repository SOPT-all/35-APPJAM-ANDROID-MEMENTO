package org.memento.data.datasource

import org.memento.data.dto.BaseResponse
import org.memento.data.dto.response.ResponseScheduleDto

interface ScheduleDataSource {
    suspend fun getScheduleList(date: String): BaseResponse<ResponseScheduleDto>

    suspend fun deleteSchedule(scheduleId: Int): BaseResponse<Unit>
}
