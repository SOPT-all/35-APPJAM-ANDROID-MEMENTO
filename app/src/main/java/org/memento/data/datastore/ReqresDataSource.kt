package org.memento.data.datastore

import org.memento.data.dto.response.ResponseReqresDto

interface ReqresDataSource {
    suspend fun getReqresLists(page: Int): ResponseReqresDto
}
