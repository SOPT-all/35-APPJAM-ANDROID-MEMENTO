package org.memento.data.datasourceimpl

import org.memento.data.datasource.AddPlanDataSource
import org.memento.data.dto.BaseResponse
import org.memento.data.dto.request.RequestAddScheduleDto
import org.memento.data.dto.request.RequestAddTodoDto
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
    }
