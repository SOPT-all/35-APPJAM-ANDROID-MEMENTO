package org.memento.data.datasourceimpl

import org.memento.data.datasource.UserInfoDataSource
import org.memento.data.dto.BaseResponse
import org.memento.data.dto.request.RequestUserInfoUpdateDto
import org.memento.data.service.UserInfoUpdateService
import javax.inject.Inject

class UserDataSourceImpl
    @Inject
    constructor(
        private val userService: UserInfoUpdateService,
    ) : UserInfoDataSource {
        override suspend fun fetchUserInfo(requestUserInfo: RequestUserInfoUpdateDto): BaseResponse<Unit> = userService.fetchUserInfo(requestUserInfo)
    }
