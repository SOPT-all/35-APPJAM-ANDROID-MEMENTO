package org.memento.presentation.today

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
import org.memento.domain.repository.ScheduleRepository
import org.memento.domain.repository.TodoRepository
import javax.inject.Inject

@HiltViewModel
class TodayViewModel
@Inject
constructor(
    private val scheduleRepository: ScheduleRepository,
    private val todoRepository: TodoRepository
) : ViewModel() {
    private val _scheduleItems = MutableStateFlow<List<MementoItem.ScheduleItem>>(emptyList())
    val scheduleItems: StateFlow<List<MementoItem.ScheduleItem>> = _scheduleItems

    private val _todoItems = MutableStateFlow<List<MementoItem.TodoItem>>(emptyList())
    val todoItems: StateFlow<List<MementoItem.TodoItem>> = _todoItems

    private val _uiState = MutableStateFlow<UiState<Unit>>(UiState.Loading)
    val uiState: StateFlow<UiState<Unit>> = _uiState


    val combinedItems: StateFlow<List<MementoItem>> = combine(
        _scheduleItems,
        _todoItems
    ) { schedules, todos ->
        (schedules + todos).sortedBy { item ->
            when (item) {
                is MementoItem.TodoItem -> item.order
                is MementoItem.ScheduleItem -> item.order
            }
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())


    fun getScheduleList(date: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val response = scheduleRepository.getScheduleList(date)
            response.onSuccess { data ->
                val mappedData = data.map { response ->
                    MementoItem.ScheduleItem(
                        description = response.description,
                        endDate = response.endDate,
                        id = response.id,
                        isAllDay = response.isAllDay,
                        order = response.order,
                        scheduleType = response.scheduleType,
                        startDate = response.startDate,
                        tagColorCode = response.tagColorCode,
                        tagName = response.tagName
                    )
                }
                _scheduleItems.value = mappedData
                _uiState.value = UiState.Success(Unit)
            }.onFailure {
                _uiState.value = UiState.Failure
            }
        }
    }

    fun getTodoDateList(date: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val response = todoRepository.getTodoDateList(date)
            response.onSuccess { data ->
                val mappedData = data.map { response ->
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
                        order = response.order
                    )
                }
                _todoItems.value = mappedData
                _uiState.value = UiState.Success(Unit)
            }.onFailure {
                _uiState.value = UiState.Failure
            }
        }
    }
}
