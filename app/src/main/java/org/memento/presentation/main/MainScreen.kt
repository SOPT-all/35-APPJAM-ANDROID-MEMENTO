package org.memento.presentation.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import org.memento.presentation.main.bottomnavigation.BottomNavGraph
import org.memento.presentation.main.bottomnavigation.BottomNavigationType
import org.memento.presentation.main.bottomnavigation.component.BottomNavigationBar
import org.memento.presentation.plusbottomsheet.MainPlusBottomSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val scaffoldState = rememberBottomSheetScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val navController = rememberNavController()

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            MainPlusBottomSheet()
        },
        sheetPeekHeight = 0.dp,
        sheetContainerColor = Color.DarkGray,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {
            BottomNavGraph(
                navController = navController,
            )

            BottomNavigationBar(
                currentNaviBarItemSelected =
                    BottomNavigationType.entries.firstOrNull {
                        it.route == navController.currentBackStackEntry?.destination?.route
                    },
                onBottomNaviBarItemSelected = { navItem ->
                    navController.navigate(navItem.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                onAddButtonClick = {
                    coroutineScope.launch {
                        scaffoldState.bottomSheetState.expand()
                    }
                },
                modifier =
                    Modifier
                        .align(Alignment.BottomCenter)
                        // 소프트키 영역 고려
                        .navigationBarsPadding(),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Main() {
    MaterialTheme {
        MainScreen()
    }
}
