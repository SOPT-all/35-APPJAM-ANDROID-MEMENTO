package org.memento.presentation.setting.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.memento.R
import org.memento.presentation.type.SettingTopBarType
import org.memento.presentation.util.noRippleClickable
import org.memento.ui.theme.MementoTheme
import org.memento.ui.theme.darkModeColors

/**
 *  @param type Setting인지 TopBar인지 type구분
 *  @param onBackClick 뒤로가기 눌렀을 떄
 *  @param onDoneClick  Done 눌렀을 때
 *  @param isDoneVisible Done 텍스트를 보일지 말지 선택
 *  @param modifier modifier 수정 사항
 */

@Composable
fun SettingTopBar(
    type: SettingTopBarType,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    onDoneClick: () -> Unit = {},
    isDoneVisible: Boolean = false,
) {
    Column {
        Row(
            modifier
                .fillMaxWidth()
                .background(color = darkModeColors.black)
                .padding(end = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_back),
                contentDescription = null,
                modifier
                    .noRippleClickable { onBackClick() },
                tint = Color.Unspecified,
            )

            Text(
                text = type.text,
                style =
                    MementoTheme.typography.body_b_14.copy(
                        color = darkModeColors.gray04,
                    ),
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f),
            )

            Text(
                text = stringResource(R.string.done),
                style =
                    MementoTheme.typography.body_r_14.copy(
                        color = darkModeColors.gray04,
                    ),
                modifier =
                    modifier
                        .noRippleClickable { onDoneClick() }
                        .alpha(if (isDoneVisible) 1f else 0f),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSettingTopBar() {
    Column {
        SettingTopBar(
            type = SettingTopBarType.SETTING,
            onBackClick = {},
            onDoneClick = {},
        )

        Spacer(modifier = Modifier.height(16.dp))

        SettingTopBar(
            type = SettingTopBarType.TAG,
            onBackClick = {},
            onDoneClick = {},
            isDoneVisible = true,
        )
    }
}
