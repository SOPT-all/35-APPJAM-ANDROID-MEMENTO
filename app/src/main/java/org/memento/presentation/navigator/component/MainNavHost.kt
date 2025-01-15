package org.memento.presentation.navigator.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import org.memento.presentation.navigator.MainNavigator
import org.memento.presentation.onboarding.navigation.onboardingNavGraph
import org.memento.presentation.plusbottomsheet.navigation.bottomSheetNavGraph
import org.memento.presentation.reqres.navigation.reqresNavGraph
import org.memento.presentation.today.navigation.todayNavGraph
import org.memento.presentation.todo.navigation.todoGraph

@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    navigator: MainNavigator,
    padding: PaddingValues,
) {
    Box(
        modifier =
            modifier
                .fillMaxSize()
                .background(color = Color.Gray),
    ) {
        NavHost(
            navController = navigator.navHostController,
            startDestination = navigator.startDestination,
        ) {
            onboardingNavGraph(
                navigateToOnboardingScreen2 = { navigator.navigateToOnboarding2() },
                navigateToMainScreen = { navigator.navigateMainNavigation(BottomNavigationType.TODAY) },
            )

            todayNavGraph(
                navigateToReqres = { navigator.navigateToReqres() },
            )

            todoGraph(
                navigateToReqres = { navigator.navigateToReqres() },
            )

            reqresNavGraph(
                navigateBack = { navigator.navHostController.popBackStack() },
            )
            bottomSheetNavGraph(
                navigateBack = { navigator.navigateToBottomSheet() },
            )
        }
    }
}
