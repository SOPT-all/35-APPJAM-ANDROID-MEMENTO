package org.memento.data.datasource

import org.memento.data.dto.BaseResponse
import org.memento.data.dto.request.RequestAddScheduleDto
import org.memento.data.dto.request.RequestAddTodoDto
import org.memento.data.dto.request.RequestBrainDumpDto
import org.memento.data.dto.response.ResponseScheduleDetailDto
import org.memento.data.dto.response.ResponseTodoDetailDto

interface AddPlanDataSource {
    suspend fun postAddTodo(requestAddTodoDto: RequestAddTodoDto): BaseResponse<Unit>

    suspend fun postAddSchedule(requestAddScheduleDto: RequestAddScheduleDto): BaseResponse<Unit>

    suspend fun postBrainDump(requestBrainDumpDto: RequestBrainDumpDto): BaseResponse<Unit>

    suspend fun patchAddTodo(
        todoId: Int,
        requestAddTodoDto: RequestAddTodoDto,
    ): BaseResponse<Unit>

    suspend fun patchAddSchedule(
        scheduleId: Int,
        requestAddScheduleDto: RequestAddScheduleDto,
    ): BaseResponse<Unit>

    suspend fun getScheduleDetail(scheduleId: Int): BaseResponse<ResponseScheduleDetailDto>

    suspend fun getTodoDetail(todoId: Int): BaseResponse<ResponseTodoDetailDto>
}
