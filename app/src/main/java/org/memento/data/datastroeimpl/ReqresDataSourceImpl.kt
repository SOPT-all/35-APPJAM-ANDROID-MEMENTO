package org.memento.data.datastroeimpl

import org.memento.data.datastore.ReqresDataSource
import org.memento.data.dto.response.ResponseReqresDto
import org.memento.data.service.ReqresService
import javax.inject.Inject

class ReqresDataSourceImpl @Inject constructor(
    private val reqresService: ReqresService
) : ReqresDataSource {
    override suspend fun getReqresLists(page: Int): ResponseReqresDto =
        reqresService.getReqresLists(page)
}
