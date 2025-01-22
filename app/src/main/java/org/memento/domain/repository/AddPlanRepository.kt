package org.memento.domain.repository

import org.memento.domain.entity.AddSchedule
import org.memento.domain.entity.AddTodo

interface AddPlanRepository {
    suspend fun postAddTodo(addTodo: AddTodo): Result<Unit>

    suspend fun postAddSchedule(addSchedule: AddSchedule): Result<Unit>
}
