package org.memento.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.memento.presentation.type.PriorityTagType
import org.memento.ui.theme.MementoTheme

@Composable
fun MementoUrgentChip(
    selectedType: PriorityTagType,
) {
    Row(
        modifier = Modifier
            .background(
                color = selectedType.backgroundColor,
                shape = RoundedCornerShape(2.dp)
            )
            .border(
                width = 0.3.dp,
                color = selectedType.outlineColor,
                shape = RoundedCornerShape(2.dp)
            )

    ) {
        Text(
            modifier = Modifier.padding(vertical = 2.dp, horizontal = 6.dp),
            text = stringResource(id = selectedType.chipName),
            color = selectedType.textColor,
            style = MementoTheme.typography.detail_b_12
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun previewChip() {
    var selectedType = remember { mutableStateOf(PriorityTagType.None) }
    MementoUrgentChip(selectedType.value)
}
