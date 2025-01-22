package org.memento.presentation.todo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.memento.presentation.component.MementoAiFloatingButton
import org.memento.presentation.component.MementoTodoItem
import org.memento.presentation.component.MementoTopBar
import org.memento.presentation.component.MementoWeeklyCalendar
import org.memento.presentation.todo.component.TodoBoxDown
import org.memento.presentation.todo.component.TodoBoxUp
import org.memento.presentation.todo.component.TodoDateLine
import org.memento.presentation.util.changeHexToColor
import org.memento.presentation.util.toLocalDate
import org.memento.presentation.util.toPriorityTagType
import org.memento.presentation.util.todoFormatDate
import java.time.LocalDate

// dummy data class
data class TodoItem(
    val id: Long,
    val groupId: String,
    val description: String,
    val date: String,
    val deadline: String,
    val isCompleted: Boolean,
    val priorityValue: Double,
    val priorityType: String,
    val tagName: String,
    val tagColor: String,
    val toDoType: String,
    val order: Int,
)

@Composable
fun TodoScreen() {
    val today = LocalDate.now()
    val nowYear = LocalDate.now().year.toString()

    val todolistState = rememberLazyListState()
    val selectedDate = remember { mutableStateOf(today) }
    val coroutineScope = rememberCoroutineScope()

    val todoItems =
        remember {
            mutableStateListOf(
                TodoItem(
                    id = 1,
                    groupId = "49684f41-42a0-45ab-90c4-d78797918b97",
                    description = "트루",
                    date = "2025-01-18",
                    deadline = "2025-01-20",
                    isCompleted = true,
                    priorityValue = 0.7049999999999998,
                    priorityType = "IMMEDIATE",
                    tagName = "도파민",
                    tagColor = "#FF5733",
                    toDoType = "TASK",
                    order = 1,
                ),
                TodoItem(
                    id = 2,
                    groupId = "124",
                    description = "알바(폴스)",
                    date = "2025-01-15",
                    deadline = "2025-01-15",
                    isCompleted = false,
                    priorityValue = 2.0,
                    priorityType = "NORMAL",
                    tagName = "노동",
                    tagColor = "#33FF57",
                    toDoType = "TASK",
                    order = 2,
                ),
                TodoItem(
                    id = 3,
                    groupId = "125",
                    description = "데모데이 발표 준비",
                    date = "2025-01-15",
                    deadline = "2025-01-15",
                    isCompleted = true,
                    priorityValue = 3.0,
                    priorityType = "MEDIUM",
                    tagName = "발표",
                    tagColor = "#3357FF",
                    toDoType = "EVENT",
                    order = 3,
                ),
                TodoItem(
                    id = 4,
                    groupId = "125",
                    description = "데모데이 발표 준비(폴스)",
                    date = "2025-01-15",
                    deadline = "2025-01-15",
                    isCompleted = false,
                    priorityValue = 3.0,
                    priorityType = "NONE",
                    tagName = "발표",
                    tagColor = "#3357FF",
                    toDoType = "EVENT",
                    order = 3,
                ),
                TodoItem(
                    id = 5,
                    groupId = "125",
                    description = "데모데이 발표 준비",
                    date = "2025-01-15",
                    deadline = "2025-01-15",
                    isCompleted = true,
                    priorityValue = 3.0,
                    priorityType = "IMMEDIATE",
                    tagName = "발표",
                    tagColor = "#3357FF",
                    toDoType = "EVENT",
                    order = 3,
                ),
                TodoItem(
                    id = 6,
                    groupId = "49684f41-42a0-45ab-90c4-d78797918b97",
                    description = "test1",
                    date = "2025-01-18",
                    deadline = "2025-01-20",
                    isCompleted = false,
                    priorityValue = 0.7049999999999998,
                    priorityType = "IMMEDIATE",
                    tagName = "도파민",
                    tagColor = "#FF5733",
                    toDoType = "TASK",
                    order = 1,
                ),
                TodoItem(
                    id = 7,
                    groupId = "124",
                    description = "알바",
                    date = "2025-01-15",
                    deadline = "2025-01-15",
                    isCompleted = false,
                    priorityValue = 2.0,
                    priorityType = "NORMAL",
                    tagName = "노동",
                    tagColor = "#33FF57",
                    toDoType = "TASK",
                    order = 2,
                ),
            )
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

    suspend fun scrollToDate(date: LocalDate) {
        coroutineScope.launch {
            val index = todoList.indexOf(date)
            if (index != -1) {
                todolistState.animateScrollToItem(index)
            }
        }
    }

    LaunchedEffect(selectedDate.value) {
        scrollToDate(selectedDate.value)
    }

    Column(
        modifier =
            Modifier
                .fillMaxSize(),
    ) {
        MementoTopBar(
            date = todoFormatDate(today),
            year = nowYear,
            onDateClick = {
                selectedDate.value = today
            },
            onIconClick = {},
        )
        MementoWeeklyCalendar(
            selectedDate = selectedDate.value,
            onDateClick = { newDate ->
                selectedDate.value = newDate
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
                    Column(
                        modifier =
                            Modifier
                                .padding(horizontal = 16.dp),
                    ) {
                        sortedTodos.forEachIndexed { index, todoItem ->
                            val deadline = if (todoItem.date == todoItem.deadline) "Today" else todoFormatDate(todoItem.date.toLocalDate())
                            MementoTodoItem(
                                tagColor = changeHexToColor(todoItem.tagColor),
                                isDone = todoItem.isCompleted,
                                onCheckedChange = { isChecked ->
                                    val indexToUpdate = todoItems.indexOfFirst { it.id == todoItem.id }
                                    if (indexToUpdate != -1) {
                                        todoItems[indexToUpdate] = todoItems[indexToUpdate].copy(isCompleted = isChecked)
                                    }
                                },
                                todoTitleText = todoItem.description,
                                priorityTagType = todoItem.priorityType.toPriorityTagType(),
                                isConnected = false,
                                isFirstUndone = false,
                                deadline = deadline,
                            )
                            Spacer(Modifier.height(10.dp))
                        }
                    }
                }
            }
            MementoAiFloatingButton(
                onClick = {},
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
    }
}

@Preview
@Composable
fun TodoScreenPreview() {
    TodoScreen()
}
