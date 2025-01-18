package org.memento.presentation.todo.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.memento.ui.theme.mementoColors

@Composable
fun TodoBoxDown(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .height(28.dp)
                .background(
                    brush =
                        Brush.verticalGradient(
                            colors =
                                listOf(
                                    mementoColors.scrollBox.copy(alpha = 0f),
                                    mementoColors.scrollBox,
                                ),
                            startY = 0f,
                            endY = Float.POSITIVE_INFINITY,
                        ),
                ),
    )
}

@Preview
@Composable
fun TodoBoxUpPrev() {
    TodoBoxUp()
}

@Preview
@Composable
fun TodoBoxDownPre() {
    TodoBoxDown()
}
