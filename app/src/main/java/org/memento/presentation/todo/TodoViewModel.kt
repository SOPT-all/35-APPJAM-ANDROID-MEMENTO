package org.memento.presentation.todo

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.memento.core.util.UiState
import org.memento.domain.entity.TargetDate
import org.memento.domain.repository.TodoRepository
import org.memento.presentation.today.MementoItem
import timber.log.Timber
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class TodoViewModel
    @Inject
    constructor(
        private val todoRepository: TodoRepository,
    ) : ViewModel() {
        private val _todoItems = MutableStateFlow<List<MementoItem.TodoItem>>(emptyList())
        val todoItems: StateFlow<List<MementoItem.TodoItem>> = _todoItems

        private val _selectedDate = MutableStateFlow<LocalDate>(LocalDate.now())
        val selectedDate: StateFlow<LocalDate> = _selectedDate

        private val _uiState = MutableStateFlow<UiState<Unit>>(UiState.Loading)
        val uiState: StateFlow<UiState<Unit>> = _uiState

        private val _uiAIState = MutableStateFlow<UiState<Unit>>(UiState.Loading)
        val uiAIState: StateFlow<UiState<Unit>> = _uiAIState

        init {
            getTodoList()
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
                        data.priorityTodoList.flatMap { todoList ->
                            todoList.toDoGetResponses.map { response ->
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
                        }
                    _todoItems.value = mappedData
                    _uiAIState.value = UiState.Success(Unit)
                }.onFailure { throwable ->
                    Log.e("TodoViewModel", "Error fetching priority todo", throwable)
                    _uiAIState.value = UiState.Failure
                }
            }
        }

        private fun getTodoList() {
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
    }
