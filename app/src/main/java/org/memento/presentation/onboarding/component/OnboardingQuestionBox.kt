package org.memento.presentation.onboarding.component

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.memento.R
import org.memento.presentation.type.YesNoButtonType
import org.memento.ui.theme.darkModeColors
import org.memento.ui.theme.defaultMementoTypography

@Composable
fun OnboardingQuestionBox(
    @StringRes question: Int,
    modifier: Modifier = Modifier,
) {
    var selectedOption by remember { mutableStateOf<YesNoButtonType?>(null) }

    Box(
        modifier =
            Modifier
                .then(modifier)
                .background(
                    color = darkModeColors.gray10,
                    shape = RoundedCornerShape(2.dp),
                ),
    ) {
        Column(
            modifier =
                Modifier
                    .padding(horizontal = 13.dp, vertical = 14.dp),
        ) {
            Text(
                text = stringResource(id = question),
                style = defaultMementoTypography.body_r_16,
                color = darkModeColors.white,
            )
            Spacer(Modifier.height(22.dp))
            Row(
                modifier = Modifier,
            ) {
                OnboardingYesNoButton(
                    content = stringResource(id = R.string.onboarding_yes),
                    isSelected = selectedOption == YesNoButtonType.YES,
                    onSelected = {
                        selectedOption = YesNoButtonType.YES
                    },
                    modifier =
                        Modifier
                            .weight(1f),
                )
                Spacer(Modifier.width(10.dp))
                OnboardingYesNoButton(
                    content = stringResource(id = R.string.onboarding_no),
                    isSelected = selectedOption == YesNoButtonType.NO,
                    onSelected = {
                        selectedOption = YesNoButtonType.NO
                    },
                    modifier =
                        Modifier
                            .weight(1f),
                )
            }
        }
    }
}

@Preview
@Composable
fun OnboardingQuestionBoxPreview() {
    Box(
        modifier =
            Modifier
                .fillMaxSize(),
    ) {
        OnboardingQuestionBox(
            question = R.string.onboarding3_q1,
            modifier = Modifier.padding(horizontal = 30.dp),
        )
    }
}
