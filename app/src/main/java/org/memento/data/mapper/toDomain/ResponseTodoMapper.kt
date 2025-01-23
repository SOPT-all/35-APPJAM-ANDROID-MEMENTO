package org.memento.data.mapper.toDomain

import org.memento.data.dto.response.ResponseTodoDto
import org.memento.domain.entity.TodoList

fun ResponseTodoDto.toTodoListModel(): List<TodoList.ToDoGetResponse> =
    toDoGetResponses.map { toDoGetResponse ->
        TodoList.ToDoGetResponse(
            description = toDoGetResponse.description,
            endDate = toDoGetResponse.endDate,
            groupId = toDoGetResponse.groupId,
            id = toDoGetResponse.id,
            isCompleted = toDoGetResponse.isCompleted,
            order = toDoGetResponse.orderNum,
            priorityType = toDoGetResponse.priorityType,
            priorityValue = toDoGetResponse.priorityValue,
            startDate = toDoGetResponse.startDate,
            tagColor = toDoGetResponse.tagColor,
            tagName = toDoGetResponse.tagName,
            toDoType = toDoGetResponse.toDoType,
        )
    }
