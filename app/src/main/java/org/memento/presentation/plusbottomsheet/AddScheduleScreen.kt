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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.memento.R
import org.memento.core.util.UiState
import org.memento.domain.type.ErrorType
import org.memento.domain.type.SuccessType
import org.memento.presentation.component.DatePickerModalHandler
import org.memento.presentation.component.MementoBottomSheet
import org.memento.presentation.component.MementoChipSelector
import org.memento.presentation.component.MementoTimePicker
import org.memento.presentation.component.TagSelectorContent
import org.memento.presentation.type.SelectorType
import org.memento.presentation.util.MementoToast
import org.memento.presentation.util.formatDate
import org.memento.presentation.util.noRippleClickable
import org.memento.ui.theme.MementoTheme
import org.memento.ui.theme.darkModeColors
import org.memento.ui.theme.defaultMementoTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScheduleScreen(
    viewModel: AddScheduleViewModel = hiltViewModel(),
    onCloseBottomSheet: () -> Unit,
    isEdit: Boolean,
    isEditCancel: () -> Unit,
    isEditDone: () -> Unit,
    planId: Int = 0,
) {
    val eventText by viewModel.eventText.collectAsStateWithLifecycle()
    val selectedStartDateText by viewModel.selectedStartDateText.collectAsStateWithLifecycle()
    val selectedEndDateText by viewModel.selectedEndDateText.collectAsStateWithLifecycle()
    val selectedStartTimeText by viewModel.selectedStartTimeText.collectAsStateWithLifecycle()
    val selectedEndTimeText by viewModel.selectedEndTimeText.collectAsStateWithLifecycle()
    val selectedTagId by viewModel.selectedTagId.collectAsStateWithLifecycle()
    val selectedTagText by viewModel.selectedTagText.collectAsStateWithLifecycle()
    val selectedTagColor by viewModel.selectedTagColor.collectAsStateWithLifecycle()
    val isAllDayChecked by viewModel.isAllDayChecked.collectAsStateWithLifecycle()
    val isTimeValid by viewModel.isTimeValid.collectAsStateWithLifecycle()
    val tagList by viewModel.tagList.collectAsStateWithLifecycle()

    val sheetTimePickerState = rememberModalBottomSheetState()
    val sheetTagState = rememberModalBottomSheetState()

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var isShowToast by remember { mutableStateOf(false) }
    var showStartTimePickerBottomSheet by remember { mutableStateOf(false) }
    var showEndTimePickerBottomSheet by remember { mutableStateOf(false) }
    var showTagBottomSheet by remember { mutableStateOf(false) }
    var isStartCalendarVisible by remember { mutableStateOf(false) }
    var isEndCalendarVisible by remember { mutableStateOf(false) }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getTagList()
    }

    LaunchedEffect(isEdit) {
        if (isEdit) {
            planId.let {
                viewModel.getScheduleDetail(it)
            }
        } else {
            viewModel.initialTimeValue()
        }
    }

    LaunchedEffect(selectedStartDateText, selectedEndDateText, selectedStartTimeText, selectedEndTimeText) {
        if (selectedStartDateText.isNotBlank() && selectedEndDateText.isNotBlank() &&
            selectedStartTimeText.isNotBlank() && selectedEndTimeText.isNotBlank()
        ) {
            viewModel.updateAllDayCheck()
            viewModel.validateTimeOrder()
        }
    }

    LaunchedEffect(isTimeValid) {
        if (!isTimeValid) {
            val toast = MementoToast(context)
            toast.makeText(
                message = ErrorType.TIME_PRECEDE_ERROR.message,
                icon = R.drawable.ic_toast,
                lifecycleOwner = lifecycleOwner,
            )
        }
    }

    LaunchedEffect(uiState) {
        when (uiState) {
            is UiState.Success -> {
                val toast = MementoToast(context)
                toast.makeText(
                    message = SuccessType.CREATE_SUCCESS.message,
                    icon = R.drawable.ic_toast,
                    lifecycleOwner = lifecycleOwner,
                )
                onCloseBottomSheet()
            }

            is UiState.Failure -> {
                val toast = MementoToast(context)
                toast.makeText(
                    message = ErrorType.NETWORK_ERROR.message,
                    icon = R.drawable.ic_toast,
                    lifecycleOwner = lifecycleOwner,
                )
            }

            else -> Unit
        }
    }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 6.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        if (isEdit) {
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 5.dp, vertical = 7.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = "Cancel",
                    style =
                        MementoTheme.typography.body_r_14.copy(
                            color = darkModeColors.gray02,
                        ),
                    modifier =
                        Modifier
                            .noRippleClickable {
                                isEditCancel()
                            },
                )

                Text(
                    text = "Done",
                    style =
                        MementoTheme.typography.body_r_14.copy(
                            color = darkModeColors.gray02,
                        ),
                    modifier =
                        Modifier
                            .noRippleClickable {
                                viewModel.patchAddSchedule(planId)
                                isEditDone()
                            },
                )
            }
        }

        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 3.dp),
        ) {
            BasicTextField(
                value = eventText,
                onValueChange = { newText ->
                    viewModel.updateEventText(newText)
                },
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .background(color = darkModeColors.gray10)
                        .padding(horizontal = 6.dp),
                textStyle =
                    MementoTheme.typography.body_b_18.copy(
                        color = darkModeColors.white,
                    ),
                cursorBrush = remember { Brush.verticalGradient(colors = listOf(darkModeColors.white, darkModeColors.white)) },
                singleLine = true,
            )

            if (eventText.isEmpty()) {
                Text(
                    text = "Add your event",
                    modifier = Modifier.padding(horizontal = 6.dp),
                    style =
                        MementoTheme.typography.body_b_18.copy(
                            color = darkModeColors.gray07,
                        ),
                )
            }
        }

        HorizontalDivider(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .background(darkModeColors.gray07),
            thickness = 2.dp,
        )

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

        // All-day 체크박스
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Checkbox(
                checked = isAllDayChecked,
                onCheckedChange = { isChecked ->
                    viewModel.toggleAllDay(isChecked)
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

        // Tag
        AddPlanSelectComponent(
            title = "Tag",
            dateText = selectedTagText,
            onDateClick = {
                showTagBottomSheet = true
            },
            timeText = null,
            onTimeClick = null,
            tagColor = selectedTagColor,
            isChipClicked = showTagBottomSheet,
        )

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .background(color = darkModeColors.gray10)
                    .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier =
                    Modifier
                        .background(
                            shape = CircleShape,
                            color = if (eventText == "") darkModeColors.green.copy(alpha = 0.3f) else darkModeColors.green,
                        )
                        .noRippleClickable {
                            viewModel.postAddSchedule()
                        },
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

    MementoBottomSheet(
        isOpenBottomSheet = showStartTimePickerBottomSheet,
        content = {
            MementoTimePicker(
                selectedTime = selectedStartTimeText,
                onTimeSelected = { newStartTime ->
                    viewModel.updateStartTime(newTime = newStartTime)
                },
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
                onTimeSelected = { newEndTime ->
                    viewModel.updateEndTime(newTime = newEndTime)
                },
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
                onTagSelected = { id, color, tag ->
                    viewModel.updateTag(id = id, tag = tag, color = color)
                },
                tagList = tagList,
            )
        },
        sheetState = sheetTagState,
        onConfirm = {
            showTagBottomSheet = false
        },
    )

    DatePickerModalHandler(
        isCalendarVisible = isStartCalendarVisible,
        onDateSelected = {
            viewModel.updateStartDate(
                formatDate(it ?: 0),
            )
        },
        onDismiss = { isStartCalendarVisible = false },
    )

    DatePickerModalHandler(
        isCalendarVisible = isEndCalendarVisible,
        onDateSelected = {
            viewModel.updateEndDate(
                formatDate(it ?: 0),
            )
        },
        onDismiss = { isEndCalendarVisible = false },
    )

    if (isShowToast) {
        val customToast = MementoToast(LocalContext.current)
        customToast.makeText(message = ErrorType.TIME_PRECEDE_ERROR.message, icon = R.drawable.ic_toast, lifecycleOwner)
        isShowToast = false
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
fun AddScheduleScreenPreview() {
    AddScheduleScreen(
        onCloseBottomSheet = { },
        isEditDone = { },
        isEditCancel = { },
        isEdit = false,
    )
}
