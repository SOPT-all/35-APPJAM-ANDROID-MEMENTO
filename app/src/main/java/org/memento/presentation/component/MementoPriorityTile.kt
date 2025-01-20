package org.memento.presentation.component

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.persistentListOf
import org.memento.presentation.type.PriorityTagType
import org.memento.ui.theme.MementoTheme
import org.memento.ui.theme.darkModeColors

@SuppressLint("ResourceAsColor")
@Composable
fun TagSelectionGrid(
    selectedType: PriorityTagType,
    onTypeSelected: (PriorityTagType) -> Unit,
    modifier: Modifier = Modifier,
) {
    val types =
        persistentListOf(
            PriorityTagType.Immediate,
            PriorityTagType.High,
            PriorityTagType.Medium,
            PriorityTagType.Low,
            PriorityTagType.None,
        )

    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        for (row in 0 until 2) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                for (col in 0 until 2) {
                    val type = types[row * 2 + col]
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier =
                            Modifier
                                .weight(1f)
                                .aspectRatio(155f / 132f)
                                .background(
                                    if (selectedType == type) {
                                        type.backgroundColor
                                    } else {
                                        darkModeColors.gray09
                                    },
                                )
                                .clickable { onTypeSelected(type) },
                    ) {
                        Text(
                            text = stringResource(id = type.chipDiscription),
                            style =
                                MementoTheme.typography.detail_r_12.copy(
                                    color = if (selectedType == type) darkModeColors.gray06 else darkModeColors.gray07,
                                    textAlign = TextAlign.Center,
                                ),
                        )
                    }
                }
            }
        }
    }
}
