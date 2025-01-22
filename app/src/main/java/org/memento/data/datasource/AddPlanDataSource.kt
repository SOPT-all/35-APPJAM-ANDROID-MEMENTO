package org.memento.data.datasource

import org.memento.data.dto.BaseResponse
import org.memento.data.dto.request.RequestAddScheduleDto

interface AddPlanDataSource {
    suspend fun postAddTodo(requestAddScheduleDto: RequestAddScheduleDto): BaseResponse<Unit>

    suspend fun postAddSchedule(requestAddScheduleDto: RequestAddScheduleDto): BaseResponse<Unit>
}
