package org.memento.presentation.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import org.memento.presentation.navigator.MainNavigator
import org.memento.presentation.navigator.component.BottomNavigationType
import org.memento.presentation.navigator.component.MainBottomBar
import org.memento.presentation.navigator.component.MainNavHost
import org.memento.presentation.navigator.rememberMainNavigator
import org.memento.presentation.plusbottomsheet.AddScheduleViewModel
import org.memento.presentation.plusbottomsheet.AddToDoDeadLineScreen
import org.memento.presentation.plusbottomsheet.AddToDoEisenScreen
import org.memento.presentation.plusbottomsheet.AddToDoTagScreen
import org.memento.presentation.plusbottomsheet.AddToDoViewModel
import org.memento.presentation.plusbottomsheet.BrainDumpViewModel
import org.memento.presentation.plusbottomsheet.MainPlusBottomSheet
import org.memento.presentation.type.BottomSheetType
import org.memento.ui.theme.darkModeColors

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenContent(
    modifier: Modifier = Modifier,
    navigator: MainNavigator,
    addToDoViewModel: AddToDoViewModel = hiltViewModel(),
    addScheduleViewModel: AddScheduleViewModel = hiltViewModel(),
    brainDoViewModel: BrainDumpViewModel = hiltViewModel(),
) {
    val coroutineScope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var currentBottomSheet by remember { mutableStateOf<BottomSheetType?>(null) }

    // BottomSheet 종료 시 데이터 초기화
    val onCloseBottomSheet: () -> Unit = {
        currentBottomSheet = null
        addToDoViewModel.resetData()
        addScheduleViewModel.resetData()
        brainDoViewModel.resetData()
        coroutineScope.launch {
            bottomSheetState.hide()
        }
    }

    if (currentBottomSheet != null) {
        ModalBottomSheet(
            onDismissRequest = { onCloseBottomSheet() },
            sheetState = bottomSheetState,
            shape = RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp),
            containerColor = darkModeColors.gray10,
            dragHandle = null,
        ) {
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.73f)
                        .imePadding(),
            ) {
                when (currentBottomSheet) {
                    BottomSheetType.MAIN ->
                        MainPlusBottomSheet(
                            onNavigateDeadLineSetting = {
                                currentBottomSheet = BottomSheetType.DEADLINE
                            },
                            onNavigateTagSetting = {
                                currentBottomSheet = BottomSheetType.TAG
                            },
                            onNavigateEisenSetting = {
                                currentBottomSheet = BottomSheetType.EISEN
                            },
                            onCloseBottomSheet = onCloseBottomSheet,
                        )

                    BottomSheetType.DEADLINE ->
                        AddToDoDeadLineScreen(
                            onClose = { currentBottomSheet = BottomSheetType.MAIN },
                            onDone = { currentBottomSheet = BottomSheetType.MAIN },
                        )

                    BottomSheetType.TAG ->
                        AddToDoTagScreen(
                            onClose = { currentBottomSheet = BottomSheetType.MAIN },
                            onDone = { currentBottomSheet = BottomSheetType.MAIN },
                        )

                    BottomSheetType.EISEN ->
                        AddToDoEisenScreen(
                            onClose = { currentBottomSheet = BottomSheetType.MAIN },
                            onDone = { currentBottomSheet = BottomSheetType.MAIN },
                        )

                    null -> {}
                }
            }
        }
    }

    Scaffold(
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
                onAddButtonClick = {
                    currentBottomSheet = BottomSheetType.MAIN
                    coroutineScope.launch {
                        bottomSheetState.show()
                    }
                },
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
