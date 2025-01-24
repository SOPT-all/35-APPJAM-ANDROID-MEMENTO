package org.memento.data.mapper.toDomain

import org.memento.data.dto.response.ResponsePriorityTodoDto
import org.memento.domain.entity.PriorityTodoList
import org.memento.domain.entity.TodoList

fun ResponsePriorityTodoDto.toDomain(): PriorityTodoList =
    PriorityTodoList(
        priorityTodoList =
            todos.map { todoList ->
                TodoList(
                    toDoGetResponses =
                        todoList.map { responseTodos ->
                            TodoList.ToDoGetResponse(
                                id = responseTodos.id,
                                groupId = responseTodos.groupId,
                                description = responseTodos.description,
                                startDate = responseTodos.startDate,
                                endDate = responseTodos.endDate,
                                isCompleted = responseTodos.isCompleted,
                                priorityValue = responseTodos.priorityValue,
                                priorityType = responseTodos.priorityType,
                                tagName = responseTodos.tagName,
                                tagColor = responseTodos.tagColor,
                                toDoType = responseTodos.toDoType,
                                order = responseTodos.orderNum,
                            )
                        },
                )
            },
    )
