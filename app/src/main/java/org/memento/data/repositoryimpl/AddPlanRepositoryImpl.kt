package org.memento.data.repositoryimpl

import org.memento.data.datasource.AddPlanDataSource
import org.memento.data.mapper.toData.toData
import org.memento.data.mapper.toDomain.toTag
import org.memento.data.util.handleBaseResponse
import org.memento.domain.entity.AddSchedule
import org.memento.domain.entity.AddTodo
import org.memento.domain.entity.Tag
import org.memento.domain.repository.AddPlanRepository
import javax.inject.Inject

class AddPlanRepositoryImpl
    @Inject
    constructor(
        private val addPlanDataSource: AddPlanDataSource,
    ) : AddPlanRepository {
        override suspend fun postAddTodo(addTodo: AddTodo): Result<Unit> {
            return runCatching {
                addPlanDataSource.postAddTodo(
                    requestAddTodoDto = addTodo.toData(),
                ).handleBaseResponse().getOrThrow()
            }
        }

        override suspend fun postAddSchedule(addSchedule: AddSchedule): Result<Unit> {
            return runCatching {
                addPlanDataSource.postAddSchedule(
                    requestAddScheduleDto = addSchedule.toData(),
                ).handleBaseResponse().getOrThrow()
            }
        }

        override suspend fun getTagList(): Result<List<Tag>> {
            return runCatching {
                val response = addPlanDataSource.getTagList()
                response.data?.map { it.toTag() } ?: emptyList()
            }
        }
    }
