package org.memento.presentation.type

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import org.memento.R
import org.memento.ui.theme.darkModeColors
import org.memento.ui.theme.mementoColors


enum class PriorityTagType(
    @ColorRes val outlineColor: Color,
    @ColorRes val backgroundColor: Color,
    @ColorRes val textColor: Color,
    @StringRes val chipName: Int,
    @StringRes val chipDiscription: Int,
) {
    Immediate(
        outlineColor = mementoColors.immediate,
        backgroundColor = mementoColors.immediate15,
        chipName = R.string.priority_tag_immediate,
        chipDiscription = R.string.priority_tag_immediate_text,
        textColor = darkModeColors.white
    ),
    High(
        outlineColor = mementoColors.high,
        backgroundColor = mementoColors.high15,
        chipName = R.string.priority_tag_high,
        chipDiscription = R.string.priority_tag_high_text,
        textColor = darkModeColors.white
    ),
    Medium(
        outlineColor = mementoColors.medium,
        backgroundColor = mementoColors.medium15,
        chipName = R.string.priority_tag_medium,
        chipDiscription = R.string.priority_tag_medium_text,
        textColor = darkModeColors.white
    ),
    Low(
        outlineColor = mementoColors.low,
        backgroundColor = mementoColors.low15,
        chipName = R.string.priority_tag_low,
        chipDiscription = R.string.priority_tag_low_text,
        textColor = darkModeColors.white
    ),
    None(
        outlineColor = darkModeColors.gray07,
        backgroundColor = darkModeColors.navy,
        chipName = R.string.priority_tag_none,
        chipDiscription = R.string.priority_tag_none_text,
        textColor = darkModeColors.gray05

    );
}
