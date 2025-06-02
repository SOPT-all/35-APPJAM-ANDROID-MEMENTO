package org.memento.data.service

import org.memento.data.dto.BaseResponse
import org.memento.data.dto.request.RequestLoginDto
import org.memento.data.dto.response.ResponseLoginDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.PUT

interface MemberService {
    @PUT("api/v1/members")
    suspend fun postLogin(
        @Body requestLoginDto: RequestLoginDto,
    ): BaseResponse<ResponseLoginDto>

    @DELETE("api/v1/members")
    suspend fun deleteMember(): BaseResponse<Unit>
}
