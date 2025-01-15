package org.memento.presentation.onboarding.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.memento.ui.theme.darkModeColors

@Composable
fun OnboardingProgressBar(
    pageNum: Int,
    modifier: Modifier = Modifier,
) {
    val progress = listOf<Float>(0.25f, 0.5f, 0.75f, 1f)

    Box(
        modifier = Modifier
            .then(modifier)
            .fillMaxWidth()
            .height(6.dp)
            .background(
                color = darkModeColors.gray10,
                shape = RoundedCornerShape(10.dp)
            ),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(fraction = progress[pageNum - 1])
                .height(6.dp)
                .background(
                    color = darkModeColors.gray08,
                    shape = RoundedCornerShape(10.dp)
                )
        )
    }
}

@Preview
@Composable
fun OnboardingProgressBarPreview() {
    OnboardingProgressBar(1)
}