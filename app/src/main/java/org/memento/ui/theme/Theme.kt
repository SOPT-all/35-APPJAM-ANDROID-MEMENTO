package org.memento.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

object MementoTheme {
    val modeColors: MementoColorScheme
        @Composable
        @ReadOnlyComposable
        get() = LocalModeColors.current
    val mementoColors: MementoColors
        @Composable
        @ReadOnlyComposable
        get() = LocalMementoColors.current
    val typography: MementoTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypo.current
}

@Composable
fun ProvideMementoColorsAndTypography(
    modeColors: MementoColorScheme,
    mementoColors: MementoColors,
    typography: MementoTypography,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalModeColors provides modeColors,
        LocalMementoColors provides mementoColors,
        LocalTypo provides typography,
        content = content,
    )
}

@Composable
fun MEMENTOTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        darkModeColors
    } else {
        lightModeColors
    }
    val mementoColors = mementoColors

    ProvideMementoColorsAndTypography(
        modeColors = colors,
        mementoColors = mementoColors,
        typography = defaultMementoTypography
    ) {
        val view = LocalView.current
        if (!view.isInEditMode) {
            SideEffect {
                val window = (view.context as Activity).window
                window.statusBarColor =
                    if (darkTheme) darkModeColors.black.toArgb() else White.toArgb()
                window.navigationBarColor =
                    if (darkTheme) darkModeColors.black.toArgb() else White.toArgb()
                WindowCompat.getInsetsController(window, view).apply {
                    isAppearanceLightStatusBars = !darkTheme
                    isAppearanceLightNavigationBars = !darkTheme
                }
            }
        }
        MaterialTheme(content = content)
    }
}