package org.memento.domain.repository

import org.memento.domain.entity.CreateTag
import org.memento.domain.entity.EditTag
import org.memento.domain.entity.UpTime
import org.memento.domain.entity.WakeUpTime

interface SettingRepository {
    suspend fun patchTag(
        tagId: Int,
        editTag: EditTag,
    ): Result<Unit>

    suspend fun deleteTag(tagId: Int): Result<Unit>

    suspend fun postTag(createTag: CreateTag): Result<Unit>

    suspend fun getUptime(): Result<UpTime>

    suspend fun patchUptime(wakeUpTime: WakeUpTime): Result<Unit>
}
