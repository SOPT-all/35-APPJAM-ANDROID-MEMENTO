package org.memento.presentation.onboarding

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.memento.R
import org.memento.presentation.onboarding.component.OnboardingBottomButton
import org.memento.presentation.onboarding.component.OnboardingTopAppBar
import org.memento.presentation.onboarding.component.SocialLoginButton
import org.memento.presentation.type.OnboardingTopType

@Composable
fun OnboardingScreen4(
    navigateToMainScreen: () -> Unit,
    popBackStack: () -> Unit,
) {
    Column(
        modifier =
        Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
    ) {
        OnboardingTopAppBar(
            type = OnboardingTopType.PAGE4,
            onBackClick = popBackStack,
        )
        Spacer(Modifier.height(203.dp))
        SocialLoginButton(
            icon = R.drawable.img_google,
            content = stringResource(id = R.string.onboarding_google_login),
            onClick = {},
        )
        Spacer(Modifier.weight(1f))
        OnboardingBottomButton(
            content = R.string.onboarding_start,
            isSelected = true,
            onSelected = navigateToMainScreen,
            modifier = Modifier
                .padding(bottom = 10.dp)
        )
    }
}
