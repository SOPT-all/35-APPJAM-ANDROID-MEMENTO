package org.memento.presentation.onboarding

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import org.memento.R
import org.memento.ui.theme.darkModeColors

@Composable
fun SplashScreen() {
    var startAnimation by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (startAnimation) 1.5f else 1f,
        animationSpec =
            tween(
                durationMillis = 3000,
            ),
    )
    LaunchedEffect(Unit) {
        startAnimation = true
    }
    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .background(color = darkModeColors.black),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            modifier = Modifier.scale(scale),
            imageVector = ImageVector.vectorResource(id = R.drawable.logo_memento_white),
            contentDescription = null,
        )
    }
}

@Preview
@Composable
private fun SplashScreenPreview() {
    SplashScreen()
}
