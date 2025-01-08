package org.memento.presentation.navigation.Routes

sealed class Routes(val route: String) {
    object Dummy1 : Routes("dummy1")
    object Main : Routes("main")
    object Todo : Routes("todo")
    object Today : Routes("today")
    object Reqres : Routes("reqres")
}
