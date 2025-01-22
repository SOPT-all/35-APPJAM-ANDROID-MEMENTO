package org.memento.data.datastore

import org.memento.data.dto.BaseResponse
import org.memento.data.dto.request.RequestAddPlanDto

interface AddScheduleDataSource {
    suspend fun postAddTodo(requestAddPlanDto: RequestAddPlanDto): BaseResponse<Unit>
    suspend fun postAddPlan(requestAddPlanDto: RequestAddPlanDto): BaseResponse<Unit>
}
