package org.memento.data.mapper.toData

import org.memento.data.dto.request.RequestPriorityDto
import org.memento.domain.entity.TargetDate

fun TargetDate.toData(): RequestPriorityDto =
    RequestPriorityDto(
        targetDate = targetDate,
    )
