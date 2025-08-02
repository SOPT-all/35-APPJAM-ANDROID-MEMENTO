package org.memento.core.event

import kotlinx.coroutines.flow.MutableSharedFlow

object GlobalLogoutEvent {
    val trigger = MutableSharedFlow<Unit>(replay = 0)
}
