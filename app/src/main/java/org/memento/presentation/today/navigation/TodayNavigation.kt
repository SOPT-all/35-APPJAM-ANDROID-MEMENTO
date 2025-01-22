package org.memento.presentation.today.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import org.memento.presentation.navigator.route.MainNavigationBarRoute
import org.memento.presentation.today.TodayScreen

fun NavController.navigationToday(navOptions: NavOptions) {
    navigate(
        route = MainNavigationBarRoute.Today::class.simpleName.orEmpty(),
        navOptions = navOptions,
    )
}

fun NavGraphBuilder.todayNavGraph(
    navigateToReqres: () -> Unit,
) {
    composable(TodayRoute.TODAY) {
        TodayScreen()
    }
}

object TodayRoute {
    const val TODAY = "Today"
}
