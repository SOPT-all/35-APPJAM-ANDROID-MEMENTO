package org.memento.presentation.onboarding

import android.util.Log
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import okhttp3.internal.immutableListOf
import org.memento.R
import org.memento.presentation.onboarding.component.CheckboxWithTextField
import org.memento.presentation.onboarding.component.OnboardingBottomButton
import org.memento.presentation.onboarding.component.OnboardingTopAppBar
import org.memento.presentation.onboarding.component.RoundCheckboxWithText
import org.memento.presentation.onboarding.viewmodel.OnboardingViewModel
import org.memento.presentation.type.OnboardingTopType

data class JobItem(val jobCode: String, val jobName: String)

@Composable
fun OnboardingScreen2(
    viewModel: OnboardingViewModel = hiltViewModel(),
    navigateToOnboardingScreen3: () -> Unit,
    navigateToOnboardingScreen4: () -> Unit,
    popBackStack: () -> Unit,
) {
    val jobItems =
        immutableListOf(
            JobItem("TECHNOLOGY", stringResource(R.string.onboarding2_tech)),
            JobItem("DATA_ANALYTICS", stringResource(R.string.onboarding2_data)),
            JobItem("DESIGN_CREATIVITY", stringResource(R.string.onboarding2_design)),
            JobItem("BUSINESS_MANAGEMENT", stringResource(R.string.onboarding2_business)),
            JobItem("EDUCATION_TRAINING", stringResource(R.string.onboarding2_edu)),
            JobItem("HEALTHCARE_WELLNESS", stringResource(R.string.onboarding2_health)),
            JobItem("FREELANCE_SELF_EMPLOYMENT", stringResource(R.string.onboarding2_free)),
            JobItem("SERVICE_HOSPITALITY", stringResource(R.string.onboarding2_service)),
            JobItem("ENGINEERING_MANUFACTURING", stringResource(R.string.onboarding2_engineer)),
        )

    var selectedIndex by remember { mutableStateOf<Int?>(null) }
    var textFieldValue by remember { mutableStateOf<String>("") }

    val job by viewModel.job.collectAsStateWithLifecycle()
    val jobOtherDetail by viewModel.jobOtherDetail.collectAsStateWithLifecycle()

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
                onSkipClick = {
                    viewModel.fetchUserInfoUpdate()
                    navigateToOnboardingScreen4()
                },
                onBackClick = { popBackStack() },
            )
            Spacer(Modifier.height(20.dp))
            LazyColumn(
                modifier =
                    Modifier
                        .weight(1f),
            ) {
                itemsIndexed(jobItems, key = { index, _ -> index }) { index, item ->
                    RoundCheckboxWithText(
                        content = item.jobName,
                        isChecked = selectedIndex == index,
                        onCheckedChange = { isChecked ->
                            viewModel.setJob(newjob = item.jobCode)
                            viewModel.setJobOtherDetail("")
                            selectedIndex = if (isChecked) index else null
                            isCheckedTextField = false
                            textFieldValue = ""
                            Log.e("text", item.jobName)
                        },
                    )
                }
                item(key = R.string.onboarding2_other) {
                    CheckboxWithTextField(
                        isChecked = (isCheckedTextField == true),
                        onCheckedChange = { isChecked ->
                            viewModel.setJob("OTHER")
                            viewModel.setJobOtherDetail(textFieldValue)
                            isCheckedTextField = isChecked
                            if (isChecked) selectedIndex = null
                            Log.e("textfield", isCheckedTextField.toString())
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
            OnboardingBottomButton(
                content = R.string.onboarding_next,
                isSelected = selectedIndex != null || isCheckedTextField,
                onSelected = {
                    if (selectedIndex != null || isCheckedTextField) navigateToOnboardingScreen3()
                },
                Modifier
                    .padding(bottom = 10.dp),
            )
        }
    }
}
