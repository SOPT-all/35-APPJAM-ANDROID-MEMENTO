package org.memento.presentation.util

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.memento.ui.theme.darkModeColors

fun Modifier.drawHorizontalLine(
    color: Color = darkModeColors.gray09,
    thickness: Dp = 1.dp,
    horizontalPadding: Dp = 3.dp,
    verticalPadding: Dp = 5.dp,
): Modifier {
    return this
        .fillMaxWidth()
        .padding(horizontal = horizontalPadding, vertical = verticalPadding)
        .height(thickness)
        .background(color)
}
