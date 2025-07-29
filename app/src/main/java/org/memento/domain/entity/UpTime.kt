package org.memento.domain.entity

data class UpTime(
    val wakeUpTime: String,
    val windDownTime: String,
)

data class WakeUpTime(
    val wakeUpTime: String,
)
