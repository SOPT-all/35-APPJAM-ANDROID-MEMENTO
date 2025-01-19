package org.memento.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.memento.R
import org.memento.presentation.type.DeadLineType
import org.memento.presentation.type.RepeatType
import org.memento.presentation.util.noRippleClickable
import org.memento.ui.theme.MementoTheme
import org.memento.ui.theme.darkModeColors

@Composable
fun MementoTextBottomSheetItem(
    option: String,
    isActive: Boolean,
    activeBgColor: Color,
    activeContentColor: Color,
    onClick: () -> Unit,
) {
    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .background(
                    color = if (isActive) activeBgColor else darkModeColors.gray09,
                )
                .noRippleClickable {
                    onClick()
                }
                .padding(vertical = 7.dp, horizontal = 12.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = option,
            style =
                MementoTheme.typography.body_r_16.copy(
                    color = if (isActive) activeContentColor else darkModeColors.gray07,
                ),
        )
    }
}

@Composable
fun DeadLineSelectorContent(
    onDateSelected: (Long?) -> Unit,
) {
    var activeIndex by remember { mutableIntStateOf(0) }
    var isCalendarVisible by remember { mutableStateOf(false) }

    val options = DeadLineType.entries.toTypedArray()

    Column {
        options.forEachIndexed { index, option ->
            MementoTextBottomSheetItem(
                option = option.text,
                isActive = activeIndex == index,
                activeBgColor = darkModeColors.gray08,
                activeContentColor = darkModeColors.gray02,
                onClick = {
                    activeIndex = if (activeIndex == index) -1 else index
                    isCalendarVisible = (option == DeadLineType.CUSTOM_DATE && activeIndex == index)
                },
            )
        }

        DatePickerModalHandler(
            isCalendarVisible = isCalendarVisible,
            onDateSelected = onDateSelected,
            onDismiss = { isCalendarVisible = false },
        )
    }
}

@Composable
fun DatePickerModalHandler(
    isCalendarVisible: Boolean,
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit,
) {
    if (isCalendarVisible) {
        DatePickerModal(
            onDateSelected = { selectedDate ->
                onDateSelected(selectedDate)
            },
            onDismiss = onDismiss,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit,
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        colors =
            DatePickerDefaults.colors(
                containerColor = darkModeColors.gray08,
            ),
        confirmButton = {
            TextButton(
                onClick = {
                    onDateSelected(datePickerState.selectedDateMillis)
                    onDismiss()
                },
            ) {
                Text(
                    text = stringResource(R.string.dialog_button_ok),
                    color = darkModeColors.green,
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = stringResource(R.string.dialog_button_cancel),
                    color = darkModeColors.green,
                )
            }
        },
    ) {
        DatePicker(
            state = datePickerState,
            colors =
                DatePickerDefaults.colors(
                    containerColor = darkModeColors.gray08,
                    titleContentColor = darkModeColors.white,
                    headlineContentColor = darkModeColors.white,
                    weekdayContentColor = darkModeColors.white,
                    subheadContentColor = darkModeColors.gray06,
                    dayContentColor = darkModeColors.white,
                    selectedDayContentColor = darkModeColors.black,
                    selectedDayContainerColor = darkModeColors.green,
                    todayContentColor = darkModeColors.green,
                    todayDateBorderColor = darkModeColors.green,
                ),
        )
    }
}

@Composable
fun RepeatSelectorContent(
    onRepeatSelected: (String) -> Unit,
) {
    var activeIndex by remember { mutableIntStateOf(0) }

    val options = RepeatType.entries.toTypedArray()

    options.forEachIndexed { index, option ->
        MementoTextBottomSheetItem(
            option = option.text,
            isActive = activeIndex == index,
            activeBgColor = darkModeColors.gray08,
            activeContentColor = darkModeColors.gray02,
            onClick = {
                if (activeIndex != index) {
                    activeIndex = index
                    onRepeatSelected(option.text)
                }
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MementoSelectContentPreview() {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(space = 5.dp),
    ) {
        DeadLineSelectorContent(
            onDateSelected = { },
        )
        RepeatSelectorContent(
            onRepeatSelected = { },
        )
    }
}
