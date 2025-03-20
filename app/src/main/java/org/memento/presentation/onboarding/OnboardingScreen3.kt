package org.memento.presentation.onboarding

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import okhttp3.internal.immutableListOf
import org.memento.R
import org.memento.presentation.onboarding.component.OnboardingBottomButton
import org.memento.presentation.onboarding.component.OnboardingQuestionBox
import org.memento.presentation.onboarding.component.OnboardingTopAppBar
import org.memento.presentation.onboarding.viewmodel.OnboardingViewModel
import org.memento.presentation.type.OnboardingTopType
import org.memento.presentation.util.toYesNoType

@Composable
fun OnboardingScreen3(
    viewModel: OnboardingViewModel = hiltViewModel(),
    navigateToOnboardingScreen4: () -> Unit,
    popBackStack: () -> Unit,
) {
    val questionList =
        immutableListOf(
            R.string.onboarding3_q1,
            R.string.onboarding3_q2,
            R.string.onboarding3_q3,
            R.string.onboarding3_q4,
        )

    val isStressedUnorganizedSchedule by viewModel.isStressedUnorganizedSchedule.collectAsStateWithLifecycle()
    val isForgetImportantThings by viewModel.isForgetImportantThings.collectAsStateWithLifecycle()
    val isPreferReminder by viewModel.isPreferReminder.collectAsStateWithLifecycle()
    val isImportantBreaks by viewModel.isImportantBreaks.collectAsStateWithLifecycle()

    val selectedOptions =
        listOf(
            isStressedUnorganizedSchedule,
            isForgetImportantThings,
            isPreferReminder,
            isImportantBreaks,
        )

    val isAllSelected = selectedOptions.all { it != null }

    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 10.dp),
    ) {
        Column {
            OnboardingTopAppBar(
                type = OnboardingTopType.PAGE3,
                onBackClick = popBackStack,
                onSkipClick = {
                    viewModel.fetchUserInfoUpdate()
                    navigateToOnboardingScreen4()
                },
            )
            Spacer(Modifier.weight(20 / 708f))
            LazyColumn(
                modifier =
                    Modifier
                        .weight(1f)
                        .padding(horizontal = 14.dp),
            ) {
                itemsIndexed(questionList, key = { index, _ -> index }) { index, item ->
                    OnboardingQuestionBox(
                        question = item,
                        selectedOption = selectedOptions[index].toYesNoType(),
                        onOptionSelected = { newSelection ->
                            viewModel.updateQuestionAnswer(index, newSelection)
                        },
                    )
                    Spacer(Modifier.height(18.dp))
                }
            }
            OnboardingBottomButton(
                content = R.string.onboarding_next,
                isSelected = isAllSelected,
                onSelected = { if (isAllSelected) navigateToOnboardingScreen4() },
                modifier =
                    Modifier
                        .fillMaxWidth(),
            )
        }
    }
}
