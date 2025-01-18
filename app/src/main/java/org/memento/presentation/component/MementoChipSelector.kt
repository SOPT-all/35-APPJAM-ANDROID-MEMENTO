package org.memento.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import org.memento.R
import org.memento.presentation.type.SelectorType
import org.memento.presentation.util.changeHexToColor
import org.memento.presentation.util.noRippleClickable

@Composable
fun MementoChipSelector(
    selectorType: SelectorType,
    isClicked: Boolean = false,
    onClickedChange: (Boolean) -> Unit = {},
    content: String,
    tagColor: String? = null,
    modifier: Modifier = Modifier,
) {
    var clicked by remember { mutableStateOf(isClicked) }

    Box(
        modifier =
            Modifier
                .then(modifier)
                .clip(RoundedCornerShape(selectorType.cornerRadius))
                .background(color = if (isClicked) selectorType.clickedBackgroundColor else selectorType.unClickedBackgroundColor)
                .noRippleClickable {
                    onClickedChange(clicked)
                },
        Alignment.Center,
    ) {
        when (selectorType) {
            SelectorType.TIMESELECTOR -> {
                Text(
                    text = stringResource(id = R.string.time_example),
                    style = selectorType.textStyle,
                    color = Color.Transparent,
                    modifier =
                        Modifier.padding(
                            horizontal = selectorType.paddingHorizontal,
                            vertical = selectorType.paddingVertical,
                        ),
                )
                Text(
                    text = content,
                    style = selectorType.textStyle,
                    color = selectorType.textColor,
                )
            }

            SelectorType.DATESELECTOR -> {
                Text(
                    text = stringResource(id = R.string.date_example),
                    style = selectorType.textStyle,
                    color = Color.Transparent,
                    modifier =
                        Modifier
                            .padding(
                                horizontal = selectorType.paddingHorizontal,
                                vertical = selectorType.paddingVertical,
                            ),
                )
                Text(
                    text = content,
                    style = selectorType.textStyle,
                    color = selectorType.textColor,
                )
            }

            SelectorType.TAG -> {
                Box(
                    modifier =
                        Modifier
                            .align(Alignment.Center),
                ) {
                    Row(
                        modifier =
                            Modifier
                                .padding(
                                    horizontal = selectorType.paddingHorizontal,
                                    vertical = selectorType.paddingVertical,
                                ),
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_tag),
                            contentDescription = null,
                            tint = Color.Transparent,
                            modifier = Modifier.padding(end = 5.dp),
                        )
                        Text(
                            text = stringResource(id = R.string.Today),
                            style = selectorType.textStyle,
                            color = Color.Transparent,
                        )
                    }
                    Row(
                        modifier =
                            Modifier
                                .align(Alignment.Center),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        tagColor?.let { changeHexToColor(hex = it) }?.let {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = R.drawable.ic_tag),
                                contentDescription = stringResource(id = R.string.tag_icon),
                                tint = it,
                                modifier = Modifier.padding(end = 5.dp),
                            )
                        }
                        Text(
                            text = content,
                            style = selectorType.textStyle,
                            color = selectorType.textColor,
                        )
                    }
                }
            }

            SelectorType.DEADLINE -> {
                Row(
                    modifier =
                        Modifier
                            .padding(
                                horizontal = selectorType.paddingHorizontal,
                                vertical = selectorType.paddingVertical,
                            ),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Image(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_deadline),
                        contentDescription = stringResource(id = R.string.deadline_icon),
                        modifier = Modifier.padding(end = 5.dp),
                    )
                    Text(
                        text = content,
                        style = selectorType.textStyle,
                        color = selectorType.textColor,
                    )
                }
            }

            SelectorType.BASIC -> {
                Text(
                    text = stringResource(id = R.string.Today),
                    style = selectorType.textStyle,
                    color = Color.Transparent,
                    modifier =
                        Modifier
                            .padding(
                                horizontal = selectorType.paddingHorizontal,
                                vertical = selectorType.paddingVertical,
                            ),
                )
                Text(
                    text = content,
                    style = selectorType.textStyle,
                    color = selectorType.textColor,
                )
            }
        }
    }
}
