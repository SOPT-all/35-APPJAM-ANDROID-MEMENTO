package org.memento.data.mapper.toData

import org.memento.data.dto.request.RequestDragAndDropDto
import org.memento.domain.entity.DragAndDrop


fun DragAndDrop.toData(): RequestDragAndDropDto =
    RequestDragAndDropDto(
        previousToDoId= previousToDoId,
        nextToDoId = nextToDoId,
    )
