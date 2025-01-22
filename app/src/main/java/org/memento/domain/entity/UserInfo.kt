package org.memento.domain.entity

data class UserInfo(
    val wakeUpTime: String,
    val windDownTime: String,
    val job: String,
    val jobOtherDetail: String? = null,
    val isStressedUnorganizedSchedule: Boolean,
    val isForgetImportantThings: Boolean,
    val isPreferReminder: Boolean,
    val isImportantBreaks: Boolean,
)
