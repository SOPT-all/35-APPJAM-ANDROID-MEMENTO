package org.memento.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sd.lib.compose.wheel_picker.FVerticalWheelPicker
import com.sd.lib.compose.wheel_picker.FWheelPickerFocusVertical
import com.sd.lib.compose.wheel_picker.rememberFWheelPickerState
import org.memento.ui.theme.MementoTheme
import org.memento.ui.theme.darkModeColors

@Composable
fun MementoTimePicker(
    onTimeSelected: (String) -> Unit,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val hourState = rememberFWheelPickerState(initialIndex = 0)
        val minuteState = rememberFWheelPickerState(initialIndex = 0)
        val periodState = rememberFWheelPickerState(initialIndex = 0)

        val minuteValues = listOf(0, 30)
        val periods = listOf("AM", "PM")

        fun getFormattedTime(): String {
            val hour = hourState.currentIndex
            val minuteIndex = minuteState.currentIndex.takeIf { it in minuteValues.indices } ?: 0
            val periodIndex = periodState.currentIndex.takeIf { it in periods.indices } ?: 0

            val minute = minuteValues[minuteIndex]
            val period = periods[periodIndex]

            return "${hour.toString().padStart(2, '0')}:${minute.toString().padStart(2, '0')} $period"
        }

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
//            onTimeSelected(getFormattedTime())
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
            count = minuteValues.size,
            state = minuteState,
            focus = {
                FWheelPickerFocusVertical(
                    dividerColor = darkModeColors.gray06,
                    dividerSize = 1.dp,
                )
            },
        ) { index ->
//            onTimeSelected(getFormattedTime())
            Text(
                text = minuteValues[index].toString().padStart(2, '0'),
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
//            onTimeSelected(getFormattedTime())
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
        onTimeSelected = { },
    )
}
