package org.memento.data.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestUserInfoUpdateDto(
    @SerialName("wakeUpTime")
    val wakeUpTime: String,
    @SerialName("windDownTime")
    val windDownTime: String,
    @SerialName("job")
    val job: String,
    @SerialName("jobOtherDetail")
    val jobOtherDetail: String? = null,
    @SerialName("isStressedUnorganizedSchedule")
    val isStressedUnorganizedSchedule: Boolean,
    @SerialName("isForgetImportantThings")
    val isForgetImportantThings: Boolean,
    @SerialName("isPreferReminder")
    val isPreferReminder: Boolean,
    @SerialName("isImportantBreaks")
    val isImportantBreaks: Boolean,
)
