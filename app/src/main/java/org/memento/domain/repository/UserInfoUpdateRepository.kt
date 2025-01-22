package org.memento.domain.repository

import org.memento.data.dto.BaseResponse
import org.memento.data.dto.request.RequestUserInfoUpdateDto
import org.memento.domain.entity.UserInfo

interface UserInfoUpdateRepository {
    suspend fun fetchUserInfo(
        userInfo: UserInfo
    ): Result<Unit>
}