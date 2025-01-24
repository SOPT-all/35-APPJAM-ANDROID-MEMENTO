package org.memento.presentation.plusbottomsheet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.memento.core.util.UiState
import org.memento.domain.entity.AddTodo
import org.memento.domain.entity.Tag
import org.memento.domain.entity.TodoDetail
import org.memento.domain.repository.AddPlanRepository
import org.memento.presentation.type.PriorityTagType
import org.memento.presentation.util.createLocalDate
import org.memento.presentation.util.formatDateString
import timber.log.Timber
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class AddToDoViewModel
    @Inject
    constructor(
        val addPlanRepository: AddPlanRepository,
    ) : ViewModel() {
        private val _selectedDateText = MutableStateFlow("Today")
        val selectedDateText: StateFlow<String> = _selectedDateText

        private val _addToDoText = MutableStateFlow("")
        val addToDoText: StateFlow<String> = _addToDoText

        private val _addTagColor = MutableStateFlow("#F0F0F3")
        val addTagColor: StateFlow<String> = _addTagColor

        private val _deadLineText = MutableStateFlow("Add DeadLine")
        val deadLineText: StateFlow<String> = _deadLineText

        private val _addPriorityType = MutableStateFlow(PriorityTagType.None)
        val addPriorityType: StateFlow<PriorityTagType> = _addPriorityType

        private val _tempDeadLineText = MutableStateFlow("Today")
        val tempDeadLineText: StateFlow<String> = _tempDeadLineText

        private val _tempTagColor = MutableStateFlow("#F0F0F3")
        val tempTagColor: StateFlow<String> = _tempTagColor

        private val _tempTagText = MutableStateFlow("Untitled")
        val tempTagText: StateFlow<String> = _tempTagText

        private val _tempPriorityType = MutableStateFlow(PriorityTagType.None)
        val tempPriorityType: StateFlow<PriorityTagType> = _tempPriorityType

        private val _uiState = MutableStateFlow<UiState<Unit>>(UiState.Loading)
        val uiState: StateFlow<UiState<Unit>> = _uiState

        private val _detailTodoState = MutableStateFlow<UiState<TodoDetail>>(UiState.Loading)
        val detailTodoState: StateFlow<UiState<TodoDetail>> = _detailTodoState
        private val _tagList = MutableStateFlow<List<Tag>>(emptyList())
        val tagList: StateFlow<List<Tag>> = _tagList.asStateFlow()

        init {
            getTagList()
        }

        fun getTagList() {
            viewModelScope.launch {
                addPlanRepository.getTagList()
                    .onSuccess { tags ->
                        _tagList.value = tags
                    }
                    .onFailure { throwable ->
                    }
            }
        }

        fun patchAddTodo() {
            viewModelScope.launch {
            }
        }

        private fun createAddTodo(): AddTodo {
            val startDate =
                if (_selectedDateText.value == "Today") {
                    LocalDate.now().toString()
                } else {
                    createLocalDate(_selectedDateText.value).toString()
                }

            val endDate =
                if (_deadLineText.value == "Add DeadLine") {
                    null
                } else {
                    createLocalDate(_deadLineText.value).toString()
                }

            val (priorityUrgency, priorityImportance) =
                when (_addPriorityType.value) {
                    PriorityTagType.None -> null to null
                    PriorityTagType.High -> 0.25 to 0.75
                    PriorityTagType.Immediate -> 0.75 to 0.75
                    PriorityTagType.Medium -> 0.75 to 0.25
                    PriorityTagType.Low -> 0.25 to 0.25
                }

            val formattedStartDate = formatDateString(startDate)
            val formattedEndDate = endDate?.let { formatDateString(it) }

            return AddTodo(
                startDate = formattedStartDate,
                description = _addToDoText.value,
                endDate = formattedEndDate,
                tagId = 19,
                priorityUrgency = priorityUrgency,
                priorityImportance = priorityImportance,
            )
        }

        fun getTodoDetail(todoId: Int) {
            viewModelScope.launch {
                _detailTodoState.value = UiState.Loading
                val result = addPlanRepository.getTodoDetail(todoId = todoId)
                _detailTodoState.value =
                    result.fold(
                        onSuccess = { todoDetail ->
                            _addToDoText.value = todoDetail.description
                            _selectedDateText.value = formatDateString(todoDetail.startDate)
                            _deadLineText.value = formatDateString(todoDetail.endDate)
                            _addTagColor.value = todoDetail.tagColor
                            _addPriorityType.value =
                                when (todoDetail.priorityType) {
                                    "IMMEDIATE" -> PriorityTagType.Immediate
                                    "HIGH" -> PriorityTagType.High
                                    "MEDIUM" -> PriorityTagType.Medium
                                    "LOW" -> PriorityTagType.Low
                                    else -> PriorityTagType.None
                                }
                            _tempTagColor.value = todoDetail.tagColor
                            _tempTagText.value = todoDetail.tagName
                            _tempPriorityType.value = _addPriorityType.value
                            UiState.Success(todoDetail)
                        },
                        onFailure = { throwable ->
                            Timber.e(throwable, "Failed to post plan")
                            UiState.Failure
                        },
                    )
            }
        }

        fun postAddTodo() {
            viewModelScope.launch {
                _uiState.value = UiState.Loading

                val addTodo = createAddTodo()
                val result = addPlanRepository.postAddTodo(addTodo)

                _uiState.value =
                    result.fold(
                        onSuccess = {
                            UiState.Success(Unit)
                        },
                        onFailure = { throwable ->
                            Timber.e(throwable, "Failed to post plan")
                            UiState.Failure
                        },
                    )
            }
        }

        fun patchAddTodo(todoId: Int) {
            viewModelScope.launch {
                val addTodo = createAddTodo()
                val result = addPlanRepository.patchAddTodo(todoId = todoId, addTodo = addTodo)

                _uiState.value =
                    result.fold(
                        onSuccess = {
                            UiState.Success(Unit)
                        },
                        onFailure = { throwable ->
                            Timber.e(throwable, "Failed to update todo")
                            UiState.Failure
                        },
                    )
            }
        }

        fun updateToDoText(newText: String) {
            _addToDoText.value = newText
        }

        fun updateSelectedDateText(newDate: String) {
            _selectedDateText.value = newDate
        }

        fun saveTagColor() {
            _addTagColor.value = _tempTagColor.value
        }

        fun savePriorityType() {
            _addPriorityType.value = _tempPriorityType.value
        }

        fun saveDeadLineText() {
            _deadLineText.value = _tempDeadLineText.value
        }

        fun updateTempDeadLineText(tempDeadLine: String) {
            _tempDeadLineText.value = tempDeadLine
        }

        fun updatePriorityType(type: PriorityTagType) {
            _tempPriorityType.value = type
        }

        fun updateTempTagData(
            tempTagColor: String,
            tempTagText: String,
        ) {
            _tempTagColor.value = tempTagColor
            _tempTagText.value = tempTagText
        }

        fun resetData() {
            _selectedDateText.value = "Today"
            _addToDoText.value = ""
            _addTagColor.value = "#F0F0F3"
            _deadLineText.value = "Add DeadLine"
            _addPriorityType.value = PriorityTagType.None

            _tempDeadLineText.value = "Today"
            _tempTagColor.value = "#F0F0F3"
            _tempTagText.value = "Untitled"
            _tempPriorityType.value = PriorityTagType.None

            _uiState.value = UiState.Loading
        }
    }
