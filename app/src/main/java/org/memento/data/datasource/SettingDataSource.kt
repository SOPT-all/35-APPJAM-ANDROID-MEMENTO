package org.memento.data.datasource

import org.memento.data.dto.BaseResponse
import org.memento.data.dto.request.RequestAddTagDto
import org.memento.data.dto.request.RequestEditTagDto

interface SettingDataSource {
    suspend fun patchTag(
        tagId: Int,
        requestEditTagDto: RequestEditTagDto,
    ): BaseResponse<Unit>

    suspend fun deleteTag(tagId: Int): BaseResponse<Unit>

    suspend fun postTag(requestAddTagDto: RequestAddTagDto): BaseResponse<Unit>
}
