package org.memento.presentation.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.memento.R
import org.memento.presentation.setting.component.SettingAlertDialog
import org.memento.presentation.setting.component.SettingTopBar
import org.memento.presentation.setting.component.TagColorSelector
import org.memento.presentation.setting.component.TagNameTextField
import org.memento.presentation.type.SettingTopBarType
import org.memento.presentation.type.TagColorType
import org.memento.presentation.util.TagColor
import org.memento.presentation.util.TagColor.toHexCode
import org.memento.presentation.util.noRippleClickable
import org.memento.ui.theme.darkModeColors
import org.memento.ui.theme.defaultMementoTypography
import org.memento.ui.theme.mementoColors

@Composable
fun SettingEditTagScreen(
    modifier: Modifier = Modifier,
    tagId: Int,
    tagColor: String,
    tagName: String,
    settingViewModel: SettingViewModel = hiltViewModel(),
    onBack: () -> Unit,
    onDone: () -> Unit,
) {
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showExistDialog by remember { mutableStateOf(false) }

    var selectedColorName by remember {
        mutableStateOf(
            if (tagColor.isBlank()) {
                TagColorType.Red
            } else {
                TagColor.fromHexCode(tagColor)
            },
        )
    }

    val selectedColorCode = selectedColorName?.let { toHexCode(it) }
    var text by remember { mutableStateOf(tagName) }

    Column(
        modifier =
            Modifier
                .then(modifier)
                .fillMaxHeight()
                .padding(top = 16.dp),
    ) {
        SettingTopBar(
            type = SettingTopBarType.TAG,
            onBackClick = { onBack() },
            onDoneClick = {
                onDone()
                if (tagId == 0) {
                    settingViewModel.postTag(
                        name = text,
                        colorHex = selectedColorCode.toString(),
                    )
                } else {
                    settingViewModel.patchEditTag(
                        tagId = tagId,
                        name = text,
                        colorHex = selectedColorName.toString().uppercase(),
                    )
                }
            },
            isDoneVisible = true,
        )
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
                    .padding(start = 20.dp, top = 26.dp, end = 20.dp, bottom = 20.dp),
        ) {
            Column(
                modifier =
                    Modifier
                        .background(color = darkModeColors.gray10, shape = RoundedCornerShape(4.dp)),
            ) {
                Text(
                    text = stringResource(id = R.string.setting_edit_tag_name),
                    style = defaultMementoTypography.detail_r_12,
                    color = darkModeColors.gray06,
                    modifier =
                        Modifier
                            .padding(start = 16.dp, top = 12.dp),
                )
                TagNameTextField(
                    text = text,
                    onTextValueChange = { text = it },
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(start = 12.dp, top = 9.dp, end = 12.dp),
                )
                Spacer(Modifier.height(30.dp))
                Text(
                    text = stringResource(id = R.string.setting_edit_tag_color),
                    style = defaultMementoTypography.detail_r_12,
                    color = darkModeColors.gray06,
                    modifier =
                        Modifier
                            .padding(start = 16.dp),
                )
                Row(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 14.dp, bottom = 24.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    TagColorType.entries.forEach { color ->
                        TagColorSelector(
                            color = color,
                            isSelected = color == selectedColorName,
                            onClick = { selectedColorName = color },
                        )
                    }
                }
            }
            Spacer(Modifier.height(10.dp))
            Text(
                text = stringResource(id = R.string.setting_delete_tag),
                style = defaultMementoTypography.body_r_14,
                color = mementoColors.red,
                modifier =
                    Modifier
                        .padding(horizontal = 11.dp, vertical = 10.dp)
                        .noRippleClickable {
                            showDeleteDialog = true
                        },
            )
        }
    }

    if (showDeleteDialog) {
        SettingAlertDialog(
            content = R.string.setting_alert_delete_content,
            subContent = R.string.setting_alert_delete_message,
            leftButtonText = R.string.setting_alert_delete_leftButtonText,
            rightButtonText = R.string.setting_alert_delete_rightButtonText,
            onLeftButtonClick = { showDeleteDialog = false },
            onRightButtonClick = {
                showDeleteDialog = false
                // Todo : Delete API
                settingViewModel.deleteTag(tagId)
                // Todo : Exist 분기 처리 ->
                showExistDialog = true
                // Todo : 해당 뷰에서 나가지기
            },
        )
    }
    if (showExistDialog) {
        SettingAlertDialog(
            content = R.string.setting_alert_exist_message,
            leftButtonText = R.string.setting_alert_exist_buttonText,
            onLeftButtonClick = {
                showExistDialog = false
            },
        )
    }
}

@Preview
@Composable
private fun SettingEditTagScreenPrev() {
//    SettingEditTagScreen(onBack = {}, onDone = {})
}
