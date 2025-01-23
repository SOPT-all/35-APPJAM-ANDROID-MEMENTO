package org.memento.presentation.onboarding.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.memento.R
import org.memento.presentation.util.noRippleClickable
import org.memento.ui.theme.darkModeColors
import org.memento.ui.theme.defaultMementoTypography

@Composable
fun RoundCheckboxWithText(
    content: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .noRippleClickable {
                    onCheckedChange(!isChecked)
                }
                .then(modifier)
                .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RoundCheckbox(
            isChecked = isChecked,
            onCheckedChange = onCheckedChange,
        )
        Spacer(modifier = Modifier.width(14.dp))
        Text(
            text = content,
            style = defaultMementoTypography.body_b_14,
            color = if (isChecked) darkModeColors.white else darkModeColors.gray06,
            modifier =
                Modifier.noRippleClickable {
                    onCheckedChange(!isChecked)
                },
        )
    }
}

@Preview
@Composable
fun RoundCheckboxWithTextExample() {
    var isChecked by remember { mutableStateOf(false) }

    Row {
        RoundCheckboxWithText(
            content = stringResource(R.string.onboarding2_free),
            isChecked = isChecked,
            onCheckedChange = { isChecked = it },
        )
    }
}
