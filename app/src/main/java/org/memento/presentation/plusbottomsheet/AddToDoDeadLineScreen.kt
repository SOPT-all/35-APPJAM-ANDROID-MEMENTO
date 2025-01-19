package org.memento.presentation.plusbottomsheet

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import org.memento.R
import org.memento.presentation.component.DeadLineSelectorContent
import org.memento.presentation.component.MementoBottomSheet
import org.memento.presentation.component.MementoChipSelector
import org.memento.presentation.type.SelectorType
import org.memento.presentation.util.formatDate
import org.memento.presentation.util.noRippleClickable
import org.memento.ui.theme.MementoTheme
import org.memento.ui.theme.darkModeColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddToDoDeadLineScreen(
    onClose: () -> Unit,
    onDone: (String) -> Unit,
) {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(color = darkModeColors.gray10),
    ) {
        val sheetDeadLineState = rememberModalBottomSheetState()
        var showDeadLineBottomSheet by remember { mutableStateOf(false) }

        var selectedDateText by remember { mutableStateOf("Today") }

        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Image(
                painter = painterResource(R.drawable.ic_back),
                contentDescription = "뒤로가기 버튼",
                modifier = Modifier.noRippleClickable {
                    onClose()
                }
            )
            Text(
                text = "Done",
                modifier =
                    Modifier
                        .padding(horizontal = 18.dp, vertical = 12.dp)
                        .noRippleClickable {
                            onDone(selectedDateText)
                        },
                style =
                    MementoTheme.typography.body_r_16.copy(
                        color = darkModeColors.gray07,
                    ),
            )
        }

        Column(
            modifier =
                Modifier
                    .weight(1f)
                    .padding(horizontal = 12.dp, vertical = 26.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Deadline",
                    style =
                        MementoTheme.typography.body_r_16.copy(
                            color = darkModeColors.gray05,
                        ),
                )

                Spacer(modifier = Modifier.weight(1f))

                MementoChipSelector(
                    selectorType = SelectorType.BASIC,
                    isClicked = showDeadLineBottomSheet,
                    onClickedChange = {
                        showDeadLineBottomSheet = true
                    },
                    content = selectedDateText,
                    tagColor = null,
                )
            }

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
        }
    }
}
