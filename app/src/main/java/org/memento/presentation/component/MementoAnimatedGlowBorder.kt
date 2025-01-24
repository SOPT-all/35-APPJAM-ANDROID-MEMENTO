package org.memento.presentation.component

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
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

@Composable
fun MementoAnimatedGlowBorder(
    modifier: Modifier,
    borderWidth: Dp = 2.dp,
    cornerRadius: Dp = 2.dp,
    animationDuration: Int = 1500,
    isShowAnimation: Boolean = true,
    content: @Composable () -> Unit,
) {
    val infiniteTransition = rememberInfiniteTransition(label = "borderAnimation")

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

    val opacity by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(400, easing = FastOutSlowInEasing),
        label = "opacity",
    )

    val scale by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(400, easing = FastOutSlowInEasing),
        label = "scale",
    )

    Box(
        modifier =
            modifier
                .graphicsLayer {
                    alpha = opacity
                    scaleX = scale
                    scaleY = scale
                },
    ) {
        if (isShowAnimation) {
            Canvas(modifier = Modifier.matchParentSize()) {
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

        content()
    }
}
