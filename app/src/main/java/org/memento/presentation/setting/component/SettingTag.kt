package org.memento.presentation.setting.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.memento.R
import org.memento.presentation.util.changeHexToColor
import org.memento.presentation.util.noRippleClickable
import org.memento.ui.theme.MementoTheme
import org.memento.ui.theme.darkModeColors

/**
 *  @param tagColor 태그 색상 (Hexcode)
 *  @param tagName 테그 이름
 *  @param onClick  Tag 수정/삭제 뷰로 이동
 *  @param modifier modifier 수정 사항
 */

@Composable
fun SettingTag(
    tagColor: String,
    tagName: String,
    onClick: () -> Unit,
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
                .noRippleClickable {
                    onClick()
                },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxWidth(0.025f)
                    .fillMaxHeight()
                    .background(
                        color = changeHexToColor(tagColor),
                        shape = RoundedCornerShape(topStart = 4.dp, bottomStart = 4.dp),
                    ),
        )

        Spacer(modifier = Modifier.width(12.dp))

        Row(
            modifier =
                Modifier
                    .padding(vertical = 8.dp)
                    .weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = tagName,
                style =
                    MementoTheme.typography.body_r_14.copy(
                        color = darkModeColors.gray05,
                    ),
            )

            Icon(
                painter = painterResource(id = R.drawable.ic_right_26),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.padding(end = 4.dp),
            )
        }
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
        SettingTag(
            tagName = "Family",
            onClick = { },
            tagColor = "#FF426E",
        )
        Spacer(modifier = Modifier.height(16.dp))

        SettingTag(
            tagName = "Hobby",
            onClick = { },
            tagColor = "#FF8162",
        )
    }
}
