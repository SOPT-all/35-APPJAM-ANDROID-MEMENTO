package org.memento.data.mapper.toData

import org.memento.data.dto.request.RequestLoginDto
import org.memento.domain.entity.Login

fun Login.toData(): RequestLoginDto =
    RequestLoginDto(
        provider = provider,
        idToken = idToken,
        timeZoneOffset = timeZoneOffset,
        fcmToken = fcmToken,
    )
