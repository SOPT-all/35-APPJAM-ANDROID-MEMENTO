package org.memento.data.repositoryimpl

import org.memento.data.datasource.MemberDataSource
import org.memento.data.mapper.toData.toData
import org.memento.data.mapper.toDomain.toUserInfo
import org.memento.data.util.handleBaseResponse
import org.memento.domain.entity.Login
import org.memento.domain.entity.LoginInfo
import org.memento.domain.repository.MemberRepository
import javax.inject.Inject

class MemberRepositoryImpl
    @Inject
    constructor(
        private val memberDataSource: MemberDataSource,
    ) : MemberRepository {
        override suspend fun postLogin(login: Login): Result<LoginInfo> {
            return runCatching {
                memberDataSource.postLogin(
                    requestLoginDto = login.toData(),
                ).handleBaseResponse()
                    .getOrThrow()?.toUserInfo() ?: throw Exception("Throw Exception Error")
            }
        }
    }
