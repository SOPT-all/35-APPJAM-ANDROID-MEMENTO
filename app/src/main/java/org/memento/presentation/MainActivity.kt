package org.memento.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import org.memento.data.local.TokenDataStore
import org.memento.presentation.main.MainScreen
import org.memento.presentation.navigator.MainNavigator
import org.memento.presentation.navigator.component.BottomNavigationType
import org.memento.presentation.navigator.rememberMainNavigator
import org.memento.presentation.onboarding.LoginScreen
import org.memento.presentation.onboarding.SplashScreen
import org.memento.ui.theme.MEMENTOTheme
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val isDarkMode by remember { mutableStateOf(true) }
            var showSplash by remember { mutableStateOf(true) }
            val navigator: MainNavigator = rememberMainNavigator()

            MEMENTOTheme(darkTheme = isDarkMode) {
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
        const val SPLASH_SCREEN_DELAY = 3000L
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MEMENTOTheme {
    }
}
