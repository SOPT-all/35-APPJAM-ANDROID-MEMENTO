package org.memento.presentation.navigation.routes

sealed class Route(val route: String) {
    object Dummy1 : Route("dummy1")

    object Main : Route("main")

    object Todo : Route("todo")

    object Today : Route("today")

    object Reqres : Route("reqres")
}
