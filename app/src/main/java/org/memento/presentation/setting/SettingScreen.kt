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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.memento.R
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
    userMail: String = "memento@gmail.com",
    modifier: Modifier = Modifier,
) {
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
            SettingMailBar(usermail = userMail)
            Spacer(modifier = modifier.padding(top = 12.dp))

            SettingOptions(onClick = {}, optionName = stringResource(R.string.notifications))
            SettingOptions(onClick = { navigateToSettingTag() }, optionName = stringResource(R.string.tag))
            SettingOptions(onClick = {}, optionName = stringResource(R.string.time))

            Divider(modifier = Modifier.drawHorizontalLine())

            SettingOptions(onClick = {}, optionName = stringResource(R.string.integrations))

            Divider(modifier = Modifier.drawHorizontalLine())

            SettingOptions(onClick = {}, optionName = stringResource(R.string.feedback))
            SettingOptions(onClick = {}, optionName = stringResource(R.string.terms))

            Divider(modifier = Modifier.drawHorizontalLine())

            SettingOptions(onClick = {}, optionName = stringResource(R.string.logout), textColor = mementoColors.red)
            SettingOptions(onClick = {}, optionName = stringResource(R.string.delete_my_account), textColor = mementoColors.red)
        }
    }
}

@Preview
@Composable
fun SettingScreenPreview() {
//    SettingScreen(onBack = {}, userMail = "memento@gmail.com")
}
