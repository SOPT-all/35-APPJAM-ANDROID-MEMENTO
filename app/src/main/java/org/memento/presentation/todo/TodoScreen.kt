package org.memento.presentation.todo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.memento.presentation.util.changeHexToColor
import org.memento.presentation.util.formatDate
import org.memento.presentation.util.noRippleClickable
import org.memento.ui.DeadLineSelectorContent
import org.memento.ui.MementoBottomSheet
import org.memento.ui.MementoTimePicker
import org.memento.ui.RepeatSelectorContent
import org.memento.ui.TagSelectorContent
import org.memento.ui.theme.darkModeColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoScreen() {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(space = 50.dp),
    ) {
        val sheetRepeatState = rememberModalBottomSheetState()
        var showRepeatBottomSheet by remember { mutableStateOf(false) }

        val sheetDeadLineState = rememberModalBottomSheetState()
        var showDeadLineBottomSheet by remember { mutableStateOf(false) }

        val sheetTagState = rememberModalBottomSheetState()
        var showTagBottomSheet by remember { mutableStateOf(false) }

        val sheetTimePickerState = rememberModalBottomSheetState()
        var showTimePickerBottomSheet by remember { mutableStateOf(false) }

        var selectedRepeatText by remember { mutableStateOf("Today") }
        var selectedDateText by remember { mutableStateOf("None") }
        var selectedTimeText by remember { mutableStateOf("Time") }

        var selectedColor by remember { mutableStateOf("#000000") }
        var selectedTag by remember { mutableStateOf("Tag") }

        Text(
            text = selectedRepeatText,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .background(darkModeColors.gray04)
                    .padding(10.dp)
                    .noRippleClickable {
                        showRepeatBottomSheet = true
                    },
        )

        Text(
            text = selectedDateText,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .background(darkModeColors.gray04)
                    .padding(10.dp)
                    .noRippleClickable {
                        showDeadLineBottomSheet = true
                    },
        )

        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .background(darkModeColors.gray04)
                    .padding(10.dp)
                    .noRippleClickable {
                        showTagBottomSheet = true
                    },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier =
                    Modifier
                        .size(12.dp)
                        .background(
                            color =
                                changeHexToColor(
                                    hex = selectedColor,
                                ),
                            shape = RoundedCornerShape(100.dp),
                        ),
            )
            Text(
                text = selectedTag,
            )
        }

        Text(
            text = selectedTimeText,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .background(darkModeColors.gray04)
                    .padding(10.dp)
                    .noRippleClickable {
                        showTimePickerBottomSheet = true
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
            isOpenBottomSheet = showTagBottomSheet,
            content = {
                TagSelectorContent(
                    onTagSelected = { color, tag ->
                        selectedColor = color
                        selectedTag = tag
                    },
                )
            },
            sheetState = sheetTagState,
            onConfirm = {
                showTagBottomSheet = false
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
