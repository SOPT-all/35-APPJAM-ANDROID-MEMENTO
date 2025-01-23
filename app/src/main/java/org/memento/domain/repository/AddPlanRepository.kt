package org.memento.domain.repository

import org.memento.domain.entity.AddSchedule
import org.memento.domain.entity.AddTodo
import org.memento.domain.entity.BrainDump

interface AddPlanRepository {
    suspend fun postAddTodo(addTodo: AddTodo): Result<Unit>

    suspend fun postAddSchedule(addSchedule: AddSchedule): Result<Unit>

    suspend fun postBrainDump(brainDump: BrainDump): Result<Unit>
}
