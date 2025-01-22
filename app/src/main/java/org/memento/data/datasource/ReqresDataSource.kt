package org.memento.data.datasource

import org.memento.data.dto.response.ResponseReqresDto

interface ReqresDataSource {
    suspend fun getReqresLists(page: Int): ResponseReqresDto
}
