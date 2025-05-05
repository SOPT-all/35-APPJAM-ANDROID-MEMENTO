package org.memento.presentation.setting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.memento.R
import org.memento.presentation.component.MementoBottomSheet
import org.memento.presentation.component.MementoChipSelector
import org.memento.presentation.component.MementoTimePicker
import org.memento.presentation.setting.component.SettingTopBar
import org.memento.presentation.type.SelectorType
import org.memento.presentation.type.SettingTopBarType
import org.memento.ui.theme.darkModeColors
import org.memento.ui.theme.defaultMementoTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingEditTimeScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
) {
    // Todo : 뷰모델 생성시, 아래와 같이 selectedStartTimeText 을 수정합니다.
    // val selectedStartTimeText by viewModel.selectedStartTimeText.collectAsStateWithLifecycle()
    val selectedStartTimeText = "00:00 AM" // dummy 입니다.
    val sheetTimePickerState = rememberModalBottomSheetState()
    var showStartTimePickerBottomSheet by remember { mutableStateOf(false) }

    Column(
        modifier =
            modifier
                .fillMaxSize()
                .padding(top = 16.dp),
    ) {
        SettingTopBar(
            type = SettingTopBarType.TIME,
            onBackClick = { onBack() },
        )

        Row(
            modifier = Modifier.padding(start = 16.dp, top = 40.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_wakeup),
                contentDescription = null,
                tint = darkModeColors.white,
            )
            Spacer(Modifier.width(6.dp))
            Text(
                text = stringResource(id = R.string.onboarding1_wake_up),
                style = defaultMementoTypography.body_r_14,
                color = darkModeColors.white,
            )
            Spacer(
                modifier =
                    Modifier
                        .weight(1f),
            )
            MementoChipSelector(
                selectorType = SelectorType.TIMESELECTOR,
                isClicked = showStartTimePickerBottomSheet,
                onClickedChange = { showStartTimePickerBottomSheet = it },
                content = selectedStartTimeText,
            )
        }
        MementoBottomSheet(
            isOpenBottomSheet = showStartTimePickerBottomSheet,
            content = {
                MementoTimePicker(
                    selectedTime = selectedStartTimeText,
                    onTimeSelected = { newStartTime ->
                        // Todo : 바뀐 기상 시간을 서버 통신 합니다.
                        // viewModel.updateStartTime(newTime = newStartTime)
                    },
                )
            },
            sheetState = sheetTimePickerState,
            onConfirm = {
                showStartTimePickerBottomSheet = false
            },
        )
    }
}

@Preview
@Composable
private fun SettingEditTimeScreenPreview() {
    SettingEditTimeScreen(onBack = {})
}
