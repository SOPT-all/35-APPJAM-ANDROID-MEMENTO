package org.memento.presentation.todo.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import org.memento.ui.theme.mementoColors

@Composable
fun TodoBoxUp(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .height(36.dp)
                .background(
                    brush =
                        Brush.verticalGradient(
                            colors =
                                listOf(
                                    mementoColors.scrollBox,
                                    mementoColors.scrollBox.copy(alpha = 0f),
                                ),
                            startY = 0f,
                            endY = Float.POSITIVE_INFINITY,
                        ),
                ),
    )
}
