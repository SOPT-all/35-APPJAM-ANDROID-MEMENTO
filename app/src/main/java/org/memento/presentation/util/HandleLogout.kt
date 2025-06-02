package org.memento.presentation.util

import androidx.navigation.NavHostController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.memento.data.datastore.TokenDataStore
import org.memento.presentation.onboarding.navigation.OnboardingRoute

suspend fun handleLogout(
    tokenDataStore: TokenDataStore,
    navController: NavHostController,
) {
    tokenDataStore.clearInfo()
    withContext(Dispatchers.Main) {
        navController.navigate(OnboardingRoute.ROUTE) {
            popUpTo(0)
            launchSingleTop = true
        }
    }
}
