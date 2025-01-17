package org.memento.presentation.plusbottomsheet

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import org.memento.R
import org.memento.domain.type.SelectorType
import org.memento.presentation.MementoChipSelector
import org.memento.presentation.util.formatDate
import org.memento.presentation.util.noRippleClickable
import org.memento.ui.DeadLineSelectorContent
import org.memento.ui.MementoBottomSheet
import org.memento.ui.MementoTimePicker
import org.memento.ui.RepeatSelectorContent
import org.memento.ui.theme.MementoTheme
import org.memento.ui.theme.darkModeColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddToDoDeadLineScreen(
    onClose: () -> Unit,
    onDone: (String) -> Unit,
) {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(color = darkModeColors.gray10),
    ) {
        val sheetRepeatState = rememberModalBottomSheetState()
        var showRepeatBottomSheet by remember { mutableStateOf(false) }

        val sheetDeadLineState = rememberModalBottomSheetState()
        var showDeadLineBottomSheet by remember { mutableStateOf(false) }

        val sheetTimePickerState = rememberModalBottomSheetState()
        var showTimePickerBottomSheet by remember { mutableStateOf(false) }

        var selectedRepeatText by remember { mutableStateOf("Select") }
        var selectedDateText by remember { mutableStateOf("Today") }
        var selectedTimeText by remember { mutableStateOf("Select Date") }

        var isRepeatClicked by remember { mutableStateOf(false) }
        var isDeadLineClicked by remember { mutableStateOf(false) }
        var isTimePickerClicked by remember { mutableStateOf(false) }

        var repeatChecked by remember { mutableStateOf(false) }

        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Image(
                painter = painterResource(R.drawable.ic_back),
                contentDescription = "뒤로가기 버튼",
            )
            Text(
                text = "Done",
                modifier =
                    Modifier
                        .padding(horizontal = 18.dp, vertical = 12.dp)
                        .noRippleClickable {
                            onDone(selectedDateText)
                        },
                style =
                    MementoTheme.typography.body_r_16.copy(
                        color = darkModeColors.gray07,
                    ),
            )
        }

        Column(
            modifier =
                Modifier
                    .weight(1f)
                    .padding(horizontal = 12.dp, vertical = 26.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Deadline",
                    style =
                        MementoTheme.typography.body_r_16.copy(
                            color = darkModeColors.gray05,
                        ),
                )

                Spacer(modifier = Modifier.weight(1f))

                MementoChipSelector(
                    selectorType = SelectorType.BASIC,
                    isClicked = isDeadLineClicked,
                    onClickedChange = {
                        showDeadLineBottomSheet = true
                    },
                    content = selectedDateText,
                    tagColor = null,
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Repeat",
                    style =
                        MementoTheme.typography.body_r_16.copy(
                            color = darkModeColors.gray05,
                        ),
                )

                Spacer(modifier = Modifier.weight(1f))

                Switch(
                    checked = repeatChecked,
                    onCheckedChange = { repeatChecked = it },
                    colors =
                        SwitchDefaults.colors(
                            checkedThumbColor = darkModeColors.white,
                            checkedTrackColor = darkModeColors.gray08,
                            uncheckedThumbColor = darkModeColors.gray06,
                            uncheckedTrackColor = darkModeColors.gray07,
                        ),
                )
            }

            if (repeatChecked) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    MementoChipSelector(
                        selectorType = SelectorType.BASIC,
                        isClicked = isRepeatClicked,
                        onClickedChange = {
                            showRepeatBottomSheet = true
                        },
                        content = selectedRepeatText,
                        tagColor = null,
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "End Repeat",
                        style =
                            MementoTheme.typography.body_r_16.copy(
                                color = darkModeColors.gray05,
                            ),
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    MementoChipSelector(
                        selectorType = SelectorType.BASIC,
                        isClicked = isTimePickerClicked,
                        onClickedChange = {
                            showTimePickerBottomSheet = true
                        },
                        content = selectedTimeText,
                        tagColor = null,
                    )
                }
            }

            MementoBottomSheet(
                isOpenBottomSheet = showRepeatBottomSheet,
                content = {
                    RepeatSelectorContent(
                        onRepeatSelected = { selectedRepeat ->
                            selectedRepeatText = selectedRepeat
                        },
                    )
                },
                sheetState = sheetRepeatState,
                onConfirm = {
                    showRepeatBottomSheet = false
                },
            )

            MementoBottomSheet(
                isOpenBottomSheet = showDeadLineBottomSheet,
                content = {
                    DeadLineSelectorContent(
                        onDateSelected = { selectedDate ->
                            selectedDate?.let {
                                val formattedDate = formatDate(it)
                                selectedDateText = formattedDate
                            }
                            showDeadLineBottomSheet = false
                        },
                    )
                },
                sheetState = sheetDeadLineState,
                onConfirm = {
                    showDeadLineBottomSheet = false
                },
            )

            MementoBottomSheet(
                isOpenBottomSheet = showTimePickerBottomSheet,
                content = {
                    MementoTimePicker(
                        selectedTime = selectedTimeText,
                        onTimeSelected = { selectedTimeText = it },
                    )
                },
                sheetState = sheetTimePickerState,
                onConfirm = {
                    showTimePickerBottomSheet = false
                },
            )
        }
    }
}
