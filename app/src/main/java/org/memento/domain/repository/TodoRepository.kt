package org.memento.domain.repository

import org.memento.domain.entity.TodoList

interface TodoRepository {
    suspend fun getTodoList(): Result<List<TodoList.ToDoGetResponse>>

    suspend fun getTodoDateList(date: String): Result<List<TodoList.ToDoGetResponse>>

    suspend fun patchTodoComplete(toDoId: Int): Result<Unit>
}
