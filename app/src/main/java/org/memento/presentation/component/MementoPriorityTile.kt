package org.memento.presentation.component

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.memento.domain.model.PriorityTagType
import org.memento.ui.theme.MementoTheme


@SuppressLint("ResourceAsColor")
@Composable
fun TagSelectionGrid(
    selectedType: PriorityTagType,
    onTypeSelected: (PriorityTagType) -> Unit,
    modifier: Modifier = Modifier
) {
    val types = listOf(
        PriorityTagType.Immediate,
        PriorityTagType.High,
        PriorityTagType.Medium,
        PriorityTagType.Low
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        for (row in 0 until 2) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                for (col in 0 until 2) {
                    val type = types[row * 2 + col]
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(155f / 132f)
                            .background(
                                if (selectedType == type) colorResource(id = type.backgroundColor)
                                else Color(0xFF2E2E2E)
                            )
                            .border(
                                width = if (selectedType == type) 2.dp else 0.dp,
                                color = if (selectedType == type) colorResource(id = type.outlineColor)
                                else Color.Transparent,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clickable { onTypeSelected(type) }
                    ) {
                        Text(
                            text = stringResource(id = type.chipText),
                            style = TextStyle(
                                color = if (selectedType == type) Color.White else Color.Gray,
                                fontSize = 14.sp,
                                textAlign = TextAlign.Center
                            )
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0F0F1E, widthDp = 340, heightDp = 300)
@Composable
fun TagSelectionGridPreview() {
    var selectedType = remember { mutableStateOf(PriorityTagType.Immediate) }

    TagSelectionGrid(
        selectedType = selectedType.value,
        onTypeSelected = { type ->
            selectedType.value = type
        },
        modifier = Modifier.padding(16.dp)
    )
}


@SuppressLint("ResourceAsColor")
@Composable
fun MementoPriorityTile(
    type: PriorityTagType,
    isSelected: Boolean,
    onClick: (PriorityTagType) -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .background(
                if (isSelected && type != PriorityTagType.None) colorResource(id = type.backgroundColor)
                else Color(0xFF2E2E2E)
            )
            .border(
                width = if (type != PriorityTagType.None) 2.dp else 0.dp,
                color = if (isSelected && type != PriorityTagType.None) colorResource(id = type.outlineColor)
                else Color.Transparent,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable { onClick(type) }
    ) {
        Text(
            text = stringResource(id = type.chipText),
            style = MementoTheme.typography.detail_r_12.copy(
                color = if (isSelected && type != PriorityTagType.None) Color.White else Color.Gray
            ),
            textAlign = TextAlign.Center
        )
    }
}


@Preview(showBackground = true, backgroundColor = 0xFF0F0F1E, widthDp = 100, heightDp = 100)
@Composable
fun MementoPriorityTilePreviewImmediate() {
    MementoPriorityTile(
        type = PriorityTagType.Immediate,
        isSelected = true,
        onClick = {}
    )
}

