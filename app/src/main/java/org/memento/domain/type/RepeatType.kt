package org.memento.domain.type

enum class RepeatType (
    val text: String,
){
    NONE(
        text = "None"
    ),
    EVERY_DAY(
        text = "Every Day"
    ),
    EVERY_WEEK(
        text = "Every Week"
    ),
    EVERY_MONTH(
        text = "Every Month"
    )
}