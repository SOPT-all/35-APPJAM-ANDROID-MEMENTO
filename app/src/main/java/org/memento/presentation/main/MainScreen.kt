package org.memento.presentation.main

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.memento.presentation.navigator.MainNavigator
import org.memento.presentation.navigator.component.BottomNavigationType
import org.memento.presentation.navigator.component.MainBottomBar
import org.memento.presentation.navigator.component.MainNavHost
import org.memento.presentation.navigator.rememberMainNavigator

@Composable
fun MainScreen(
    navigator: MainNavigator = rememberMainNavigator(),
) {
    MainScreenContent(
        navigator = navigator,
        modifier =
        Modifier,
    )
}

@Composable
fun MainScreenContent(
    modifier: Modifier = Modifier,
    navigator: MainNavigator,
) {
    Scaffold(
        modifier = modifier,
        content = { padding ->
            MainNavHost(
                navigator = navigator,
                padding = padding,
            )
        },
        bottomBar = {
            MainBottomBar(
                isVisible = navigator.showBottomBar(),
                navigationBarItems = BottomNavigationType.entries,
                currentNavigationBarItem = navigator.currentMainNavigationBarItem,
                onNavigationBarItemSelected = { navigator.navigateMainNavigation(it) },
                onAddButtonClick = { navigator.navigateToBottomSheet() },
            )
        },
    )
}

@Preview(showBackground = true)
@Composable
fun Main() {
    MaterialTheme {
        MainScreen()
    }
}
