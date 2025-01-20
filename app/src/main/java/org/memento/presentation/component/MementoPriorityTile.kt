package org.memento.presentation.component

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.persistentListOf
import org.memento.presentation.type.PriorityTagType
import org.memento.presentation.util.noRippleClickable
import org.memento.ui.theme.MementoTheme
import org.memento.ui.theme.darkModeColors
import org.memento.ui.theme.mementoColors

@SuppressLint("ResourceAsColor")
@Composable
fun MementoPriorityTile(
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

    var arrowHeight by remember { mutableStateOf(0f) }

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier =
                Modifier
                    .wrapContentWidth()
                    .padding(start = 10.dp)
                    .onGloballyPositioned { coordinates ->
                        arrowHeight = coordinates.size.height.toFloat()
                    },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Column(
                modifier =
                    Modifier
                        .width(1.dp)
                        .wrapContentHeight()
                        .height(with(LocalDensity.current) { arrowHeight.toDp() })
                        .background(
                            brush =
                                Brush.linearGradient(
                                    colors =
                                        listOf(
                                            darkModeColors.gray04,
                                            mementoColors.todoNowEnd,
                                            mementoColors.todoNowStart,
                                        ),
                                ),
                        ),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Canvas(
                    modifier =
                        Modifier
                            .height(with(LocalDensity.current) { arrowHeight.toDp() }),
                ) {
                    val arrowSize = 10.dp.toPx()
                    drawLine(
                        color = darkModeColors.gray04,
                        start = Offset(size.width / 2, 0f),
                        end = Offset((size.width / 2) - (arrowSize / 2), arrowSize),
                        strokeWidth = 1.dp.toPx(),
                    )
                    drawLine(
                        color = darkModeColors.gray04,
                        start = Offset(size.width / 2, 0f),
                        end = Offset((size.width / 2) + (arrowSize / 2), arrowSize),
                        strokeWidth = 1.dp.toPx(),
                    )
                }
            }
        }

        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(16.dp),
                contentAlignment = Alignment.Center,
            ) {
                Row(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(
                                brush =
                                    Brush.linearGradient(
                                        colors =
                                            listOf(
                                                darkModeColors.gray04,
                                                mementoColors.todoNowEnd,
                                                mementoColors.todoNowStart,
                                            ),
                                    ),
                            ),
                ) {
                }

                Canvas(modifier = Modifier.fillMaxWidth()) {
                    val arrowSize = 10.dp.toPx()
                    drawLine(
                        color = darkModeColors.gray04,
                        start = Offset(0f, size.height / 2),
                        end = Offset(arrowSize, (size.height / 2) - (arrowSize / 2)),
                        strokeWidth = 1.dp.toPx(),
                    )
                    drawLine(
                        color = darkModeColors.gray04,
                        start = Offset(0f, size.height / 2),
                        end = Offset(arrowSize, (size.height / 2) + (arrowSize / 2)),
                        strokeWidth = 1.dp.toPx(),
                    )
                }
            }

            for (row in 0 until 2) {
                Row(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .onGloballyPositioned { coordinates ->
                                arrowHeight = coordinates.size.height.toFloat() * 2
                            },
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
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
                                    .noRippleClickable { onTypeSelected(type) },
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
}

@Preview
@Composable
private fun ScreenPreview() {
    MementoPriorityTile(selectedType = PriorityTagType.Medium, onTypeSelected = {})
}
