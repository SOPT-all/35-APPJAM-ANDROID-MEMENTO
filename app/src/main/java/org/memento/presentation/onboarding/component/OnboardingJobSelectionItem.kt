package org.memento.presentation.onboarding.component

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.memento.R
import org.memento.presentation.type.JobSelectionType
import org.memento.presentation.util.noRippleClickable

@Composable
fun OnboardingJobSelectionItem(
    type: JobSelectionType,
    @StringRes content: Int? = R.string.onboarding2_placeholder,
    isSelected: Boolean = false,
    onSelectedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier =
        Modifier
            .then(modifier)
            .fillMaxWidth()
            .noRippleClickable { },
    ) {
        when (type) {
            JobSelectionType.Selection -> {
            }

            JobSelectionType.Input -> {
                var isChecked by remember { mutableStateOf(false) }
                var text by remember { mutableStateOf("") }
                CheckboxWithTextField(
                    isChecked = isChecked,
                    onCheckedChange = { isChecked = it },
                    text = text,
                    onTextChange = { text = it },
                    placeholder = "",
                    modifier = Modifier.padding(16.dp),
                )
            }
        }
    }
}

@Preview
@Composable
private fun OnboardingJobSelectionItemPreview() {
    val isClicked by remember { mutableStateOf(false) }
    Column {
        Spacer(Modifier.height(5.dp))
        OnboardingJobSelectionItem(
            type = JobSelectionType.Selection,
            content = R.string.onboarding3_q1,
            isSelected = isClicked,
            onSelectedChange = {},
        )
        Spacer(Modifier.height(5.dp))
        OnboardingJobSelectionItem(
            type = JobSelectionType.Input,
            isSelected = isClicked,
            onSelectedChange = {},
        )
    }
}
