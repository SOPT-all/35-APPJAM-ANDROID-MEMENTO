package org.memento.data.datastore

import org.memento.data.dto.BaseResponse
import org.memento.data.dto.request.RequestLoginDto
import org.memento.data.dto.response.ResponseLoginDto

interface LoginDataSource {
    suspend fun postLogin(requestLoginDto: RequestLoginDto): BaseResponse<ResponseLoginDto>
}