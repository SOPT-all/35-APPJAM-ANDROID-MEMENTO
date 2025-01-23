package org.memento.domain.entity

data class TodoDetail(
    val id: Int,
    val description: String,
    val startDate: String,
    val endDate: String,
    val isCompleted: Boolean,
    val priorityType: String,
    val tagId: Int,
    val tagName: String,
    val tagColor: String,
    val toDoType: String
)