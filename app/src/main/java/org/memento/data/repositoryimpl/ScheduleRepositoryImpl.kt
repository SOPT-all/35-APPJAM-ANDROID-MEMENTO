package org.memento.data.repositoryimpl

import org.memento.data.mapper.toDomain.toScheduleListModel
import org.memento.data.service.ScheduleService
import org.memento.domain.entity.ScheduleList
import org.memento.domain.repository.ScheduleRepository
import javax.inject.Inject

class ScheduleRepositoryImpl
    @Inject
    constructor(
        private val scheduleService: ScheduleService,
    ) : ScheduleRepository {
        override suspend fun getScheduleList(date: String): Result<List<ScheduleList.ScheduleWithOrderInfo>> =
            runCatching {
                val response = scheduleService.getScheduleLists(date).data
                response?.toScheduleListModel() ?: throw Exception("null")
            }
    }
