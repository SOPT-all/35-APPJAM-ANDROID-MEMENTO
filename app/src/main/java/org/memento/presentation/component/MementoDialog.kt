package org.memento.presentation.component

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import org.memento.R
import org.memento.presentation.today.TodayViewModel
import org.memento.presentation.type.DialogType
import org.memento.presentation.type.PriorityTagType
import org.memento.presentation.util.changeHexToColor
import org.memento.presentation.util.noRippleClickable
import org.memento.ui.theme.MementoTheme
import org.memento.ui.theme.darkModeColors
import org.memento.ui.theme.mementoColors

@Composable
fun MementoDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onDelete: () -> Unit,
    onEdit: () -> Unit,
    dialogType: DialogType,
    planId: Int,
    viewModel: TodayViewModel = hiltViewModel()
    ) {
    if (showDialog) {
        val todoData = viewModel.getTodoDialogData(planId)
        val scheduleData = viewModel.getScheduleDialogData(planId)

        Dialog(
            onDismissRequest = onDismiss,
        ) {
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .background(
                            color = darkModeColors.gray10,
                            shape = RoundedCornerShape(2.dp),
                        ),
            ) {
                Column(
                    modifier =
                        Modifier
                            .padding(horizontal = 16.dp, vertical = 20.dp),
                ) {
                    when (dialogType) {
                        DialogType.SCHEDULE -> {
                            scheduleData?.let {
                                AddScheduleDialogComponent(
                                    title = it.title,
                                    startDate = it.startDate,
                                    endDate = it.endDate,
                                    startTime = it.startTime,
                                    endTime = it.endTime,
                                    tagColor = it.tagColor,
                                    tagText = it.tagText,
                                    platform = it.platform,
                                    platFormText = it.platformText
                                )
                            }
                        }

                        DialogType.TO_DO -> {
                            todoData?.let {
                                ToDoDialogComponent(
                                    isChecked = it.isChecked,
                                    title = it.title,
                                    tagColor = it.tagColor,
                                    tagText = it.tagText,
                                    urgentType = it.priorityType,
                                )
                            }
                        }
                    }

                    Row(
                        modifier = Modifier.padding(top = 53.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        Column(
                            modifier =
                                Modifier
                                    .weight(1f)
                                    .background(
                                        color = mementoColors.red.copy(alpha = 0.15f),
                                        shape = RoundedCornerShape(2.dp),
                                    )
                                    .padding(vertical = 11.dp)
                                    .noRippleClickable {
                                        onDelete()
                                    },
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_delete),
                                contentDescription = "삭제 버튼",
                                tint = mementoColors.red,
                            )
                            Text(
                                text = "Delete",
                                style =
                                    MementoTheme.typography.body_r_16.copy(
                                        color = mementoColors.red,
                                    ),
                            )
                        }

                        Column(
                            modifier =
                                Modifier
                                    .weight(1f)
                                    .background(
                                        color = darkModeColors.gray09,
                                        shape = RoundedCornerShape(2.dp),
                                    )
                                    .padding(vertical = 11.dp)
                                    .noRippleClickable {
                                        onEdit()
                                    },
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_edit),
                                contentDescription = "수정 버튼",
                                tint = darkModeColors.gray05,
                            )
                            Text(
                                text = "Edit",
                                style =
                                    MementoTheme.typography.body_r_16.copy(
                                        color = darkModeColors.gray05,
                                    ),
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ToDoDialogComponent(
    isChecked: Boolean,
    title: String,
    tagColor: String,
    tagText: String,
    urgentType: PriorityTagType
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        CompositionLocalProvider(LocalMinimumInteractiveComponentSize provides Dp.Unspecified) {
            Checkbox(
                checked = isChecked,
                onCheckedChange = { !isChecked },
                colors =
                    CheckboxDefaults.colors(
                        uncheckedColor = darkModeColors.gray05,
                        checkedColor = darkModeColors.gray05,
                        checkmarkColor = darkModeColors.black,
                    ),
                modifier = Modifier.padding(end = 10.dp),
            )
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = title,
                style =
                    MementoTheme.typography.body_b_16.copy(
                        color = darkModeColors.white,
                    ),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                textDecoration = if (isChecked) TextDecoration.LineThrough else null,
            )

            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 18.dp),
                horizontalArrangement = Arrangement.spacedBy(27.dp),
            ) {
                Text(
                    text = "DeadLine",
                    style =
                        MementoTheme.typography.detail_r_12.copy(
                            color = darkModeColors.gray05,
                        ),
                    modifier = Modifier.weight(0.3f),
                )
                Row(
                    modifier = Modifier.weight(1f),
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_deadline),
                        contentDescription = "데드라인 아이콘",
                        tint = darkModeColors.gray05,
                    )

                    Spacer(modifier = Modifier.width(3.dp))

                    Text(
                        text = "날짜 데이터",
                        style =
                            MementoTheme.typography.detail_r_12.copy(
                                color = darkModeColors.gray05,
                            ),
                    )
                }
            }

            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(27.dp),
            ) {
                Text(
                    text = "Tag",
                    style =
                        MementoTheme.typography.detail_r_12.copy(
                            color = darkModeColors.gray05,
                        ),
                    modifier = Modifier.weight(0.3f),
                )
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_tag),
                        contentDescription = "태그 색 표시",
                        tint = changeHexToColor(tagColor),
                        modifier = Modifier.padding(all = 2.dp),
                    )

                    Spacer(modifier = Modifier.width(2.dp))

                    Text(
                        text = tagText,
                        style =
                            MementoTheme.typography.detail_r_12.copy(
                                color = darkModeColors.gray05,
                            ),
                    )
                }
            }

            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 14.dp),
                horizontalArrangement = Arrangement.spacedBy(27.dp),
            ) {
                Text(
                    text = "Priority",
                    style =
                        MementoTheme.typography.detail_r_12.copy(
                            color = darkModeColors.gray05,
                        ),
                    modifier = Modifier.weight(0.3f),
                )

                Box(
                    modifier = Modifier.weight(1f),
                ) {
                    MementoUrgentChip(
                        selectedType = urgentType,
                    )
                }
            }
        }
    }
}

@Composable
fun AddScheduleDialogComponent(
    title: String,
    startDate: String,
    endDate: String,
    startTime: String,
    endTime: String,
    tagColor: String,
    tagText: String,
    platform: Int,
    platFormText: String
) {
    var isChecked by remember { mutableStateOf(false) }

    Row(
        modifier =
            Modifier
                .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_event),
            contentDescription = "일정 아이콘",
            tint = darkModeColors.white,
            modifier =
                Modifier.padding(end = 10.dp)
                    .padding(vertical = 2.dp),
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = title,
                style =
                    MementoTheme.typography.body_b_16.copy(
                        color = darkModeColors.white,
                    ),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                textDecoration = if (isChecked) TextDecoration.LineThrough else null,
            )

            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 18.dp),
                horizontalArrangement = Arrangement.spacedBy(27.dp),
            ) {
                Text(
                    text = "Starts",
                    style =
                        MementoTheme.typography.detail_r_12.copy(
                            color = darkModeColors.gray05,
                        ),
                    modifier = Modifier.weight(0.3f),
                )
                Row(
                    modifier = Modifier.weight(1f),
                ) {
                    Text(
                        text = startDate,
                        style =
                            MementoTheme.typography.detail_r_12.copy(
                                color = darkModeColors.gray05,
                            ),
                        modifier = Modifier.padding(end = 10.dp),
                    )

                    Text(
                        text = startTime,
                        style =
                            MementoTheme.typography.detail_r_12.copy(
                                color = darkModeColors.gray05,
                            ),
                    )
                }
            }

            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 18.dp),
                horizontalArrangement = Arrangement.spacedBy(27.dp),
            ) {
                Text(
                    text = "Ends",
                    style =
                        MementoTheme.typography.detail_r_12.copy(
                            color = darkModeColors.gray05,
                        ),
                    modifier = Modifier.weight(0.3f),
                )
                Row(
                    modifier = Modifier.weight(1f),
                ) {
                    Text(
                        text = endDate,
                        style =
                            MementoTheme.typography.detail_r_12.copy(
                                color = darkModeColors.gray05,
                            ),
                        modifier = Modifier.padding(end = 10.dp),
                    )

                    Text(
                        text = endTime,
                        style =
                            MementoTheme.typography.detail_r_12.copy(
                                color = darkModeColors.gray05,
                            ),
                        modifier = Modifier.padding(end = 10.dp),
                    )
                }
            }

            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(27.dp),
            ) {
                Text(
                    text = "Tag",
                    style =
                        MementoTheme.typography.detail_r_12.copy(
                            color = darkModeColors.gray05,
                        ),
                    modifier = Modifier.weight(0.3f),
                )
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_tag),
                        contentDescription = "태그 색 표시",
                        tint = changeHexToColor(tagColor),
                        modifier = Modifier.padding(all = 2.dp),
                    )

                    Spacer(modifier = Modifier.width(2.dp))

                    Text(
                        text = tagText,
                        style =
                            MementoTheme.typography.detail_r_12.copy(
                                color = darkModeColors.gray05,
                            ),
                    )
                }
            }

            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(27.dp),
            ) {
                Text(
                    text = "From",
                    style =
                        MementoTheme.typography.detail_r_12.copy(
                            color = darkModeColors.gray05,
                        ),
                    modifier = Modifier.weight(0.3f),
                )
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        painter = painterResource(platform),
                        contentDescription = "태그 색 표시",
                        tint = mementoColors.red,
                        modifier = Modifier.padding(all = 2.dp),
                    )

                    Spacer(modifier = Modifier.width(2.dp))

                    Text(
                        text = platFormText,
                        style =
                            MementoTheme.typography.detail_r_12.copy(
                                color = darkModeColors.gray05,
                            ),
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun MementoDialogPreview() {
    val showDialog = remember { mutableStateOf(true) }
    val closeDialog = { showDialog.value = false }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(darkModeColors.white),
    ) {
        Button(
            onClick = { showDialog.value = true },
        ) {
            Text("Show Dialog")
        }

        MementoDialog(
            showDialog = showDialog.value,
            onDismiss = closeDialog,
            onDelete = { },
            onEdit = { },
            dialogType = DialogType.SCHEDULE,
            planId = 1,
        )
    }
}
