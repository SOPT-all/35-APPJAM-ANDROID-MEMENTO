package org.memento.data.mapper.toData

import org.memento.data.dto.request.RequestAddScheduleDto
import org.memento.data.dto.request.RequestBrainDumpDto
import org.memento.domain.entity.AddSchedule
import org.memento.domain.entity.BrainDump

fun AddSchedule.toData(): RequestAddScheduleDto =
    RequestAddScheduleDto(
        description = description,
        startDate = startDate,
        endDate = endDate,
        isAllDay = isAllDay,
        tagId = tagId,
    )

fun BrainDump.toData(): RequestBrainDumpDto =
    RequestBrainDumpDto(
        content = content,
    )
