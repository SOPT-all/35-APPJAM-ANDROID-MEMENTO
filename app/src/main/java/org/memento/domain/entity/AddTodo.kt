package org.memento.domain.entity

data class AddTodo(
    val date: String,
    val description: String,
    val deadline: String,
    val tagId: Int,
    val priorityUrgency: Long,
    val priorityImportance: Long,
)
