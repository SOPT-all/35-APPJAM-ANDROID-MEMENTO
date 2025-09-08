package org.memento.presentation.plusbottomsheet

import java.time.LocalDateTime

data class ParsedDateResult(
    val title: String,
    val startDate: LocalDateTime? = null,
    val endDate: LocalDateTime? = null,
    val isAllDay: Boolean = false,
)
