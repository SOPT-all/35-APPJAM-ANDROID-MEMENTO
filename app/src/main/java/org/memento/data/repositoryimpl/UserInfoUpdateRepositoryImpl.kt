package org.memento.data.repositoryimpl

import org.memento.data.datasource.UserInfoDataSource
import org.memento.data.mapper.toData.toData
import org.memento.domain.entity.UserInfo
import org.memento.domain.repository.UserInfoUpdateRepository
import javax.inject.Inject

class UserInfoUpdateRepositoryImpl
    @Inject
    constructor(
        private val userInfoDataSource: UserInfoDataSource,
    ) : UserInfoUpdateRepository {
        override suspend fun fetchUserInfo(userInfo: UserInfo): Result<Unit> {
            return runCatching {
                userInfoDataSource.fetchUserInfo(
                    requestUserInfo = userInfo.toData(),
                )
            }
        }
    }
