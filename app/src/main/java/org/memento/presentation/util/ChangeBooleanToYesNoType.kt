package org.memento.presentation.util

import org.memento.presentation.type.YesNoButtonType

fun Boolean?.toYesNoType(): YesNoButtonType? {
    return when (this) {
        true -> YesNoButtonType.YES
        false -> YesNoButtonType.NO
        null -> null
    }
}
