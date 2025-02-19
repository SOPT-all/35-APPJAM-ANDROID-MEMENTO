package org.memento.domain.repository

import org.memento.domain.entity.LoginInfo

interface RefreshTokenRepository {
    suspend fun postRefreshToken(): Result<LoginInfo>
}
