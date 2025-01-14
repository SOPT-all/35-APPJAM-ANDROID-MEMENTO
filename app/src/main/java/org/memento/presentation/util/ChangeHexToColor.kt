package org.memento.presentation.util

import androidx.compose.ui.graphics.Color

fun changeHexToColor(hex: String): Color {
    return Color(android.graphics.Color.parseColor(hex))
}
