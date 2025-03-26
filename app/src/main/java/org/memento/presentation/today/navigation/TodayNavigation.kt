package org.memento.presentation.today.navigation

import androidx.compose.foundation.layout.PaddingValues
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
    navigateToSetting: () -> Unit,
    padding: PaddingValues,
) {
    composable(TodayRoute.TODAY) {
        TodayScreen(padding = padding, navigateToSetting = navigateToSetting)
    }
}

object TodayRoute {
    const val TODAY = "Today"
}
