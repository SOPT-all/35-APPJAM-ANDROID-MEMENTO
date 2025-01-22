package org.memento.data.datasourceimpl

import org.memento.data.datasource.TodoDataSource
import org.memento.data.dto.BaseResponse
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
    }
