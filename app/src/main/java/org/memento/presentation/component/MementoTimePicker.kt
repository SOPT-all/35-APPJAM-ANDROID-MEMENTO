package org.memento.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sd.lib.compose.wheel_picker.FVerticalWheelPicker
import com.sd.lib.compose.wheel_picker.FWheelPickerFocusVertical
import com.sd.lib.compose.wheel_picker.rememberFWheelPickerState
import kotlinx.coroutines.delay
import org.memento.ui.theme.MementoTheme
import org.memento.ui.theme.darkModeColors

@Composable
fun MementoTimePicker(
    selectedTime: String,
    onTimeSelected: (String) -> Unit,
) {
    val hourState = rememberFWheelPickerState(initialIndex = 0)
    val minuteState = rememberFWheelPickerState(initialIndex = 0)
    val periodState = rememberFWheelPickerState(initialIndex = 0)

    val periods = listOf("AM", "PM")

    fun getFormattedTime(): String {
        val hour = hourState.currentIndex
        val minute = minuteState.currentIndex
        val periodIndex = periodState.currentIndex.takeIf { it in periods.indices } ?: 0
        val period = periods[periodIndex]

        return "${hour.toString().padStart(2, '0')}:${minute.toString().padStart(2, '0')} $period"
    }

    LaunchedEffect(hourState.currentIndex, minuteState.currentIndex, periodState.currentIndex) {
        delay(100)
        onTimeSelected(getFormattedTime())
    }

    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        FVerticalWheelPicker(
            modifier = Modifier.weight(1f),
            count = 12,
            state = hourState,
            focus = {
                FWheelPickerFocusVertical(
                    dividerColor = darkModeColors.gray06,
                    dividerSize = 1.dp,
                )
            },
        ) { index ->
            Text(
                text = index.toString().padStart(2, '0'),
                style =
                    MementoTheme.typography.body_b_18.copy(
                        color = darkModeColors.white,
                    ),
            )
        }

        Spacer(modifier = Modifier.width(20.dp))

        FVerticalWheelPicker(
            modifier = Modifier.weight(1f),
            count = 60,
            state = minuteState,
            focus = {
                FWheelPickerFocusVertical(
                    dividerColor = darkModeColors.gray06,
                    dividerSize = 1.dp,
                )
            },
        ) { index ->
            Text(
                text = index.toString().padStart(2, '0'),
                style =
                    MementoTheme.typography.body_b_18.copy(
                        color = darkModeColors.white,
                    ),
            )
        }

        Spacer(modifier = Modifier.width(20.dp))

        FVerticalWheelPicker(
            modifier = Modifier.weight(1f),
            count = periods.size,
            state = periodState,
            focus = {
                FWheelPickerFocusVertical(
                    dividerColor = darkModeColors.gray06,
                    dividerSize = 1.dp,
                )
            },
        ) { index ->
            Text(
                text = periods[index],
                style =
                    MementoTheme.typography.body_b_18.copy(
                        color = darkModeColors.white,
                    ),
            )
        }
    }
}

@Preview
@Composable
fun MementoTimePickerPreview() {
    MementoTimePicker(
        selectedTime = "",
        onTimeSelected = { },
    )
}
