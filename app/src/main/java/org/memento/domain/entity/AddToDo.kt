package org.memento.domain.entity

data class AddToDo(
    val date: String,
    val description: String,
    val deadline: String,
    val repeatOption: String,
    val repeatExpiredDate: String,
    val tagId: Int,
    val priorityUrgency: Long,
    val priorityImportance: Long
)