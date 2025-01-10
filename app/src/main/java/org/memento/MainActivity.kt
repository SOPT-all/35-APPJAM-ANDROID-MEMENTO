package org.memento

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import org.memento.ui.theme.MEMENTOTheme
import org.memento.ui.theme.MementoTheme
import org.memento.ui.theme.darkModeColors
import org.memento.ui.theme.mementoColors

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var isDarkMode by remember { mutableStateOf(true) }
            MEMENTOTheme(
                darkTheme = isDarkMode,
            ) {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Column {
        Text(
            text = "Hello $name!\n" +
                    "mementoColors.immediate",
            color = mementoColors.immediate
        )
        Text(
            text = "darkModeColors.navy",
            color = darkModeColors.navy
        )
        Text(
            text = "mementoColors.purple",
            color = mementoColors.purple
        )
        Text(
            text = "darkModeColors.green",
            color = darkModeColors.green
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MEMENTOTheme {
        Greeting("Android")
    }
}
