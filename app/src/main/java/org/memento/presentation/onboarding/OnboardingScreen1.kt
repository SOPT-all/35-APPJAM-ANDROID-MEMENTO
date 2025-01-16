package org.memento.presentation.onboarding

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.memento.R
import org.memento.presentation.component.MementoBottomSheet
import org.memento.presentation.component.MementoChipSelector
import org.memento.presentation.component.MementoTimePicker
import org.memento.presentation.onboarding.component.OnboardingBottomButton
import org.memento.presentation.onboarding.component.OnboardingTopAppBar
import org.memento.presentation.type.OnboardingTopType
import org.memento.presentation.type.SelectorType
import org.memento.ui.theme.darkModeColors
import org.memento.ui.theme.defaultMementoTypography

enum class SETTIME {
    WAKEUP,
    WINDDOWN,
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnboardingScreen1(navigateToOnboardingScreen2: () -> Unit) {
    val initialTimeText = stringResource(id = R.string.time_example)
    val sheetTimePickerState = rememberModalBottomSheetState()
    var showTimePickerBottomSheet by remember { mutableStateOf(false) }

    var selectedTimeTextWakeUp by remember { mutableStateOf(initialTimeText) }
    var selectedTimeTextWindDown by remember { mutableStateOf(initialTimeText) }

    var isClickedWakeUp by remember { mutableStateOf(false) }
    var isClickedWindDown by remember { mutableStateOf(false) }

    var isSelectedWakeUp by remember { mutableStateOf(false) }
    var isSelectedWindDown by remember { mutableStateOf(false) }

    var currentActiveSelector by remember { mutableStateOf<SETTIME?>(null) }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 10.dp),
    ) {
        OnboardingTopAppBar(
            type = OnboardingTopType.PAGE1,
            onSkipClick = {},
        )
        Spacer(Modifier.height(40.dp))
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_wakeup),
                    contentDescription = null,
                    tint = darkModeColors.white,
                )
                Spacer(Modifier.width(6.dp))
                Text(
                    text = stringResource(id = R.string.onboarding1_wake_up),
                    style = defaultMementoTypography.title_b_22,
                    color = darkModeColors.white,
                )
                Spacer(
                    modifier =
                        Modifier
                            .weight(1f),
                )
                MementoChipSelector(
                    selectorType = SelectorType.TIMESELECTOR,
                    content = selectedTimeTextWakeUp,
                    isClicked = isClickedWakeUp,
                    onClickedChange = {
                        currentActiveSelector = SETTIME.WAKEUP
                        showTimePickerBottomSheet = true
                    },
                )
            }
            Spacer(Modifier.height(24.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.ic__winddown),
                    contentDescription = null,
                    tint = darkModeColors.white,
                )
                Spacer(Modifier.width(6.dp))
                Text(
                    text = stringResource(id = R.string.onboarding1_wind_down),
                    style = defaultMementoTypography.title_b_22,
                    color = darkModeColors.white,
                )
                Spacer(
                    modifier =
                        Modifier
                            .weight(1f),
                )
                MementoChipSelector(
                    selectorType = SelectorType.TIMESELECTOR,
                    content = selectedTimeTextWindDown,
                    isClicked = isClickedWindDown,
                    onClickedChange = {
                        currentActiveSelector = SETTIME.WINDDOWN
                        showTimePickerBottomSheet = true
                    },
                )
            }
            MementoBottomSheet(
                isOpenBottomSheet = showTimePickerBottomSheet,
                content = {
                    MementoTimePicker(
                        onTimeSelected = { selectedTime ->
                            when (currentActiveSelector) {
                                SETTIME.WAKEUP -> {
                                    selectedTimeTextWakeUp = selectedTime
                                    isClickedWakeUp = true
                                    isClickedWindDown = false
                                    isSelectedWakeUp = true
                                }

                                SETTIME.WINDDOWN -> {
                                    selectedTimeTextWindDown = selectedTime
                                    isClickedWindDown = true
                                    isClickedWakeUp = false
                                    isSelectedWindDown = true
                                }

                                else -> Unit
                            }
                        },
                    )
                },
                sheetState = sheetTimePickerState,
                onDismissRequest = {
                    showTimePickerBottomSheet = false
                    isClickedWakeUp = false
                    isClickedWindDown = false
                },
            )
        }
        Spacer(Modifier.weight(1f))
        OnboardingBottomButton(
            content = R.string.onboarding_next,
            isSelected = isSelectedWakeUp && isSelectedWindDown,
            onSelected = {
                if (isSelectedWakeUp && isSelectedWindDown) navigateToOnboardingScreen2()
            },
        )
    }
}

@Preview
@Composable
fun OnboardingScreen1Prev() {
    OnboardingScreen1(
        {},
    )
}
