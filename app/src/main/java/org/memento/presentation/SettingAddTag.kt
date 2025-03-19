package org.memento.presentation

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.memento.R
import org.memento.presentation.util.noRippleClickable
import org.memento.ui.theme.MementoTheme
import org.memento.ui.theme.darkModeColors

/**
 *  @param onClick  Add Tag 눌렀을 때 동작
 *  @param modifier modifier 수정 사항
 */

@Composable
fun SettingAddTag(
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
        verticalAlignment = Alignment.CenterVertically
    ) {

        Row(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .weight(1f),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                modifier = Modifier.padding(start = 10.dp, end = 4.dp),
                painter = painterResource(id = R.drawable.ic_add_gray_26),
                contentDescription = null,
                tint = darkModeColors.gray05,
            )
            Text(
                text = "Add",
                style = MementoTheme.typography.body_r_14.copy(
                    color = darkModeColors.gray05,
                ),
            )

        }
    }
}


@Preview
@Composable
private fun SettingTagPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = darkModeColors.black)
            .padding(horizontal = 20.dp)
            .padding(top = 20.dp)
    ) {


        SettingAddTag(
            onClick = { },

            )
    }
}
