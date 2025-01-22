package org.memento.presentation.onboarding

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import org.memento.R
import org.memento.presentation.onboarding.component.OnboardingBottomButton
import org.memento.presentation.onboarding.component.OnboardingTopAppBar
import org.memento.presentation.onboarding.component.SocialLoginButton
import org.memento.presentation.onboarding.viewmodel.OnboardingViewModel
import org.memento.presentation.type.OnboardingTopType
import org.memento.presentation.util.noRippleClickable
import timber.log.Timber

@Composable
fun OnboardingScreen4(
    viewModel: OnboardingViewModel = hiltViewModel(),
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
        Spacer(Modifier.weight(1f))
        SocialLoginButton(
            icon = R.drawable.img_google,
            content = stringResource(id = R.string.onboarding_google_login),
            onClick = {},
        )
        Spacer(Modifier.weight(1f))
        OnboardingBottomButton(
            content = R.string.onboarding_start,
            isSelected = true,
            onSelected = {
                viewModel.fetchUserInfoUpdate()
                navigateToMainScreen()
            },
            modifier =
            Modifier
                .padding(bottom = 10.dp)
            )
    }
}
