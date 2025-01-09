package org.memento.domain.repository

import org.memento.domain.model.Reqres

interface ReqresRepository {
    suspend fun getReqresLists(page: Int): Result<List<Reqres>>
}
