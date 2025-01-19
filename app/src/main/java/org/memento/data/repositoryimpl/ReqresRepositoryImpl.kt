package org.memento.data.repositoryimpl

import org.memento.data.datastore.ReqresDataSource
import org.memento.data.mapper.toReqres
import org.memento.domain.entity.Reqres
import org.memento.domain.repository.ReqresRepository
import javax.inject.Inject

class ReqresRepositoryImpl
    @Inject
    constructor(
        private val reqresDataSource: ReqresDataSource,
    ) : ReqresRepository {
        override suspend fun getReqresLists(page: Int): Result<List<Reqres>> =
            runCatching {
                reqresDataSource.getReqresLists(page).toReqres()
            }
    }
