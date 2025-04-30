package org.memento.presentation.setting.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.memento.presentation.type.TagColorType
import org.memento.presentation.util.noRippleClickable
import org.memento.ui.theme.darkModeColors

/**
 * @param color 버튼에 적용될 색상
 * @param isSelected 현재 색상된 색상 여부 (-> true 일 때 흰색 테두리 표시를 위함)
 * @param onClick 버튼 클릭 시 호출 되는 콜백 함수 (-> 선택된 색상을 처리하기 위함)
 * @param modifier padding, 다른 스타일을 적용할 modifier
 */

@Composable
fun TagColorSelector(
    color: TagColorType,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
            modifier
                .size(18.dp)
                .clip(CircleShape)
                .background(color.color)
                .border(
                    width = if (isSelected) 1.dp else 0.dp,
                    color = if (isSelected) darkModeColors.gray02 else Color.Unspecified,
                    shape = CircleShape,
                )
                .noRippleClickable { onClick() },
    )
}
