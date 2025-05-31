package org.memento.data.repositoryimpl

import org.memento.data.datasource.ScheduleDataSource
import org.memento.data.mapper.toDomain.toScheduleListModel
import org.memento.data.mapper.toDomain.toUpTime
import org.memento.data.util.handleBaseResponse
import org.memento.domain.entity.ScheduleList
import org.memento.domain.entity.UpTime
import org.memento.domain.repository.ScheduleRepository
import javax.inject.Inject

class ScheduleRepositoryImpl
@Inject
constructor(
    private val scheduleDataSource: ScheduleDataSource,
) : ScheduleRepository {
    override suspend fun getScheduleList(date: String): Result<List<ScheduleList.ScheduleWithOrderInfo>> =
        runCatching {
            val response = scheduleDataSource.getScheduleList(date).data
            response?.toScheduleListModel() ?: throw Exception("null")
        }

    override suspend fun deleteSchedule(scheduleId: Int): Result<Unit> {
        return runCatching {
            scheduleDataSource.deleteSchedule(
                scheduleId = scheduleId,
            ).handleBaseResponse().getOrThrow()
        }
    }

    override suspend fun getUpTime(): Result<UpTime> =
        runCatching {
            val response = scheduleDataSource.getUpTime().data
            response?.toUpTime() ?: throw Exception("null")
        }
}
