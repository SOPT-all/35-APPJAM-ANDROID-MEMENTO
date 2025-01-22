package org.memento.domain.repository

import org.memento.domain.entity.AddPlan
import org.memento.domain.entity.AddTodo

interface AddScheduleRepository {
    suspend fun postAddTodo(addTodo: AddTodo): Result<Unit>

    suspend fun postAddPlan(addPlan: AddPlan): Result<Unit>
}
