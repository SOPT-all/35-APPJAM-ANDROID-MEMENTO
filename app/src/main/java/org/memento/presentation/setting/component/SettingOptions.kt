package org.memento.presentation.setting.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.memento.presentation.util.noRippleClickable
import org.memento.ui.theme.MementoTheme
import org.memento.ui.theme.darkModeColors

/**
 *  @param onClick  click 했을 때 이동
 *  @param optionName  setting option 이름 입력
 *  @param textColor text color 변경 파라미터
 *  @param modifier modifier 수정 사항
 */

@Composable
fun SettingOptions(
    onClick: () -> Unit,
    optionName: String,
    textColor: Color = darkModeColors.gray05,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .background(color = darkModeColors.black)
                .padding(top = 8.dp, bottom = 4.dp, start = 10.dp)
                .noRippleClickable { onClick() },
    ) {
        Text(
            text = optionName,
            style =
                MementoTheme.typography.body_r_14.copy(
                    color = textColor,
                ),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Top() {
    Column {
        SettingOptions(onClick = {}, optionName = "dd")
    }
}
