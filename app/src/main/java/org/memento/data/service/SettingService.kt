package org.memento.data.service

import org.memento.data.dto.BaseResponse
import org.memento.data.dto.request.RequestAddTagDto
import org.memento.data.dto.request.RequestEditTagDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface SettingService {
    @PATCH("/api/v1/tags/{tagId}")
    suspend fun patchTag(
        @Path("tagId") tagId: Int,
        @Body requestEditTagDto: RequestEditTagDto,
    ): BaseResponse<Unit>

    @DELETE("/api/v1/tags/{tagId}")
    suspend fun deleteTag(
        @Path("tagId") tagId: Int,
    ): BaseResponse<Unit>

    @POST("/api/v1/tags")
    suspend fun postTag(
        @Body requestAddTagDto: RequestAddTagDto,
    ): BaseResponse<Unit>
}
