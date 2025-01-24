package org.memento.data.repositoryimpl

import org.memento.data.datasource.AddPlanDataSource
import org.memento.data.mapper.toData.toData
import org.memento.data.mapper.toDomain.toAllDay
import org.memento.data.mapper.toDomain.toScheduleDetail
import org.memento.data.mapper.toDomain.toTag
import org.memento.data.mapper.toDomain.toTodoDetail
import org.memento.data.util.handleBaseResponse
import org.memento.domain.entity.AddSchedule
import org.memento.domain.entity.AddTodo
import org.memento.domain.entity.AllDay
import org.memento.domain.entity.BrainDump
import org.memento.domain.entity.ScheduleDetail
import org.memento.domain.entity.Tag
import org.memento.domain.entity.TodoDetail
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

        override suspend fun postBrainDump(brainDump: BrainDump): Result<Unit> {
            return runCatching {
                addPlanDataSource.postBrainDump(
                    requestBrainDumpDto = brainDump.toData(),
                ).handleBaseResponse().getOrThrow()
            }
        }

        override suspend fun getTagList(): Result<List<Tag>> {
            return runCatching {
                val response = addPlanDataSource.getTagList()
                response.data?.map { it.toTag() } ?: emptyList()
            }
        }

        override suspend fun patchAddTodo(
            todoId: Int,
            addTodo: AddTodo,
        ): Result<Unit> {
            return runCatching {
                addPlanDataSource.patchAddTodo(
                    todoId = todoId,
                    requestAddTodoDto = addTodo.toData(),
                ).handleBaseResponse().getOrThrow()
            }
        }

        override suspend fun patchAddSchedule(
            scheduleId: Int,
            addSchedule: AddSchedule,
        ): Result<Unit> {
            return runCatching {
                addPlanDataSource.patchAddSchedule(
                    scheduleId = scheduleId,
                    requestAddScheduleDto = addSchedule.toData(),
                ).handleBaseResponse().getOrThrow()
            }
        }

        override suspend fun getScheduleDetail(scheduleId: Int): Result<ScheduleDetail> {
            return runCatching {
                addPlanDataSource.getScheduleDetail(
                    scheduleId = scheduleId,
                ).handleBaseResponse().getOrThrow()?.toScheduleDetail() ?: throw Exception("Throw Exception Error")
            }
        }

        override suspend fun getTodoDetail(todoId: Int): Result<TodoDetail> {
            return runCatching {
                addPlanDataSource.getTodoDetail(
                    todoId = todoId,
                ).handleBaseResponse().getOrThrow()?.toTodoDetail() ?: throw Exception("Throw Exception Error")
            }
        }

        override suspend fun getAllDay(): Result<List<AllDay.AllDaySchedules>> =
            runCatching {
                val response = addPlanDataSource.getAllDay().data
                response?.toAllDay() ?: throw Exception("null")
            }
    }
