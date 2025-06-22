package org.memento.core.event

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import org.memento.presentation.type.EventType
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventBus @Inject constructor() {
    private val _events = MutableSharedFlow<EventType>()
    val events = _events.asSharedFlow()

    suspend fun emit(event: EventType) {
        _events.emit(event)
    }
} 