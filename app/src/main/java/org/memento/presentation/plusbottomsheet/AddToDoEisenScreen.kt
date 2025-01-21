package org.memento.presentation.plusbottomsheet

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import org.memento.R
import org.memento.presentation.component.MementoPriorityTile
import org.memento.presentation.component.MementoUrgentChip
import org.memento.presentation.type.PriorityTagType
import org.memento.presentation.util.noRippleClickable
import org.memento.ui.theme.MementoTheme
import org.memento.ui.theme.darkModeColors

@Composable
fun AddToDoEisenScreen(
    viewModel: AddToDoViewModel = hiltViewModel(),
    onClose: () -> Unit,
    onDone: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val tempPriorityType by viewModel.tempPriorityType.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        Row(
            modifier =
            Modifier
                .fillMaxWidth()
                .padding(top = 6.dp, start = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Image(
                painter = painterResource(R.drawable.ic_back),
                contentDescription = "",
                modifier = modifier.noRippleClickable {
                    onClose()
                }
            )
            Text(
                text = "Done",
                modifier =
                Modifier
                    .padding(horizontal = 18.dp, vertical = 12.dp)
                    .noRippleClickable {
                        viewModel.savePriorityType()
                        onDone()
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
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
            ) {
                MementoUrgentChip(selectedType = tempPriorityType)
            }
            Text(
                text = "Urgency",
                style = MementoTheme.typography.detail_r_12,
                color = darkModeColors.gray04,
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(end = 10.dp),
            ) {
                Text(
                    text = "Importance",
                    style = MementoTheme.typography.detail_r_12,
                    modifier = Modifier.rotateVertically(false),
                    color = darkModeColors.white,
                )

                MementoPriorityTile(
                    selectedType = tempPriorityType,
                    onTypeSelected = { viewModel.updatePriorityType(it) }
                )
            }
            Text(
                text = "Select an area,",
                style = MementoTheme.typography.body_r_16,
                color = darkModeColors.gray07,
                modifier = Modifier.padding(top = 15.dp),
            )
            Text(
                text =
                    "or let AI do it for you.",
                style = MementoTheme.typography.body_r_16,
                color = darkModeColors.gray07,
            )
        }
    }
}

fun Modifier.rotateVertically(clockwise: Boolean = true): Modifier {
    val rotate = rotate(if (clockwise) 90f else -90f)

    val adjustBounds =
        layout { measurable, constraints ->
            val placeable = measurable.measure(constraints)
            layout(placeable.height, placeable.width) {
                placeable.place(
                    x = -(placeable.width / 2 - placeable.height / 2),
                    y = -(placeable.height / 2 - placeable.width / 2),
                )
            }
        }
    return rotate then adjustBounds
}

@Preview
@Composable
private fun ScreenPreview() {
    AddToDoEisenScreen(onClose = {}, onDone = {})
}
