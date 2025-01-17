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
import androidx.compose.runtime.LaunchedEffect
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
import java.util.Locale

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

    LaunchedEffect(Unit) {
        val currentTime = System.currentTimeMillis()
        val startDate = formatDate(System.currentTimeMillis())

        val calendar = java.util.Calendar.getInstance().apply { timeInMillis = currentTime }

        val hour = calendar.get(java.util.Calendar.HOUR_OF_DAY)
        val minute = calendar.get(java.util.Calendar.MINUTE)

        val roundedMinute = if (minute in 16..44) 30 else 0
        val startTime = String.format(Locale.ENGLISH, "%02d:%02d %s", if (hour % 12 == 0) 12 else hour % 12, roundedMinute, if (hour < 12) "AM" else "PM")

        calendar.add(java.util.Calendar.HOUR_OF_DAY, 2)
        val endHour = calendar.get(java.util.Calendar.HOUR_OF_DAY)
        val endTime = String.format(Locale.ENGLISH, "%02d:%02d %s", if (endHour % 12 == 0) 12 else endHour % 12, roundedMinute, if (endHour < 12) "AM" else "PM")

        selectedStartDateText = startDate
        selectedEndDateText = startDate
        selectedStartTimeText = startTime
        selectedEndTimeText = endTime
    }

    LazyColumn(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 6.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        item {
            TextField(
                value = eventText,
                onValueChange = { eventText = it },
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .background(color = darkModeColors.gray10),
                textStyle =
                    MementoTheme.typography.body_b_18.copy(
                        color = darkModeColors.white,
                    ),
                placeholder = {
                    Text(
                        text = "Add your event",
                        style =
                            MementoTheme.typography.body_b_18.copy(
                                color = darkModeColors.gray07,
                            ),
                    )
                },
                colors =
                    TextFieldDefaults.colors(
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
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .background(darkModeColors.gray08),
                thickness = 2.dp,
            )
        }

        item {
            // 시작 날짜와 시간 선택
            AddPlanSelectComponent(
                title = "Starts",
                dateText = selectedStartDateText,
                onDateClick = { isStartCalendarVisible = true },
                timeText = if (isAllDayChecked) "All-day" else selectedStartTimeText,
                onTimeClick = { showStartTimePickerBottomSheet = true },
                isAllChecked = isAllDayChecked,
            )
        }

        item {
            // 종료 날짜와 시간 선택
            AddPlanSelectComponent(
                title = "Ends",
                dateText = selectedEndDateText,
                onDateClick = { isEndCalendarVisible = true },
                timeText = if (isAllDayChecked) "All-day" else selectedEndTimeText,
                onTimeClick = { showEndTimePickerBottomSheet = true },
                isAllChecked = isAllDayChecked,
            )
        }

        item {
            // All-day 체크박스
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Checkbox(
                    checked = isAllDayChecked,
                    onCheckedChange = { isAllDayChecked = it },
                    colors =
                        CheckboxDefaults.colors(
                            uncheckedColor = darkModeColors.gray05,
                            checkedColor = darkModeColors.gray05,
                            checkmarkColor = darkModeColors.black,
                        ),
                )
                Text(
                    text = "All-day",
                    style =
                        defaultMementoTypography.body_r_14.copy(
                            darkModeColors.gray05,
                        ),
                )
            }
        }

        item {
            // 그냥 Repeat
            AddPlanSelectComponent(
                title = "Repeat",
                dateText = selectedRepeatText,
                onDateClick = { showRepeatBottomSheet = true },
                timeText = null,
                onTimeClick = null,
            )
        }

        if (isAllDayChecked) {
            item {
                // End Repeat
                AddPlanSelectComponent(
                    title = "End Repeat",
                    dateText = selectedEndRepeatText,
                    onDateClick = { isEndRepeatCalendarVisible = true },
                    timeText = null,
                    onTimeClick = null,
                )
            }
        }

        item {
            // Tag
            AddPlanSelectComponent(
                title = "Tag",
                dateText = selectedTagText,
                onDateClick = { showTagBottomSheet = true },
                timeText = null,
                onTimeClick = null,
                tagColor = selectedTagColor,
            )
        }
    }

    MementoBottomSheet(
        isOpenBottomSheet = showStartTimePickerBottomSheet,
        content = {
            MementoTimePicker(
                selectedTime = selectedStartTimeText,
                onTimeSelected = { selectedStartTimeText = it },
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
        onDismiss = { isStartCalendarVisible = false },
    )

    DatePickerModalHandler(
        isCalendarVisible = isEndCalendarVisible,
        onDateSelected = { selectedEndDateText = formatDate(it ?: 0) },
        onDismiss = { isEndCalendarVisible = false },
    )

    DatePickerModalHandler(
        isCalendarVisible = isEndRepeatCalendarVisible,
        onDateSelected = { selectedEndRepeatText = formatDate(it ?: 0) },
        onDismiss = { isEndRepeatCalendarVisible = false },
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
    isAllChecked: Boolean? = null,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
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
            selectorType =
                if (title == "Repeat" || title == "End Repeat") {
                    SelectorType.BASIC
                } else if (title == "Tag") {
                    SelectorType.TAG
                } else {
                    SelectorType.DATESELECTOR
                },
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
                modifier = Modifier.padding(start = 10.dp),
                isLimited = isAllChecked,
            )
        }
    }
}

@Preview
@Composable
fun AddPlanScreenPreview() {
    AddPlanScreen()
}
