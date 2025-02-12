package org.memento.presentation.todo

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import kotlinx.coroutines.launch
import org.memento.R
import org.memento.core.util.UiState
import org.memento.presentation.component.MementoAiFloatingButton
import org.memento.presentation.component.MementoAlertDialog
import org.memento.presentation.component.MementoAnimatedGlowBorder
import org.memento.presentation.component.MementoDialog
import org.memento.presentation.component.MementoEditTodoBottomSheet
import org.memento.presentation.component.MementoTodoItem
import org.memento.presentation.component.MementoTopBar
import org.memento.presentation.component.MementoWeeklyCalendar
import org.memento.presentation.todo.component.TodoBoxDown
import org.memento.presentation.todo.component.TodoBoxUp
import org.memento.presentation.todo.component.TodoDateLine
import org.memento.presentation.type.DialogType
import org.memento.presentation.util.MementoToast
import org.memento.presentation.util.changeHexToColor
import org.memento.presentation.util.toLocalDate
import org.memento.presentation.util.toPriorityTagType
import org.memento.presentation.util.todoFormatDate
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoScreen(
    viewModel: TodoViewModel = hiltViewModel(),
    padding: PaddingValues,
) {
    val today = LocalDate.now()
    val nowYear = LocalDate.now().year.toString()

    val todolistState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    val selectedDate by viewModel.selectedDate.collectAsState()
    val todoItems by viewModel.todoItems.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    var isShowAnimation by remember { mutableStateOf(false) }
    var isShowToast by remember { mutableStateOf(false) }
    var isClickedAiButton by remember { mutableStateOf(false) }
    val uiAIState by viewModel.uiAIState.collectAsState()

    LaunchedEffect(uiAIState) {
        when (uiAIState) {
            is UiState.Loading -> {
            }

            is UiState.Success -> {
                isShowAnimation = false
                isClickedAiButton = true
            }

            is UiState.Failure -> {
                isShowToast = true
                isShowAnimation = false
                isClickedAiButton = false
            }
        }
    }

    var showDetailDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    val sheetEditTodoState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showEditTodoBottomSheet by remember { mutableStateOf(false) }
    var selectedPlanId by remember { mutableIntStateOf(3) }

    when (uiState) {
        is UiState.Loading -> {
        }

        is UiState.Failure -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Failed to load ToDos. Please try again.")
            }
            return
        }

        is UiState.Success -> {}
    }

    val todoList =
        remember {
            mutableStateListOf<LocalDate>().apply {
                for (i in -30..30) {
                    add(today.plusDays(i.toLong()))
                }
            }
        }

    LaunchedEffect(todolistState) {
        snapshotFlow { todolistState.layoutInfo }
            .collect { layoutInfo ->
                val firstVisibleIndex = layoutInfo.visibleItemsInfo.firstOrNull()?.index ?: return@collect
                val lastVisibleIndex = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: return@collect
                if (firstVisibleIndex < 5) {
                    val firstDate = todoList.first()
                    repeat(10) { todoList.add(0, firstDate.minusDays(it.toLong() + 1)) }
                } else if (lastVisibleIndex > todoList.size - 10) {
                    val lastDate = todoList.last()
                    repeat(10) { todoList.add(lastDate.plusDays(it.toLong() + 1)) }
                }
            }
    }

    fun scrollToDate(date: LocalDate) {
        coroutineScope.launch {
            val index = todoList.indexOf(date)
            if (index != -1) {
                todolistState.animateScrollToItem(index)
            }
        }
    }

    LaunchedEffect(selectedDate) {
        scrollToDate(selectedDate)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        MementoAnimatedGlowBorder(
            modifier =
                Modifier
                    .padding(padding),
            isShowAnimation = isShowAnimation,
            borderWidth = 4.dp,
            cornerRadius = 8.dp,
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier =
                        Modifier
                            .fillMaxSize(),
                ) {
                    MementoTopBar(
                        date = todoFormatDate(today),
                        year = nowYear,
                        onDateClick = {
                            viewModel.updateSelectedDate(today)
                        },
                        onIconClick = {},
                    )
                    MementoWeeklyCalendar(
                        selectedDate = selectedDate,
                        onDateClick = { newDate ->
                            viewModel.updateSelectedDate(newDate)
                            coroutineScope.launch { scrollToDate(newDate) }
                        },
                    )

                    Box(
                        contentAlignment = Alignment.Center,
                    ) {
                        TodoBoxUp(
                            modifier =
                                Modifier
                                    .align(Alignment.TopCenter),
                        )
                        Column(modifier = Modifier.wrapContentSize()) {
                            LazyColumn(
                                state = todolistState,
                            ) {
                                items(todoList, key = { it }) { date ->
                                    TodoDateLine(date)
                                    Spacer(Modifier.height(8.dp))
                                    val filteredTodos =
                                        todoItems.filter {
                                            it.date.toLocalDate() == date
                                        }
                                    val sortedTodos = filteredTodos.sortedBy { it.isCompleted }
                                    val firstUndoneTodoId = sortedTodos.firstOrNull { !it.isCompleted }?.id
                                    Column(
                                        modifier =
                                            Modifier
                                                .padding(horizontal = 16.dp),
                                    ) {
                                        sortedTodos.forEachIndexed { index, todoItem ->
                                            val deadline = if (todoItem.date == todoItem.deadline) "Today" else todoFormatDate(todoItem.date.toLocalDate())

                                            MementoTodoItem(
                                                tagColor = changeHexToColor(todoItem.tagColor),
                                                isChecked = todoItem.isCompleted,
                                                onCheckedChange = { newChecked ->
                                                    viewModel.updateTodoCompletion(todoItem.id, newChecked)
                                                },
                                                todoTitleText = todoItem.description,
                                                priorityTagType = todoItem.priorityType.toPriorityTagType(),
                                                isConnected = false,
                                                isFirstUndone = (todoItem.id == firstUndoneTodoId),
                                                deadline = deadline,
                                                onClick = {
                                                    selectedPlanId = todoItem.id
                                                    showDetailDialog = true
                                                },
                                            )
                                            Spacer(Modifier.height(10.dp))
                                        }
                                    }
                                }
                            }
                        }
                        MementoAiFloatingButton(
                            isClicked = isClickedAiButton,
                            onClick = {
                                isClickedAiButton = !isClickedAiButton
                                if (isClickedAiButton) {
                                    isShowAnimation = true
                                    viewModel.postPriorityTodo()
                                }
                            },
                            modifier =
                                Modifier
                                    .align(Alignment.BottomEnd)
                                    .padding(bottom = 20.dp, end = 20.dp),
                        )
                        TodoBoxDown(
                            modifier =
                                Modifier
                                    .align(Alignment.BottomCenter),
                        )
                    }

                    MementoDialog(
                        showDialog = showDetailDialog,
                        onDismiss = { showDetailDialog = false },
                        onDelete = { showDeleteDialog = true },
                        onEdit = { showEditTodoBottomSheet = true },
                        dialogType = DialogType.TO_DO,
                        planId = selectedPlanId,
                    )

                    if (showDeleteDialog) {
                        MementoAlertDialog(
                            content = R.string.alert_delete,
                            leftButtonText = R.string.alert_cancel_button,
                            rightButtonText = R.string.alert_delete_button,
                            onLeftButtonClick = { showDeleteDialog = false },
                            onRightButtonClick = {
                                viewModel.deletePlan(
                                    planId = selectedPlanId,
                                    dialogType = DialogType.TO_DO,
                                )
                                showDeleteDialog = false
                                showDetailDialog = false
                            },
                        )
                    }

                    MementoEditTodoBottomSheet(
                        isOpenBottomSheet = showEditTodoBottomSheet,
                        sheetState = sheetEditTodoState,
                        onConfirm = { showEditTodoBottomSheet = false },
                        planId = selectedPlanId,
                    )
                }
                if (isShowToast) {
                    MementoToast(LocalContext.current).makeText(
                        message = "Failed to load ToDos. Please try again.",
                        icon = R.drawable.ic_toast,
                        duration = Toast.LENGTH_SHORT,
                        lifecycleOwner = LocalLifecycleOwner.current,
                    )
                    isShowToast = false
                }
            }
        }
    }
}

@Preview
@Composable
fun TodoScreenPreview() {
//    TodoScreen(padding = p)
}
