package org.memento.presentation.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import org.memento.R

@Composable
fun SplashScreen() {
    Box(
        modifier =
            Modifier
                .fillMaxSize(),
        Alignment.Center,
    ) {
        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.logo_memento_green),
            contentDescription = stringResource(id = R.string.onboarding_logo),
        )
    }
}

@Preview
@Composable
private fun SplashScreenPreview() {
    SplashScreen()
}
