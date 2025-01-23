package org.memento.presentation.component

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.memento.presentation.plusbottomsheet.AddToDoDeadLineScreen
import org.memento.presentation.plusbottomsheet.AddToDoEisenScreen
import org.memento.presentation.plusbottomsheet.AddToDoScreen
import org.memento.presentation.plusbottomsheet.AddToDoTagScreen
import org.memento.presentation.plusbottomsheet.MainPlusBottomSheet
import org.memento.presentation.type.BottomSheetType
import org.memento.presentation.util.formatDate
import org.memento.presentation.util.noRippleClickable
import org.memento.ui.theme.MementoTheme
import org.memento.ui.theme.darkModeColors

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MementoCancelBottomSheet(
    isOpenBottomSheet: Boolean,
    sheetState: SheetState,
    onConfirm: () -> Unit = {},
) {
    val coroutineScope = rememberCoroutineScope()
    var currentBottomSheet by remember { mutableStateOf<BottomSheetType?>(BottomSheetType.MAIN) }

    if (isOpenBottomSheet) {
        coroutineScope.launch {
            sheetState.show()
        }

        ModalBottomSheet(
            onDismissRequest = { onConfirm() },
            sheetState = sheetState,
            shape = RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp),
            containerColor = darkModeColors.gray10,
            dragHandle = null,
        ) {
            Column(
                modifier =
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.73f)
                    .imePadding(),
            ) {
                when (currentBottomSheet) {
                    BottomSheetType.MAIN ->
                        AddToDoScreen(
                            onNavigateDeadLineSetting = {
                                currentBottomSheet = BottomSheetType.DEADLINE
                            },
                            onNavigateTagSetting = {
                                currentBottomSheet = BottomSheetType.TAG
                            },
                            onNavigateEisenHourSetting = {
                                currentBottomSheet = BottomSheetType.EISEN
                            },
                            isEdit = true,
                            isEditDone = { currentBottomSheet = null },
                            isEditCancel = { currentBottomSheet = null }
                        )

                    BottomSheetType.DEADLINE ->
                        AddToDoDeadLineScreen(
                            onClose = { currentBottomSheet = BottomSheetType.MAIN },
                            onDone = { currentBottomSheet = BottomSheetType.MAIN },
                        )

                    BottomSheetType.TAG ->
                        AddToDoTagScreen(
                            onClose = { currentBottomSheet = BottomSheetType.MAIN },
                            onDone = { currentBottomSheet = BottomSheetType.MAIN },
                        )

                    BottomSheetType.EISEN ->
                        AddToDoEisenScreen(
                            onClose = { currentBottomSheet = BottomSheetType.MAIN },
                            onDone = { currentBottomSheet = BottomSheetType.MAIN },
                        )

                    null -> {}
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun MementoCancelBottomSheetPreview() {
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

        Text(
            text = "Tag",
            modifier =
            Modifier
                .fillMaxWidth()
                .background(darkModeColors.gray04)
                .padding(10.dp)
                .noRippleClickable {
                    showTagBottomSheet = true
                },
        )

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
            onConfirm = { _ ->
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
            onConfirm = { _ ->
                showDeadLineBottomSheet = false
            },
        )

        MementoBottomSheet(
            isOpenBottomSheet = showTagBottomSheet,
            content = {
                TagSelectorContent(
                    onTagSelected = { color, tag ->
                    },
                )
            },
            sheetState = sheetTagState,
            onConfirm = { _ ->
                showTagBottomSheet = false
            },
        )

        MementoBottomSheet(
            isOpenBottomSheet = showTimePickerBottomSheet,
            content = {
                var tempTime by remember { mutableStateOf(selectedTimeText) }
                MementoTimePicker(
                    selectedTime = tempTime,
                    onTimeSelected = { tempTime = it },
                )
            },
            sheetState = sheetTimePickerState,
            onConfirm = { selectedValue ->
                selectedTimeText = selectedValue ?: selectedTimeText
                showTimePickerBottomSheet = false
            },
        )
    }
}
