package org.memento.data.mapper.toData

import org.memento.data.dto.request.RequestAddScheduleDto
import org.memento.domain.entity.AddSchedule

fun AddSchedule.toData(): RequestAddScheduleDto =
    RequestAddScheduleDto(
        description = description,
        startDate = startDate,
        endDate = endDate,
        isAllDay = isAllDay,
        tagId = tagId,
    )
