package org.memento.presentation.plusbottomsheet

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.memento.R
import org.memento.domain.type.ErrorType
import org.memento.presentation.util.MementoToast
import org.memento.presentation.util.changeHexToColor
import org.memento.presentation.util.formatDate
import org.memento.presentation.util.noRippleClickable
import org.memento.ui.DatePickerModal
import org.memento.ui.theme.MementoTheme
import org.memento.ui.theme.darkModeColors

@Composable
fun AddToDoScreen(
    tagColor: String,
    deadLineText: String,
    onNavigateDeadLineSetting: () -> Unit,
    onNavigateTagSetting: () -> Unit,
    onNavigateEisenHourSetting: () -> Unit
) {
    var selectedDateText by remember { mutableStateOf("Today") }
    var isCalendarVisible by remember { mutableStateOf(false) }
    var addToDoText by remember { mutableStateOf("") }
    var isShowToast by remember { mutableStateOf(false) }

    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Column(
        modifier =
        Modifier
            .fillMaxSize()
            .padding(horizontal = 23.dp, vertical = 5.dp),
    ) {
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
                        if (selectedDate != null) {
                            selectedDateText = formatDate(selectedDate)
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
            onValueChange = { addToDoText = it },
            modifier =
            Modifier
                .background(color = Color.Transparent)
                .focusRequester(focusRequester)
                .padding(top = 16.dp),
            textStyle =
            MementoTheme.typography.body_b_16.copy(
                color = darkModeColors.white,
            ),
            cursorBrush =
            Brush.verticalGradient(
                listOf(darkModeColors.green, darkModeColors.green),
            ),
            keyboardOptions =
            KeyboardOptions.Default.copy(
                capitalization = KeyboardCapitalization.Sentences,
            ),
        )

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier.padding(vertical = 16.dp),
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
                            color = changeHexToColor(hex = tagColor),
                            shape = CircleShape,
                        ),
                )
            }

            Box(
                modifier =
                Modifier
                    .background(color = darkModeColors.gray09)
                    .align(alignment = Alignment.CenterVertically)
                    .padding(all = 8.dp)
                    .noRippleClickable {
                        onNavigateTagSetting()
                    },
            ) {
                Box(
                    modifier =
                    Modifier
                        .background(
                            color = changeHexToColor(hex = tagColor),
                            shape = RoundedCornerShape(2.dp),
                        )
                        .padding(all = 2.dp),
                    contentAlignment = Alignment.Center
                ){
                    Image(
                        painter = painterResource(R.drawable.ic_eisen_hour),
                        contentDescription = "아이젠 하워 버튼"
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

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
                            isShowToast = true
                        },
                )
            }
        }
    }

    if (isShowToast) {
        val customToast = MementoToast(LocalContext.current)
        customToast.MakeText(message = ErrorType.NETWORK_ERROR.message, icon = R.drawable.ic_toast)
        isShowToast = false
    }
}

@Preview
@Composable
fun AddToDoScreenPreview() {
    AddToDoScreen(
        tagColor = "",
        deadLineText = "",
        onNavigateDeadLineSetting = { },
        onNavigateTagSetting = { },
        onNavigateEisenHourSetting = { }
    )
}
