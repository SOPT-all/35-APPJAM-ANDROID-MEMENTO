package org.memento

import org.memento.domain.entity.LoginInfo
import org.memento.domain.repository.RefreshTokenRepository
import javax.inject.Inject

class TokenManager
    @Inject
    constructor(
        private val refreshTokenRepository: RefreshTokenRepository,
    ) {
        suspend fun postRefreshToken(): Result<LoginInfo> {
            return refreshTokenRepository.postRefreshToken()
        }
    }
