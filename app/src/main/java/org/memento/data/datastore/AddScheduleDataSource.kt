package org.memento.data.datastore

import org.memento.data.dto.BaseResponse
import org.memento.data.dto.request.RequestAddTodoDto

interface AddScheduleDataSource {
    suspend fun postAddTodo(requestAddTodoDto: RequestAddTodoDto): BaseResponse<Unit>
}
