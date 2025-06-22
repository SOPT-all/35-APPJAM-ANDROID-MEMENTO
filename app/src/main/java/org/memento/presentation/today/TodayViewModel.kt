package org.memento.presentation.today

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.memento.core.event.EventBus
import org.memento.core.util.UiState
import org.memento.domain.entity.AllDay
import org.memento.domain.entity.ScheduleDetail
import org.memento.domain.entity.TodoDetail
import org.memento.domain.entity.UpTime
import org.memento.domain.repository.AddPlanRepository
import org.memento.domain.repository.ScheduleRepository
import org.memento.domain.repository.TodoRepository
import org.memento.presentation.type.DialogType
import org.memento.presentation.type.EventType
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TodayViewModel
    @Inject
    constructor(
        private val scheduleRepository: ScheduleRepository,
        private val todoRepository: TodoRepository,
        private val addPlanRepository: AddPlanRepository,
        private val eventBus: EventBus,
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

        private val _uiState = MutableStateFlow<UiState<Unit>>(UiState.Loading)
        val uiState: StateFlow<UiState<Unit>> = _uiState

        // TodayScreen refresh를 위한 SharedFlow
        private val _refreshTrigger = MutableSharedFlow<Unit>()
        val refreshTrigger = _refreshTrigger

        val combinedItems: StateFlow<List<MementoItem>> =
            combine(
                _scheduleItems,
                _todoItems,
            ) { schedules, todos ->
                (schedules + todos).sortedBy { item ->
                    when (item) {
                        is MementoItem.TodoItem -> item.order
                        is MementoItem.ScheduleItem -> item.order
                    }
                }
            }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

        init {
            // eventbus의 이벤트를 감지하여 변경
            viewModelScope.launch {
                eventBus.events.collect { event ->
                    when (event) {
                        EventType.TodoAdded, EventType.TodoUpdated, EventType.TodoDeleted -> {
                            // Todo 관련 이벤트 발생 시 Today 데이터 갱신
                            _refreshTrigger.emit(Unit)
                        }
                        EventType.ScheduleAdded, EventType.ScheduleUpdated, EventType.ScheduleDeleted -> {
                            // Schedule 관련 이벤트 발생 시 Today 데이터 갱신
                            _refreshTrigger.emit(Unit)
                        }
                    }
                }
            }
        }

        // 데이터 새로고침 로직
        fun refreshTodayData() {
            viewModelScope.launch {
                _refreshTrigger.emit(Unit)
            }
        }

        fun reorderItems(
            fromIndex: Int,
            toIndex: Int,
        ) {
            val currentList =
                (scheduleItems.value + todoItems.value)
                    .sortedBy { item ->
                        when (item) {
                            is MementoItem.TodoItem -> item.order
                            is MementoItem.ScheduleItem -> item.order
                            else -> 0.0
                        }
                    }
                    .toMutableList()

            val movedItem = currentList.removeAt(fromIndex)
            currentList.add(toIndex, movedItem)

            val updatedList =
                currentList.mapIndexed { index, item ->
                    when (item) {
                        is MementoItem.TodoItem -> item.copy(order = index.toDouble())
                        is MementoItem.ScheduleItem -> item.copy(order = index.toDouble())
                    }
                }

            _todoItems.value = updatedList.filterIsInstance<MementoItem.TodoItem>()
            _scheduleItems.value = updatedList.filterIsInstance<MementoItem.ScheduleItem>()
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
                                onSuccess = {
                                    viewModelScope.launch {
                                        eventBus.emit(EventType.ScheduleDeleted)
                                    }
                                    UiState.Success(Unit)
                                },
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
                                onSuccess = {
                                    viewModelScope.launch {
                                        eventBus.emit(EventType.TodoDeleted)
                                    }
                                    UiState.Success(Unit)
                                },
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
                    viewModelScope.launch {
                        eventBus.emit(EventType.TodoUpdated)
                    }
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
