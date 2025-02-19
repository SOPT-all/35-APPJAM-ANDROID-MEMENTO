package org.memento.data.service

import org.memento.data.dto.BaseResponse
import org.memento.data.dto.request.RequestLoginDto
import org.memento.data.dto.response.ResponseLoginDto
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface LoginService {
    @POST("/api/v1/auth/login")
    suspend fun postLogin(
        @Body requestLoginDto: RequestLoginDto,
    ): BaseResponse<ResponseLoginDto>

    @POST("/api/v1/auth/token/refresh")
    suspend fun postRefreshToken(
        @Header("Authorization") refreshToken: String,
    ): BaseResponse<ResponseLoginDto>
}
