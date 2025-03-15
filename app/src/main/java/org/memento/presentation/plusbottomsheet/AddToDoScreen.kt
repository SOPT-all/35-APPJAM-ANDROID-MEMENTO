package org.memento.presentation.plusbottomsheet

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.memento.R
import org.memento.core.util.UiState
import org.memento.domain.type.ErrorType
import org.memento.domain.type.SuccessType
import org.memento.presentation.component.DatePickerModal
import org.memento.presentation.type.PriorityTagType
import org.memento.presentation.util.MementoToast
import org.memento.presentation.util.changeHexToColor
import org.memento.presentation.util.formatDate
import org.memento.presentation.util.noRippleClickable
import org.memento.ui.theme.MementoTheme
import org.memento.ui.theme.darkModeColors

@Composable
fun AddToDoScreen(
    viewModel: AddToDoViewModel = hiltViewModel(),
    onNavigateDeadLineSetting: () -> Unit,
    onNavigateTagSetting: () -> Unit,
    onNavigateEisenHourSetting: () -> Unit,
    onCloseBottomSheet: () -> Unit,
    isEdit: Boolean = false,
    planId: Int = 0,
    isEditDone: () -> Unit,
    isEditCancel: () -> Unit,
) {
    val selectedDateText by viewModel.selectedDateText.collectAsStateWithLifecycle()
    val addToDoText by viewModel.addToDoText.collectAsStateWithLifecycle()
    val addTagColor by viewModel.addTagColor.collectAsStateWithLifecycle()
    val deadLineText by viewModel.deadLineText.collectAsStateWithLifecycle()
    val addPriorityType by viewModel.addPriorityType.collectAsStateWithLifecycle()
    var isCalendarVisible by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val focusRequester = remember { FocusRequester() }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val patchState by viewModel.patchState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        viewModel.getTagList()
    }

    LaunchedEffect(patchState) {
        when (patchState) {
            is UiState.Success -> {
                viewModel.setLoadingState()
                isEditDone()
                val toast = MementoToast(context)
                toast.makeText(
                    message = SuccessType.CREATE_SUCCESS.message,
                    icon = R.drawable.ic_toast,
                    lifecycleOwner = lifecycleOwner,
                )
            }
            is UiState.Failure -> {
                viewModel.setLoadingState()
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

    LaunchedEffect(uiState) {
        when (uiState) {
            is UiState.Success -> {
                viewModel.setLoadingState()
                onCloseBottomSheet()
                val toast = MementoToast(context)
                toast.makeText(
                    message = SuccessType.CREATE_SUCCESS.message,
                    icon = R.drawable.ic_toast,
                    lifecycleOwner = lifecycleOwner,
                )
            }

            is UiState.Failure -> {
                val toast = MementoToast(context)
                viewModel.setLoadingState()
                toast.makeText(
                    message = ErrorType.NETWORK_ERROR.message,
                    icon = R.drawable.ic_toast,
                    lifecycleOwner = lifecycleOwner,
                )
            }

            else -> Unit
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = 23.dp, vertical = 5.dp),
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
                                    viewModel.patchAddTodo(planId)
                                    isEditDone()
                                },
                    )
                }
            }
            Row {
                Text(
                    text = "Add to-do,",
                    style =
                        MementoTheme.typography.body_b_18.copy(
                            color = darkModeColors.gray07,
                        ),
                )

                Text(
                    text = selectedDateText,
                    modifier =
                        Modifier
                            .padding(start = 5.dp)
                            .noRippleClickable {
                                isCalendarVisible = true
                            },
                    style =
                        MementoTheme.typography.body_b_18.copy(
                            color = darkModeColors.white,
                        ),
                )

                if (isCalendarVisible) {
                    DatePickerModal(
                        onDateSelected = { selectedDate ->
                            selectedDate?.let {
                                viewModel.updateSelectedDateText(formatDate(it))
                            }
                        },
                        onDismiss = {
                            isCalendarVisible = false
                        },
                    )
                }
            }

            BasicTextField(
                value = addToDoText,
                onValueChange = { newText ->
                    if (newText.replace(" ", "").length <= 30) {
                        viewModel.updateToDoText(newText = newText)
                    }
                },
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .aspectRatio(1.9f)
                        .background(color = Color.Transparent)
                        .focusRequester(focusRequester)
                        .padding(top = 16.dp),
                textStyle =
                    MementoTheme.typography.body_b_16.copy(
                        color = darkModeColors.white,
                    ),
                cursorBrush = SolidColor(darkModeColors.green),
                keyboardOptions =
                    KeyboardOptions.Default.copy(
                        capitalization = KeyboardCapitalization.Sentences,
                    ),
            )
        }

        Row(
            modifier =
                Modifier
                    .padding(vertical = 16.dp, horizontal = 20.dp)
                    .align(alignment = Alignment.BottomCenter),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                modifier =
                    Modifier
                        .background(color = darkModeColors.gray09)
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                        .noRippleClickable {
                            onNavigateDeadLineSetting()
                        },
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_deadline),
                    contentDescription = "마감 기한 아이콘",
                )
                Text(
                    text = deadLineText,
                    style =
                        MementoTheme.typography.detail_r_12.copy(
                            color = darkModeColors.gray02,
                        ),
                )
            }

            Box(
                modifier =
                    Modifier
                        .background(color = darkModeColors.gray09)
                        .align(alignment = Alignment.CenterVertically)
                        .padding(all = 16.dp)
                        .noRippleClickable {
                            onNavigateTagSetting()
                        },
            ) {
                Box(
                    modifier =
                        Modifier
                            .size(10.dp)
                            .background(
                                color = changeHexToColor(hex = addTagColor),
                                shape = CircleShape,
                            ),
                )
            }

            Image(
                painter =
                    painterResource(
                        when (addPriorityType) {
                            PriorityTagType.None -> R.drawable.ic_eisen_none
                            PriorityTagType.Immediate -> R.drawable.ic_eisen_immediate
                            PriorityTagType.High -> R.drawable.ic_eisen_high
                            PriorityTagType.Medium -> R.drawable.ic_eisen_medium
                            PriorityTagType.Low -> R.drawable.ic_eisen_low
                        },
                    ),
                contentDescription = "아이젠 하워 버튼",
                modifier =
                    Modifier.noRippleClickable {
                        onNavigateEisenHourSetting()
                    },
            )

            Spacer(modifier = Modifier.weight(1f))

            if (!isEdit) {
                Box(
                    modifier =
                        Modifier.background(
                            shape = CircleShape,
                            color = if (addToDoText == "") darkModeColors.green.copy(alpha = 0.3f) else darkModeColors.green,
                        ),
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_send),
                        contentDescription = "전송 버튼",
                        modifier =
                            Modifier
                                .padding(horizontal = 13.dp)
                                .padding(top = 12.dp, bottom = 10.dp)
                                .noRippleClickable {
                                    viewModel.postAddTodo()
                                },
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun AddToDoScreenPreview() {
    AddToDoScreen(
        onNavigateDeadLineSetting = { },
        onNavigateTagSetting = { },
        onNavigateEisenHourSetting = { },
        onCloseBottomSheet = { },
        isEditCancel = { },
        isEditDone = { },
    )
}
