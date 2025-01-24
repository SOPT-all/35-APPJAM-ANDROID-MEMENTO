package org.memento.data.datasourceimpl

import org.memento.data.datasource.AddPlanDataSource
import org.memento.data.dto.BaseResponse
import org.memento.data.dto.request.RequestAddScheduleDto
import org.memento.data.dto.request.RequestAddTodoDto
import org.memento.data.dto.response.ResponseAllDayDto
import org.memento.data.dto.response.ResponseScheduleDetailDto
import org.memento.data.dto.response.ResponseTodoDetailDto
import org.memento.data.service.AddPlanService
import javax.inject.Inject

class AddPlanDataSourceImpl
    @Inject
    constructor(
        private val addPlanService: AddPlanService,
    ) : AddPlanDataSource {
        override suspend fun postAddTodo(requestAddTodoDto: RequestAddTodoDto): BaseResponse<Unit> =
            addPlanService.postAddToDo(requestAddTodoDto)

        override suspend fun postAddSchedule(requestAddScheduleDto: RequestAddScheduleDto): BaseResponse<Unit> =
            addPlanService.postAddSchedule(requestAddScheduleDto)

        override suspend fun patchAddTodo(
            todoId: Int,
            requestAddTodoDto: RequestAddTodoDto,
        ): BaseResponse<Unit> =
            addPlanService.patchAddTodo(
                todoId,
                requestAddTodoDto,
            )

        override suspend fun patchAddSchedule(
            scheduleId: Int,
            requestAddScheduleDto: RequestAddScheduleDto,
        ): BaseResponse<Unit> =
            addPlanService.patchAddSchedule(
                scheduleId,
                requestAddScheduleDto,
            )

        override suspend fun getScheduleDetail(scheduleId: Int): BaseResponse<ResponseScheduleDetailDto> =
            addPlanService.getScheduleDetail(scheduleId)

        override suspend fun getTodoDetail(todoId: Int): BaseResponse<ResponseTodoDetailDto> =
            addPlanService.getTodoDetail(todoId)

        override suspend fun getAllDay(): BaseResponse<ResponseAllDayDto> =
            addPlanService.getAllDay()
    }
