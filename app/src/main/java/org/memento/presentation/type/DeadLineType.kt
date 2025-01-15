package org.memento.presentation.type

enum class DeadLineType(
    val text: String,
) {
    TODAY(
        text = "Today",
    ),
    CUSTOM_DATE(
        text = "Custom Date",
    ),
}
