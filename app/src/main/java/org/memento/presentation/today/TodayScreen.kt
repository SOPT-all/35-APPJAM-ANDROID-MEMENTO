package org.memento.presentation.today

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.memento.domain.entity.AllDay
import org.memento.presentation.component.MementoScheduleItemWithLine
import org.memento.presentation.component.MementoTodoItemWithLine
import org.memento.presentation.component.MementoTopBar
import org.memento.presentation.component.MementoWeeklyCalendar
import org.memento.presentation.today.component.AllDayScheduleTag
import org.memento.presentation.type.PriorityTagType
import org.memento.ui.theme.MementoTheme
import org.memento.ui.theme.darkModeColors

@Composable
fun TodayScreen(modifier: Modifier = Modifier) {
    val dummyDataState = remember { mutableStateListOf(*dummyData.toTypedArray()) }
    var draggedItemIndex by remember { mutableStateOf(-1) }
    var draggedOffsetY by remember { mutableStateOf(0f) }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(color = darkModeColors.black),
    ) {
        val allDay =
            listOf(
                AllDay("Untitled", "#FF5733"),
                AllDay("SOPT", "#33FF57"),
                AllDay("Fitness", "#3357FF"),
                AllDay("Project", "#FFD700"),
            )

        MementoTopBar(date = "Jan 3", year = "2025", onDateClick = {}, onIconClick = {})

        MementoWeeklyCalendar(onDateClick = {})

        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .heightIn(max = (5 * 30).dp),
        ) {
            val state = rememberLazyListState()
            LazyColumn(
                state = state,
                modifier = Modifier.fillMaxWidth(),
            ) {
                items(4) { index ->
                    AllDayScheduleTag(
                        allDayText = "디자인 가정방문 Day $index",
                        tagColor = if (index % 2 == 0) Color.Red else Color.Blue,
                    )
                }
            }
        }
        if (dummyDataState.isEmpty()) {
            Box(
                modifier =
                    Modifier
                        .fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "No plans yet! Add one now!",
                    style = MementoTheme.typography.body_b_16,
                    color = darkModeColors.gray05,
                )
            }
        } else {
            LazyColumn(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                item {
                    Text(
                        text = "2:00 PM",
                        style = MementoTheme.typography.body_b_16,
                        color = darkModeColors.gray05,
                        modifier =
                            Modifier
                                .padding(top = 16.dp),
                    )
                }
                item {
                    VerticalDivider(thickness = 4.dp, color = Color.White)
                }

                itemsIndexed(dummyDataState) { index, item ->
                    val isDragging = index == draggedItemIndex
                    val scale = animateFloatAsState(if (isDragging) 1.1f else 1f)
                    var initialIndex by remember { mutableStateOf(-1) }
                    var finalIndex by remember { mutableStateOf(-1) }

                    Box(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .graphicsLayer(
                                    scaleX = scale.value,
                                    scaleY = scale.value,
                                    translationY = if (isDragging) draggedOffsetY else 0f,
                                )
                                .pointerInput(Unit) {
                                    detectDragGestures(
                                        onDragStart = {
                                            draggedItemIndex = index
                                            initialIndex = index
                                        },
                                        onDrag = { change, dragAmount ->
                                            change.consume()
                                            draggedOffsetY += dragAmount.y
                                            val targetIndex =
                                                (draggedItemIndex + (draggedOffsetY / 60.dp.toPx()).toInt())
                                                    .coerceIn(0, dummyDataState.size - 1)
                                            if (targetIndex != draggedItemIndex) {
                                                dummyDataState.move(draggedItemIndex, targetIndex)
                                                draggedItemIndex = targetIndex
                                                draggedOffsetY = 0f
                                            }
                                        },
                                        onDragEnd = {
                                            finalIndex = draggedItemIndex
                                            draggedItemIndex = -1
                                            draggedOffsetY = 0f

                                            Log.d("1", finalIndex.toString())
                                            Log.d("1", initialIndex.toString())
                                        },
                                    )
                                },
                    ) {
                        when (item) {
                            is MementoItem.TodoItem -> {
                                MementoTodoItemWithLine(
                                    tagColor = Color.Red,
                                    isDone = item.isChecked,
                                    onCheckedChange = { },
                                    todoTitleText = item.title,
                                    priorityTagType = item.priority,
                                    isConnected = item.isConnected,
                                    isFirstUndone = item.isFirstUndone,
                                )
                            }

                            is MementoItem.ScheduleItem -> {
                                MementoScheduleItemWithLine(
                                    tagColor = Color.Blue,
                                    scheduleTitleText = item.title,
                                    timeRange = item.timeRange,
                                    isConnected = item.isConnected,
                                )
                            }
                        }
                    }
                }
                item {
                    Text(
                        text = "2:00 PM",
                        style = MementoTheme.typography.body_b_16,
                        color = darkModeColors.gray05,
                        modifier =
                            Modifier
                                .padding(bottom = 16.dp),
                    )
                }
            }
        }
    }
}

fun <T> MutableList<T>.move(
    fromIndex: Int,
    toIndex: Int,
) {
    if (fromIndex == toIndex) return
    val item = removeAt(fromIndex)
    add(toIndex, item)
}

fun updateOrders(list: MutableList<MementoItem>) {
    list.forEachIndexed { index, item ->
        when (item) {
            is MementoItem.TodoItem -> item.order = index + 1
            is MementoItem.ScheduleItem -> item.order = index + 1
        }
    }
}

sealed class MementoItem {
    data class TodoItem(
        val id: String,
        val title: String,
        val isChecked: Boolean,
        val priority: PriorityTagType,
        var order: Int,
        val isConnected: Boolean,
        val isFirstUndone: Boolean = false,
    ) : MementoItem()

    data class ScheduleItem(
        val id: String,
        val title: String,
        val timeRange: String,
        var order: Int,
        val isConnected: Boolean,
    ) : MementoItem()
}

val dummyData: List<MementoItem> =
    listOf(
        MementoItem.TodoItem(
            id = "1",
            title = "UXUI 과제",
            isChecked = true,
            priority = PriorityTagType.Immediate,
            order = 6,
            isConnected = true,
            isFirstUndone = true,
        ),
        MementoItem.TodoItem(
            id = "8",
            title = "이건 마지막에",
            isChecked = true,
            priority = PriorityTagType.None,
            order = 9,
            isConnected = false,
        ),
        MementoItem.ScheduleItem(
            id = "2",
            title = "3차 세미나",
            timeRange = "12 PM - 4 PM (4h)",
            order = 3,
            isConnected = true,
        ),
        MementoItem.TodoItem(
            id = "10",
            title = "이건 마지막에",
            isChecked = false,
            priority = PriorityTagType.None,
            order = 12,
            isConnected = false,
            isFirstUndone = true,
        ),
    ).sortedBy { item ->
        when (item) {
            is MementoItem.TodoItem -> item.order
            is MementoItem.ScheduleItem -> item.order
        }
    }

@Preview(showBackground = true)
@Composable
private fun previewToday() {
    TodayScreen()
}
