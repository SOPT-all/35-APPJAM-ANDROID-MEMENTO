package org.memento.presentation.plusbottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.memento.domain.type.SelectorType
import org.memento.presentation.MementoChipSelector
import org.memento.presentation.util.formatDate
import org.memento.ui.DatePickerModal
import org.memento.ui.MementoBottomSheet
import org.memento.ui.MementoTimePicker
import org.memento.ui.RepeatSelectorContent
import org.memento.ui.TagSelectorContent
import org.memento.ui.theme.MementoTheme
import org.memento.ui.theme.darkModeColors
import org.memento.ui.theme.defaultMementoTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPlanScreen(

) {
    var eventText by remember { mutableStateOf("") }

    val sheetTimePickerState = rememberModalBottomSheetState()
    val sheetRepeatState = rememberModalBottomSheetState()
    val sheetTagState = rememberModalBottomSheetState()

    var showStartTimePickerBottomSheet by remember { mutableStateOf(false) }
    var showEndTimePickerBottomSheet by remember { mutableStateOf(false) }
    var showRepeatBottomSheet by remember { mutableStateOf(false) }
    var showTagBottomSheet by remember { mutableStateOf(false) }

    var selectedStartDateText by remember { mutableStateOf("Today") }
    var selectedEndDateText by remember { mutableStateOf("Today2") }
    var selectedStartTimeText by remember { mutableStateOf("9:30 PM") }
    var selectedEndTimeText by remember { mutableStateOf("10:30 PM") }
    var selectedRepeatText by remember { mutableStateOf("Repeat") }
    var selectedEndRepeatText by remember { mutableStateOf("End Repeat") }
    var selectedTagText by remember { mutableStateOf("Untitled") }
    var selectedTagColor by remember { mutableStateOf("#F0F0F3") }

    var isAllDayChecked by remember { mutableStateOf(false) }

    var isStartCalendarVisible by remember { mutableStateOf(false) }
    var isEndCalendarVisible by remember { mutableStateOf(false) }
    var isEndRepeatCalendarVisible by remember { mutableStateOf(false) }

    var isStartDateText by remember { mutableStateOf(false) }
    var isEndDateText by remember { mutableStateOf(false) }
    var isTimeStartText by remember { mutableStateOf(false) }
    var isTimeEndText by remember { mutableStateOf(false) }
    var isRepeatText by remember { mutableStateOf(false) }
    var isEndRepeatText by remember { mutableStateOf(false) }
    var isTagText by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 6.dp)
    ) {
        TextField(
            value = eventText,
            onValueChange = { eventText = it },
            modifier = Modifier
                .fillMaxWidth()
                .background(color = darkModeColors.gray10),
            textStyle = MementoTheme.typography.body_b_18.copy(
                color = darkModeColors.white
            ),
            placeholder = {
                Text(
                    text = "Add your event",
                    style = MementoTheme.typography.body_b_18.copy(
                        color = darkModeColors.gray07
                    )
                )
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = darkModeColors.gray10,
                unfocusedContainerColor = darkModeColors.gray10,
                cursorColor = darkModeColors.white,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
            singleLine = true
        )

        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .background(darkModeColors.gray08),
            thickness = 2.dp
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 31.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Starts",
                style = MementoTheme.typography.body_r_16.copy(
                    color = darkModeColors.gray05
                )
            )

            Spacer(modifier = Modifier.weight(1f))

            MementoChipSelector(
                selectorType = SelectorType.DATESELECTOR,
                isClicked = isStartDateText,
                onClickedChange = {
                    isStartCalendarVisible = true
                },
                content = selectedStartDateText,
                tagColor = null,
                modifier = Modifier.padding(end = 10.dp)
            )

            MementoChipSelector(
                selectorType = SelectorType.TIMESELECTOR,
                isClicked = isTimeStartText,
                onClickedChange = {
                    showStartTimePickerBottomSheet = true
                },
                content = selectedStartTimeText,
                tagColor = null,
            )

            if (isStartCalendarVisible) {
                DatePickerModal(
                    onDateSelected = { selectedDate ->
                        if (selectedDate != null)
                            selectedStartDateText = formatDate(selectedDate)
                    },
                    onDismiss = {
                        isStartCalendarVisible = false
                    },
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Ends",
                style = MementoTheme.typography.body_r_16.copy(
                    color = darkModeColors.gray05
                )
            )

            Spacer(modifier = Modifier.weight(1f))

            MementoChipSelector(
                selectorType = SelectorType.DATESELECTOR,
                isClicked = isEndDateText,
                onClickedChange = {
                    isEndCalendarVisible = true
                },
                content = selectedEndDateText,
                tagColor = null,
                modifier = Modifier.padding(end = 10.dp)
            )

            MementoChipSelector(
                selectorType = SelectorType.TIMESELECTOR,
                isClicked = isTimeEndText,
                onClickedChange = {
                    showEndTimePickerBottomSheet = true
                },
                content = selectedEndTimeText,
                tagColor = null,
            )

            if (isEndCalendarVisible) {
                DatePickerModal(
                    onDateSelected = { selectedDate ->
                        if (selectedDate != null)
                            selectedEndDateText = formatDate(selectedDate)
                    },
                    onDismiss = {
                        isEndCalendarVisible = false
                    },
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isAllDayChecked,
                onCheckedChange = {
                    isAllDayChecked = it
                },
                colors = CheckboxDefaults.colors(
                    uncheckedColor = darkModeColors.gray05,
                    checkedColor = darkModeColors.gray05,
                    checkmarkColor = darkModeColors.black,
                ),
            )

            Text(
                text = "All-day",
                style = defaultMementoTypography.body_r_14,
                color = darkModeColors.gray05,
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 25.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Repeat",
                style = MementoTheme.typography.body_r_16.copy(
                    color = darkModeColors.gray05
                )
            )

            Spacer(modifier = Modifier.weight(1f))

            MementoChipSelector(
                selectorType = SelectorType.BASIC,
                isClicked = isRepeatText,
                onClickedChange = {
                    showRepeatBottomSheet = true
                },
                content = selectedRepeatText,
                tagColor = null,
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "End Repeat",
                style = MementoTheme.typography.body_r_16.copy(
                    color = darkModeColors.gray05
                )
            )

            Spacer(modifier = Modifier.weight(1f))

            MementoChipSelector(
                selectorType = SelectorType.BASIC,
                isClicked = isEndRepeatText,
                onClickedChange = {
                    isEndRepeatCalendarVisible = true
                },
                content = selectedEndRepeatText,
                tagColor = null,
            )

            if (isEndRepeatCalendarVisible) {
                DatePickerModal(
                    onDateSelected = { selectedDate ->
                        if (selectedDate != null)
                            selectedEndRepeatText = formatDate(selectedDate)
                    },
                    onDismiss = {
                        isEndRepeatCalendarVisible = false
                    },
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Tag",
                style = MementoTheme.typography.body_r_16.copy(
                    color = darkModeColors.gray05
                )
            )

            Spacer(modifier = Modifier.weight(1f))

            MementoChipSelector(
                selectorType = SelectorType.TAG,
                isClicked = isTagText,
                onClickedChange = {
                    showTagBottomSheet = true
                },
                content = selectedTagText,
                tagColor = selectedTagColor,
            )
        }

        MementoBottomSheet(
            isOpenBottomSheet = showStartTimePickerBottomSheet,
            content = {
                MementoTimePicker(
                    onTimeSelected = { selectedTime ->
//                        selectedTimeText = selectedTime
                        showStartTimePickerBottomSheet = false
                    },
                )
            },
            sheetState = sheetTimePickerState,
            onDismissRequest = {
                showStartTimePickerBottomSheet = false
            },
        )

        MementoBottomSheet(
            isOpenBottomSheet = showEndTimePickerBottomSheet,
            content = {
                MementoTimePicker(
                    onTimeSelected = { selectedTime ->
//                        selectedTimeText = selectedTime
                        showEndTimePickerBottomSheet = false
                    },
                )
            },
            sheetState = sheetTimePickerState,
            onDismissRequest = {
                showEndTimePickerBottomSheet = false
            },
        )

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
            onDismissRequest = {
                showRepeatBottomSheet = false
            },
        )

        MementoBottomSheet(
            isOpenBottomSheet = showTagBottomSheet,
            content = {
                TagSelectorContent(
                    onTagSelected = { color, tag ->
                        selectedTagColor = color
                        selectedTagText = tag
                    },
                )
            },
            sheetState = sheetTagState,
            onDismissRequest = {
                showTagBottomSheet = false
            },
        )
    }
}

@Preview
@Composable
fun AddPlanScreenPreview() {
    AddPlanScreen()
}