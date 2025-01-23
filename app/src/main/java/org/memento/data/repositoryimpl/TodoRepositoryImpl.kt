package org.memento.data.repositoryimpl

import org.memento.data.datasource.TodoDataSource
import org.memento.data.mapper.toDomain.toTodoListModel
import org.memento.domain.entity.TodoList
import org.memento.domain.repository.TodoRepository
import javax.inject.Inject

class TodoRepositoryImpl
    @Inject
    constructor(
        private val todoDataSource: TodoDataSource,
    ) : TodoRepository {
        override suspend fun getTodoList(): Result<List<TodoList.ToDoGetResponse>> =
            runCatching {
                val response = todoDataSource.getTodoList().data
                response?.toTodoListModel() ?: throw Exception("null")
            }
    }
