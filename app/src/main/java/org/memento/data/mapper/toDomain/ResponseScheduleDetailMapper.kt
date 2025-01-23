package org.memento.data.mapper.toDomain

import org.memento.data.dto.response.ResponseScheduleDetailDto
import org.memento.domain.entity.ScheduleDetail

fun ResponseScheduleDetailDto.toScheduleDetail(): ScheduleDetail =
    ScheduleDetail(
        id = id,
        description = description,
        startDate = startDate,
        endDate = endDate,
        scheduleType = scheduleType,
        tagId = tagId,
    )
