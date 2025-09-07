package org.memento.presentation.navigator.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import org.memento.presentation.navigator.MainNavigator
import org.memento.presentation.reqres.navigation.reqresNavGraph
import org.memento.presentation.setting.navigation.settingNavGraph
import org.memento.presentation.today.navigation.todayNavGraph
import org.memento.presentation.todo.navigation.todoGraph

@Composable
fun MainNavHost(
    navigator: MainNavigator,
    padding: PaddingValues,
    startDestination: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
            modifier
                .fillMaxSize(),
    ) {
        NavHost(
            navController = navigator.navHostController,
            startDestination = startDestination,
        ) {
            todayNavGraph(
                padding = padding,
                navigateToSetting = { navigator.navigateToSetting() },
            )

            todoGraph(
                padding = padding,
                navigateToSetting = { navigator.navigateToSetting() },
            )

            reqresNavGraph(
                navigateBack = { navigator.navHostController.popBackStack() },
            )

            settingNavGraph(
                navigateBack = { navigator.navHostController.popBackStack() },
                navigateToSettingTag = { navigator.navigateToSettingTag() },
                navigateToSettingEditTag = { id, color, name ->
                    navigator.navigateToSettingEditTag(id, color, name)
                },
                navigateToSettingEditTime = { navigator.navigateToSettingEditTime() },
                navigateToLogin = {
                    navigator.navigateToLogin()
                },
            )
        }
    }
}
