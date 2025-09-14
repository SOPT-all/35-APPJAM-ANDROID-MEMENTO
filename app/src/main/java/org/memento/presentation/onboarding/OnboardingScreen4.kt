package org.memento.presentation.onboarding

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.memento.R
import org.memento.presentation.onboarding.component.OnboardingBottomButton
import org.memento.presentation.onboarding.component.OnboardingTopAppBar
import org.memento.presentation.onboarding.viewmodel.OnboardingViewModel
import org.memento.presentation.type.OnboardingTopType

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import org.memento.ui.theme.darkModeColors

@Composable
fun OnboardingScreen4(
    viewModel: OnboardingViewModel = hiltViewModel(),
    popBackStack: () -> Unit,
) {
    Box(Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.img_onboarding_background),
            contentDescription = null,
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            OnboardingTopAppBar(
                type = OnboardingTopType.PAGE4,
                onBackClick = popBackStack,
            )
            Spacer(Modifier.weight(1f))
//            SocialLoginButton(
//                icon = R.drawable.img_google,
//                content = stringResource(id = R.string.onboarding_google_login),
//                onClick = {},
//            )
//            Spacer(Modifier.weight(1f))
            Icon(
                painter = painterResource(id = R.drawable.ic_onboarding_character),
                contentDescription = null,
                tint = darkModeColors.white,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            OnboardingBottomButton(
                content = R.string.onboarding_start,
                isSelected = true,
                isOnBoardingFinish = true,
                onSelected = { viewModel.completeOnboarding() },
                modifier = Modifier.padding(bottom = 10.dp)
            )
        }
    }
}

