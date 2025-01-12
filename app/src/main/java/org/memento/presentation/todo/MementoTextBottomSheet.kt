package org.memento.presentation.todo

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import org.memento.presentation.util.noRippleClickable
import org.memento.ui.theme.MementoTheme
import org.memento.ui.theme.darkModeColors

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MementoTextBottomSheet(
    isOpenBottomSheet: Boolean,
    content: @Composable () -> Unit,
    sheetState: SheetState,
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit = {}
) {
    val coroutineScope = rememberCoroutineScope()

    if (isOpenBottomSheet) {
        coroutineScope.launch {
            sheetState.show()
        }

        ModalBottomSheet(
            onDismissRequest = onDismissRequest,
            sheetState = sheetState,
            shape = RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp),
            containerColor = darkModeColors.gray09,
            contentColor = darkModeColors.gray02,
            dragHandle = null
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .background(darkModeColors.gray09)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 22.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = "OK",
                        style = MementoTheme.typography.body_r_14.copy(
                            color = darkModeColors.gray02
                        ),
                        modifier = Modifier
                            .noRippleClickable {
                                coroutineScope.launch {
                                    sheetState.hide()
                                    onDismissRequest()
                                }
                            }
                    )
                }
                content()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun MementoDateSelectorPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(space = 5.dp)
    ) {
        val sheetRepeatState = rememberModalBottomSheetState()
        var showRepeatBottomSheet by remember { mutableStateOf(false) }

        val sheetDeadLineState = rememberModalBottomSheetState()
        var showDeadLineBottomSheet by remember { mutableStateOf(false) }

        Text(
            text = "Today",
            modifier = Modifier.clickableWithoutRipple {
                showRepeatBottomSheet = true
            }
        )
        Spacer(
            modifier = Modifier
                .height(10.dp)
        )
        Text(
            text = "None",
            modifier = Modifier.clickableWithoutRipple {
                showDeadLineBottomSheet = true
            }
        )

        MementoTextBottomSheet(
            isOpenBottomSheet = showRepeatBottomSheet,
            content = {
                RepeatSelectorContent()
            },
            sheetState = sheetRepeatState,
            onDismissRequest = {
                showRepeatBottomSheet = false
            }
        )

        MementoTextBottomSheet(
            isOpenBottomSheet = showDeadLineBottomSheet,
            content = {
                DeadLineSelectorContent()
            },
            sheetState = sheetDeadLineState,
            onDismissRequest = {
                showDeadLineBottomSheet = false
            }
        )
    }
}