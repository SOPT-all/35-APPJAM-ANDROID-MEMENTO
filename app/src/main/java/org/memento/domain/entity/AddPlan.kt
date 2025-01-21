package org.memento.domain.entity

import java.time.LocalDateTime

data class AddPlan(
    val description: String,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val isAllDay: Boolean,
    val tagId: Long
)