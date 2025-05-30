package org.memento.data.datasource

import org.memento.data.dto.BaseResponse
import org.memento.data.dto.request.RequestLoginDto
import org.memento.data.dto.response.ResponseLoginDto

interface MemberDataSource {
    suspend fun postLogin(requestLoginDto: RequestLoginDto): BaseResponse<ResponseLoginDto>

    suspend fun deleteMember(): BaseResponse<Unit>
}
