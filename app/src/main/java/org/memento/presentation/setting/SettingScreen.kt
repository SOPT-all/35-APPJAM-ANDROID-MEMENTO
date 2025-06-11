package org.memento.presentation.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.memento.R
import org.memento.presentation.setting.component.SettingAlertDialog
import org.memento.presentation.setting.component.SettingMailBar
import org.memento.presentation.setting.component.SettingOptions
import org.memento.presentation.setting.component.SettingTopBar
import org.memento.presentation.type.SettingTopBarType
import org.memento.presentation.util.drawHorizontalLine
import org.memento.ui.theme.darkModeColors
import org.memento.ui.theme.mementoColors

/**
 *  @param onBack  뒤로가기
 *  @param navigateToSettingTag Setting Tag Screen으로 이동
 *  @param userMail 사용자 mail값
 *  @param modifier modifier 수정 사항
 */

@Composable
fun SettingScreen(
    onBack: () -> Unit,
    navigateToSettingTag: () -> Unit,
    navigateToSettingTime: () -> Unit,
    navigateToLogin: () -> Unit,
    viewmodel: SettingViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
) {
    val userEmail by viewmodel.userEmail.collectAsState()

    var showLogoutDialog by remember { mutableStateOf(false) }
    var showDeleteAccountDialog by remember { mutableStateOf(false) }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(color = darkModeColors.black)
                .padding(top = 16.dp),
    ) {
        SettingTopBar(type = SettingTopBarType.SETTING, onBackClick = { onBack() }, onDoneClick = {})

        Column(
            modifier =
                modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 26.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            SettingMailBar(usermail = userEmail ?: "")
            Spacer(modifier = modifier.padding(top = 12.dp))

            SettingOptions(onClick = {}, optionName = stringResource(R.string.notifications))
            SettingOptions(onClick = { navigateToSettingTag() }, optionName = stringResource(R.string.tag))
            SettingOptions(onClick = { navigateToSettingTime() }, optionName = stringResource(R.string.time))

            Divider(modifier = Modifier.drawHorizontalLine())

            SettingOptions(onClick = {}, optionName = stringResource(R.string.integrations))

            Divider(modifier = Modifier.drawHorizontalLine())

            SettingOptions(onClick = {}, optionName = stringResource(R.string.feedback))
            SettingOptions(onClick = {}, optionName = stringResource(R.string.terms))

            Divider(modifier = Modifier.drawHorizontalLine())

            SettingOptions(onClick = {
                showLogoutDialog = true
            }, optionName = stringResource(R.string.logout), textColor = mementoColors.red)
            SettingOptions(onClick = {
                showDeleteAccountDialog = true
            }, optionName = stringResource(R.string.delete_my_account), textColor = mementoColors.red)
        }
    }

    if (showLogoutDialog) {
        SettingAlertDialog(
            content = R.string.alert_setting_log_out,
            leftButtonText = R.string.setting_alert_delete_leftButtonText,
            rightButtonText = R.string.logout,
            onLeftButtonClick = { showLogoutDialog = false },
            onRightButtonClick = {
                showLogoutDialog = false
                viewmodel.logout()
                navigateToLogin()
            },
        )
    }

    if (showDeleteAccountDialog) {
        SettingAlertDialog(
            content = R.string.alert_setting_delete_account_title,
            subContent = R.string.alert_setting_delete_account_subtitle,
            leftButtonText = R.string.setting_alert_delete_leftButtonText,
            rightButtonText = R.string.alert_delete_account_button,
            onLeftButtonClick = { showDeleteAccountDialog = false },
            onRightButtonClick = {
                showDeleteAccountDialog = false
                viewmodel.deleteMember(
                    onLogout = navigateToLogin,
                )
            },
        )
    }
}

@Preview
@Composable
fun SettingScreenPreview() {
//    SettingScreen(onBack = {}, userMail = "memento@gmail.com")
}
