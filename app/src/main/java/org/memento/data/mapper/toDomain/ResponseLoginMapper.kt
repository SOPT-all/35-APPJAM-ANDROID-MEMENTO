package org.memento.data.mapper.toDomain

import org.memento.data.dto.response.ResponseLoginDto
import org.memento.domain.entity.LoginInfo

fun ResponseLoginDto.toUserInfo(): LoginInfo =
    LoginInfo(
        accessToken = accessToken,
        refreshToken = refreshToken,
        isNewUser = isNewUser,
    )
