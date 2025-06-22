package org.memento.presentation.type

sealed class EventType {
    data object TodoAdded : EventType()

    data object ScheduleAdded : EventType()

    data object TodoUpdated : EventType()

    data object ScheduleUpdated : EventType()

    data object TodoDeleted : EventType()

    data object ScheduleDeleted : EventType()
} 
