package org.memento.data.mapper.toData

import org.memento.data.dto.request.RequestUpTimeDto
import org.memento.domain.entity.WakeUpTime

fun WakeUpTime.toData(): RequestUpTimeDto =
    RequestUpTimeDto(
        wakeUpTime = this.wakeUpTime,
    )
