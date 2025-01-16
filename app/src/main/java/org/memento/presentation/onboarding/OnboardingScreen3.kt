package org.memento.presentation.onboarding

import android.text.Layout.Alignment
import androidx.compose.foundation.background
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import okhttp3.internal.immutableListOf
import org.memento.R
import org.memento.presentation.onboarding.component.OnboardingBottomButton
import org.memento.presentation.onboarding.component.OnboardingQuestionBox
import org.memento.presentation.onboarding.component.OnboardingTopAppBar
import org.memento.presentation.type.OnboardingTopType
import org.memento.presentation.type.YesNoButtonType
import org.memento.ui.theme.darkModeColors

@Composable
fun OnboardingScreen3() {
    val questionList =
        immutableListOf(
            R.string.onboarding3_q1,
            R.string.onboarding3_q2,
            R.string.onboarding3_q3,
            R.string.onboarding3_q4,
        )
    var selectedOptions by remember { mutableStateOf(List<YesNoButtonType?>(questionList.size) { null }) }
    val isAllSelected = selectedOptions.all { it != null }

    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
    ) {
        Column {
            OnboardingTopAppBar(
                type = OnboardingTopType.PAGE3,
                onBackClick = {},
                onSkipClick = {},
            )
            Spacer(Modifier.height(20.dp))

            LazyColumn(
                modifier =
                    Modifier
                        .padding(horizontal = 14.dp),
            ) {
                itemsIndexed(questionList, key = { index, _ -> index }) { index, item ->
                    OnboardingQuestionBox(
                        question = item,
                        selectedOption = selectedOptions[index],
                        onOptionSelected = { newSelection ->
                            selectedOptions =
                                selectedOptions.toMutableList().apply {
                                    set(index, newSelection)
                                }
                        },
                    )
                    Spacer(Modifier.height(18.dp))
                }
                item {
                    Box(
                        modifier =
                            Modifier
                                .height(50.dp)
                                .fillMaxWidth()
                                .background(color = darkModeColors.black),
                    )
                }
            }
        }
        OnboardingBottomButton(
            content = R.string.onboarding_next,
            isSelected = isAllSelected,
            onSelected = {},
            modifier =
                Modifier
                    .align(androidx.compose.ui.Alignment.BottomCenter)
                    .padding(bottom = 10.dp),
        )
    }
}

@Preview
@Composable
fun OnboardingScreen3Preview() {
    OnboardingScreen3()
}
