package org.memento.presentation.onboarding.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.memento.R
import org.memento.presentation.util.noRippleClickable
import org.memento.ui.theme.darkModeColors
import org.memento.ui.theme.defaultMementoTypography

@Composable
fun OnboardingTopBar() {
    Box(
        modifier =
            Modifier
                .fillMaxWidth(),
    ) {
        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.btn_back),
            contentDescription = stringResource(id = R.string.onboarding_back),
        )
        Text(
            text = stringResource(id = R.string.onboarding_skip),
            style = defaultMementoTypography.body_b_14,
            color = darkModeColors.gray06,
            modifier =
                Modifier
                    .padding(
                        horizontal = 16.dp,
                        vertical = 14.dp,
                    )
                    .align(Alignment.CenterEnd)
                    .noRippleClickable {
                    },
        )
    }
}

@Preview
@Composable
fun OnboardingTopBarPreview() {
    OnboardingTopBar()
}
