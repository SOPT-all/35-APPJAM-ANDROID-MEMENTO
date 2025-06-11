package org.memento.data.datasource

import org.memento.data.dto.BaseResponse
import org.memento.data.dto.request.RequestAddTagDto
import org.memento.data.dto.request.RequestEditTagDto
import org.memento.data.dto.response.ResponseUpTimeDto

interface SettingDataSource {
    suspend fun patchTag(
        tagId: Int,
        requestEditTagDto: RequestEditTagDto,
    ): BaseResponse<Unit>

    suspend fun deleteTag(tagId: Int): BaseResponse<Unit>

    suspend fun postTag(requestAddTagDto: RequestAddTagDto): BaseResponse<Unit>

    suspend fun getUptime(): BaseResponse<ResponseUpTimeDto>
}
