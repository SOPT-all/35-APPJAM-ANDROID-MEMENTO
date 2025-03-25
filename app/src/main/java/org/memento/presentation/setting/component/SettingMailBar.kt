package org.memento.presentation.setting.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.memento.R
import org.memento.ui.theme.MementoTheme
import org.memento.ui.theme.darkModeColors


/**
 *  @param usermail  사용자 mail 입력
 *  @param modifier modifier 수정 사항
 */

@Composable
fun SettingMailBar(
    usermail: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier =
        Modifier
            .then(modifier)
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .background(
                color = darkModeColors.gray10,
                shape = RoundedCornerShape(size = 4.dp),
            )
            .padding(vertical = 20.dp)

    ) {

        Icon(
            modifier = Modifier.padding(start = 18.dp, end = 16.dp),
            painter = painterResource(id = R.drawable.ic_logo_16),
            contentDescription = null,
            tint = darkModeColors.gray05,
        )
        Text(
            text = usermail,
            style =
            MementoTheme.typography.body_b_14.copy(
                color = darkModeColors.gray04,
            ),
        )
    }
}


@Preview
@Composable
private fun SettingTagPreview() {
    Column(
        modifier =
        Modifier
            .fillMaxSize()
            .background(color = darkModeColors.black)
            .padding(horizontal = 20.dp)
            .padding(top = 20.dp),
    ) {
        SettingMailBar(
            usermail = "memento@gmail.com"
        )
    }
}
