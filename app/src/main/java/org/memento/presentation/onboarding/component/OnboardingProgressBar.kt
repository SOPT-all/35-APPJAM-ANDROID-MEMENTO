package org.memento.presentation.onboarding.component

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.memento.ui.theme.darkModeColors

@Composable
fun OnboardingProgressBar(
    pageNum: Int,
    modifier: Modifier = Modifier,
) {
    val progress = listOf<Float>(0.25f, 0.5f, 0.75f, 1f)

    var targetProgress by remember { mutableFloatStateOf(progress[pageNum - 1]) }

    LaunchedEffect(pageNum) {
        targetProgress = progress[pageNum - 1]
    }

    val animatedProgress by animateFloatAsState(
        targetValue = targetProgress,
        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing),
        label = "Progress Animation",
    )

    Box(
        modifier =
            Modifier
                .then(modifier)
                .fillMaxWidth()
                .height(6.dp)
                .background(
                    color = darkModeColors.gray10,
                    shape = RoundedCornerShape(10.dp),
                ),
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxWidth(fraction = animatedProgress)
                    .height(6.dp)
                    .background(
                        color = darkModeColors.gray08,
                        shape = RoundedCornerShape(10.dp),
                    ),
        )
    }
}
