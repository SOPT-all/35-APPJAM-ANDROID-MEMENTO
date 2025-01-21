package org.memento.data.mapper.toDomain

import org.memento.data.dto.response.ResponseLoginDto
import org.memento.domain.entity.UserInfo

fun ResponseLoginDto.toUserInfo()=
    ResponseLoginDto(
        accessToken = accessToken,
        refreshToken=refreshToken,
        isNewUser=isNewUser
    )