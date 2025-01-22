package org.memento.data.datastroeimpl

import org.memento.data.datastore.AddScheduleDataSource
import org.memento.data.dto.BaseResponse
import org.memento.data.dto.request.RequestAddTodoDto
import org.memento.data.service.AddScheduleService
import javax.inject.Inject

class AddScheduleDataSourceImpl
@Inject
constructor(
    private val addScheduleService: AddScheduleService,
) : AddScheduleDataSource {
    override suspend fun postAddTodo(requestAddTodoDto: RequestAddTodoDto): BaseResponse<Unit> =
        addScheduleService.postAddToDo(requestAddTodoDto)

}
