package org.memento.presentation.navigator.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import org.memento.R
import org.memento.presentation.navigator.route.MainNavigationBarRoute
import org.memento.presentation.navigator.route.Route

enum class BottomNavigationType(
    @DrawableRes val iconRes: Int,
    @StringRes val label: Int? = null,
    val route: MainNavigationBarRoute,
) {
    TODAY(
        iconRes = R.drawable.ic_android_black_24,
        route = MainNavigationBarRoute.Today,
    ),
    TODO(
        iconRes = R.drawable.ic_android_black_24,
        route = MainNavigationBarRoute.Todo,
    ),
    ;

    companion object {
        @Composable
        fun find(predicate: @Composable (MainNavigationBarRoute) -> Boolean): BottomNavigationType? {
            return entries.find { predicate(it.route) }
        }

        @Composable
        fun contains(predicate: @Composable (Route) -> Boolean): Boolean {
            return entries.map { it.route }.any { predicate(it) }
        }
    }
}
