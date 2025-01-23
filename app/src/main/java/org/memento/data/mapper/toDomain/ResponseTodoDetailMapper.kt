package org.memento.data.mapper.toDomain

import org.memento.data.dto.response.ResponseTodoDetailDto
import org.memento.domain.entity.TodoDetail

fun ResponseTodoDetailDto.toTodoDetail(): TodoDetail =
    TodoDetail(
        id = id,
        description = description,
        startDate = startDate,
        endDate = endDate,
        isCompleted = isCompleted,
        priorityType = priorityType,
        tagId = tagId,
        tagName = tagName,
        tagColor = tagColor,
        toDoType = toDoType,
    )
