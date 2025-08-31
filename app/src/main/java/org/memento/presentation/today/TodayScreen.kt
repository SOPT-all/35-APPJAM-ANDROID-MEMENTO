package org.memento.presentation.today

import android.app.Activity
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.memento.R
import org.memento.presentation.component.MementoAiFloatingButton
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
import org.memento.presentation.util.changeHexToColor
import org.memento.presentation.util.toPriorityTagType
import org.memento.presentation.util.todoFormatDate
import org.memento.ui.theme.MementoTheme
import org.memento.ui.theme.darkModeColors
import org.memento.ui.theme.mementoColors
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodayScreen(
    modifier: Modifier = Modifier,
    viewModel: TodayViewModel = hiltViewModel(),
    padding: PaddingValues,
    navigateToSetting: () -> Unit,
) {
    var draggedItemIndex by remember { mutableStateOf(-1) }
    var itemHeight by remember { mutableIntStateOf(0) }
    var listHeight by remember { mutableIntStateOf(0) }
    val density = LocalDensity.current

    var showDetailDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    val sheetEditTodoState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showEditTodoBottomSheet by remember { mutableStateOf(false) }
    val sheetEditScheduleState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showEditScheduleBottomSheet by remember { mutableStateOf(false) }
    var selectedPlanId by remember { mutableIntStateOf(3) }
    var dialogType by remember { mutableStateOf(DialogType.TO_DO) }
    var refreshTrigger by remember { mutableStateOf(false) }

    val today = LocalDate.now()
    val nowYear = LocalDate.now().year.toString()
    val selectedDate = remember { mutableStateOf(today) }
    val coroutineScope = rememberCoroutineScope()

    var isDraggingEnabled by remember { mutableStateOf(false) }
    var isRefreshing by remember { mutableStateOf(false) }

    var backPressedTime by remember { mutableLongStateOf(0L) }
    val backPressThreshold = 2000
    val context = LocalContext.current

    var actualItemHeight by remember { mutableStateOf(0f) }
    var draggedItemId by remember { mutableStateOf<Int?>(null) }
    var draggedOffsetY by remember { mutableStateOf(0f) }

    BackHandler {
        val currentTime = System.currentTimeMillis()
        if (currentTime - backPressedTime <= backPressThreshold) {
            (context as? Activity)?.finish()
        } else {
            backPressedTime = currentTime
        }
    }

    val closeTodoBottomSheet = {
        showEditTodoBottomSheet = false
        coroutineScope.launch {
            sheetEditTodoState.hide()
        }
    }

    val closeScheduleBottomSheet = {
        showEditScheduleBottomSheet = false
        coroutineScope.launch {
            sheetEditScheduleState.hide()
        }
    }

    LaunchedEffect(selectedDate.value) {
        val selectedDate = selectedDate.value.toString()
        viewModel.getTodoDateList(selectedDate)
        viewModel.getScheduleList(selectedDate)
        viewModel.getAllDay()
        viewModel.getUpTime()
    }

    LaunchedEffect(refreshTrigger) {
        viewModel.getScheduleList(selectedDate.value.toString())
        viewModel.getTodoDateList(selectedDate.value.toString())
        viewModel.getAllDay()
    }

    val combinedItems by viewModel.combinedItems.collectAsState()
    val allDayItems by viewModel.allDayItems.collectAsState()
    val upTimeState by viewModel.upTimeState.collectAsState()


    val filteredAllDayItems =
        allDayItems.filter { item ->
            val startDate = LocalDate.parse(item.startDate.substring(0, 10))
            val endDate = LocalDate.parse(item.endDate.substring(0, 10))
            selectedDate.value in startDate..endDate
        }

    fun resetDraggingState() {
        draggedItemIndex = -1
        draggedOffsetY = 0f
    }

    fun refreshData() {
        coroutineScope.launch {
            isRefreshing = true
            delay(2000)
            isRefreshing = false
        }
    }

    fun triggerRefresh() {
        refreshTrigger = !refreshTrigger
    }

    Box(
        modifier =
        Modifier
            .fillMaxSize()
            .padding(padding),
    ) {
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
                onSettingClick = { navigateToSetting() },
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
                ) {
                    items(filteredAllDayItems, key = { it.id }) { item ->
                        AllDayScheduleTag(
                            allDayText = item.description,
                            tagColor = changeHexToColor(item.tagColorCode),
                        )
                    }
                }
            }

            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing),
                onRefresh = {
                    refreshData()
                    viewModel.updateCurrentTime()
                    viewModel.getScheduleList(selectedDate.value.toString())
                    viewModel.getTodoDateList(selectedDate.value.toString())
                    viewModel.getAllDay()

                },
            ) {
                if (combinedItems.isEmpty()) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        item {
                            Spacer(modifier = Modifier.height(150.dp))
                            Text(
                                text = stringResource(R.string.today_empty_view_text),
                                style = MementoTheme.typography.title_b_22,
                                color = darkModeColors.gray08,
                                modifier = Modifier.padding(horizontal = 16.dp),
                            )
                        }
                    }
                } else {
                    LazyColumn(
                        modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .onGloballyPositioned { it ->
                                listHeight = it.size.height
                            },
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        userScrollEnabled = !isDraggingEnabled,
                    ) {
                        item {
                            Row {
                                Text(
                                    text = upTimeState?.wakeUpTime ?: "8:00",
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
                                    text = stringResource(R.string.wake_up),
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

                        itemsIndexed(combinedItems, key = { _, item ->
                            when (item) {
                                is MementoItem.TodoItem -> "todo_${item.id}"
                                is MementoItem.ScheduleItem -> "schedule_${item.id}"
                            }
                        }) { index, item ->

                            val isDragging = when (item) {
                                is MementoItem.TodoItem -> draggedItemId == item.id
                                is MementoItem.ScheduleItem -> false
                            }
                            val scale by animateFloatAsState(if (isDragging) 1.1f else 1f)

                            val canDrag = item is MementoItem.TodoItem

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .onGloballyPositioned { coordinates ->
                                        if (index == 0 && actualItemHeight == 0f) {
                                            actualItemHeight = coordinates.size.height.toFloat()
                                        }
                                    }
                                    .graphicsLayer(
                                        scaleX = scale,
                                        scaleY = scale,
                                        translationY = if (isDragging) draggedOffsetY else 0f,
                                    )
                                    .zIndex(if (isDragging) 1f else 0f)
                                    .then(
                                        if (canDrag){
                                            // 캐스팅
                                            val todoItem = item as MementoItem.TodoItem

                                            Modifier.pointerInput(item.id) {
                                                detectDragGesturesAfterLongPress(
                                                    onDragStart = {
                                                        // ID로 추적 추가
                                                        draggedItemId = (item as? MementoItem.TodoItem)?.id ?: -1
                                                        draggedItemIndex = index
                                                        isDraggingEnabled = true
                                                    },

                                                    onDrag = { change, dragAmount ->
                                                        if (isDraggingEnabled && draggedItemId != null) {
                                                            change.consume()
                                                            draggedOffsetY += dragAmount.y

                                                            val itemSpacing = with(density) { 8.dp.toPx() }
                                                            val totalItemHeight = actualItemHeight + itemSpacing

                                                            // 드래그 거리가 아이템 높이를 넘으면 위치 변경
                                                            while (kotlin.math.abs(draggedOffsetY) >= totalItemHeight) {
                                                                val direction = if (draggedOffsetY > 0) 1 else -1

                                                                // 현재 드래그 중인 아이템의 실제 인덱스 찾기
                                                                var currentDraggedIndex = -1
                                                                combinedItems.forEachIndexed { idx, item ->
                                                                    if (item is MementoItem.TodoItem && item.id == draggedItemId) {
                                                                        currentDraggedIndex = idx
                                                                    }
                                                                }

                                                                if (currentDraggedIndex == -1) {
                                                                    break
                                                                }

                                                                val targetIndex = (currentDraggedIndex + direction).coerceIn(0, combinedItems.size - 1)

                                                                if (targetIndex != currentDraggedIndex) {
                                                                    val targetItem = combinedItems[targetIndex]

                                                                    if (targetItem is MementoItem.ScheduleItem) {
                                                                        // 일정 건너뛰기
                                                                        val nextIndex = (targetIndex + direction).coerceIn(0, combinedItems.size - 1)
                                                                        if (nextIndex != targetIndex && combinedItems[nextIndex] is MementoItem.TodoItem) {
                                                                            viewModel.reorderItems(currentDraggedIndex, nextIndex)
                                                                            draggedOffsetY -= direction * totalItemHeight * 2
                                                                        } else {
                                                                            draggedOffsetY = 0f
                                                                            break
                                                                        }
                                                                    } else {
                                                                        // TodoItem으로 이동
                                                                        viewModel.reorderItems(currentDraggedIndex, targetIndex)
                                                                        draggedOffsetY -= direction * totalItemHeight
                                                                    }
                                                                } else {
                                                                    draggedOffsetY = 0f
                                                                    break
                                                                }
                                                            }
                                                        }
                                                    }
                                                    ,
                                                    onDragEnd = {
                                                        if (draggedItemIndex != -1) {
                                                            val item = combinedItems.getOrNull(draggedItemIndex)
                                                            if (item is MementoItem.TodoItem) {
                                                                var prevTodoId: Int? = null
                                                                var nextTodoId: Int? = null

                                                                for (i in draggedItemIndex - 1 downTo 0) {
                                                                    if (combinedItems[i] is MementoItem.TodoItem) {
                                                                        prevTodoId = (combinedItems[i] as MementoItem.TodoItem).id
                                                                        break
                                                                    }
                                                                }

                                                                for (i in draggedItemIndex + 1 until combinedItems.size) {
                                                                    if (combinedItems[i] is MementoItem.TodoItem) {
                                                                        nextTodoId = (combinedItems[i] as MementoItem.TodoItem).id
                                                                        break
                                                                    }
                                                                }

                                                                viewModel.patchDragAndDrop(
                                                                    toDoId = item.id,
                                                                    previousToDoId = prevTodoId ?: item.id,
                                                                    nextToDoId = nextTodoId ?: item.id
                                                                )
                                                            }
                                                        }

                                                        // 리셋 시 ID도 null로
                                                        draggedItemId = null
                                                        resetDraggingState()
                                                        isDraggingEnabled = false
                                                    },
                                                    onDragCancel = {
                                                        // 드래그 취소 시 상태 초기화
                                                        draggedItemId = null
                                                        resetDraggingState()
                                                        isDraggingEnabled = false
                                                    }
                                                )
                                            }
                        }
                                        else Modifier
                                    )
                            )
                           {
                                when (item) {
                                    is MementoItem.TodoItem -> {
                                        MementoTodoItemWithLine(
                                            tagColor = changeHexToColor(item.tagColor),
                                            isDone = item.isCompleted,
                                            isNow = item.isNow,
                                            onCheckedChange = { newChecked ->
                                                viewModel.updateTodoCompletion(item.id, newChecked)
                                            },
                                            modifier = if (item.isDimmed) Modifier.alpha(0.5f) else Modifier,

                                            todoTitleText = item.description,
                                            priorityTagType = item.priorityType.toPriorityTagType(),
                                            isConnected = item.toDoType.toBoolean(),
                                            onClick = {
                                                selectedPlanId = item.id
                                                dialogType = DialogType.TO_DO
                                                showDetailDialog = true
                                            },
                                        )
                                    }

                                    is MementoItem.ScheduleItem -> {
                                        MementoScheduleItemWithLine(
                                            tagColor = changeHexToColor(item.tagColorCode),
                                            scheduleTitleText = item.description,
                                            isNow = item.isNow,
                                            modifier = if (item.isDimmed) Modifier.alpha(0.5f) else Modifier,
                                            timeRange = item.timeDuration,
                                            isChecked = item.isDimmed,

                                            onClick = {
                                                selectedPlanId = item.id
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
                                    text = upTimeState?.windDownTime ?: "22:00",
                                    style = MementoTheme.typography.detail_b_12,
                                    color = darkModeColors.gray07,
                                    modifier =
                                    Modifier
                                        .padding(bottom = 16.dp),
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = stringResource(R.string.wind_down),
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
        }

        MementoDialog(
            showDialog = showDetailDialog,
            onDismiss = { showDetailDialog = false },
            onDelete = { showDeleteDialog = true },
            onEdit = {
                if (dialogType == DialogType.TO_DO) {
                    showEditTodoBottomSheet = true
                } else {
                    showEditScheduleBottomSheet = true
                }
            },
            dialogType = dialogType,
            planId = selectedPlanId,
            refreshKey = refreshTrigger,
        )
        if (showDeleteDialog) {
            MementoAlertDialog(
                content = R.string.alert_delete,
                leftButtonText = R.string.alert_cancel_button,
                rightButtonText = R.string.alert_delete_button,
                onLeftButtonClick = { showDeleteDialog = false },
                onRightButtonClick = {
                    viewModel.deletePlan(planId = selectedPlanId, dialogType = dialogType)
                    showDeleteDialog = false
                    showDetailDialog = false
                    triggerRefresh()
                },
            )
        }

        MementoEditTodoBottomSheet(
            isOpenBottomSheet = showEditTodoBottomSheet,
            sheetState = sheetEditTodoState,
            onCancel = { closeTodoBottomSheet() },
            onConfirm = {
                triggerRefresh()
                closeTodoBottomSheet()
            },
            planId = selectedPlanId,
        )

        MementoEditScheduleBottomSheet(
            isOpenBottomSheet = showEditScheduleBottomSheet,
            sheetState = sheetEditScheduleState,
            onCancel = { closeScheduleBottomSheet() },
            onConfirm = {
                triggerRefresh()
                closeScheduleBottomSheet()
            },
            planId = selectedPlanId,
        )
        MementoAiFloatingButton(
            onClick = {
            },
            modifier =
            Modifier
                .padding(20.dp)
                .align(Alignment.BottomEnd),
        )
    }
}

sealed class MementoItem {
    data class TodoItem(
        val id: Int,
        val groupId: String? = null,
        val description: String,
        val date: String,
        val deadline: String,
        val isCompleted: Boolean,
        val priorityValue: Double,
        val priorityType: String,
        val tagName: String,
        val tagColor: String,
        val toDoType: String,
        val order: Double,
        val isDimmed: Boolean = false,
        val isNow: Boolean = false
    ) : MementoItem()

    data class ScheduleItem(
        val description: String,
        val endDate: String,
        val id: Int,
        val isAllDay: Boolean,
        val order: Double,
        val scheduleType: String,
        val startDate: String,
        val tagColorCode: String,
        val tagName: String,
        val timeDuration: String,
        val isDimmed: Boolean = false,
        val isNow: Boolean = false
    ) : MementoItem()
}

@Preview(showBackground = true)
@Composable
private fun previewToday() {
//    TodayScreen(x
}
