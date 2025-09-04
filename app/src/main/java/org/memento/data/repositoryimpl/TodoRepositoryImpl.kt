package org.memento.data.repositoryimpl

import android.util.Log
import org.memento.data.datasource.TodoDataSource
import org.memento.data.mapper.toData.toData
import org.memento.data.mapper.toDomain.toDomain
import org.memento.data.mapper.toDomain.toTodoListModel
import org.memento.data.util.handleBaseResponse
import org.memento.domain.entity.DragAndDrop
import org.memento.domain.entity.PriorityTodoList
import org.memento.domain.entity.TargetDate
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

        override suspend fun postPriorityTodo(targetDate: TargetDate): Result<PriorityTodoList> =
            runCatching {
                todoDataSource.postTodoPriority(targetDate.toData()).data?.toDomain() ?: throw Exception("null")
            }

        override suspend fun patchDragAndDrop(
            toDoId: Int,
            dragAndDrop: DragAndDrop,
        ): Result<Unit> {
            return runCatching {
                Log.d("drag", "📡 Sending PATCH to /todo/$toDoId/reorder with body = $dragAndDrop")

                todoDataSource.patchDragAndDrop(
                    toDoId = toDoId,
                    requestDragAndDropDto = dragAndDrop.toData(),
                ).handleBaseResponse().getOrThrow()
            }
        }
    }
