package org.memento.domain.repository

import org.memento.domain.entity.ScheduleList

interface ScheduleRepository {
    suspend fun getScheduleList(date: String): Result<List<ScheduleList.ScheduleWithOrderInfo>>
}
