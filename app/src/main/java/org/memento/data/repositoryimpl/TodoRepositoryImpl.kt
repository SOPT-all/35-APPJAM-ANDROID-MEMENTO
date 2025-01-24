package org.memento.data.repositoryimpl

import org.memento.data.datasource.TodoDataSource
import org.memento.data.mapper.toDomain.toTodoListModel
import org.memento.data.util.handleBaseResponse
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

        override suspend fun getTodoDateList(date: String): Result<List<TodoList.ToDoGetResponse>> =
            runCatching {
                val response = todoDataSource.getTodoDateList(date).data
                response?.toTodoListModel() ?: throw Exception("null")
            }

        override suspend fun deleteTodo(toDoId: Int): Result<Unit> {
            return runCatching {
                todoDataSource.deleteTodo(
                    toDoId = toDoId,
                ).handleBaseResponse().getOrThrow()
            }
        }

        override suspend fun patchTodoComplete(toDoId: Int): Result<Unit> =
            runCatching {
                todoDataSource.patchTodoComplete(toDoId)
            }
    }
