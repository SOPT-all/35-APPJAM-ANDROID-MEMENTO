package org.memento.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import org.memento.presentation.main.MainScreen
import org.memento.presentation.navigator.MainNavigator
import org.memento.presentation.navigator.rememberMainNavigator
import org.memento.presentation.onboarding.SplashScreen
import org.memento.ui.theme.MEMENTOTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().setOnExitAnimationListener { splashScreenView ->
            splashScreenView.remove()
        }
        setContent {
            val isDarkMode by remember { mutableStateOf(true) }
            var showSplash by remember { mutableStateOf(true) }
            val navigator: MainNavigator = rememberMainNavigator()

            MEMENTOTheme(
                darkTheme = isDarkMode,
            ) {
                LaunchedEffect(Unit) {
                    delay(SPLASH_SCREEN_DELAY)
                    showSplash = false
                }
                if (showSplash) {
                    SplashScreen()
                } else {
                    MainScreen(navigator = navigator)
                }
            }
        }
    }

    companion object {
        const val SPLASH_SCREEN_DELAY = 2000L
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MEMENTOTheme {
    }
}
