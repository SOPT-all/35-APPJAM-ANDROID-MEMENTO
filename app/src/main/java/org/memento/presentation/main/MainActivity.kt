package org.memento.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import org.memento.presentation.StartDummyScreen
import org.memento.presentation.navigation.Routes.Routes
import org.memento.ui.theme.MEMENTOTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var isDarkMode by remember { mutableStateOf(true) }
            MEMENTOTheme(
                darkTheme = isDarkMode,
            ) {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Routes.Dummy1.route
                ) {
                    composable(Routes.Dummy1.route) {
                        StartDummyScreen(
                            navigateToMain = {
                                navController.navigate(Routes.Main.route) {
                                    popUpTo(Routes.Dummy1.route) { inclusive = true }
                                }
                            }
                        )
                    }
                    composable(Routes.Main.route) {
                        MainScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier,
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MEMENTOTheme {
        Greeting("Android")
    }
}
