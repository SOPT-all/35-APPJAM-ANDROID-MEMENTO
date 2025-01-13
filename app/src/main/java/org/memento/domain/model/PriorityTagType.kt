package org.memento.domain.model

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import org.memento.R


enum class PriorityTagType(
    @ColorRes val outlineColor: Int,
    @ColorRes val backgroundColor: Int,
    @StringRes val chipNameRes: Int,
    @StringRes val chipText: Int
) {
    Immediate(
        outlineColor = android.R.color.holo_red_dark,
        backgroundColor = android.R.color.holo_red_light,
        chipNameRes = R.string.priority_tag_immediate,
        chipText = R.string.priority_tag_immediate_text
    ),
    High(
        outlineColor = android.R.color.holo_orange_dark,
        backgroundColor = android.R.color.holo_orange_light,
        chipNameRes = R.string.priority_tag_high,
        chipText = R.string.priority_tag_high_text

    ),
    Medium(
        outlineColor = android.R.color.holo_green_dark,
        backgroundColor = android.R.color.holo_green_light,
        chipNameRes = R.string.priority_tag_medium,
        chipText = R.string.priority_tag_medium_text

    ),
    Low(
        outlineColor = android.R.color.holo_blue_dark,
        backgroundColor = android.R.color.holo_blue_light,
        chipNameRes = R.string.priority_tag_low,
        chipText = R.string.priority_tag_low_text

    ),
    None(
        outlineColor = android.R.color.transparent,
        backgroundColor = android.R.color.transparent,
        chipNameRes = R.string.priority_tag_none,
        chipText = R.string.priority_tag_none_text
    );
}
