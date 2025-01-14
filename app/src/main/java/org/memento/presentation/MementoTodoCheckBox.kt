package org.memento.presentation

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.Checkbox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import org.memento.ui.theme.darkModeColors
import org.memento.ui.theme.defaultMementoTypography

@Composable
fun MementoTodoCheckBox(
    content: String,
    isChecked: Boolean = false,
    onCheckedChange: (Boolean) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(
                uncheckedColor = darkModeColors.gray05,
                checkedColor = darkModeColors.gray05,
                checkmarkColor = darkModeColors.black,
            ),
        )

        Text(
            text = content,
            style = defaultMementoTypography.body_b_16,
            color = darkModeColors.white,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            textDecoration = if (isChecked) TextDecoration.LineThrough else null
        )
    }
}


@Preview
@Composable
fun MementoTodoCheckBoxPreview() {
    MementoTodoCheckBox(
        content = "title"
    )
}