package org.memento.presentation.todo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.memento.domain.type.DeadLineType
import org.memento.domain.type.RepeatType
import org.memento.ui.theme.MementoTheme
import org.memento.ui.theme.darkModeColors

@Composable
fun MementoTextBottomSheetItem(
    option: String,
    isActive: Boolean,
    activeBgColor: Color,
    activeContentColor: Color,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = if (isActive) activeBgColor else darkModeColors.gray09
            )
            .clickableWithoutRipple {
                onClick()
            }
            .padding(vertical = 7.dp, horizontal = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = option,
            style = MementoTheme.typography.body_r_16.copy(
                color = if (isActive) activeContentColor else darkModeColors.gray07
            )
        )
    }
}

@Composable
fun DeadLineSelectorContent() {
    var activeIndex by remember { mutableIntStateOf(0) }

    val options = DeadLineType.entries.toTypedArray()

    options.forEachIndexed { index, option ->
        MementoTextBottomSheetItem(
            option = option.text,
            isActive = activeIndex == index,
            activeBgColor = darkModeColors.gray08,
            activeContentColor = darkModeColors.gray02,
            onClick = {
                activeIndex = if (activeIndex == index) -1 else index
            }
        )
    }
}

@Composable
fun RepeatSelectorContent() {
    var activeIndex by remember { mutableIntStateOf(0) }

    val options = RepeatType.entries.toTypedArray()

    options.forEachIndexed { index, option ->
        MementoTextBottomSheetItem(
            option = option.text,
            isActive = activeIndex == index,
            activeBgColor = darkModeColors.gray08,
            activeContentColor = darkModeColors.gray02,
            onClick = {
                activeIndex = if (activeIndex == index) -1 else index
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MementoSelectContentPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(space = 5.dp)
    ) {
        DeadLineSelectorContent()
        RepeatSelectorContent()
    }
}
