package org.memento.data.mapper.toDomain

import org.memento.data.dto.response.ResponseSettingTagDto
import org.memento.domain.entity.SettingTag

fun ResponseSettingTagDto.toSettingTag(): SettingTag =
    SettingTag(
        id = id,
        name = name,
        colorCode = colorCode,
    )
