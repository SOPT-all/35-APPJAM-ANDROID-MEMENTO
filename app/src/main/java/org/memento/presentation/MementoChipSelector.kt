package org.memento.presentation

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
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import org.memento.R
import org.memento.domain.type.SelectorType
import org.memento.presentation.util.noRippleClickable

@Composable
fun MementoChipSelector(
    selectorType: SelectorType,
    isClicked: Boolean = false,
    onClickedChange: (Boolean) -> Unit = {},
    content: String,
    tagColor: Color = Color.Unspecified,
    modifier: Modifier = Modifier,
) {
    var clicked by remember { mutableStateOf(isClicked) }

    Box(
        modifier =
            Modifier
                .then(modifier)
                .clip(RoundedCornerShape(selectorType.cornerRadius))
                .background(color = if (clicked) selectorType.clickedBackgroundColor else selectorType.unClickedBackgroundColor)
                .noRippleClickable {
                    // Todo : Selector 호출하고 그에 따라 컬러 체인지 로직
                    clicked = !clicked
                    onClickedChange(clicked)
                },
        Alignment.Center,
    ) {
        when (selectorType) {
            SelectorType.TIMESELECTOR -> {
                Text(
                    text = "00:00 AM",
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
                    text = "Jan 30, 2025",
                    style = selectorType.textStyle,
                    color = Color.Transparent,
                    modifier =
                        Modifier
                            .padding(horizontal = selectorType.paddingHorizontal, vertical = selectorType.paddingVertical),
                )
                Text(
                    text = content,
                    style = selectorType.textStyle,
                    color = selectorType.textColor,
                )
            }

            SelectorType.TAG -> {
                // TODO : 서버에서 HEXCODE 받아 적용하는 로직 -> 색상을 받으면 원안에 그 색을 넣자
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
                            contentDescription = "null",
                            tint = Color.Transparent,
                            modifier = Modifier.padding(end = 5.dp),
                        )
                        Text(
                            text = "Today",
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
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_tag),
                            contentDescription = "Tag Icon",
                            tint = tagColor,
                            modifier = Modifier.padding(end = 5.dp),
                        )
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
                        contentDescription = "Deadline Icon",
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
                    text = "Today",
                    style = selectorType.textStyle,
                    color = Color.Transparent,
                    modifier =
                        Modifier
                            .padding(horizontal = selectorType.paddingHorizontal, vertical = selectorType.paddingVertical),
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
