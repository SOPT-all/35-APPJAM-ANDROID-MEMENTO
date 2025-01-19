package org.memento.presentation.today.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.memento.ui.theme.MEMENTOTheme
import org.memento.ui.theme.MementoTheme
import org.memento.ui.theme.darkModeColors

@Composable
fun AllDayScheduleTag(
    allDayText: String,
    tagColor: Color,
) {
    val fraction = 5f / 360f
    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp)
                .background(color = darkModeColors.navy),
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth(),
        ) {
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth(fraction = fraction)
                        .aspectRatio(5f / 30f)
                        .background(color = tagColor),
            )

            Spacer(modifier = Modifier.width(22.dp))

            Text(
                text = allDayText,
                style = MementoTheme.typography.body_r_14,
                color = darkModeColors.gray05,
                modifier = Modifier.align(Alignment.CenterVertically),
            )
        }
    }
}

@Preview()
@Composable
private fun preview() {
    MEMENTOTheme {
        AllDayScheduleTag(allDayText = "하이하이", tagColor = Color.Red)
    }
}
