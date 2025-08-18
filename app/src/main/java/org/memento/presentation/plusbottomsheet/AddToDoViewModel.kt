package org.memento.presentation.plusbottomsheet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.memento.core.event.EventBus
import org.memento.core.util.UiState
import org.memento.domain.entity.AddTodo
import org.memento.domain.entity.Tag
import org.memento.domain.entity.TodoDetail
import org.memento.domain.repository.AddPlanRepository
import org.memento.presentation.type.EventType
import org.memento.presentation.type.PriorityTagType
import org.memento.presentation.util.createLocalDate
import org.memento.presentation.util.formatDate
import org.memento.presentation.util.formatDateString
import org.memento.presentation.util.formatDateTime
import org.memento.presentation.util.toMillis
import timber.log.Timber
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class AddToDoViewModel
    @Inject
    constructor(
        val addPlanRepository: AddPlanRepository,
        private val eventBus: EventBus,
    ) : ViewModel() {
        private val _selectedDateText = MutableStateFlow("Today")
        val selectedDateText: StateFlow<String> = _selectedDateText

        private val _addToDoText = MutableStateFlow("")
        val addToDoText: StateFlow<String> = _addToDoText

        private val _addTagId = MutableStateFlow(0)
        val addTagId: StateFlow<Int> = _addTagId

        private val _addTagColor = MutableStateFlow("#F0F0F3")
        val addTagColor: StateFlow<String> = _addTagColor

        private val _deadLineText = MutableStateFlow("Add DeadLine")
        val deadLineText: StateFlow<String> = _deadLineText

        private val _addPriorityType = MutableStateFlow(PriorityTagType.None)
        val addPriorityType: StateFlow<PriorityTagType> = _addPriorityType

        private val _tempDeadLineText = MutableStateFlow("Today")
        val tempDeadLineText: StateFlow<String> = _tempDeadLineText

        private val _tempTagId = MutableStateFlow(0)
        val tempTagId: StateFlow<Int> = _tempTagId

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

        private val _patchState = MutableStateFlow<UiState<Unit>>(UiState.Loading)
        val patchState: StateFlow<UiState<Unit>> = _patchState

        private val _tagList = MutableStateFlow<List<Tag>>(emptyList())
        val tagList: StateFlow<List<Tag>> = _tagList.asStateFlow()

        private val _isSwitchOn = MutableStateFlow<Boolean>(false)
        val isSwitchOn: StateFlow<Boolean> = _isSwitchOn

        private var parseJob: Job? = null

        init {
            getTagList()
        }

        fun setLoadingState() {
            _patchState.value = UiState.Loading
            _uiState.value = UiState.Loading
            _detailTodoState.value = UiState.Loading
        }

        fun getTagList() {
            viewModelScope.launch {
                addPlanRepository.getTagList()
                    .onSuccess { tags ->
                        _tagList.value = tags
                        if (_addTagId.value == 0) {
                            _addTagId.value = tags[0].id
                        }
                    }
                    .onFailure { throwable ->
                    }
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
                if (_deadLineText.value == "Add DeadLine" || _deadLineText.value == "Today") {
                    LocalDate.now().toString()
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

            val formattedStartDate = formatDateTime(startDate)
            val formattedEndDate = formatDateTime(endDate)

            return AddTodo(
                startDate = formattedStartDate,
                description = _addToDoText.value,
                endDate = formattedEndDate,
                tagId = _addTagId.value,
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
                            viewModelScope.launch {
                                eventBus.emit(EventType.TodoAdded)
                            }
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
                _uiState.value = UiState.Loading

                val addTodo = createAddTodo()
                val result = addPlanRepository.patchAddTodo(todoId = todoId, addTodo = addTodo)

                _uiState.value =
                    result.fold(
                        onSuccess = {
                            viewModelScope.launch {
                                eventBus.emit(EventType.TodoUpdated)
                            }
                            UiState.Success(Unit)
                        },
                        onFailure = { throwable ->
                            Timber.e(throwable, "Failed to update todo")
                            UiState.Failure
                        },
                    )
            }
        }

        fun updateSelectedDateText(newDate: String) {
            _selectedDateText.value = newDate
        }

        fun saveTagId() {
            _addTagId.value = _tempTagId.value
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
            tempTagId: Int,
            tempTagColor: String,
            tempTagText: String,
        ) {
            _tempTagId.value = tempTagId
            _tempTagColor.value = tempTagColor
            _tempTagText.value = tempTagText
        }

        fun updateToDoInputWithParsing(input: String) {
            _addToDoText.value = input

            if (!_isSwitchOn.value || input.replace(" ", "").length > 30) return

            parseJob?.cancel()
            parseJob =
                viewModelScope.launch {
                    delay(500L)

                    val parsed = parseNaturalLanguage(input, isParseTime = false)

                    _addToDoText.value = parsed.title

                    parsed.startDate?.let { start ->
                        val formatted = formatDate(start.toLocalDate().toMillis())
                        _selectedDateText.value = formatted
                    }

                    parsed.endDate?.let { end ->
                        val formatted = formatDate(end.toLocalDate().toMillis())
                        _deadLineText.value = formatted
                    }
                }
        }

        fun updateSwitchState(isOn: Boolean) {
            _isSwitchOn.value = isOn
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
