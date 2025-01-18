package org.memento.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.memento.R
import org.memento.presentation.type.PriorityTagType
import org.memento.ui.theme.MEMENTOTheme
import org.memento.ui.theme.MementoTheme
import org.memento.ui.theme.darkModeColors

@Composable
fun MementoScheduleItem(
    tagColor: Color,
    scheduleTitleText: String,
    timeRange: String,
    isConnected: Boolean = false,
) {
    val fraction = 3f / 300f
    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .background(color = darkModeColors.navy)
                .clip(RoundedCornerShape(2.dp)),
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
                        .aspectRatio(3f / 68f)
                        .background(color = tagColor),
            )
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                        .padding(top = 12.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Spacer(modifier = Modifier.width(12.dp))

                    Icon(
                        painter = painterResource(id = R.drawable.ic_event),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier.size(24.dp),
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = scheduleTitleText,
                        style = MementoTheme.typography.body_b_16,
                        color = darkModeColors.white,
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }

                Row(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Spacer(modifier = Modifier.padding(start = 46.dp))
                    if (isConnected) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_notion),
                            contentDescription = null,
                            tint = Color.Unspecified,
                            modifier = Modifier.padding(end = 10.dp),
                        )
                    }

                    Text(
                        text = timeRange,
                        style = MementoTheme.typography.detail_r_12,
                        color = darkModeColors.gray05,
                    )
                }
            }
        }
    }
}

@Composable
fun MementoScheduleItemWithLine(
    tagColor: Color,
    scheduleTitleText: String,
    timeRange: String,
    isConnected: Boolean = false,
    isFirstUndone: Boolean = false,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_progress),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier.padding(end = 10.dp),
        )

        MementoScheduleItem(
            tagColor = tagColor,
            scheduleTitleText = scheduleTitleText,
            timeRange = timeRange,
            isConnected = isConnected,
        )
    }
}

@Preview()
@Composable
private fun preview2() {
    MEMENTOTheme {
        Column {
            MementoScheduleItem(tagColor = Color.Red, scheduleTitleText = "ddddd", timeRange = "12:00", isConnected = true)
            MementoTodoItem(tagColor = Color.Red, todoTitleText = "ddddd", priorityTagType = PriorityTagType.Low, isConnected = true, isDone = true)
        }
    }
}
