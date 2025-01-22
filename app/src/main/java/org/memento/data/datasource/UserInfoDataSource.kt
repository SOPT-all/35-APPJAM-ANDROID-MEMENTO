package org.memento.data.datasource

import org.memento.data.dto.BaseResponse
import org.memento.data.dto.request.RequestUserInfoUpdateDto

interface UserInfoDataSource {
    suspend fun fetchUserInfo(requestUserInfo: RequestUserInfoUpdateDto): BaseResponse<Unit>
}