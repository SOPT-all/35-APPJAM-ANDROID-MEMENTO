package org.memento.presentation.onboarding

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import okhttp3.internal.immutableListOf
import org.memento.R
import org.memento.presentation.onboarding.component.CheckboxWithTextField
import org.memento.presentation.onboarding.component.OnboardingBottomButton
import org.memento.presentation.onboarding.component.OnboardingTopAppBar
import org.memento.presentation.onboarding.component.RoundCheckboxWithText
import org.memento.presentation.type.OnboardingTopType

@Composable
fun OnboardingScreen2(
    navigateToOnboardingScreen3: () -> Unit,
    navigateToOnboardingScreen4: () -> Unit,
    popBackStack: () -> Unit,
) {
    val jobItems =
        immutableListOf(
            R.string.onboarding2_tech,
            R.string.onboarding2_data,
            R.string.onboarding2_design,
            R.string.onboarding2_business,
            R.string.onboarding2_edu,
            R.string.onboarding2_health,
            R.string.onboarding2_free,
            R.string.onboarding2_service,
            R.string.onboarding2_engineer,
        )

    var selectedIndex by remember { mutableStateOf<Int?>(null) }
    var textFieldValue by remember { mutableStateOf("") }
    var isCheckedTextField by remember { mutableStateOf(false) }

    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 10.dp),
    ) {
        Column {
            OnboardingTopAppBar(
                type = OnboardingTopType.PAGE2,
                onSkipClick = { navigateToOnboardingScreen4() },
                onBackClick = { popBackStack() },
            )
            Spacer(Modifier.height(20.dp))
            LazyColumn {
                itemsIndexed(jobItems, key = { index, _ -> index }) { index, item ->
                    RoundCheckboxWithText(
                        content = item,
                        isChecked = selectedIndex == index,
                        onCheckedChange = { isChecked ->
                            selectedIndex = if (isChecked) index else null
                            isCheckedTextField = false
                        },
                    )
                }
                item(key = R.string.onboarding2_other) {
                    CheckboxWithTextField(
                        isChecked = isCheckedTextField,
                        onCheckedChange = {
                            isCheckedTextField = it
                            if (it) selectedIndex = null
                        },
                        text = textFieldValue,
                        onTextChange = { textFieldValue = it },
                        placeholder = stringResource(id = R.string.onboarding2_placeholder),
                        modifier =
                            Modifier
                                .padding(bottom = 16.dp),
                    )
                }
            }
            Spacer(Modifier.weight(1f))
        }
        OnboardingBottomButton(
            content = R.string.onboarding_next,
            isSelected = selectedIndex != null || isCheckedTextField,
            onSelected = {
                if (selectedIndex != null || isCheckedTextField) navigateToOnboardingScreen3()
            },
            Modifier
                .align(androidx.compose.ui.Alignment.BottomCenter)
                .padding(bottom = 10.dp),
        )
    }
}
