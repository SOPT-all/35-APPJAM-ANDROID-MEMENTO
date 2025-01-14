package org.memento.domain.type

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.memento.ui.theme.darkModeColors
import org.memento.ui.theme.defaultMementoTypography

enum class SelectorType(
    val width: Dp,
    val height: Dp,
    val paddingHorizontal: Dp = 0.dp,
    val paddingVertical: Dp = 0.dp,
    val cornerRadius: Dp,
    val clickedBackgroundColor: Color,
    val unClickedBackgroundColor: Color,
    val textColor: Color,
    val textStyle: TextStyle,
) {
    TIMESELECTOR(
        width = 94.dp,
        height = 36.dp,
        paddingHorizontal = 12.dp,
        paddingVertical = 8.dp,
        cornerRadius = 2.dp,
        clickedBackgroundColor = darkModeColors.gray07,
        unClickedBackgroundColor = darkModeColors.gray09,
        textColor = darkModeColors.gray02,
        textStyle = defaultMementoTypography.body_r_14,
    ),
    DATESELECTOR(
        width = 124.dp,
        height = 36.dp,
        paddingHorizontal = 22.dp,
        paddingVertical = 8.dp,
        cornerRadius = 2.dp,
        clickedBackgroundColor = darkModeColors.gray07,
        unClickedBackgroundColor = darkModeColors.gray09,
        textColor = darkModeColors.gray02,
        textStyle = defaultMementoTypography.body_r_14,
    ),
    TAG(
        width = 200.dp,
        height = 36.dp,
        paddingHorizontal = 67.dp,
        paddingVertical = 8.dp,
        cornerRadius = 2.dp,
        clickedBackgroundColor = darkModeColors.gray07,
        unClickedBackgroundColor = darkModeColors.gray09,
        textColor = darkModeColors.gray02,
        textStyle = defaultMementoTypography.body_r_14,
    ),
    DEADLINE(
        width = 200.dp,
        height = 36.dp,
        paddingHorizontal = 70.dp,
        paddingVertical = 8.dp,
        cornerRadius = 2.dp,
        clickedBackgroundColor = darkModeColors.gray07,
        unClickedBackgroundColor = darkModeColors.gray09,
        textColor = darkModeColors.gray02,
        textStyle = defaultMementoTypography.body_r_14,
    ),
    BASIC(
        width = 200.dp,
        height = 36.dp,
        paddingHorizontal = 81.dp,
        paddingVertical = 8.dp,
        cornerRadius = 2.dp,
        clickedBackgroundColor = darkModeColors.gray07,
        unClickedBackgroundColor = darkModeColors.gray09,
        textColor = darkModeColors.gray02,
        textStyle = defaultMementoTypography.body_r_14,
    )
}