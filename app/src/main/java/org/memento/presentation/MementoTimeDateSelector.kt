package org.memento.presentation

import android.app.sdksandbox.SdkSandboxManager.SdkSandboxProcessDeathCallback
import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.memento.domain.type.SelectorType
import org.memento.presentation.util.noRippleClickable

@Composable
fun MementoTimeDateSelector(
    selectorType: SelectorType,
    isClicked: Boolean = false,
    onClickedChange: (Boolean) -> Unit = {},
    content: String,
    modifier: Modifier = Modifier,
) {
    var clicked by remember { mutableStateOf(isClicked) }

    Row(
        modifier = Modifier
            .then(modifier)
            .aspectRatio(selectorType.width / selectorType.height)
            .clip(RoundedCornerShape(selectorType.cornerRadius))
            .background(color = if (clicked) selectorType.clickedBackgroundColor else selectorType.unClickedBackgroundColor)
            .noRippleClickable {
                // Todo : Selector 호출하고
                clicked = !clicked
                onClickedChange(clicked)
            },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = content,
            style = selectorType.textStyle,
            color = selectorType.textColor,
            modifier = Modifier
                .padding(
                    horizontal = selectorType.paddingHorizontal,
                    vertical = selectorType.paddingVertical
                )
        )
    }
}

