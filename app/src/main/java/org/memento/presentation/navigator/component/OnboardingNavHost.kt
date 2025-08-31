package org.memento.presentation.navigator.component

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import org.memento.presentation.onboarding.navigation.OnboardingRoute
import org.memento.presentation.onboarding.navigation.onboardingNavGraph

@Composable
fun OnboardingNavHost() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = OnboardingRoute.Login.route,
    ) {
        onboardingNavGraph(navController = navController)
    }
}
