package org.memento.presentation.plusbottomsheet

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.memento.R
import org.memento.presentation.component.MementoBottomSheet
import org.memento.presentation.component.MementoChipSelector
import org.memento.presentation.component.TagSelectorContent
import org.memento.presentation.type.SelectorType
import org.memento.presentation.util.noRippleClickable
import org.memento.ui.theme.MementoTheme
import org.memento.ui.theme.darkModeColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddToDoTagScreen(
    onClose: () -> Unit,
    onDone: (String) -> Unit,
) {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(color = darkModeColors.gray10),
    ) {
        val sheetTagState = rememberModalBottomSheetState()
        var showTagBottomSheet by remember { mutableStateOf(false) }

        var selectedTagText by remember { mutableStateOf("Untitled") }
        var selectedTagColor by remember { mutableStateOf("#F0F0F3") }

        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Image(
                painter = painterResource(R.drawable.ic_back),
                contentDescription = "뒤로가기 버튼",
            )
            Text(
                text = "Done",
                modifier =
                    Modifier.padding(horizontal = 18.dp, vertical = 12.dp)
                        .noRippleClickable {
                            onDone(selectedTagColor)
                        },
                style =
                    MementoTheme.typography.body_r_16.copy(
                        color = darkModeColors.gray07,
                    ),
            )
        }

        Column(
            modifier =
                Modifier
                    .weight(1f)
                    .padding(horizontal = 12.dp, vertical = 26.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Deadline",
                    style =
                        MementoTheme.typography.body_r_16.copy(
                            color = darkModeColors.gray05,
                        ),
                )

                Spacer(modifier = Modifier.weight(1f))

                MementoChipSelector(
                    selectorType = SelectorType.TAG,
                    isClicked = true,
                    onClickedChange = {
                        showTagBottomSheet = true
                    },
                    content = selectedTagText,
                    tagColor = selectedTagColor,
                )
            }

            MementoBottomSheet(
                isOpenBottomSheet = showTagBottomSheet,
                content = {
                    TagSelectorContent(
                        onTagSelected = { color, tag ->
                            selectedTagText = tag
                            selectedTagColor = color
                        },
                    )
                },
                sheetState = sheetTagState,
                onConfirm = {
                    showTagBottomSheet = false
                },
            )
        }
    }
}

@Preview
@Composable
fun AddToDoTagScreenPreview() {
    AddToDoTagScreen(
        onClose = { },
        onDone = { },
    )
}
