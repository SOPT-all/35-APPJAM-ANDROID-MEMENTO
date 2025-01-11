package org.memento.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
sealed class MementoColorScheme

// Gray Scale (Dark)
val White = Color(0xFFFFFFFF)
val Gray01 = Color(0xFFF6F7F9)
val Gray02 = Color(0xFFF0F0F3)
val Gray03 = Color(0xFFE3E4EA)
val Gray04 = Color(0xFFCDCFD9)
val Gray05 = Color(0xFFA9ADBB)
val Gray06 = Color(0xFF848898)
val Gray07 = Color(0xFF565C6D)
val Gray08 = Color(0xFF363A49)
val Gray09 = Color(0xFF222531)
val Gray10 = Color(0xFF11121C)
val Black = Color(0xFF010211)

// Main Color (Dark)
val Green = Color(0xFF29FF74)
val Navy = Color(0xFF181925)

// Colors
val Red = Color(0xFFFF426E)
val Pink = Color(0xFFEE8AAD)
val Orange = Color(0xFFFF8162)
val Yellow = Color(0xFFFFE483)
val LightGreen = Color(0xFF7BD27D)
val Mint = Color(0xFF149C95)
val Cyan = Color(0xFF6CA9E1)
val Blue = Color(0xFF3867FF)
val Purple = Color(0xFF7B5DFF)

// Labels
val Immediate = Color(0xFFFF0D45)
val High = Color(0xFFFF5035)
val Medium = Color(0xFFDDA153)
val Low = Color(0xFF00AC34)

val Immediate15 = Color(0xFFFF0D45)
val High15 = Color(0xFFFF5035)
val Medium15 = Color(0xFFDDA153)
val Low15 = Color(0xFF00AC34)

// Gradient
val TodoNowStart = Color(0xFF181925)
val TodoNowEnd = Color(0xFF424565)
val ProgressBar = Color(0xFFADB3BA)
val ScrollBox = Color(0xFF010211)
val BrainDumpExStart = Color(0xFF222531)
val BrainDumpExEnd = Color(0xFF161B2D)

// Memento color scheme class
@Immutable
data class DarkModeColors(
    // Gray Scale
    val white: Color,
    val gray01: Color,
    val gray02: Color,
    val gray03: Color,
    val gray04: Color,
    val gray05: Color,
    val gray06: Color,
    val gray07: Color,
    val gray08: Color,
    val gray09: Color,
    val gray10: Color,
    val black: Color,
    // Main Color
    val green: Color,
    val navy: Color,
) : MementoColorScheme()

@Immutable
data class MementoColors(
    // Colors
    val red: Color,
    val pink: Color,
    val orange: Color,
    val yellow: Color,
    val lightGreen: Color,
    val mint: Color,
    val cyan: Color,
    val blue: Color,
    val purple: Color,
    // Labels
    val immediate: Color,
    val immediate15: Color,
    val high: Color,
    val high15: Color,
    val medium: Color,
    val medium15: Color,
    val low: Color,
    val low15: Color,
    // Gradient
    val todoNowStart: Color,
    val todoNowEnd: Color,
    val progressBar: Color,
    val scrollBox: Color,
    val brainDumpExStart: Color,
    val brainDumpExEnd: Color,
) : MementoColorScheme()

@Immutable
data class LightModelColors(
    val white: Color,
) : MementoColorScheme()

val darkModeColors = DarkModeColors(
    // Gray Scale
    white = White,
    gray01 = Gray01,
    gray02 = Gray02,
    gray03 = Gray03,
    gray04 = Gray04,
    gray05 = Gray05,
    gray06 = Gray06,
    gray07 = Gray07,
    gray08 = Gray08,
    gray09 = Gray09,
    gray10 = Gray10,
    black = Black,
    // Main Color
    green = Green,
    navy = Navy,
)

val mementoColors = MementoColors(
    // Colors
    red = Red,
    pink = Pink,
    orange = Orange,
    yellow = Yellow,
    lightGreen = LightGreen,
    mint = Mint,
    cyan = Cyan,
    blue = Blue,
    purple = Purple,
    // Labels
    immediate = Immediate,
    immediate15 = Immediate15,
    high = High,
    high15 = High15,
    medium = Medium,
    medium15 = Medium15,
    low = Low,
    low15 = Low15,
    // Gradient
    todoNowStart = TodoNowStart,
    todoNowEnd = TodoNowEnd,
    progressBar = ProgressBar,
    scrollBox = ScrollBox,
    brainDumpExStart = BrainDumpExStart,
    brainDumpExEnd = BrainDumpExEnd,
)

val lightModeColors = LightModelColors(
    white = White,
)

// Local Colors
val LocalModeColors = staticCompositionLocalOf<MementoColorScheme> {
    error("No DarkColorScheme provided")
}
val LocalMementoColors = staticCompositionLocalOf<MementoColors> {
    error("No ColorsColorScheme provided")
}