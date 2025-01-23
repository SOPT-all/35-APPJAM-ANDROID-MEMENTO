package org.memento.domain.repository

import org.memento.domain.entity.AddSchedule
import org.memento.domain.entity.AddTodo
import org.memento.domain.entity.ScheduleDetail
import org.memento.domain.entity.TodoDetail

interface AddPlanRepository {
    suspend fun postAddTodo(addTodo: AddTodo): Result<Unit>

    suspend fun postAddSchedule(addSchedule: AddSchedule): Result<Unit>

    suspend fun patchAddTodo(
        todoId: Int,
        addTodo: AddTodo,
    ): Result<Unit>

    suspend fun patchAddSchedule(
        scheduleId: Int,
        addSchedule: AddSchedule,
    ): Result<Unit>

    suspend fun getScheduleDetail(
        scheduleId: Int,
    ): Result<ScheduleDetail>

    suspend fun getTodoDetail(
        todoId: Int
    ): Result<TodoDetail>
}
