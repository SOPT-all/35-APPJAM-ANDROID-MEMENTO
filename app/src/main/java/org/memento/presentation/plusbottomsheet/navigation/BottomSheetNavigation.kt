package org.memento.presentation.plusbottomsheet.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.memento.presentation.plusbottomsheet.MainPlusBottomSheet

fun NavController.navigationBottomSheet() {
    navigate(
        route = BottomSheetRoute.ROUTE,
    )
}

fun NavGraphBuilder.bottomSheetNavGraph(
    navigateBack: () -> Unit,
) {
    composable(BottomSheetRoute.ROUTE) {
        MainPlusBottomSheet()
    }
}

object BottomSheetRoute {
    const val ROUTE = "BottomSheet"
}
