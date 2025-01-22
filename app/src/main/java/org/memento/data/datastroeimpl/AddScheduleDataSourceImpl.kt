package org.memento.data.datastroeimpl

import org.memento.data.datastore.AddScheduleDataSource
import org.memento.data.dto.BaseResponse
import org.memento.data.dto.request.RequestAddPlanDto
import org.memento.data.service.AddScheduleService
import javax.inject.Inject

class AddScheduleDataSourceImpl
@Inject
constructor(
    private val addScheduleService: AddScheduleService,
) : AddScheduleDataSource {
    override suspend fun postAddTodo(requestAddPlanDto: RequestAddPlanDto): BaseResponse<Unit> =
        addScheduleService.postAddToDo(requestAddPlanDto)

    override suspend fun postAddPlan(requestAddPlanDto: RequestAddPlanDto): BaseResponse<Unit> =
        addScheduleService.postAddPlan(requestAddPlanDto)

}
