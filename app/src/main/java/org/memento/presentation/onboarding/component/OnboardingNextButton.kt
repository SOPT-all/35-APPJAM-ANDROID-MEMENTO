package org.memento.presentation.onboarding.component

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.memento.R
import org.memento.presentation.util.noRippleClickable
import org.memento.ui.theme.darkModeColors
import org.memento.ui.theme.defaultMementoTypography

@Composable
fun OnboardingBottomButton(
    @StringRes content: Int,
    isSelected: Boolean = false,
    onSelected: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
            Modifier
                .then(modifier)
                .fillMaxWidth()
                .height(50.dp)
                .background(
                    color =
                        if (isSelected) {
                            darkModeColors.green
                        } else {
                            darkModeColors.gray10
                        },
                    shape = RoundedCornerShape(2.dp),
                )
                .noRippleClickable {
                    onSelected()
                },
        Alignment.Center,
    ) {
        Text(
            text = stringResource(content),
            style = defaultMementoTypography.body_b_16,
            color =
                if (isSelected) {
                    darkModeColors.black
                } else {
                    darkModeColors.gray08
                },
            modifier =
                Modifier
                    .padding(vertical = 13.dp),
        )
    }
}

@Preview
@Composable
fun OnboardingBottomButtonPreview() {
    val isSelected by remember { mutableStateOf(true) }
    OnboardingBottomButton(
        content = R.string.onboarding_next,
        isSelected = isSelected,
        onSelected = {},
        modifier = Modifier.padding(horizontal = 10.dp),
    )
}
