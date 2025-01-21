package org.memento.presentation.component

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import org.memento.R
import org.memento.ui.theme.darkModeColors
import org.memento.ui.theme.mementoColors

@Composable
fun MementoAlertDialog(
    @StringRes content: Int,
    @StringRes leftButtonText: Int,
    @StringRes rightButtonText: Int,
    onLeftButtonClick: () -> Unit,
    onRightButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AlertDialog(
        onDismissRequest = { onLeftButtonClick() },
        text = {
            Text(
                text = stringResource(id = content),
                color = darkModeColors.gray05,
            )
        },
        dismissButton = {
            TextButton(
                onClick = onLeftButtonClick,
            ) {
                Text(
                    text = stringResource(id = leftButtonText),
                    color = darkModeColors.gray05,
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = onRightButtonClick,
            ) {
                Text(
                    text = stringResource(id = rightButtonText),
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

@Composable
fun DeleteDialogTestScreen() {
    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(onClick = { showDialog = true }) {
            Text(text = stringResource(id = R.string.alert_delete))
        }

        if (showDialog) {
            MementoAlertDialog(
                content = R.string.alert_delete,
                leftButtonText = R.string.alert_cancel_button,
                rightButtonText = R.string.alert_delete_button,
                onLeftButtonClick = { showDialog = false },
                onRightButtonClick = {
                    showDialog = false
                },
            )
        }
    }
}
