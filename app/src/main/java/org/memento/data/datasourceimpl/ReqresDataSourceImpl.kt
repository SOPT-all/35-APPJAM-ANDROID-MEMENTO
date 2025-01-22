package org.memento.data.datasourceimpl

import org.memento.data.datasource.ReqresDataSource
import org.memento.data.dto.response.ResponseReqresDto
import org.memento.data.service.ReqresService
import javax.inject.Inject

class ReqresDataSourceImpl
    @Inject
    constructor(
        private val reqresService: ReqresService,
    ) : ReqresDataSource {
        override suspend fun getReqresLists(page: Int): ResponseReqresDto = reqresService.getReqresLists(page)
    }
