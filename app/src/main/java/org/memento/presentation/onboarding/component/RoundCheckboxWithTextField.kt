package org.memento.presentation.onboarding.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.memento.presentation.util.noRippleClickable
import org.memento.ui.theme.darkModeColors
import org.memento.ui.theme.defaultMementoTypography

@Composable
fun CheckboxWithTextField(
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    text: String,
    onTextChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
) {
    val isActive = text.isNotEmpty()
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier =
        Modifier
            .then(modifier)
            .padding(16.dp),
    ) {
        RoundCheckbox(
            isChecked = isActive || isChecked,
            onCheckedChange = {
                onCheckedChange(it)
                if (!it) onTextChange("")
            },
        )
        Spacer(modifier = Modifier.width(15.dp))
        Box(
            modifier =
            Modifier
                .weight(1f)
                .noRippleClickable {
                    onCheckedChange(true)
                },
        ) {
            BasicTextField(
                value = text,
                onValueChange = { newText ->
                    onTextChange(newText)
                    if (newText.isNotEmpty()) {
                        onCheckedChange(true)
                    }
                },
                singleLine = true,
                textStyle =
                LocalTextStyle.current.copy(
                    color = if (isActive) darkModeColors.white else darkModeColors.gray08,
                ),
                decorationBox = { innerTextField ->
                    Column {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            if (text.isEmpty()) {
                                Text(
                                    text = placeholder,
                                    color = darkModeColors.gray08,
                                    style = defaultMementoTypography.body_b_14,
                                )
                            }
                            innerTextField()
                        }
                        Box(
                            modifier =
                            Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(
                                    color = if (isActive) darkModeColors.white else darkModeColors.gray08,
                                ),
                        )
                    }
                },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CheckboxWithTextFieldExample() {
    var isChecked by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf("") }

    CheckboxWithTextField(
        isChecked = isChecked,
        onCheckedChange = { isChecked = it },
        text = text,
        onTextChange = { text = it },
        placeholder = "Enter your task",
        modifier = Modifier.padding(16.dp),
    )
}
