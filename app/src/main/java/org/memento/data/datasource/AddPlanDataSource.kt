package org.memento.data.datasource

import org.memento.data.dto.BaseResponse
import org.memento.data.dto.request.RequestAddScheduleDto
import org.memento.data.dto.request.RequestAddTodoDto

interface AddPlanDataSource {
    suspend fun postAddTodo(requestAddTodoDto: RequestAddTodoDto): BaseResponse<Unit>

    suspend fun postAddSchedule(requestAddScheduleDto: RequestAddScheduleDto): BaseResponse<Unit>
}
