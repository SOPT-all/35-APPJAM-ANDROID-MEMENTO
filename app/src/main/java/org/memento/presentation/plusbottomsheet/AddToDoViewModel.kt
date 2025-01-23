package org.memento.presentation.plusbottomsheet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.memento.core.util.UiState
import org.memento.domain.entity.AddTodo
import org.memento.domain.repository.AddPlanRepository
import org.memento.presentation.type.PriorityTagType
import org.memento.presentation.util.createLocalDate
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

        fun patchAddTodo() {
            viewModelScope.launch {
            }
        }

        fun postAddTodo() {
            viewModelScope.launch {
                _uiState.value = UiState.Loading

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

                val addTodo =
                    AddTodo(
                        startDate = startDate,
                        description = _addToDoText.value,
                        endDate = endDate,
                        tagId = null,
                        priorityUrgency = priorityUrgency,
                        priorityImportance = priorityImportance,
                    )

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
