package org.memento.data.datastroeimpl

import org.memento.data.datastore.LoginDataSource
import org.memento.data.dto.BaseResponse
import org.memento.data.dto.request.RequestLoginDto
import org.memento.data.dto.response.ResponseLoginDto
import org.memento.data.service.LoginService
import javax.inject.Inject

class LoginDataSourceImpl
    @Inject
    constructor(
        private val loginService: LoginService,
    ) : LoginDataSource {
        override suspend fun postLogin(requestLoginDto: RequestLoginDto): BaseResponse<ResponseLoginDto> = loginService.postLogin(requestLoginDto = requestLoginDto)
    }
