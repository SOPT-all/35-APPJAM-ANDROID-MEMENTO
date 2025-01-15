package org.memento.presentation.onboarding.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import org.memento.presentation.onboarding.OnboardingScreen1
import org.memento.presentation.onboarding.OnboardingScreen2

fun NavController.navigationOnboarding2(navOptions: NavOptions? = null) {
    navigate("OnboardingScreen2", navOptions)
}

fun NavGraphBuilder.onboardingNavGraph(
    navigateToOnboardingScreen2: () -> Unit,
    navigateToMainScreen: () -> Unit,
) {
    composable("OnboardingScreen1") {
        OnboardingScreen1(navigateToOnboardingScreen2 = navigateToOnboardingScreen2)
    }
    composable("OnboardingScreen2") {
        OnboardingScreen2(navigateToMainScreen = navigateToMainScreen)
    }
}

object OnboardingRoute {
    const val ROUTE = "OnboardingScreen1"
}
