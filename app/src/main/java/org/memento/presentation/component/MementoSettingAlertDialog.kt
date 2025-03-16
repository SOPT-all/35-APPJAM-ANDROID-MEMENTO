package org.memento.presentation.component

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.memento.R
import org.memento.presentation.util.noRippleClickable
import org.memento.ui.theme.MementoTheme
import org.memento.ui.theme.darkModeColors
import org.memento.ui.theme.mementoColors

@Composable
fun MementoSettingAlertDialog(
    @StringRes content: Int,
    @StringRes subContent: Int? = null,
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
                text = buildAnnotatedString {
                    append(AnnotatedString(text = stringResource(id = content)))
                    subContent?.let {
                        append("\n")
                        append(
                            AnnotatedString(
                                text = stringResource(id = subContent),
                                spanStyle = SpanStyle(
                                    color = darkModeColors.gray07
                                )
                            )
                        )
                    }
                },
                color = darkModeColors.gray03,
                style = MementoTheme.typography.body_r_14,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        confirmButton = {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            color = darkModeColors.gray08,
                            shape = RoundedCornerShape(size = 4.dp)
                        )
                        .padding(vertical = 8.dp)
                        .noRippleClickable {
                            onLeftButtonClick()
                        },
                ) {
                    Text(
                        text = stringResource(id = leftButtonText),
                        color = darkModeColors.gray05,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            color = mementoColors.red,
                            shape = RoundedCornerShape(size = 4.dp)
                        )
                        .padding(vertical = 8.dp)
                        .noRippleClickable {
                            onRightButtonClick()
                        }
                ) {
                    Text(
                        text = stringResource(id = rightButtonText),
                        color = darkModeColors.black,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }
        },
        containerColor = darkModeColors.gray09,
        modifier =
        Modifier
            .then(modifier)
            .fillMaxWidth(),
    )
}

@Preview
@Composable
fun MementoSettingAlertDialogPreview() {
    MementoSettingAlertDialog(
        content = R.string.alert_setting_delete_account_title,
        subContent = R.string.alert_setting_delete_account_subtitle,
        leftButtonText = R.string.alert_cancel_button,
        rightButtonText = R.string.alert_delete_button,
        onLeftButtonClick = { },
        onRightButtonClick = { },
    )
}
