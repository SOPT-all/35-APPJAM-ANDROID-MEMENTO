package org.memento.presentation.plusbottomsheet

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.memento.R
import org.memento.domain.type.SelectorType
import org.memento.presentation.MementoChipSelector
import org.memento.presentation.util.formatDate
import org.memento.presentation.util.formatTime
import org.memento.presentation.util.parseDateTime
import org.memento.ui.DatePickerModalHandler
import org.memento.ui.MementoBottomSheet
import org.memento.ui.MementoTimePicker
import org.memento.ui.TagSelectorContent
import org.memento.ui.theme.MementoTheme
import org.memento.ui.theme.darkModeColors
import org.memento.ui.theme.defaultMementoTypography
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPlanScreen() {
    var eventText by remember { mutableStateOf("") }

    val sheetTimePickerState = rememberModalBottomSheetState()
    val sheetTagState = rememberModalBottomSheetState()

    var showStartTimePickerBottomSheet by remember { mutableStateOf(false) }
    var showEndTimePickerBottomSheet by remember { mutableStateOf(false) }
    var showTagBottomSheet by remember { mutableStateOf(false) }

    var selectedStartDateText by remember { mutableStateOf("") }
    var selectedEndDateText by remember { mutableStateOf("") }
    var selectedStartTimeText by remember { mutableStateOf("") }
    var selectedEndTimeText by remember { mutableStateOf("") }
    var selectedTagText by remember { mutableStateOf("Untitled") }
    var selectedTagColor by remember { mutableStateOf("#F0F0F3") }

    var isAllDayChecked by remember { mutableStateOf(false) }
    var isStartCalendarVisible by remember { mutableStateOf(false) }
    var isEndCalendarVisible by remember { mutableStateOf(false) }

    fun initialTimeValue() {
        val currentTime = System.currentTimeMillis()
        val startDate = formatDate(currentTime)

        val calendar = Calendar.getInstance().apply { timeInMillis = currentTime }
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val roundedMinute =
            when (minute) {
                in 0..15 -> 0
                in 16..45 -> 30
                else -> 0
            }

        val adjustedHour = if (minute in 46..59) (hour + 1) % 24 else hour

        val startTime = formatTime(adjustedHour, roundedMinute)

        calendar.add(Calendar.HOUR_OF_DAY, 2)
        val endHour = calendar.get(Calendar.HOUR_OF_DAY)
        val endTime = formatTime(endHour, roundedMinute)

        selectedStartDateText = startDate
        selectedEndDateText = startDate
        selectedStartTimeText = startTime
        selectedEndTimeText = endTime
    }

    fun calculateEndTime(
        startDate: String,
        startTime: String,
        hoursToAdd: Int = 2,
    ): Pair<String, String> {
        val startDateTime = parseDateTime(startDate, startTime)
        val calendar = Calendar.getInstance().apply { time = startDateTime }
        calendar.add(Calendar.HOUR_OF_DAY, hoursToAdd)

        val endDate = formatDate(calendar.timeInMillis)
        val endHour = calendar.get(Calendar.HOUR_OF_DAY)
        val endMinute = calendar.get(Calendar.MINUTE)
        val endTime = formatTime(endHour, endMinute)

        return endDate to endTime
    }

    fun updateAllDayCheck() {
        val startDateTime = parseDateTime(selectedStartDateText, selectedStartTimeText)
        val endDateTime = parseDateTime(selectedEndDateText, selectedEndTimeText)

        val difference = endDateTime.time - startDateTime.time

        isAllDayChecked = difference >= 86_400_000
    }

    LaunchedEffect(Unit) {
        initialTimeValue()
    }

    LaunchedEffect(selectedStartDateText, selectedEndDateText, selectedStartTimeText, selectedEndTimeText) {
        updateAllDayCheck()
    }

    Column {
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
                    onTimeClick = {
                        showStartTimePickerBottomSheet = true
                    },
                    isAllChecked = isAllDayChecked,
                    isChipClicked = showStartTimePickerBottomSheet,
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
                    isChipClicked = showEndTimePickerBottomSheet,
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
                        onCheckedChange = {
                            isAllDayChecked = it
                            if (!it) {
                                val (endDate, endTime) = calculateEndTime(selectedStartDateText, selectedStartTimeText)
                                selectedEndDateText = endDate
                                selectedEndTimeText = endTime
                            }
                        },
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
                // Tag
                AddPlanSelectComponent(
                    title = "Tag",
                    dateText = selectedTagText,
                    onDateClick = { showTagBottomSheet = true },
                    timeText = null,
                    onTimeClick = null,
                    tagColor = selectedTagColor,
                    isChipClicked = showTagBottomSheet,
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

        Spacer(modifier = Modifier.weight(1f))

        Box(
            modifier =
                Modifier.background(
                    shape = CircleShape,
                    color = if (eventText == "") darkModeColors.green.copy(alpha = 0.3f) else darkModeColors.green,
                ),
        ) {
            Image(
                painter = painterResource(R.drawable.ic_send),
                contentDescription = "전송 버튼",
                modifier =
                    Modifier
                        .padding(horizontal = 13.dp)
                        .padding(top = 12.dp, bottom = 10.dp),
            )
        }
    }
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
    isChipClicked: Boolean = false,
) {
    var isClicked by remember { mutableStateOf(false) }

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
                when (title) {
                    "Repeat", "End Repeat" -> {
                        SelectorType.BASIC
                    }

                    "Tag" -> {
                        SelectorType.TAG
                    }

                    else -> {
                        SelectorType.DATESELECTOR
                    }
                },
            isClicked = false,
            onClickedChange = {
                onDateClick()
            },
            content = dateText,
            tagColor = tagColor,
        )

        timeText?.let {
            MementoChipSelector(
                selectorType = SelectorType.TIMESELECTOR,
                isClicked = isChipClicked,
                onClickedChange = {
                    isClicked = it
                    onTimeClick?.invoke()
                },
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
