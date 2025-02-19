package org.memento.data.repositoryimpl

import org.memento.data.datastore.TokenDataStore
import org.memento.data.mapper.toDomain.toUserInfo
import org.memento.data.service.LoginService
import org.memento.data.util.handleBaseResponse
import org.memento.domain.entity.LoginInfo
import org.memento.domain.repository.RefreshTokenRepository
import timber.log.Timber
import javax.inject.Inject

class RefreshTokenRepositoryImpl
    @Inject
    constructor(
        private val loginService: LoginService,
        private val tokenDataStore: TokenDataStore,
    ) : RefreshTokenRepository {
        override suspend fun postRefreshToken(): Result<LoginInfo> {
            Timber.d("refreshToken $tokenDataStore.refreshToken")
            return runCatching {
                loginService.postRefreshToken("Bearer $tokenDataStore.refreshToken").handleBaseResponse().getOrNull()
                    ?.toUserInfo()?.also { loginInfo ->
                        tokenDataStore.accessToken = loginInfo.accessToken
                        tokenDataStore.refreshToken = loginInfo.refreshToken
                    } ?: throw Exception("Failed to refresh token")
            }
        }
    }
