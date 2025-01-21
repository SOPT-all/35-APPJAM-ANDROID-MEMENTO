package org.memento.presentation.component

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import org.memento.ui.theme.darkModeColors
import org.memento.ui.theme.mementoColors

@Composable
fun MementoAlertDialog(
    @StringRes content: Int,
    @StringRes confirmButtonText: Int,
    @StringRes dismissButtonText: Int,
    onConfirmButtonClick: () -> Unit,
    onDismissButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AlertDialog(
        onDismissRequest = { onConfirmButtonClick() },
        text = {
            Text(
                text = stringResource(id = content),
                color = darkModeColors.gray05,
            )
        },
        dismissButton = {
            TextButton(
                onClick = onConfirmButtonClick,
            ) {
                Text(
                    text = stringResource(id = confirmButtonText),
                    color = darkModeColors.gray05,
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = onDismissButtonClick,
            ) {
                Text(
                    text = stringResource(id = dismissButtonText),
                    color = mementoColors.red,
                )
            }
        },
        containerColor = darkModeColors.gray09,
        modifier =
            Modifier
                .then(modifier)
                .fillMaxWidth(),
    )
}
