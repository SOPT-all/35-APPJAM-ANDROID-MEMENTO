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
    onTimeSelected : (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val hourState = rememberFWheelPickerState(initialIndex = 0)
        FVerticalWheelPicker(
            modifier = Modifier.weight(1f),
            count = 11,
            state = hourState,
            focus = {
                FWheelPickerFocusVertical(
                    dividerColor = darkModeColors.gray06,
                    dividerSize = 1.dp
                )
            }
        ) { index ->
            val hour = if (index == 0) 12 else index
            onTimeSelected(
                "${hour.toString().padStart(2, '0')}:00 AM"
            )
            Text(
                text = index.toString().padStart(2, '0'),
                style = MementoTheme.typography.body_b_18.copy(
                    color = darkModeColors.white
                )
            )
        }

        Spacer(modifier = Modifier.width(20.dp))

        val minuteState = rememberFWheelPickerState(initialIndex = 0)
        val minuteValues = listOf(0, 30)
        FVerticalWheelPicker(
            modifier = Modifier.weight(1f),
            count = minuteValues.size,
            state = minuteState,
            focus = {
                FWheelPickerFocusVertical(
                    dividerColor = darkModeColors.gray06,
                    dividerSize = 1.dp
                )
            }
        ) { index ->
            Text(
                text = minuteValues[index].toString().padStart(2, '0'),
                style = MementoTheme.typography.body_b_18.copy(
                    color = darkModeColors.white
                )
            )
        }

        Spacer(modifier = Modifier.width(20.dp))

        val periodState = rememberFWheelPickerState(initialIndex = 0)
        val periods = listOf("AM", "PM")
        FVerticalWheelPicker(
            modifier = Modifier.weight(1f),
            count = periods.size,
            state = periodState,
            focus = {
                FWheelPickerFocusVertical(
                    dividerColor = darkModeColors.gray06,
                    dividerSize = 1.dp
                )
            }
        ) { index ->
            Text(
                text = periods[index],
                style = MementoTheme.typography.body_b_18.copy(
                    color = darkModeColors.white
                )
            )
        }
    }
}

@Preview
@Composable
fun MementoTimePickerPreview() {
    MementoTimePicker(
        onTimeSelected = { }
    )
}