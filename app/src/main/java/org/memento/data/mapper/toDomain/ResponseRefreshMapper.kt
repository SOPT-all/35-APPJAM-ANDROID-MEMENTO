package org.memento.data.mapper.toDomain

import org.memento.data.dto.response.ResponseRefreshDto
import org.memento.domain.entity.TokenInfo

fun ResponseRefreshDto.toTokenInfo(): TokenInfo =
    TokenInfo(
        accessToken = accessToken,
        refreshToken = refreshToken,
    )
