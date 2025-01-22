package org.memento.data.mapper.toData

import org.memento.data.dto.request.RequestAddPlanDto
import org.memento.domain.entity.AddPlan

fun AddPlan.toData(): RequestAddPlanDto =
    RequestAddPlanDto(
        description = description,
        startDate = startDate,
        endDate = endDate,
        isAllDay = isAllDay,
        tagId = tagId,
    )
