package org.memento.domain.entity

data class PriorityTodoList(
    val priorityTodoList: TodoList,
)

data class TodoList(
    val toDoGetResponses: List<ToDoGetResponse>,
) {
    data class ToDoGetResponse(
        val description: String,
        val endDate: String,
        val groupId: String,
        val id: Int,
        val isCompleted: Boolean,
        val order: Int,
        val priorityType: String,
        val priorityValue: Double,
        val startDate: String,
        val tagColor: String,
        val tagName: String,
        val toDoType: String,
    )
}
