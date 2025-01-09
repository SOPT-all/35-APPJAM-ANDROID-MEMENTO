package org.memento.data.mapper

import org.memento.data.dto.response.ResponseReqresDto
import org.memento.domain.model.Reqres

fun ResponseReqresDto.toReqres() = data.map {
    Reqres(
        avatar = it.avatar,
        email = it.email,
        firstName = it.firstName,
        lastName = it.lastName
    )
}
