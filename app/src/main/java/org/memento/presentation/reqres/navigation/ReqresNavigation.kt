package org.memento.presentation.reqres.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.memento.presentation.reqres.ReqresScreen

fun NavController.navigationReqres() {
    navigate(
        route = ReqresRoute.ROUTE,
    )
}

fun NavGraphBuilder.reqresNavGraph(
    navigateBack: () -> Unit,
) {
    composable(ReqresRoute.ROUTE) {
        ReqresScreen(onBack = navigateBack)
    }
}

object ReqresRoute {
    const val ROUTE = "Reqres"
}
