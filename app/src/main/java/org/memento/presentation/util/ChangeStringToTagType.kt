package org.memento.presentation.util

import org.memento.presentation.type.PriorityTagType

fun String.toPriorityTagType(): PriorityTagType {
    return when (this.uppercase()) {
        "IMMEDIATE" -> PriorityTagType.Immediate
        "HIGH" -> PriorityTagType.High
        "MEDIUM" -> PriorityTagType.Medium
        "LOW" -> PriorityTagType.Low
        "NONE" -> PriorityTagType.None
        else -> PriorityTagType.None
    }
}
