package org.memento.data.mapper.toDomain

import org.memento.data.dto.response.ResponseUpTimeDto
import org.memento.domain.entity.UpTime

fun ResponseUpTimeDto.toUpTime(): UpTime =
    UpTime(
        wakeUpTime = wakeUpTime,
        windDownTime = windDownTime
    )


