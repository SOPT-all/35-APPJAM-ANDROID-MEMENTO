package org.memento.presentation.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.memento.ui.theme.darkModeColors
import org.memento.ui.theme.defaultMementoTypography

@Composable
fun MementoTodoCheckBox(
    content: String,
    isChecked: Boolean = false,
    onCheckedChange: (Boolean) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CompositionLocalProvider(LocalMinimumInteractiveComponentSize provides Dp.Unspecified) {
            Checkbox(
                checked = isChecked,
                onCheckedChange = onCheckedChange,
                colors =
                    CheckboxDefaults.colors(
                        uncheckedColor = darkModeColors.gray05,
                        checkedColor = darkModeColors.gray05,
                        checkmarkColor = darkModeColors.black,
                    ),
                modifier = Modifier.padding(end = 10.dp),
            )
        }

        Text(
            text = content,
            style = defaultMementoTypography.body_b_16,
            color = darkModeColors.white,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            textDecoration = if (isChecked) TextDecoration.LineThrough else null,
        )
    }
}

@Preview
@Composable
fun MementoTodoCheckBoxPreview() {
    MementoTodoCheckBox(
        content = "title",
    )
}
