package org.memento.data.mapper.toData

import org.memento.data.dto.request.RequestAddTagDto
import org.memento.data.dto.request.RequestEditTagDto
import org.memento.domain.entity.CreateTag
import org.memento.domain.entity.EditTag

fun EditTag.toData(): RequestEditTagDto =
    RequestEditTagDto(
        name = name,
        color = color,
    )

fun CreateTag.toTagData(): RequestAddTagDto =
    RequestAddTagDto(
        name = name,
        hexCode = hexCode,
    )
