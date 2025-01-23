package org.memento.domain.entity

data class Login(
    val provider: String,
    val idToken: String,
)

data class LoginInfo(
    val accessToken: String,
    val refreshToken: String,
    val isNewUser: Boolean,
)
