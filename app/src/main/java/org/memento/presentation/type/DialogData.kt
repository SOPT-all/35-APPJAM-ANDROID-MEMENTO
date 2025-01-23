package org.memento.presentation.type

data class ScheduleData(
    val title: String,
    val startDate: String,
    val endDate: String,
    val startTime: String,
    val endTime: String,
    val tagColor: String,
    val tagText: String,
    val platform: Int,
    val platformText: String,
)

data class ToDoData(
    val title: String,
    val isChecked: Boolean,
    val tagColor: String,
    val tagText: String,
    val priorityType: PriorityTagType,
)
