package org.memento.presentation.setting.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import org.memento.presentation.setting.SettingEditTagScreen
import org.memento.presentation.setting.SettingEditTimeScreen
import org.memento.presentation.setting.SettingScreen
import org.memento.presentation.setting.SettingTagScreen

fun NavController.navigationSetting() {
    navigate(
        route = SettingRoute.SETTING,
    )
}

fun NavController.navigationSettingTag() {
    navigate(
        route = SettingRoute.SETTINGTAG,
        builder = {
            popUpTo(SettingRoute.SETTINGTAG) {
                inclusive = true
            }
            launchSingleTop = true
        },
    )
}

fun NavController.navigationSettingEditTime() {
    navigate(
        route = SettingRoute.SETTING_EDIT_TIME,
    )
}

fun NavGraphBuilder.settingNavGraph(
    navigateBack: () -> Unit,
    navigateToSettingTag: () -> Unit,
    navigateToSettingEditTag: (Int, String, String) -> Unit,
    navigateToSettingEditTime: () -> Unit,
) {
    composable(SettingRoute.SETTING) {
        SettingScreen(
            onBack = navigateBack,
            navigateToSettingTag = navigateToSettingTag,
            navigateToSettingTime = navigateToSettingEditTime,
        )
    }
    composable(SettingRoute.SETTINGTAG) {
        SettingTagScreen(
            onBack = navigateBack,
            onDone = navigateBack,
            navigateToSettingEditTag = navigateToSettingEditTag,
        )
    }
    composable(
        route = SettingRoute.SETTING_EDIT_TAG_ARGS,
        arguments =
            listOf(
                navArgument("tagId") { type = NavType.IntType },
                navArgument("tagColor") { type = NavType.StringType },
                navArgument("tagName") { type = NavType.StringType },
            ),
    ) { backStackEntry ->
        val tagId = backStackEntry.arguments?.getInt("tagId") ?: 0
        val tagColor = backStackEntry.arguments?.getString("tagColor") ?: ""
        val tagName = backStackEntry.arguments?.getString("tagName") ?: ""

        SettingEditTagScreen(
            tagId = tagId,
            tagColor = tagColor,
            tagName = tagName,
            onBack = navigateBack,
            onDone = navigateToSettingTag,
        )
    }

    composable(SettingRoute.SETTING_EDIT_TIME) {
        SettingEditTimeScreen(
            onBack = navigateBack,
        )
    }
}

object SettingRoute {
    const val SETTING = "Setting"
    const val SETTINGTAG = "SettingTag"
    const val SETTING_EDIT_TIME = "SettingEditTime"

    const val SETTING_EDIT_TAG_ARGS = "SettingEditTag/{tagId}/{tagColor}/{tagName}"
}
