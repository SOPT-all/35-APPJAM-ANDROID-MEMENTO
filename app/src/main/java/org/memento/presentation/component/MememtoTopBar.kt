package org.memento.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import org.memento.presentation.util.noRippleClickable
import org.memento.ui.theme.MementoTheme
import org.memento.ui.theme.darkModeColors

@Composable
fun MementoTopBar(
    date: String,
    year: String,
    onDateClick: () -> Unit,
    onIconClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .background(color = darkModeColors.black)
                .padding(start = 22.dp, end = 33.dp)
                .padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = date,
            style = MementoTheme.typography.body_b_20,
            color = darkModeColors.white,
            modifier =
                Modifier
                    .noRippleClickable { onDateClick() },
        )

        Row(verticalAlignment = Alignment.Top) {
            Text(
                text = year,
                style = MementoTheme.typography.detail_b_11,
                color = darkModeColors.gray07,
            )
            Spacer(modifier = Modifier.padding(start = 15.dp))
            Icon(
                painter = painterResource(id = R.drawable.ic_settings_26),
                contentDescription = "Settings",
                tint = Color.Unspecified,
                modifier =
                    Modifier
                        .noRippleClickable { onIconClick() },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TopBarComponentPreview() {
    MementoTopBar(
        date = "Jan 3",
        year = "2025",
        onDateClick = {},
        onIconClick = {},
    )
}
