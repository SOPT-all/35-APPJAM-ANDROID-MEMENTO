package org.memento.presentation.navigator.route

sealed interface Route

sealed interface MainNavigationBarRoute : Route {
    data object Today : MainNavigationBarRoute

    data object Todo : MainNavigationBarRoute
}
