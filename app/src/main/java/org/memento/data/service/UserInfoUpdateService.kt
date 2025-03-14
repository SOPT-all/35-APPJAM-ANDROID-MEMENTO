package org.memento.data.service

import org.memento.data.dto.BaseResponse
import org.memento.data.dto.request.RequestUserInfoUpdateDto
import retrofit2.http.Body
import retrofit2.http.PATCH

interface UserInfoUpdateService {
    @PATCH("api/v1/members/personal-info")
    suspend fun fetchUserInfo(
        @Body requestUserInfo: RequestUserInfoUpdateDto,
    ): BaseResponse<Unit>
}
