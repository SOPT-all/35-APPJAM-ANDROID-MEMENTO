package org.memento.data.mapper.toData

import org.memento.data.dto.request.RequestAddTodoDto
import org.memento.domain.entity.AddTodo

fun AddTodo.toData(): RequestAddTodoDto =
    RequestAddTodoDto(
        description = description,
        startDate = startDate,
        endDate = endDate,
        tagId = tagId,
        priorityUrgency = priorityUrgency,
        priorityImportance = priorityImportance
    )
