package org.memento.presentation.today

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.memento.R
import org.memento.presentation.component.MementoAlertDialog
import org.memento.presentation.component.MementoDialog
import org.memento.presentation.component.MementoEditScheduleBottomSheet
import org.memento.presentation.component.MementoEditTodoBottomSheet
import org.memento.presentation.component.MementoScheduleItemWithLine
import org.memento.presentation.component.MementoTodoItemWithLine
import org.memento.presentation.component.MementoTopBar
import org.memento.presentation.component.MementoWeeklyCalendar
import org.memento.presentation.today.component.AllDayScheduleTag
import org.memento.presentation.type.DialogType
import org.memento.presentation.type.PriorityTagType
import org.memento.presentation.util.todoFormatDate
import org.memento.ui.theme.MementoTheme
import org.memento.ui.theme.darkModeColors
import org.memento.ui.theme.mementoColors
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodayScreen(
    modifier: Modifier = Modifier,
    wakeUpTime: String = "8 AM",
    windDownTime: String = "22 PM",
    viewModel: TodayViewModel = hiltViewModel(),
) {
    val dummyDataState = remember { mutableStateListOf(*dummyData.toTypedArray()) }
    var draggedItemIndex by remember { mutableStateOf(-1) }
    var draggedOffsetY by remember { mutableStateOf(0f) }
    var itemHeight by remember { mutableIntStateOf(0) }
    var listHeight by remember { mutableIntStateOf(0) }
    val density = LocalDensity.current

    var showDetailDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    val sheetEditTodoState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showEditTodoBottomSheet by remember { mutableStateOf(false) }
    val sheetEditScheduleState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showEditScheduleBottomSheet by remember { mutableStateOf(false) }
    var selectedPlanId by remember { mutableIntStateOf(13) }
    var dialogType by remember { mutableStateOf(DialogType.TO_DO) }

    val today = LocalDate.now()
    val nowYear = LocalDate.now().year.toString()
    val selectedDate = remember { mutableStateOf(today) }
    val coroutineScope = rememberCoroutineScope()

    val isDraggingEnabled = remember { mutableStateOf(false) }
    var isRefreshing by remember { mutableStateOf(false) }

    fun resetDraggingState() {
        draggedItemIndex = -1
        draggedOffsetY = 0f
        isDraggingEnabled.value = false
    }

    fun refreshData() {
        coroutineScope.launch {
            isRefreshing = true
            delay(1000)
            isRefreshing = false
        }
    }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(color = darkModeColors.black),
    ) {
        MementoTopBar(
            date = todoFormatDate(today),
            year = nowYear,
            onDateClick = { selectedDate.value = today },
            onIconClick = {},
        )

        MementoWeeklyCalendar(
            onDateClick = { newDate ->
                selectedDate.value = newDate
                coroutineScope.launch { }
            },
            selectedDate = selectedDate.value,
        )

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
                items(2) { index ->
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
                        .fillMaxSize()
                        .padding(top = 150.dp),
                contentAlignment = Alignment.TopCenter,
            ) {
                Text(
                    text = "No plans yet? Add one now!",
                    style = MementoTheme.typography.title_b_22,
                    color = darkModeColors.gray08,
                )
            }
        } else {
            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing),
                onRefresh = { refreshData() },
            ) {
                LazyColumn(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .onGloballyPositioned { it ->
                                listHeight = it.size.height
                            },
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    userScrollEnabled = !isDraggingEnabled.value,
                ) {
                    item {
                        Row {
                            Text(
                                text = wakeUpTime,
                                style = MementoTheme.typography.detail_b_12,
                                color = darkModeColors.gray07,
                                modifier =
                                    Modifier
                                        .padding(top = 16.dp)
                                        .onGloballyPositioned { it ->
                                            itemHeight = it.size.height
                                        },
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "Wake Up",
                                style = MementoTheme.typography.detail_b_12,
                                color = darkModeColors.gray07,
                                modifier =
                                    Modifier
                                        .padding(top = 16.dp)
                                        .onGloballyPositioned { it ->
                                            itemHeight = it.size.height
                                        },
                            )
                        }
                    }

                    itemsIndexed(dummyDataState) { index, item ->
                        val isDragging = index == draggedItemIndex
                        val scale = animateFloatAsState(if (isDragging) 1.1f else 1f)

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
                                        detectDragGesturesAfterLongPress(
                                            onDragStart = {
                                                draggedItemIndex = index
                                                isDraggingEnabled.value = true
                                            },
                                            onDrag = { change, dragAmount ->
                                                if (isDraggingEnabled.value) {
                                                    change.consume()
                                                    draggedOffsetY += dragAmount.y
                                                    val itemHeightPx = with(density) { 60.dp.toPx() }

                                                    while (kotlin.math.abs(draggedOffsetY) >= itemHeightPx) {
                                                        val moveDirection =
                                                            if (draggedOffsetY > 0) 1 else -1
                                                        val targetIndex =
                                                            (draggedItemIndex + moveDirection)
                                                                .coerceIn(0, dummyDataState.size - 1)

                                                        if (targetIndex != draggedItemIndex) {
                                                            dummyDataState.move(
                                                                draggedItemIndex,
                                                                targetIndex,
                                                            )
                                                            draggedItemIndex = targetIndex
                                                            draggedOffsetY -= moveDirection * itemHeightPx
                                                        } else {
                                                            draggedOffsetY = 0f
                                                            break
                                                        }
                                                    }
                                                }
                                            },
                                            onDragEnd = { resetDraggingState() },
                                            onDragCancel = { resetDraggingState() },
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
                                        isNow = item.isNow,
                                        onClick = {
                                            // selectedPlanId = item.id
                                            dialogType = DialogType.TO_DO
                                            showDetailDialog = true
                                        },
                                    )
                                }

                                is MementoItem.ScheduleItem -> {
                                    MementoScheduleItemWithLine(
                                        tagColor = Color.Blue,
                                        scheduleTitleText = item.title,
                                        timeRange = item.timeRange,
                                        isConnected = item.isConnected,
                                        isNow = item.isNow,
                                        onClick = {
//                                            selectedPlanId = schedule.id
                                            dialogType = DialogType.SCHEDULE
                                            showDetailDialog = true
                                        },
                                    )
                                }
                            }
                        }
                    }

                    item {
                        Row {
                            Text(
                                text = windDownTime,
                                style = MementoTheme.typography.detail_b_12,
                                color = darkModeColors.gray07,
                                modifier =
                                    Modifier
                                        .padding(bottom = 16.dp),
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "Wind down",
                                style = MementoTheme.typography.detail_b_12,
                                color = darkModeColors.gray07,
                                modifier =
                                    Modifier
                                        .padding(bottom = 16.dp),
                            )
                        }
                    }
                }
                Column(
                    modifier =
                        Modifier
                            .offset(x = 28.dp)
                            .width(1.dp)
                            .height(with(density) { listHeight.toDp() })
                            .padding(vertical = with(density) { itemHeight.toDp() + 16.dp })
                            .background(
                                brush =
                                    Brush.linearGradient(
                                        colors =
                                            listOf(
                                                Color.Transparent,
                                                mementoColors.progressBar,
                                                mementoColors.progressBar,
                                                mementoColors.progressBar,
                                                Color.Transparent,
                                            ),
                                    ),
                            ),
                ) {}
            }
        }

        MementoDialog(
            showDialog = showDetailDialog,
            onDismiss = { showDetailDialog = false },
            onDelete = { showDeleteDialog = true },
            onEdit = { if (dialogType == DialogType.TO_DO) showEditTodoBottomSheet = true else showEditScheduleBottomSheet = true },
            dialogType = dialogType,
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
                        dialogType = dialogType,
                    )
                    showDeleteDialog = false
                },
            )
        }

        MementoEditTodoBottomSheet(
            isOpenBottomSheet = showEditTodoBottomSheet,
            sheetState = sheetEditTodoState,
            onConfirm = { showEditTodoBottomSheet = false },
            planId = selectedPlanId,
        )

        MementoEditScheduleBottomSheet(
            isOpenBottomSheet = showEditScheduleBottomSheet,
            sheetState = sheetEditScheduleState,
            onConfirm = { showEditScheduleBottomSheet = false },
            planId = selectedPlanId,
        )
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

sealed class MementoItem {
    data class TodoItem(
        val id: String,
        val title: String,
        val isChecked: Boolean,
        val priority: PriorityTagType,
        var order: Int,
        val isConnected: Boolean,
        val isFirstUndone: Boolean = false,
        var isNow: Boolean = false,
    ) : MementoItem()

    data class ScheduleItem(
        val id: String,
        val title: String,
        val timeRange: String,
        var order: Int,
        val isConnected: Boolean,
        var isNow: Boolean = false,
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
            isNow = true,
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
            isNow = false,
        ),
        MementoItem.ScheduleItem(
            id = "2",
            title = "3차 세미나",
            timeRange = "12 PM - 4 PM (4h)",
            order = 3,
            isConnected = true,
            isNow = false,
        ),
        MementoItem.ScheduleItem(
            id = "2",
            title = "3차 세미나",
            timeRange = "12 PM - 4 PM (4h)",
            order = 3,
            isConnected = true,
            isNow = false,
        ),
        MementoItem.ScheduleItem(
            id = "2",
            title = "3차 세미나",
            timeRange = "12 PM - 4 PM (4h)",
            order = 3,
            isConnected = true,
            isNow = false,
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
