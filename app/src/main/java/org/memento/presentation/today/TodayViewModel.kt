package org.memento.presentation.today

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.memento.core.util.UiState
import org.memento.domain.entity.AllDay
import org.memento.domain.entity.DragAndDrop
import org.memento.domain.entity.ScheduleDetail
import org.memento.domain.entity.TodoDetail
import org.memento.domain.entity.UpTime
import org.memento.domain.repository.AddPlanRepository
import org.memento.domain.repository.ScheduleRepository
import org.memento.domain.repository.TodoRepository
import org.memento.presentation.type.DialogType
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class TodayViewModel
@Inject
constructor(
    private val scheduleRepository: ScheduleRepository,
    private val todoRepository: TodoRepository,
    private val addPlanRepository: AddPlanRepository,
) : ViewModel() {
    private val _scheduleItems = MutableStateFlow<List<MementoItem.ScheduleItem>>(emptyList())
    val scheduleItems: StateFlow<List<MementoItem.ScheduleItem>> = _scheduleItems

    private val _todoItems = MutableStateFlow<List<MementoItem.TodoItem>>(emptyList())
    val todoItems: StateFlow<List<MementoItem.TodoItem>> = _todoItems

    private val _detailScheduleState = MutableStateFlow<UiState<ScheduleDetail>>(UiState.Loading)
    val detailScheduleState: StateFlow<UiState<ScheduleDetail>> = _detailScheduleState

    private val _detailTodoState = MutableStateFlow<UiState<TodoDetail>>(UiState.Loading)
    val detailTodoState: StateFlow<UiState<TodoDetail>> = _detailTodoState

    private val _allDayItems = MutableStateFlow<List<AllDay.AllDaySchedules>>(emptyList())
    val allDayItems: StateFlow<List<AllDay.AllDaySchedules>> = _allDayItems

    private val _deleteState = MutableStateFlow<UiState<Unit>>(UiState.Loading)
    val deleteState: StateFlow<UiState<Unit>> = _deleteState

    private val _upTimeState = MutableStateFlow<UpTime?>(null)
    val upTimeState: StateFlow<UpTime?> = _upTimeState

    private val _wakeUpTime = MutableStateFlow<String?>(null)
    val wakeUpTime: StateFlow<String?> = _wakeUpTime

    private val _windDownTime = MutableStateFlow<String?>(null)
    val windDownTime: StateFlow<String?> = _windDownTime

    private val _dragAndDropState = MutableStateFlow<UiState<Unit>>(UiState.Loading)
    val dragAndDropState: StateFlow<UiState<Unit>> = _dragAndDropState

    private val _uiState = MutableStateFlow<UiState<Unit>>(UiState.Loading)
    val uiState: StateFlow<UiState<Unit>> = _uiState

    val combinedItems: StateFlow<List<MementoItem>> =
        combine(
            _scheduleItems,
            _todoItems,
        ) { schedules, todos ->
            val sortedTodos = todos
                .sortedWith(
                    compareByDescending<MementoItem.TodoItem> { it.isCompleted }
                        .thenBy { it.order }
                )

            val sortedSchedules = schedules.sortedBy { it.order }

            (sortedTodos + sortedSchedules)
        }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())



    fun reorderItems(fromIndex: Int, toIndex: Int) {
        if (fromIndex == toIndex) return

        Log.d("DragDebug", "reorderItems: from=$fromIndex to=$toIndex")

        val currentList = combinedItems.value.toMutableList()
        val movedItem = currentList[fromIndex]

        if (movedItem !is MementoItem.TodoItem) return

        currentList.removeAt(fromIndex)
        currentList.add(toIndex, movedItem)

        // 모든 아이템에 새로운 order 값 할당 (10씩 증가)
        var order = 10.0
        val updatedTodos = mutableListOf<MementoItem.TodoItem>()
        val updatedSchedules = mutableListOf<MementoItem.ScheduleItem>()

        currentList.forEach { item ->
            when (item) {
                is MementoItem.TodoItem -> {
                    updatedTodos.add(item.copy(order = order))
                }
                is MementoItem.ScheduleItem -> {
                    updatedSchedules.add(item.copy(order = order))
                }
            }
            // 간격 확보용
            order += 10.0
        }

        // UI 바로 업데이트
        _todoItems.value = updatedTodos
        _scheduleItems.value = updatedSchedules
    }

    //드래그 앤 드롭 서버통신 실패 (테스트용  로그, 추후 서버 연결 완료되면 지우겠습니다)
    fun patchDragAndDrop(
        toDoId: Int,
        previousToDoId: Int,
        nextToDoId: Int
    ) {
        viewModelScope.launch {
            _dragAndDropState.value = UiState.Loading

            val dragAndDrop = DragAndDrop(previousToDoId = previousToDoId, nextToDoId = nextToDoId)

            Log.d("drag", "📤 patchDragAndDrop() called with → toDoId: $toDoId, body: $dragAndDrop")

            val result = todoRepository.patchDragAndDrop(toDoId, dragAndDrop)

            _dragAndDropState.value = result.fold(
                onSuccess = { UiState.Success(Unit) },
                onFailure = {
                    Timber.e(it, "드래그 앤 드롭 서버통신 실패")
                    if (it is HttpException) {
                        val error = it.response()?.errorBody()?.string()
                        Log.e("drag", "❌ HTTP ${it.code()} Error Body: $error")
                    }
                    UiState.Failure
                }
            )
        }
    }

    fun getScheduleList(date: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val response = scheduleRepository.getScheduleList(date)
            response.onSuccess { data ->
                val mappedData =
                    data.map { response ->
                        MementoItem.ScheduleItem(
                            description = response.description,
                            endDate = response.endDate,
                            id = response.id,
                            isAllDay = response.isAllDay,
                            order = response.order,
                            scheduleType = response.scheduleType,
                            startDate = response.startDate,
                            tagColorCode = response.tagColorCode,
                            tagName = response.tagName,
                            timeDuration = response.timeDuration,
                        )
                    }
                _scheduleItems.value = mappedData
                _uiState.value = UiState.Success(Unit)
            }.onFailure {
                _uiState.value = UiState.Failure
            }
        }
    }

    fun getScheduleDetail(scheduleId: Int) {
        viewModelScope.launch {
            _detailScheduleState.value = UiState.Loading
            val result = addPlanRepository.getScheduleDetail(scheduleId = scheduleId)
            _detailScheduleState.value =
                result.fold(
                    onSuccess = {
                        UiState.Success(it)
                    },


                    onFailure = { throwable ->
                        Timber.e(throwable, "Failed to post plan")
                        Timber.tag("dd")
                        UiState.Failure
                    },
                )
        }
    }

    fun getTodoDetail(todoId: Int) {
        viewModelScope.launch {
            _detailTodoState.value = UiState.Loading
            val result = addPlanRepository.getTodoDetail(todoId = todoId)
            _detailTodoState.value =
                result.fold(
                    onSuccess = {
                        UiState.Success(it)
                    },
                    onFailure = { throwable ->
                        Timber.e(throwable, "Failed to post plan")
                        UiState.Failure
                    },
                )
        }
    }

    fun getAllDay() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val response = addPlanRepository.getAllDay()
            response.onSuccess { data ->
                val mappedData =
                    data.map { response ->
                        AllDay.AllDaySchedules(
                            description = response.description,
                            endDate = response.endDate,
                            id = response.id,
                            isAllDay = response.isAllDay,
                            scheduleType = response.scheduleType,
                            startDate = response.startDate,
                            tagColorCode = response.tagColorCode,
                            tagName = response.tagName,
                        )
                    }
                _allDayItems.value = mappedData
                _uiState.value = UiState.Success(Unit)
            }.onFailure {
                _uiState.value = UiState.Failure
            }
        }
    }

    // 일정 삭제 로직
    fun deletePlan(
        planId: Int,
        dialogType: DialogType,
    ) {
        when (dialogType) {
            DialogType.SCHEDULE -> {
                viewModelScope.launch {
                    _deleteState.value = UiState.Loading
                    val result = scheduleRepository.deleteSchedule(scheduleId = planId)

                    _deleteState.value =
                        result.fold(
                            onSuccess = { UiState.Success(Unit) },
                            onFailure = { throwable ->
                                Timber.e(throwable, "Failed to delete schedule")
                                UiState.Failure
                            },
                        )
                }
            }

            else -> {
                viewModelScope.launch {
                    _deleteState.value = UiState.Loading
                    val result = todoRepository.deleteTodo(toDoId = planId)

                    _deleteState.value =
                        result.fold(
                            onSuccess = { UiState.Success(Unit) },
                            onFailure = { throwable ->
                                Timber.e(throwable, "Failed to delete schedule")
                                UiState.Failure
                            },
                        )
                }
            }
        }
    }

    fun getTodoDateList(date: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val response = todoRepository.getTodoDateList(date)
            response.onSuccess { data ->
                val mappedData =
                    data.map { response ->
                        MementoItem.TodoItem(
                            id = response.id,
                            groupId = response.groupId,
                            description = response.description,
                            date = response.startDate,
                            deadline = response.endDate,
                            isCompleted = response.isCompleted,
                            priorityValue = response.priorityValue,
                            priorityType = response.priorityType,
                            tagName = response.tagName,
                            tagColor = response.tagColor,
                            toDoType = response.toDoType,
                            order = response.order,
                        )
                    }
                _todoItems.value = mappedData
                _uiState.value = UiState.Success(Unit)
            }.onFailure {
                _uiState.value = UiState.Failure
            }
        }
    }

    fun updateTodoCompletion(
        id: Int,
        isCompleted: Boolean,
    ) {
        viewModelScope.launch {
            _todoItems.value =
                _todoItems.value.map { todo ->
                    if (todo.id == id) todo.copy(isCompleted = isCompleted) else todo
                }

            _uiState.value = UiState.Loading
            val result = todoRepository.patchTodoComplete(id.toInt())
            result.onSuccess {
                _uiState.value = UiState.Success(Unit)
            }.onFailure {
                _todoItems.value =
                    _todoItems.value.map { todo ->
                        if (todo.id == id) todo.copy(isCompleted = !isCompleted) else todo
                    }
                _uiState.value = UiState.Failure
            }
        }
    }

    fun getUpTime() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val response = scheduleRepository.getUpTime()
            response.onSuccess { data ->
                _upTimeState.value =
                    UpTime(
                        wakeUpTime = data.wakeUpTime,
                        windDownTime = data.windDownTime,
                    )
                _uiState.value = UiState.Success(Unit)
            }.onFailure { throwable ->
                _uiState.value = UiState.Failure
            }
        }
    }
}
