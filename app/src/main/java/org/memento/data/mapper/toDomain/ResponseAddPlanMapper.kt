package org.memento.data.mapper.toDomain

import org.memento.data.dto.response.ResponseTagDto
import org.memento.domain.entity.Tag

fun ResponseTagDto.toTag(): Tag =
    Tag(
        id = id,
        name = name,
        colorCode = colorCode,
    )
