package org.memento.presentation.type

import androidx.annotation.StringRes
import org.memento.R

enum class OnboardingTopType(
    val isBack: Boolean,
    val isSkip: Boolean,
    val pageNum: Int,
    @StringRes val title: Int,
) {
    PAGE1(
        isBack = false,
        isSkip = true,
        pageNum = 1,
        title = R.string.onboarding1_title,
    ),
    PAGE2(
        isBack = true,
        isSkip = true,
        pageNum = 2,
        title = R.string.onboarding2_title,
    ),
    PAGE3(
        isBack = true,
        isSkip = true,
        pageNum = 3,
        title = R.string.onboarding3_title,
    ),
    PAGE4(
        isBack = true,
        isSkip = false,
        pageNum = 4,
        title = R.string.onboarding4_title,
    ),
}
