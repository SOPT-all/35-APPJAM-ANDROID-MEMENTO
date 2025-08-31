package org.memento.presentation.todo

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.memento.core.event.EventBus
import org.memento.core.util.UiState
import org.memento.domain.entity.TargetDate
import org.memento.domain.entity.TodoDetail
import org.memento.domain.repository.AddPlanRepository
import org.memento.domain.repository.ScheduleRepository
import org.memento.domain.repository.TodoRepository
import org.memento.presentation.today.MementoItem
import org.memento.presentation.type.EventType
import timber.log.Timber
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class TodoViewModel
@Inject
constructor(
    private val todoRepository: TodoRepository,
    private val scheduleRepository: ScheduleRepository,
    private val addPlanRepository: AddPlanRepository,
    private val eventBus: EventBus,
) : ViewModel() {
    private val _todoItems = MutableStateFlow<List<MementoItem.TodoItem>>(emptyList())
    val todoItems: StateFlow<List<MementoItem.TodoItem>> = _todoItems

    private val _deleteState = MutableStateFlow<UiState<Unit>>(UiState.Loading)
    val deleteState: StateFlow<UiState<Unit>> = _deleteState

    private val _selectedDate = MutableStateFlow<LocalDate>(LocalDate.now())
    val selectedDate: StateFlow<LocalDate> = _selectedDate

    private val _uiState = MutableStateFlow<UiState<Unit>>(UiState.Loading)
    val uiState: StateFlow<UiState<Unit>> = _uiState

    private val _uiAIState = MutableStateFlow<UiState<Unit>>(UiState.Loading)
    val uiAIState: StateFlow<UiState<Unit>> = _uiAIState

    private val _detailTodoState = MutableStateFlow<UiState<TodoDetail>>(UiState.Loading)
    val detailTodoState: StateFlow<UiState<TodoDetail>> = _detailTodoState

    private val _refreshTrigger = MutableSharedFlow<Unit>(replay = 1)
    val refreshTrigger = _refreshTrigger

    init {
        getTodoList()

        // eventbus의 투두 관련 이벤트를 감지하여 변경
        viewModelScope.launch {
            eventBus.events.collect { event ->
                if (event == EventType.TodoDeleted ||
                    event == EventType.TodoUpdated ||
                    event == EventType.TodoAdded
                ) {
                    _refreshTrigger.emit(Unit)
                }
            }
        }
    }

    fun deletePlan(
        planId: Int,
    ) {
        viewModelScope.launch {
            _deleteState.value = UiState.Loading
            val result = todoRepository.deleteTodo(toDoId = planId)

            _deleteState.value =
                result.fold(
                    onSuccess = {
                        eventBus.emit(EventType.TodoDeleted)
                        UiState.Success(Unit)
                    },
                    onFailure = { throwable ->
                        Timber.e(throwable, "Failed to delete Todo")
                        UiState.Failure
                    },
                )
        }
    }

    fun postPriorityTodo() {
        Timber.e("TodoViewModel", _selectedDate.value.toString())
        viewModelScope.launch {
            val response =
                todoRepository.postPriorityTodo(
                    TargetDate(
                        targetDate = _selectedDate.value.toString(),
                    ),
                )
            response.onSuccess { data ->
                val mappedData =
                    data.priorityTodoList.toDoGetResponses.map { response ->
                        MementoItem.TodoItem(
                            id = response.id,
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
                            groupId = response.groupId,
                        )
                    }
                _todoItems.value = mappedData
                _uiAIState.value = UiState.Success(Unit)
            }.onFailure { throwable ->
                Log.e("TodoViewModel", "Error fetching priority todo", throwable)
                _uiAIState.value = UiState.Failure
            }
        }
    }

    fun getTodoList() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val response = todoRepository.getTodoList()
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
                eventBus.emit(EventType.TodoUpdated)
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

    fun updateSelectedDate(
        selectedDate: LocalDate,
    ) {
        _selectedDate.value = selectedDate
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
                        Timber.e(throwable, "Failed to get todo detail")
                        UiState.Failure
                    },
                )
        }
    }

    fun resetTodoDetailState() {
        _detailTodoState.value = UiState.Loading
    }
}
