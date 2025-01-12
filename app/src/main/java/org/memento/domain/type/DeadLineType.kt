package org.memento.domain.type

enum class DeadLineType (
    val text: String,
){
    NONE(
        text = "None"
    ),
    TODAY(
        text = "Today"
    ),
    TOMORROW(
        text = "Tomorrow"
    ),
    CUSTOM_DATE(
        text = "Custom Date"
    )
}