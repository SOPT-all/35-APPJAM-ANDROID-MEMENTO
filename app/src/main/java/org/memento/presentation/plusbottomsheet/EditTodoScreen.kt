package org.memento.presentation.plusbottomsheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.memento.R
import org.memento.presentation.component.MementoPriorityTile
import org.memento.presentation.component.MementoUrgentChip
import org.memento.presentation.type.PriorityTagType
import org.memento.presentation.util.noRippleClickable
import org.memento.ui.theme.MementoTheme
import org.memento.ui.theme.darkModeColors
import org.memento.ui.theme.defaultMementoTypography
import org.memento.ui.theme.mementoColors

@Composable
fun EditToDoScreen(
    onClose: () -> Unit,
    onDone: () -> Unit,
    selectedType: PriorityTagType,
    onTypeSelected: (PriorityTagType) -> Unit,
    modifier: Modifier = Modifier,
    isChecked: Boolean = false,
    onCheckedChange: (Boolean) -> Unit = {},
    content: String,
    deadline: String = "Today",
    tag: String = "SOPT",
    tagColor: String,
) {
    Column(
        modifier =
            Modifier
                .fillMaxSize(),
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = "Cancle",
                modifier =
                    Modifier
                        .padding(horizontal = 18.dp, vertical = 12.dp)
                        .noRippleClickable { onClose() },
                style =
                    MementoTheme.typography.body_r_16.copy(
                        color = mementoColors.red,
                    ),
            )
            Text(
                text = "Done",
                modifier =
                    Modifier
                        .padding(horizontal = 18.dp, vertical = 12.dp)
                        .noRippleClickable { onDone() },
                style =
                    MementoTheme.typography.body_r_16.copy(
                        color = darkModeColors.white,
                    ),
            )
        }

        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(end = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Spacer(modifier = Modifier.width(24.dp))

                Checkbox(
                    checked = isChecked,
                    onCheckedChange = onCheckedChange,
                    colors =
                        CheckboxDefaults.colors(
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
                    maxLines = 2,
                    textDecoration = if (isChecked) TextDecoration.LineThrough else null,
                )
            }
            MementoUrgentChip(selectedType = selectedType)
        }

        Column(
            modifier =
                Modifier
                    .wrapContentSize()
                    .padding(start = 75.dp),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Deadline",
                    style = MementoTheme.typography.detail_r_12,
                    color = darkModeColors.gray05,
                )
                Spacer(modifier = Modifier.width(14.dp))

                Icon(
                    painter = painterResource(id = R.drawable.ic_deadline),
                    contentDescription = "Deadline Icon",
                    tint = darkModeColors.gray05,
                )
                Text(
                    text = deadline,
                    style = MementoTheme.typography.detail_r_12,
                    color = darkModeColors.gray05,
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 6.dp),
            ) {
                Text(
                    text = "Tag",
                    style = MementoTheme.typography.detail_r_12,
                    color = darkModeColors.gray05,
                )
                Spacer(modifier = Modifier.width(40.dp))

                Icon(
                    painter = painterResource(id = R.drawable.ic_deadline),
                    contentDescription = "Tag Icon",
                    tint = Color.Blue,
                )
                Text(
                    text = tag,
                    style = MementoTheme.typography.detail_r_12,
                    color = darkModeColors.gray05,
                )
            }
        }

        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp)
                    .padding(end = 20.dp, start = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Urgency",
                style = MementoTheme.typography.detail_r_12,
                color = darkModeColors.gray04,
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
            ) {
                Text(
                    text = "Importance",
                    style = MementoTheme.typography.detail_r_12,
                    color = darkModeColors.gray04,
                    modifier = Modifier.rotateVertically(false),
                )

                MementoPriorityTile(
                    selectedType = PriorityTagType.Low,
                    onTypeSelected = { onTypeSelected },
                )
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Select an area,",
                style = MementoTheme.typography.body_r_16,
                color = darkModeColors.gray07,
                modifier = Modifier.padding(top = 15.dp),
            )
            Text(
                text = "or let AI do it for you.",
                style = MementoTheme.typography.body_r_16,
                color = darkModeColors.gray07,
            )
        }
    }
}

@Preview
@Composable
private fun ScreenPreview() {
    EditToDoScreen(onClose = {}, onDone = {}, selectedType = PriorityTagType.Low, onTypeSelected = {}, content = "UI", tagColor = "")
}
