package org.memento.domain.entity

data class AddTodo(
    val description: String,
    val startDate: String,
    val endDate: String?,
    val tagId: Long?,
    val priorityUrgency: Double?,
    val priorityImportance: Double?,
)
