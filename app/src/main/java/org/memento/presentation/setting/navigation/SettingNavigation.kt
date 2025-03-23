package org.memento.presentation.setting.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.memento.presentation.setting.SettingScreen

fun NavController.navigationSetting() {
    navigate(
        route = SettingRoute.SETTING,
    )
}

fun NavGraphBuilder.settingNavGraph(
    navigateBack: () -> Unit,
) {
    composable(SettingRoute.SETTING) {
        SettingScreen(
            onBack = navigateBack,
        )
    }
}

object SettingRoute {
    const val SETTING = "Setting"
}
