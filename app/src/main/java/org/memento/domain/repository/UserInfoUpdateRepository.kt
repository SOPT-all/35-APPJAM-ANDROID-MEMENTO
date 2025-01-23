package org.memento.domain.repository

import org.memento.domain.entity.UserInfo

interface UserInfoUpdateRepository {
    suspend fun fetchUserInfo(
        userInfo: UserInfo,
    ): Result<Unit>
}
