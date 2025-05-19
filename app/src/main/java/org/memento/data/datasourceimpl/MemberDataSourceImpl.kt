package org.memento.data.datasourceimpl

import org.memento.data.datasource.MemberDataSource
import org.memento.data.dto.BaseResponse
import org.memento.data.dto.request.RequestLoginDto
import org.memento.data.dto.response.ResponseLoginDto
import org.memento.data.service.MemberService
import javax.inject.Inject

class MemberDataSourceImpl
    @Inject
    constructor(
        private val memberService: MemberService,
    ) : MemberDataSource {
        override suspend fun postLogin(requestLoginDto: RequestLoginDto): BaseResponse<ResponseLoginDto> =
            memberService.postLogin(requestLoginDto = requestLoginDto)
    }
