package org.memento.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.memento.presentation.StartDummyScreen
import org.memento.presentation.main.MainScreen
import org.memento.presentation.navigation.routes.Route
import org.memento.presentation.reqres.ReqresScreen
import org.memento.presentation.today.TodayScreen
import org.memento.presentation.todo.TodoScreen

fun NavGraphBuilder.appNavGraph(navController: NavController) {
    composable(Route.Dummy1.route) {
        StartDummyScreen(
            navigateToMain = { navController.navigate(Route.Main.route) },
        )
    }

    composable(Route.Main.route) {
        MainScreen()
    }

    composable(Route.Reqres.route) {
        ReqresScreen()
    }
}

fun NavGraphBuilder.mainNavGraph(navController: NavController) {
    composable(Route.Todo.route) {
        TodoScreen()
    }

    composable(Route.Today.route) {
        TodayScreen(
            navigateToReqres = { navController.navigate(Route.Reqres.route) },
        )
    }

    composable(Route.Reqres.route) {
        ReqresScreen()
    }
}
