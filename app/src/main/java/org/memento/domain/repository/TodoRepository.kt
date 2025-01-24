package org.memento.domain.repository

import org.memento.data.dto.response.ResponsePriorityTodoDto
import org.memento.domain.entity.PriorityTodoList
import org.memento.domain.entity.TargetDate
import org.memento.domain.entity.TodoList

interface TodoRepository {
    suspend fun getTodoList(): Result<List<TodoList.ToDoGetResponse>>

    suspend fun getTodoDateList(date: String): Result<List<TodoList.ToDoGetResponse>>

    suspend fun patchTodoComplete(toDoId: Int): Result<Unit>

    suspend fun postPriorityTodo(targetDate: TargetDate): Result<PriorityTodoList>
}
