package org.memento.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import dagger.hilt.android.AndroidEntryPoint
import org.memento.presentation.main.MainScreen
import org.memento.presentation.navigator.MainNavigator
import org.memento.presentation.navigator.rememberMainNavigator
import org.memento.ui.theme.MEMENTOTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var isDarkMode by remember { mutableStateOf(true) }
            val navigator: MainNavigator = rememberMainNavigator()

            MEMENTOTheme(
                darkTheme = isDarkMode,
            ) {
                MainScreen(navigator = navigator)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MEMENTOTheme {
    }
}
