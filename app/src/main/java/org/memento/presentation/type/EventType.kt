package org.memento.presentation.type

sealed class EventType {
    // todo 이벤트 타입
    data object TodoAdded : EventType()

    data object TodoUpdated : EventType()

    data object TodoDeleted : EventType()

    // schedule 이벤트 타입
    data object ScheduleAdded : EventType()

    data object ScheduleUpdated : EventType()

    data object ScheduleDeleted : EventType()
}
