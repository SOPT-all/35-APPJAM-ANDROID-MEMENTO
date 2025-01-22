package org.memento.data.repositoryimpl

import org.memento.data.datastore.AddScheduleDataSource
import org.memento.data.mapper.toData.toData
import org.memento.data.util.handleBaseResponse
import org.memento.domain.entity.AddPlan
import org.memento.domain.entity.AddTodo
import org.memento.domain.repository.AddScheduleRepository
import javax.inject.Inject

class AddScheduleRepositoryImpl
    @Inject
    constructor(
        private val addScheduleDataSource: AddScheduleDataSource,
    ) : AddScheduleRepository {
        override suspend fun postAddTodo(addTodo: AddTodo): Result<Unit> {
            return runCatching {
                addScheduleDataSource.postAddTodo(
                    requestAddPlanDto = addTodo.toData(),
                ).handleBaseResponse().getOrThrow()
            }
        }

        override suspend fun postAddPlan(addPlan: AddPlan): Result<Unit> {
            return runCatching {
                addScheduleDataSource.postAddPlan(
                    requestAddPlanDto = addPlan.toData(),
                ).handleBaseResponse().getOrThrow()
            }
        }
    }
