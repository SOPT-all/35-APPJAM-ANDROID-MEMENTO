package org.memento.data.mapper.toDomain

import org.memento.data.dto.response.ResponseScheduleDto
import org.memento.domain.entity.ScheduleList

fun ResponseScheduleDto.toScheduleListModel(): List<ScheduleList.ScheduleWithOrderInfo> =
    scheduleWithOrderInfos.map { scheduleWithOrderInfo ->
        ScheduleList.ScheduleWithOrderInfo(
            description = scheduleWithOrderInfo.description,
            endDate = scheduleWithOrderInfo.endDate,
            id = scheduleWithOrderInfo.id,
            isAllDay = scheduleWithOrderInfo.isAllDay,
            order = scheduleWithOrderInfo.order,
            scheduleType = scheduleWithOrderInfo.scheduleType,
            startDate = scheduleWithOrderInfo.startDate,
            tagColorCode = scheduleWithOrderInfo.tagColorCode,
            tagName = scheduleWithOrderInfo.tagName,
            timeDuration = scheduleWithOrderInfo.timeDuration,
        )
    }
