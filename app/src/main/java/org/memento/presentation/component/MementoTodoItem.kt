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
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import org.memento.R
import org.memento.presentation.type.PriorityTagType
import org.memento.ui.theme.MEMENTOTheme
import org.memento.ui.theme.MementoTheme
import org.memento.ui.theme.darkModeColors
import org.memento.ui.theme.mementoColors

@Composable
fun MementoTodoItem(
    tagColor: Color,
    isDone: Boolean = false,
    onCheckedChange: (Boolean) -> Unit = {},
    todoTitleText: String,
    priorityTagType: PriorityTagType,
    isConnected: Boolean = false,
    isFirstUndone: Boolean = false
) {
    val fraction = 3f / 300f
    val backgroundColord = if (isFirstUndone) Color.White else darkModeColors.navy
    val backgroundModifier = if (isFirstUndone) {
        Modifier.background(
            brush = Brush.linearGradient(
                colors = listOf(
                    mementoColors.todoNowStart,
                    mementoColors.todoNowEnd
                )
            )
        )
    } else {
        Modifier.background(color = darkModeColors.navy)
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .then(backgroundModifier)
                .clip(RoundedCornerShape(2.dp))
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(fraction = fraction)
                        .aspectRatio(3f / 68f)
                        .background(color = tagColor)
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                        .padding(top = 12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(modifier = Modifier.width(14.dp))

                        Checkbox(
                            modifier = Modifier.size(18.dp),
                            checked = isDone,
                            onCheckedChange = onCheckedChange,
                            colors =
                            CheckboxDefaults.colors(
                                uncheckedColor = darkModeColors.gray05,
                                checkedColor = darkModeColors.gray05,
                                checkmarkColor = darkModeColors.black,
                            ),
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = todoTitleText,
                            style = MementoTheme.typography.body_b_16,
                            color = darkModeColors.white,
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        MementoUrgentChip(priorityTagType)
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(modifier = Modifier.padding(start = 44.dp))
                        if (isConnected) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_notion),
                                contentDescription = null,
                                tint = Color.Unspecified,
                                modifier = Modifier.padding(end = 10.dp)
                            )
                        }
                        Icon(
                            painter = painterResource(id = R.drawable.ic_deadline),
                            contentDescription = null,
                            tint = Color.Unspecified,
                        )
                        Text(
                            text = "dfdf",
                            style = MementoTheme.typography.detail_r_12,
                            color = darkModeColors.gray05
                        )
                    }
                }
            }
        }

        if (isDone) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .clip(RoundedCornerShape(2.dp))
                    .zIndex(0f)
            )
        }

    }
}

@Composable
fun MementoTodoItemWithLine(
    tagColor: Color,
    isDone: Boolean = false,
    onCheckedChange: (Boolean) -> Unit = {},
    todoTitleText: String,
    priorityTagType: PriorityTagType,
    isConnected: Boolean = false,
    isFirstUndone: Boolean = false,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_progress),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier.padding(end = 10.dp)
        )

        MementoTodoItem(
            tagColor = tagColor,
            isDone = isDone,
            onCheckedChange = onCheckedChange,
            todoTitleText = todoTitleText,
            priorityTagType = priorityTagType,
            isConnected = isConnected,
            isFirstUndone = isFirstUndone
        )
    }
}


@Preview()
@Composable
private fun preview2() {
    MEMENTOTheme {
        MementoTodoItemWithLine(tagColor = Color.Red, todoTitleText = "ddddd", priorityTagType = PriorityTagType.Low, isConnected = true, isFirstUndone = true)
    }
}

