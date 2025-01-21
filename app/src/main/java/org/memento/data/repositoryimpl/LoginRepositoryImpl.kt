package org.memento.data.repositoryimpl

import org.memento.data.datastore.LoginDataSource
import org.memento.data.mapper.toData.toData
import org.memento.data.mapper.toDomain.toUserInfo
import org.memento.data.util.handleBaseResponse
import org.memento.domain.entity.Login
import org.memento.domain.entity.UserInfo
import org.memento.domain.repository.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl
    @Inject
    constructor(
        private val loginDataSource: LoginDataSource,
    ) : LoginRepository {
        override suspend fun postLogin(login: Login): Result<UserInfo> {
            return runCatching {
                loginDataSource.postLogin(
                    requestLoginDto = login.toData(),
                ).handleBaseResponse().getOrThrow().toUserInfo()
            }
        }
    }
