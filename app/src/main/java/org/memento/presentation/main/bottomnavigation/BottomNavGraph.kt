package org.memento.presentation.main.bottomnavigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.memento.presentation.navigation.routes.Route
import org.memento.presentation.reqres.ReqresScreen
import org.memento.presentation.today.TodayScreen
import org.memento.presentation.todo.TodoScreen

@Composable
fun BottomNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = BottomNavigationType.TODO.route,
    ) {
        composable(route = BottomNavigationType.TODO.route) {
            TodoScreen()
        }
        composable(route = BottomNavigationType.TODAY.route) {
            TodayScreen(
                navigateToReqres = {
                    navController.navigate(Route.Reqres.route)
                },
            )
        }
        composable(route = Route.Reqres.route) {
            ReqresScreen()
        }
    }
}
