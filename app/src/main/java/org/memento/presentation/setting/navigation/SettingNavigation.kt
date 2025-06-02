package org.memento.presentation.setting.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.memento.presentation.setting.SettingEditTagScreen
import org.memento.presentation.setting.SettingEditTimeScreen
import org.memento.presentation.setting.SettingScreen
import org.memento.presentation.setting.SettingTagScreen
import org.memento.presentation.setting.Tag

fun NavController.navigationSetting() {
    navigate(
        route = SettingRoute.SETTING,
    )
}

fun NavController.navigationSettingTag() {
    navigate(
        route = SettingRoute.SETTINGTAG,
    )
}

fun NavController.navigationSettingEditTag() {
    navigate(
        route = SettingRoute.SETTING_EDIT_TAG,
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
    navigateToSettingEditTag: () -> Unit,
    navigateToSettingEditTime: () -> Unit,
    navigateToLogin: () -> Unit,
) {
    composable(SettingRoute.SETTING) {
        SettingScreen(
            onBack = navigateBack,
            navigateToSettingTag = navigateToSettingTag,
            navigateToSettingTime = navigateToSettingEditTime,
            navigateToLogin = navigateToLogin,
        )
    }
    composable(SettingRoute.SETTINGTAG) {
        SettingTagScreen(
            onBack = navigateBack,
            onDone = navigateBack,
            tags = dummyTags,
            navigateToSettingEditTag = navigateToSettingEditTag,
        )
    }
    composable(SettingRoute.SETTING_EDIT_TAG) {
        SettingEditTagScreen(
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

// 예시로 여기에 넣어놨습니다.. 서버통신 부분에서 지울거임
val dummyTags =
    listOf(
        Tag(id = 163, name = "Untitled", colorCode = "#A9ADBB"),
        Tag(id = 164, name = "Family", colorCode = "#FF426E"),
        Tag(id = 165, name = "Hobby", colorCode = "#FF8162"),
        Tag(id = 166, name = "Self-Development", colorCode = "#149C95"),
        Tag(id = 167, name = "Work", colorCode = "#6CA9E1"),
        Tag(id = 168, name = "Personal", colorCode = "#3867FF"),
    )

object SettingRoute {
    const val SETTING = "Setting"
    const val SETTINGTAG = "SettingTag"
    const val SETTING_EDIT_TAG = "SettingEditTag"
    const val SETTING_EDIT_TIME = "SettingEditTime"
}
