package org.memento.presentation.plusbottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import org.memento.ui.DatePickerModalHandler
import org.memento.ui.MementoBottomSheet
import org.memento.ui.MementoTimePicker
import org.memento.ui.RepeatSelectorContent
import org.memento.ui.TagSelectorContent
import org.memento.ui.theme.MementoTheme
import org.memento.ui.theme.darkModeColors
import org.memento.ui.theme.defaultMementoTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPlanScreen() {
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

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            TextField(
                value = eventText,
                onValueChange = { eventText = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = darkModeColors.gray10),
                textStyle = MementoTheme.typography.body_b_18.copy(
                    color = darkModeColors.white,
                ),
                placeholder = {
                    Text(
                        text = "Add your event",
                        style = MementoTheme.typography.body_b_18.copy(
                            color = darkModeColors.gray07,
                        ),
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = darkModeColors.gray10,
                    unfocusedContainerColor = darkModeColors.gray10,
                    cursorColor = darkModeColors.white,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                ),
                singleLine = true,
            )
        }

        item {
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(darkModeColors.gray08),
                thickness = 2.dp,
            )
        }

        item {
            AddPlanSelectComponent(
                title = "Starts",
                dateText = selectedStartDateText,
                onDateClick = { isStartCalendarVisible = true },
                timeText = selectedStartTimeText,
                onTimeClick = { showStartTimePickerBottomSheet = true }
            )
        }

        item {
            // 종료 날짜와 시간 선택
            AddPlanSelectComponent(
                title = "Ends",
                dateText = selectedEndDateText,
                onDateClick = { isEndCalendarVisible = true },
                timeText = selectedEndTimeText,
                onTimeClick = { showEndTimePickerBottomSheet = true }
            )
        }

        item {
            // All-day 체크박스
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = isAllDayChecked,
                    onCheckedChange = { isAllDayChecked = it },
                    colors = CheckboxDefaults.colors(
                        uncheckedColor = darkModeColors.gray05,
                        checkedColor = darkModeColors.gray05,
                        checkmarkColor = darkModeColors.black,
                    ),
                )
                Text(
                    text = "All-day",
                    style = defaultMementoTypography.body_r_14.copy(
                        darkModeColors.gray05
                    )
                )
            }
        }

        item {
            // 반복 설정
            AddPlanSelectComponent(
                title = "Repeat",
                dateText = selectedRepeatText,
                onDateClick = { showRepeatBottomSheet = true },
                timeText = null,
                onTimeClick = null
            )
        }

        item {
            // 반복 설정
            AddPlanSelectComponent(
                title = "End Repeat",
                dateText = selectedEndRepeatText,
                onDateClick = { isEndRepeatCalendarVisible = true },
                timeText = null,
                onTimeClick = null
            )
        }

        item {
            // 태그 설정
            AddPlanSelectComponent(
                title = "Tag",
                dateText = selectedTagText,
                onDateClick = { showTagBottomSheet = true },
                timeText = null,
                onTimeClick = null,
                tagColor = selectedTagColor
            )
        }
    }

    MementoBottomSheet(
        isOpenBottomSheet = showStartTimePickerBottomSheet,
        content = {
            MementoTimePicker(
                selectedTime = selectedStartTimeText,
                onTimeSelected = { selectedStartTimeText = it }
            )
        },
        sheetState = sheetTimePickerState,
        onConfirm = {
            showStartTimePickerBottomSheet = false
        },
    )

    MementoBottomSheet(
        isOpenBottomSheet = showEndTimePickerBottomSheet,
        content = {
            MementoTimePicker(
                selectedTime = selectedEndTimeText,
                onTimeSelected = { selectedEndTimeText = it },
            )
        },
        sheetState = sheetTimePickerState,
        onConfirm = {
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
        onConfirm = {
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
        onConfirm = {
            showTagBottomSheet = false
        },
    )

    DatePickerModalHandler(
        isCalendarVisible = isStartCalendarVisible,
        onDateSelected = { selectedStartDateText = formatDate(it ?: 0) },
        onDismiss = { isStartCalendarVisible = false }
    )

    DatePickerModalHandler(
        isCalendarVisible = isEndCalendarVisible,
        onDateSelected = { selectedEndDateText = formatDate(it ?: 0) },
        onDismiss = { isEndCalendarVisible = false }
    )

    DatePickerModalHandler(
        isCalendarVisible = isEndRepeatCalendarVisible,
        onDateSelected = { selectedEndRepeatText = formatDate(it ?: 0) },
        onDismiss = { isEndRepeatCalendarVisible = false }
    )
}

@Composable
fun AddPlanSelectComponent(
    title: String,
    dateText: String,
    onDateClick: () -> Unit,
    timeText: String?,
    onTimeClick: (() -> Unit)?,
    tagColor: String? = null,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style =
            MementoTheme.typography.body_r_16.copy(
                color = darkModeColors.gray05,
            ),
        )

        Spacer(modifier = Modifier.weight(1f))

        MementoChipSelector(
            selectorType = if (title == "Repeat" || title == "End Repeat") SelectorType.BASIC else if (title == "Tag") SelectorType.TAG else SelectorType.DATESELECTOR,
            isClicked = false,
            onClickedChange = { onDateClick() },
            content = dateText,
            tagColor = tagColor,
        )

        timeText?.let {
            MementoChipSelector(
                selectorType = SelectorType.TIMESELECTOR,
                isClicked = false,
                onClickedChange = { onTimeClick?.invoke() },
                content = timeText,
                tagColor = null,
                modifier = Modifier.padding(start = 10.dp)
            )
        }
    }
}


@Preview
@Composable
fun AddPlanScreenPreview() {
    AddPlanScreen()
}
