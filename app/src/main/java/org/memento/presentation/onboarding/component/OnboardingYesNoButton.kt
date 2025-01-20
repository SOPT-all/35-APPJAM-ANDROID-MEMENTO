package org.memento.presentation.onboarding.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.memento.presentation.util.noRippleClickable
import org.memento.ui.theme.darkModeColors
import org.memento.ui.theme.defaultMementoTypography

@Composable
fun OnboardingYesNoButton(
    content: String,
    isSelected: Boolean = false,
    onSelected: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
            Modifier
                .then(modifier)
                .fillMaxWidth()
                .height(38.dp)
                .background(
                    color =
                        if (isSelected) {
                            darkModeColors.gray08
                        } else {
                            darkModeColors.navy
                        },
                    shape = RoundedCornerShape(2.dp),
                )
                .noRippleClickable {
                    onSelected()
                },
        Alignment.Center,
    ) {
        Text(
            text = content,
            style = defaultMementoTypography.body_b_14,
            color =
                if (isSelected) {
                    darkModeColors.white
                } else {
                    darkModeColors.gray07
                },
            modifier =
                Modifier
                    .padding(vertical = 8.dp),
        )
    }
}

@Preview
@Composable
fun OnboardingYesNoButtonPreview() {
    Column {
        OnboardingYesNoButton(
            content = "Yes",
            onSelected = {},
        )
    }
}
