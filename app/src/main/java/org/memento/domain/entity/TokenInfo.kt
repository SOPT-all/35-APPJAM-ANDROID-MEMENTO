package org.memento.domain.entity

data class TokenInfo(
    val accessToken: String,
    val refreshToken: String,
)
