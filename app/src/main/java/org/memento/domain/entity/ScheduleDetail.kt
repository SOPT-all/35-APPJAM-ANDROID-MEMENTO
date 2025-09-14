package org.memento.domain.entity

data class ScheduleDetail(
    val id: Int,
    val description: String,
    val startDate: String,
    val endDate: String,
    val scheduleType: String,
    val tagId: Int,
    // 기존 데이터 클래스 유지를 위해 초기화
    val tagName: String = "",
    val tagColor: String = "#FFFFFF",
)
