package org.memento.data.datasourceimpl

import org.memento.data.datasource.TodoDataSource
import org.memento.data.dto.BaseResponse
import org.memento.data.dto.request.RequestPriorityDto
import org.memento.data.dto.response.ResponsePriorityTodoDto
import org.memento.data.dto.response.ResponseTodoCompleteDto
import org.memento.data.dto.response.ResponseTodoDto
import org.memento.data.service.TodoService
import javax.inject.Inject

class TodoDataSourceImpl
    @Inject
    constructor(
        private val todoService: TodoService,
    ) : TodoDataSource {
        override suspend fun getTodoList(): BaseResponse<ResponseTodoDto> =
            todoService.getTodoList()

        override suspend fun getTodoDateList(date: String): BaseResponse<ResponseTodoDto> =
            todoService.getTodoDateList(date)

        override suspend fun patchTodoComplete(toDoId: Int): BaseResponse<ResponseTodoCompleteDto> =
            todoService.patchTodoCompleted(toDoId)

        override suspend fun postTodoPriority(requestPriorityDto: RequestPriorityDto): BaseResponse<ResponsePriorityTodoDto> =
            todoService.postPriorityTodo(requestPriorityDto)
    }
