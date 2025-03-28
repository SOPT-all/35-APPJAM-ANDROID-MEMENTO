package org.memento.data.datasourceimpl

import org.memento.data.datasource.SettingDataSource
import org.memento.data.service.SettingService
import javax.inject.Inject

class SettingDataSourceImpl
@Inject
constructor(
    private val settingService: SettingService
) : SettingDataSource {
}
