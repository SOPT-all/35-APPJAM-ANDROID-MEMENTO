package org.memento.data.datasourceimpl

import org.memento.data.datasource.SettingDataSource
import org.memento.data.dto.BaseResponse
import org.memento.data.dto.request.RequestAddTagDto
import org.memento.data.dto.request.RequestEditTagDto
import org.memento.data.service.SettingService
import javax.inject.Inject

class SettingDataSourceImpl
@Inject
constructor(
    private val settingService: SettingService,
) : SettingDataSource {
    override suspend fun patchTag(tagId: Int, requestEditTagDto: RequestEditTagDto): BaseResponse<Unit> =
        settingService.patchTag(tagId, requestEditTagDto)

    override suspend fun deleteTag(tagId: Int): BaseResponse<Unit> =
        settingService.deleteTag(tagId)

    override suspend fun postTag(requestAddTagDto: RequestAddTagDto): BaseResponse<Unit> =
        settingService.postTag(requestAddTagDto)

}
