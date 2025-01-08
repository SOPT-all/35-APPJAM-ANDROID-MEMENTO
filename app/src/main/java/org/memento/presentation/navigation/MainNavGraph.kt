package org.memento.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.memento.presentation.StartDummyScreen
import org.memento.presentation.main.MainScreen
import org.memento.presentation.navigation.Routes.Routes
import org.memento.presentation.reqres.ReqresScreen
import org.memento.presentation.today.TodayScreen
import org.memento.presentation.todo.TodoScreen


fun NavGraphBuilder.appNavGraph(navController: NavController) {
    composable(Routes.Dummy1.route) {
        StartDummyScreen(
            navigateToMain = { navController.navigate(Routes.Main.route) }
        )
    }

    composable(Routes.Main.route) {
        MainScreen()
    }

    composable(Routes.Reqres.route) {
        ReqresScreen()
    }
}

fun NavGraphBuilder.mainNavGraph(navController: NavController) {
    composable(Routes.Todo.route) {
        TodoScreen()
    }

    composable(Routes.Today.route) {
        TodayScreen(
            navigateToReqres = { navController.navigate(Routes.Reqres.route) }
        )
    }

    composable(Routes.Reqres.route) {
        ReqresScreen()
    }
}
