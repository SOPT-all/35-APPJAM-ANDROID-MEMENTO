package org.memento.data.service

import org.memento.data.dto.response.ResponseReqresDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ReqresService {
    @GET("api/users")
    suspend fun getReqresLists(
        @Query("page") page: Int
    ): ResponseReqresDto
}
