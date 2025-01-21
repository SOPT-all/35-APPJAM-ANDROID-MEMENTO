package org.memento.presentation.util

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.memento.ui.theme.darkModeColors

fun Modifier.lineThrough(
    isChecked: Boolean,
    lineColor: Color = darkModeColors.white,
    lineThickness: Dp = 3.dp
): Modifier = this.then(
    if (isChecked) {
        Modifier.drawBehind {
            val thicknessPx = lineThickness.toPx()
            val yPosition = size.height / 2 // 텍스트 중앙에 선을 그리기 위한 Y 위치
            drawLine(
                color = lineColor,
                start = Offset(0f, yPosition),
                end = Offset(size.width, yPosition),
                strokeWidth = thicknessPx
            )
        }
    } else {
        Modifier // isChecked가 false인 경우 아무 작업도 하지 않음
    }
)
