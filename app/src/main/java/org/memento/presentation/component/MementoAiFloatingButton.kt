package org.memento.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.memento.R
import org.memento.presentation.util.noRippleClickable
import org.memento.ui.theme.MementoTheme
import org.memento.ui.theme.darkModeColors
import org.memento.ui.theme.mementoColors

@Composable
fun MementoAiFloatingButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var isClicked by remember { mutableStateOf(false) }

    Box(
        contentAlignment = Alignment.Center,
        modifier =
            modifier
                .background(
                    color = if (isClicked) darkModeColors.green else darkModeColors.gray09,
                    shape = CircleShape,
                )
                .noRippleClickable {
                    isClicked = !isClicked
                    onClick()
                }
                .padding(12.dp),
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_sparkle_29),
            contentDescription = "AI Button",
            tint = if (isClicked) Color.Black else Color.White,
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0F0F1E)
@Composable
fun CustomFloatingButtonPreview() {
    MementoAiFloatingButton(
        onClick = { },
    )
}
