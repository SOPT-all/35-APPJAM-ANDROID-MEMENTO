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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.memento.presentation.plusbottomsheet.AddScheduleScreen
import org.memento.ui.theme.darkModeColors

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MementoEditScheduleBottomSheet(
    isOpenBottomSheet: Boolean,
    sheetState: SheetState,
    onCancel: () -> Unit = {},
    onConfirm: (tagName: String, tagColor: String) -> Unit = { _, _ -> },
    preTagId: Int? = null,
    planId: Int,
) {
    val coroutineScope = rememberCoroutineScope()

    if (isOpenBottomSheet) {
        coroutineScope.launch {
            sheetState.show()
        }

        ModalBottomSheet(
            onDismissRequest = { onCancel() },
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
                AddScheduleScreen(
                    onCloseBottomSheet = { tagName, tagColor -> onConfirm(tagName, tagColor) },
                    isEdit = true,
                    preTagId = preTagId,
                    isEditCancel = { onCancel() },
                    planId = planId,
                )
            }
        }
    }
}
