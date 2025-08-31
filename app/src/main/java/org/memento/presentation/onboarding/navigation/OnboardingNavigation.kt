package org.memento.presentation.onboarding.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import org.memento.presentation.onboarding.LoginScreen
import org.memento.presentation.onboarding.OnboardingScreen1
import org.memento.presentation.onboarding.OnboardingScreen2
import org.memento.presentation.onboarding.OnboardingScreen3
import org.memento.presentation.onboarding.OnboardingScreen4

fun NavController.navigationLogin(navOptions: NavOptions? = null) {
    this.navigate(
        OnboardingRoute.Login.route,
        navOptions ?: NavOptions.Builder()
            .setPopUpTo(graph.findStartDestination().id, inclusive = true)
            .setLaunchSingleTop(true)
            .build(),
    )
}

fun NavController.navigationOnboarding1(navOptions: NavOptions? = null) {
    navigate(OnboardingRoute.OnboardingScreen1.route, navOptions)
}

fun NavController.navigationOnboarding2(navOptions: NavOptions? = null) {
    navigate(OnboardingRoute.OnboardingScreen2.route, navOptions)
}

fun NavController.navigationOnboarding3(navOptions: NavOptions? = null) {
    navigate(OnboardingRoute.OnboardingScreen3.route, navOptions)
}

fun NavController.navigationOnboarding4(navOptions: NavOptions? = null) {
    navigate(OnboardingRoute.OnboardingScreen4.route, navOptions)
}

fun NavGraphBuilder.onboardingNavGraph(
    navController: NavController,
) {
    composable(OnboardingRoute.Login.route) {
        LoginScreen(
            navigationToOnboardingScreen1 = { navController.navigationOnboarding1() },
        )
    }
    composable(OnboardingRoute.OnboardingScreen1.route) {
        OnboardingScreen1(
            navigateToOnboardingScreen2 = { navController.navigationOnboarding2() },
            navigateToOnboardingScreen4 = { navController.navigationOnboarding4() },
        )
    }
    composable(OnboardingRoute.OnboardingScreen2.route) {
        OnboardingScreen2(
            navigateToOnboardingScreen3 = { navController.navigationOnboarding3() },
            navigateToOnboardingScreen4 = { navController.navigationOnboarding4() },
            popBackStack = { navController.popBackStack() },
        )
    }
    composable(OnboardingRoute.OnboardingScreen3.route) {
        OnboardingScreen3(
            navigateToOnboardingScreen4 = { navController.navigationOnboarding4() },
            popBackStack = { navController.popBackStack() },
        )
    }
    composable(OnboardingRoute.OnboardingScreen4.route) {
        OnboardingScreen4(
            popBackStack = { navController.popBackStack() },
        )
    }
}

sealed interface OnboardingRoute {
    val route: String

    data object Login : OnboardingRoute {
        override val route: String = "LoginScreen"
    }

    data object OnboardingScreen1 : OnboardingRoute {
        override val route: String = "OnboardingScreen1"
    }

    data object OnboardingScreen2 : OnboardingRoute {
        override val route: String = "OnboardingScreen2"
    }

    data object OnboardingScreen3 : OnboardingRoute {
        override val route: String = "OnboardingScreen3"
    }

    data object OnboardingScreen4 : OnboardingRoute {
        override val route: String = "OnboardingScreen4"
    }
}
