package org.memento.data.mapper.toData

import org.memento.data.dto.request.RequestUserInfoUpdateDto
import org.memento.domain.entity.UserInfo

fun UserInfo.toData(): RequestUserInfoUpdateDto =
    RequestUserInfoUpdateDto(
        wakeUpTime = wakeUpTime,
        windDownTime = windDownTime,
        job = job,
        jobOtherDetail = jobOtherDetail,
        isStressedUnorganizedSchedule = isStressedUnorganizedSchedule,
        isForgetImportantThings = isForgetImportantThings,
        isPreferReminder = isPreferReminder,
        isImportantBreaks = isImportantBreaks,
    )
