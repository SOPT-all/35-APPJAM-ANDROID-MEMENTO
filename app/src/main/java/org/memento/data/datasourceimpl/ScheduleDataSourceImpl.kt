package org.memento.data.datasourceimpl

import org.memento.data.datasource.ScheduleDataSource
import org.memento.data.dto.BaseResponse
import org.memento.data.dto.response.ResponseScheduleDto
import org.memento.data.service.ScheduleService
import javax.inject.Inject

class ScheduleDataSourceImpl
@Inject
constructor(
    private val scheduleService: ScheduleService,
) : ScheduleDataSource {
    override suspend fun getScheduleList(date: String): BaseResponse<ResponseScheduleDto> =
        scheduleService.getScheduleLists(date)
}
