package org.memento.presentation.plusbottomsheet

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.memento.ui.theme.MementoTheme
import org.memento.ui.theme.darkModeColors

@Composable
fun BrainDumpAITextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "",
    modifier: Modifier = Modifier,
    borderWidth: Dp = 2.dp,
    cornerRadius: Dp = 2.dp,
    animationDuration: Int = 1500,
    isShowAnimation: Boolean = true,
) {
    // 테두리 색상 및 두께 변화 애니메이션
    val infiniteTransition = rememberInfiniteTransition(label = "textFieldAnimation")

    // 그라디언트 애니메이션을 위한 progress 값
    val progress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec =
            infiniteRepeatable(
                animation = tween(animationDuration, easing = LinearEasing),
                repeatMode = RepeatMode.Restart,
            ),
        label = "progress",
    )

    // 테두리 두께 애니메이션
    val borderWidthAnim by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 2.5f,
        animationSpec =
            infiniteRepeatable(
                animation = tween(750, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse,
            ),
        label = "borderWidth",
    )

    // 블러 효과 애니메이션
    val blurAlpha by infiniteTransition.animateFloat(
        initialValue = 0.6f,
        targetValue = 1f,
        animationSpec =
            infiniteRepeatable(
                animation = tween(500, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse,
            ),
        label = "blurAlpha",
    )

    // 페이드 인/아웃 애니메이션
    val opacity by animateFloatAsState(
        targetValue = if (isShowAnimation) 1f else 0f,
        animationSpec = tween(400, easing = FastOutSlowInEasing),
        label = "opacity",
    )

    // 스케일 애니메이션
    val scale by animateFloatAsState(
        targetValue = if (isShowAnimation) 1f else 0.95f,
        animationSpec = tween(400, easing = FastOutSlowInEasing),
        label = "scale",
    )

    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .background(
                    color = Color.Black,
                    shape = RoundedCornerShape(2.dp),
                )
                .border(borderWidthAnim.dp, Color.Transparent, RoundedCornerShape(cornerRadius)),
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp)
                    .padding(top = 8.dp),
            textStyle =
                MementoTheme.typography.body_b_16.copy(
                    color = darkModeColors.white,
                ),
            cursorBrush =
                Brush.verticalGradient(
                    listOf(darkModeColors.green, darkModeColors.green),
                ),
        )

        if (value.isEmpty()) {
            Text(
                text = placeholder,
                modifier =
                    Modifier
                        .padding(horizontal = 12.dp)
                        .padding(top = 8.dp),
                style =
                    MementoTheme.typography.body_b_16.copy(
                        color = darkModeColors.navy,
                    ),
            )
        }

        if (opacity > 0f) {
            Box(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .graphicsLayer {
                            alpha = opacity
                            scaleX = scale
                            scaleY = scale
                        },
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val gradientWidth = size.width * 1.5f

                    val brush =
                        Brush.linearGradient(
                            colors =
                                listOf(
                                    Color(0xFF0EA5E9),
                                    Color(0xFF2DD4BF),
                                    Color(0xFF818CF8),
                                    Color(0xFF0EA5E9),
                                ),
                            start =
                                Offset(
                                    x = -gradientWidth + (size.width + gradientWidth * 2) * progress,
                                    y = 0f,
                                ),
                            end =
                                Offset(
                                    x = (-gradientWidth + (size.width + gradientWidth * 2) * progress) + gradientWidth,
                                    y = size.height,
                                ),
                        )

                    // 외부 글로우 효과
                    drawRoundRect(
                        brush = brush,
                        style =
                            Stroke(
                                width = (borderWidth * borderWidthAnim * 2f).toPx(),
                                cap = StrokeCap.Round,
                                join = StrokeJoin.Round,
                            ),
                        cornerRadius = CornerRadius(cornerRadius.toPx()),
                        blendMode = BlendMode.Screen,
                        alpha = blurAlpha * 0.15f,
                    )

                    // 중간 글로우 효과
                    drawRoundRect(
                        brush = brush,
                        style =
                            Stroke(
                                width = (borderWidth * borderWidthAnim * 1.5f).toPx(),
                                cap = StrokeCap.Round,
                                join = StrokeJoin.Round,
                            ),
                        cornerRadius = CornerRadius(cornerRadius.toPx()),
                        blendMode = BlendMode.Screen,
                        alpha = blurAlpha * 0.3f,
                    )

                    // 메인 테두리
                    drawRoundRect(
                        brush = brush,
                        style =
                            Stroke(
                                width = (borderWidth * borderWidthAnim).toPx(),
                                cap = StrokeCap.Round,
                                join = StrokeJoin.Round,
                            ),
                        cornerRadius = CornerRadius(cornerRadius.toPx()),
                        blendMode = BlendMode.Screen,
                        alpha = blurAlpha,
                    )
                }
            }
        }
    }
}
