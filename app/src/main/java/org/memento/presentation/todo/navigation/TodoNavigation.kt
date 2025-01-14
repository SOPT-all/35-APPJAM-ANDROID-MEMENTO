package org.memento.presentation.todo.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import org.memento.presentation.navigator.route.MainNavigationBarRoute
import org.memento.presentation.todo.TodoScreen

fun NavController.navigationTodo(navOptions: NavOptions) {
    navigate(
        route = MainNavigationBarRoute.Todo::class.simpleName.orEmpty(),
        navOptions = navOptions,
    )
}

fun NavGraphBuilder.todoGraph(
    navigateToReqres: () -> Unit,
) {
    composable(TodoRoute.TODO) {
        TodoScreen()
    }
}

object TodoRoute {
    const val TODO = "Todo"
}
