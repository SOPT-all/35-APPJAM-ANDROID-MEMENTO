package org.memento.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.memento.domain.type.SelectorType
import org.memento.presentation.util.noRippleClickable

@Composable
fun MementoTimeDateSelector(
    selectorType: SelectorType,
    isClicked: Boolean = false,
    onClickedChange: (Boolean) -> Unit = {},
    content: String,
    modifier: Modifier = Modifier,
) {
    var clicked by remember { mutableStateOf(isClicked) }

    Box(
        modifier = Modifier
            .then(modifier)
            .clip(RoundedCornerShape(selectorType.cornerRadius))
            .background(color = if (clicked) selectorType.clickedBackgroundColor else selectorType.unClickedBackgroundColor)
            .noRippleClickable {
                // Todo : Selector 호출하고
                clicked = !clicked
                onClickedChange(clicked)
            },
        Alignment.Center
    ) {
        Text(
            text = "00:00 AM",
            style = selectorType.textStyle,
            color = Color.Transparent,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)

        )
        Text(
            text = content,
            style = selectorType.textStyle,
            color = selectorType.textColor,
        )
    }
}

