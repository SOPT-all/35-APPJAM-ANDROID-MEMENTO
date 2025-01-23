package org.memento.presentation.todo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.memento.core.util.UiState
import org.memento.domain.repository.TodoRepository
import org.memento.presentation.today.MementoItem
import javax.inject.Inject

@HiltViewModel
class TodoViewModel
@Inject
constructor(
    private val todoRepository: TodoRepository,
) : ViewModel() {

    private val _todoItems = MutableStateFlow<List<MementoItem.TodoItem>>(emptyList())
    val todoItems: StateFlow<List<MementoItem.TodoItem>> = _todoItems

    private val _uiState = MutableStateFlow<UiState<Unit>>(UiState.Loading)
    val uiState: StateFlow<UiState<Unit>> = _uiState

    init {
        getTodoList()
    }

    private fun getTodoList() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val response = todoRepository.getTodoList()
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

    fun updateTodoCompletion(id: Int, isCompleted: Boolean) {
        viewModelScope.launch {
            _todoItems.value = _todoItems.value.map { todo ->
                if (todo.id == id) todo.copy(isCompleted = isCompleted) else todo
            }

            _uiState.value = UiState.Loading
            val result = todoRepository.patchTodoComplete(id.toInt())
            result.onSuccess {
                _uiState.value = UiState.Success(Unit)
            }.onFailure {
                _todoItems.value = _todoItems.value.map { todo ->
                    if (todo.id == id) todo.copy(isCompleted = !isCompleted) else todo
                }
                _uiState.value = UiState.Failure
            }
        }
    }
}
