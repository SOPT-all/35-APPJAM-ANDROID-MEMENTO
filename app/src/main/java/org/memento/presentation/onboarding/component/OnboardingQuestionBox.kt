package org.memento.presentation.onboarding.component

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.memento.R
import org.memento.presentation.type.YesNoButtonType
import org.memento.ui.theme.darkModeColors
import org.memento.ui.theme.defaultMementoTypography

@Composable
fun OnboardingQuestionBox(
    @StringRes question: Int,
    selectedOption: YesNoButtonType?,
    onOptionSelected: (YesNoButtonType) -> Unit,
    modifier: Modifier = Modifier,
) {
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
            Row {
                OnboardingYesNoButton(
                    content = stringResource(id = R.string.onboarding_yes),
                    isSelected = selectedOption == YesNoButtonType.YES,
                    onSelected = {
                        onOptionSelected(YesNoButtonType.YES)
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
                        onOptionSelected(YesNoButtonType.NO)
                    },
                    modifier =
                        Modifier
                            .weight(1f),
                )
            }
        }
    }
}
