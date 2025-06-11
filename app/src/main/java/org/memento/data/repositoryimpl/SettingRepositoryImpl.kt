package org.memento.data.repositoryimpl

import org.memento.data.datasource.SettingDataSource
import org.memento.data.mapper.toData.toData
import org.memento.data.mapper.toData.toTagData
import org.memento.data.mapper.toDomain.toUpTime
import org.memento.data.util.handleBaseResponse
import org.memento.domain.entity.CreateTag
import org.memento.domain.entity.EditTag
import org.memento.domain.entity.UpTime
import org.memento.domain.repository.SettingRepository
import javax.inject.Inject

class SettingRepositoryImpl
    @Inject
    constructor(
        private val settingDataSource: SettingDataSource,
    ) : SettingRepository {
        override suspend fun patchTag(
            tagId: Int,
            editTag: EditTag,
        ): Result<Unit> {
            return runCatching {
                settingDataSource.patchTag(
                    tagId = tagId,
                    requestEditTagDto = editTag.toData(),
                ).handleBaseResponse().getOrThrow()
            }
        }

        override suspend fun deleteTag(tagId: Int): Result<Unit> {
            return runCatching {
                settingDataSource.deleteTag(
                    tagId = tagId,
                ).handleBaseResponse().getOrThrow()
            }
        }

        override suspend fun postTag(createTag: CreateTag): Result<Unit> {
            return runCatching {
                settingDataSource.postTag(
                    requestAddTagDto = createTag.toTagData(),
                ).handleBaseResponse().getOrThrow()
            }
        }

        override suspend fun getUptime(): Result<UpTime> {
            return runCatching {
                val response = settingDataSource.getUptime().data
                response?.toUpTime() ?: throw Exception("null")
            }
        }
    }
