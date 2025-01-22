package org.memento.presentation.todo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.memento.core.util.UiState
import org.memento.domain.entity.TodoList
import org.memento.domain.repository.TodoRepository
import javax.inject.Inject

@HiltViewModel
class TodoViewModel
@Inject
constructor(
    private val todoRepository: TodoRepository,
) : ViewModel() {
    private val _todoListState =
        MutableStateFlow<UiState<List<TodoList.ToDoGetResponse>>>(UiState.Loading)
    val todoListState get() = _todoListState.asStateFlow()

    fun getTodoList() =
        viewModelScope.launch {
            todoRepository.getTodoList()
                .onSuccess { scheduleRepository ->
                    _todoListState.emit(UiState.Success(scheduleRepository))
                }
                .onFailure { exception ->
                    _todoListState.value = UiState.Failure
                }
        }
}
