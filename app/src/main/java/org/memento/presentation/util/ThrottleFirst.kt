package org.memento.presentation.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ThrottleFirst(private val intervalMs: Long = 2000L) {
    private var lastJob: Job? = null

    fun run(
        scope: CoroutineScope,
        block: () -> Unit,
    ) {
        if (lastJob?.isActive == true) return
        lastJob =
            scope.launch {
                block()
                delay(intervalMs)
            }
    }
}
