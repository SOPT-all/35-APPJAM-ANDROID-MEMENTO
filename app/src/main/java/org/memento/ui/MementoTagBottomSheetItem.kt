package org.memento.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
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
import org.memento.domain.type.ColorTagData
import org.memento.presentation.util.changeHexToColor
import org.memento.presentation.util.noRippleClickable
import org.memento.ui.theme.MementoTheme
import org.memento.ui.theme.darkModeColors

@Composable
fun MementoTagBottomSheetItem(
    option: String,
    isActive: Boolean,
    activeBgColor: Color,
    activeContentColor: Color,
    tagColor: Color,
    onClick: () -> Unit,
) {
    Row(
        modifier =
        Modifier
            .fillMaxWidth()
            .background(
                color = if (isActive) activeBgColor else darkModeColors.gray09,
            )
            .noRippleClickable {
                onClick()
            }
            .padding(vertical = 7.dp, horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Box(
            modifier =
            Modifier
                .size(10.dp)
                .background(
                    color = tagColor,
                    shape = CircleShape,
                ),
        )
        Text(
            text = option,
            style =
            MementoTheme.typography.body_r_16.copy(
                color = if (isActive) activeContentColor else darkModeColors.gray07,
            ),
        )
    }
}

@Composable
fun TagSelectorContent(
    onTagSelected: (String, String) -> Unit,
) {
    var activeIndex by remember { mutableIntStateOf(0) }

    val options = getDummyTagData()

    var itemHeight by remember { mutableIntStateOf(0) }
    val maxHeight = if (itemHeight > 0) (itemHeight * 5.5).dp else 25.dp


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(maxHeight)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            itemsIndexed(options) { index, option ->
                MementoTagBottomSheetItem(
                    option = option.text,
                    isActive = activeIndex == index,
                    activeBgColor = darkModeColors.gray08,
                    activeContentColor = darkModeColors.gray02,
                    tagColor = changeHexToColor(option.color),
                    onClick = {
                        if (activeIndex != index) {
                            activeIndex = index
                            onTagSelected(option.color, option.text)
                        }
                    },
                )
            }
        }
    }

}

fun getDummyTagData(): List<ColorTagData> {
    return listOf(
        ColorTagData("Untitled", "#FF5733"),
        ColorTagData("SOPT", "#33FF57"),
        ColorTagData("Fitness", "#3357FF"),
        ColorTagData("Project", "#FFD700"),
        ColorTagData("Untitled", "#FF5733"),
        ColorTagData("SOPT", "#33FF57"),
        ColorTagData("Fitness", "#3357FF"),
        ColorTagData("Project", "#FFD700"),
        ColorTagData("Untitled", "#FF5733"),
        ColorTagData("SOPT", "#33FF57"),
        ColorTagData("Fitness", "#3357FF"),
        ColorTagData("Project", "#FFD700"),
        ColorTagData("Untitled", "#FF5733"),
        ColorTagData("SOPT", "#33FF57"),
        ColorTagData("Fitness", "#3357FF"),
        ColorTagData("Project", "#FFD700"),
        ColorTagData("Project", "#FFD700"),
        ColorTagData("Untitled", "#FF5733"),
        ColorTagData("SOPT", "#33FF57"),
        ColorTagData("Fitness", "#3357FF"),
        ColorTagData("Project", "#FFD700"),
        ColorTagData("Project", "#FFD700"),
        ColorTagData("Untitled", "#FF5733"),
        ColorTagData("SOPT", "#33FF57"),
        ColorTagData("Fitness", "#3357FF"),
        ColorTagData("Project", "#FFD700"),
    )
}

@Preview(showBackground = true)
@Composable
fun MementoTagBottomSheetItemPreview() {
    Column(
        modifier =
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(space = 5.dp),
    ) {
        TagSelectorContent(
            onTagSelected = { color, tag ->
            },
        )
    }
}
