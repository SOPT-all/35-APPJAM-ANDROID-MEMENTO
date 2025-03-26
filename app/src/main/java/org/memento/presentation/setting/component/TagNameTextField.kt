package org.memento.presentation.setting.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.memento.R
import org.memento.presentation.util.isKorean
import org.memento.presentation.util.noRippleClickable
import org.memento.ui.theme.darkModeColors
import org.memento.ui.theme.defaultMementoTypography

/**
 * @param text 외부 Screen 에서 현재 TextField 에 입력된 값
 * @param onTextValueChange 외부 Screen 에서 사용자가 입력할 때 값이 변경되는 이벤트
 * @param modifier 외부 Screen 에서 padding 등을 적용할 수 있도록 하는 modifier
 */

@Composable
fun TagNameTextField(
    text: String,
    onTextValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            Modifier
                .then(modifier),
    ) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            BasicTextField(
                value = text,
                onValueChange = { newText ->
                    val koreanCharCount = newText.count { it.isKorean() }
                    val isValid =
                        if (koreanCharCount > 0) {
                            newText.length <= 17
                        } else {
                            newText.length <= 35
                        }

                    if (isValid) {
                        onTextValueChange(newText)
                    }
                },
                textStyle = defaultMementoTypography.body_r_14.copy(color = darkModeColors.gray03),
                singleLine = true,
                modifier =
                    Modifier
                        .padding(horizontal = 4.dp)
                        .weight(1f),
            )
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_text_eraser),
                contentDescription = stringResource(id = R.string.tag_text_field_clear),
                tint = darkModeColors.gray05,
                modifier =
                    Modifier
                        .noRippleClickable { onTextValueChange("") },
            )
        }
        Spacer(Modifier.height(5.dp))
        Divider(
            color = darkModeColors.gray05,
            thickness = 1.dp,
        )
    }
}

@Preview
@Composable
fun PreviewTextField() {
    var text by remember { mutableStateOf("") }
    TagNameTextField(
        text = text,
        onTextValueChange = { text = it },
    )
}
