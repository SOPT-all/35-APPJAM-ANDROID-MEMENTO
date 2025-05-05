package org.memento.domain.entity

data class Login(
    val provider: String,
    val idToken: String,
    val timeZoneOffset: String,
    val fcmToken: String,
)

data class LoginInfo(
    val accessToken: String,
    val refreshToken: String,
    val isNewUser: Boolean,
)
