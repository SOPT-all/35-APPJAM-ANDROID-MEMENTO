package org.memento.presentation.setting.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.memento.R
import org.memento.presentation.util.noRippleClickable
import org.memento.ui.theme.darkModeColors
import org.memento.ui.theme.defaultMementoTypography

/**
 * @param icon 플랫폼 이미지
 * @param content 플랫폼 텍스트
 * @param onClick 클릭 시, 다이얼로그 출력 혹은 플랫폼 연동
 * @param modifier modifier 수정 시 사용
 */

@Composable
fun SettingSocialChip(
    @DrawableRes icon: Int,
    content: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
        Modifier
            .then(modifier)
            .fillMaxWidth()
            .background(
                color = darkModeColors.gray10,
                shape = RoundedCornerShape(size = 4.dp)
            )
            .padding(vertical = 10.dp, horizontal = 8.dp)
            .noRippleClickable {
                onClick()
            },
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = stringResource(id = R.string.onboarding_google_login),
            modifier = Modifier.align(alignment = Alignment.CenterStart),
        )
        Text(
            text = content,
            style = defaultMementoTypography.body_r_14,
            color = darkModeColors.gray03,
            modifier = Modifier.align(alignment = Alignment.Center)
        )
    }
}

@Preview
@Composable
fun SettingSocialChipPreview() {
    SettingSocialChip(
        icon = R.drawable.img_google,
        content = "Google Calendar",
        onClick = { },
    )
}
