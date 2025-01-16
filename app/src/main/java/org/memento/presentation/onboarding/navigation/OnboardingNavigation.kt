package org.memento.presentation.onboarding.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import org.memento.presentation.onboarding.LoginScreen
import org.memento.presentation.onboarding.OnboardingScreen1
import org.memento.presentation.onboarding.OnboardingScreen2
import org.memento.presentation.onboarding.OnboardingScreen3
import org.memento.presentation.onboarding.OnboardingScreen4

fun NavController.navigationOnboarding1(navOptions: NavOptions? = null) {
    navigate("OnboardingScreen1", navOptions)
}

fun NavController.navigationOnboarding2(navOptions: NavOptions? = null) {
    navigate("OnboardingScreen2", navOptions)
}

fun NavController.navigationOnboarding3(navOptions: NavOptions? = null) {
    navigate("OnboardingScreen3", navOptions)
}

fun NavController.navigationOnboarding4(navOptions: NavOptions? = null) {
    navigate("OnboardingScreen4", navOptions)
}

fun NavGraphBuilder.onboardingNavGraph(
    navigateToOnboardingScreen1: () -> Unit,
    navigateToOnboardingScreen2: () -> Unit,
    navigateToOnboardingScreen3: () -> Unit,
    navigateToOnboardingScreen4: () -> Unit,
    navigateToMainScreen: () -> Unit,
) {
    composable("LoginScreen") {
        LoginScreen(
            navigationToOnboardingScreen1 = navigateToOnboardingScreen1,
        )
    }
    composable("OnboardingScreen1") {
        OnboardingScreen1(navigateToOnboardingScreen2 = navigateToOnboardingScreen2)
    }
    composable("OnboardingScreen2") {
        OnboardingScreen2(navigateToOnboardingScreen3 = navigateToOnboardingScreen3)
    }

    composable("OnboardingScreen3") {
        OnboardingScreen3(navigateToOnboardingScreen4 = navigateToOnboardingScreen4)
    }

    composable("OnboardingScreen4") {
        OnboardingScreen4(navigateToMainScreen = navigateToMainScreen)
    }
}

object OnboardingRoute {
    const val ROUTE = "LoginScreen"
}
