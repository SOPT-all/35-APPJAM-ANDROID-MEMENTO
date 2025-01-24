package org.memento.domain.repository

import org.memento.domain.entity.ScheduleList
import org.memento.domain.entity.UpTime

interface ScheduleRepository {
    suspend fun getScheduleList(date: String): Result<List<ScheduleList.ScheduleWithOrderInfo>>

    suspend fun deleteSchedule(scheduleId: Int): Result<Unit>

    suspend fun getUpTime(): Result<UpTime>
}
