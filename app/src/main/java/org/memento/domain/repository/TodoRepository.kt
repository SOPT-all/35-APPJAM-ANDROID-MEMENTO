package org.memento.domain.repository

import org.memento.domain.entity.TodoList

interface TodoRepository {
    suspend fun getTodoList(): Result<List<TodoList.ToDoGetResponse>>
}
