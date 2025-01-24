package org.memento.presentation.component

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import org.memento.presentation.plusbottomsheet.AddToDoDeadLineScreen
import org.memento.presentation.plusbottomsheet.AddToDoEisenScreen
import org.memento.presentation.plusbottomsheet.AddToDoScreen
import org.memento.presentation.plusbottomsheet.AddToDoTagScreen
import org.memento.presentation.plusbottomsheet.AddToDoViewModel
import org.memento.presentation.type.BottomSheetType
import org.memento.ui.theme.darkModeColors

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MementoEditTodoBottomSheet(
    isOpenBottomSheet: Boolean,
    sheetState: SheetState,
    onCancel: () -> Unit = {},
    onConfirm: () -> Unit = {},
    planId: Int,
    viewModel: AddToDoViewModel = hiltViewModel(),
) {
    val coroutineScope = rememberCoroutineScope()
    var currentBottomSheet by remember { mutableStateOf<BottomSheetType?>(BottomSheetType.MAIN) }

    if (isOpenBottomSheet) {
        viewModel.getTodoDetail(todoId = planId)
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
                            onCloseBottomSheet = { },
                            isEdit = true,
                            planId = planId,
                            isEditDone = {
                                onConfirm()
                            },
                            isEditCancel = {
                                onCancel()
                            },
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
