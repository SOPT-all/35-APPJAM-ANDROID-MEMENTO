package org.memento.presentation.onboarding.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.memento.presentation.util.noRippleClickable
import org.memento.ui.theme.darkModeColors

@Composable
fun RoundCheckbox(
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    size: Dp = 20.dp,
    checkedColor: Color = darkModeColors.white,
    uncheckedBorderColor: Color = darkModeColors.gray02,
    checkmarkColor: Color = darkModeColors.black,
    borderWidth: Dp = 1.dp,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier =
            modifier
                .size(size)
                .background(
                    color = if (isChecked) checkedColor else Color.Transparent,
                    shape = CircleShape,
                )
                .border(
                    width = if (!isChecked) borderWidth else 0.dp,
                    color = if (!isChecked) uncheckedBorderColor else Color.Transparent,
                    shape = CircleShape,
                )
                .noRippleClickable { onCheckedChange(!isChecked) },
    ) {
        if (isChecked) {
            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = null,
                tint = checkmarkColor,
                modifier = Modifier.size(size * 0.6f),
            )
        }
    }
}

@Preview
@Composable
fun RoundCheckboxExample() {
    var isChecked by remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(16.dp),
    ) {
        RoundCheckbox(
            isChecked = isChecked,
            onCheckedChange = { isChecked = it },
        )
    }
}
