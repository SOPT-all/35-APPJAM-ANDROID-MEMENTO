package org.memento.data.repositoryimpl

import org.memento.data.datasource.SettingDataSource
import org.memento.domain.repository.SettingRepository
import javax.inject.Inject

class SettingRepositoryImpl
    @Inject
    constructor(
        private val settingDataSource: SettingDataSource,
    ) : SettingRepository
