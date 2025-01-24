package org.memento.data.mapper.toDomain

import org.memento.data.dto.response.ResponseAllDayDto
import org.memento.domain.entity.AllDay


fun ResponseAllDayDto.toAllDay(): List<AllDay.AllDaySchedules> =
    allDaySchedulesList.map { allDaySchedulesList ->
        AllDay.AllDaySchedules(
            description = allDaySchedulesList.description.toString(),
            endDate = allDaySchedulesList.endDate,
            id = allDaySchedulesList.id,
            isAllDay = allDaySchedulesList.isAllDay,
            scheduleType = allDaySchedulesList.scheduleType,
            startDate = allDaySchedulesList.startDate,
            tagColorCode = allDaySchedulesList.tagColorCode,
            tagName = allDaySchedulesList.tagName
        )
    }



