package org.memento.domain.repository

import org.memento.domain.entity.Reqres

interface ReqresRepository {
    suspend fun getReqresLists(page: Int): Result<List<Reqres>>
}
