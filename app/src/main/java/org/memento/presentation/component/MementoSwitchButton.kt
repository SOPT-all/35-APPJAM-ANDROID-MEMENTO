package org.memento.presentation.component

import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.memento.ui.theme.darkModeColors

/**
 * @param modifier modifier 수정 사항
 * @param isSwitchOn 초기 Switch의 체크 상태
 * @param onSwitchChange Switch의 상태 변경에 따른 로직
 */

@Composable
fun MementoSwitchButton(
    modifier: Modifier = Modifier,
    isSwitchOn: Boolean,
    onSwitchChange: (Boolean) -> Unit
) {
    Switch(
        checked = isSwitchOn,
        onCheckedChange = onSwitchChange,
        modifier = modifier,
        colors = SwitchDefaults.colors(
            checkedThumbColor = darkModeColors.white,
            checkedTrackColor = darkModeColors.gray08,
            uncheckedThumbColor = darkModeColors.gray06,
            uncheckedTrackColor = darkModeColors.gray07,
        ),
    )
}

@Preview
@Composable
fun MementoSwitchButtonPreview() {
    var isSwitchOn by remember { mutableStateOf(false) }

    MementoSwitchButton(
        isSwitchOn = isSwitchOn,
        onSwitchChange = { isToggled ->
            isSwitchOn = isToggled
            if (isSwitchOn) {
               // TODO(): 자연어 처리, 공지 알림 로직 구현
            }
        }
    )
}