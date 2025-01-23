package org.memento.presentation.today

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.memento.core.util.UiState
import org.memento.domain.entity.ScheduleDetail
import org.memento.domain.entity.ScheduleList
import org.memento.domain.entity.TodoDetail
import org.memento.domain.repository.AddPlanRepository
import org.memento.domain.repository.ScheduleRepository
import org.memento.presentation.type.DialogType
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TodayViewModel
    @Inject
    constructor(
        private val scheduleRepository: ScheduleRepository,
        private val addPlanRepository: AddPlanRepository,
    ) : ViewModel() {
        private val _scheduleListState =
            MutableStateFlow<UiState<List<ScheduleList.ScheduleWithOrderInfo>>>(UiState.Loading)
        val scheduleListState get() = _scheduleListState.asStateFlow()

        private val _detailScheduleState = MutableStateFlow<UiState<ScheduleDetail>>(UiState.Loading)
        val detailScheduleState: StateFlow<UiState<ScheduleDetail>> = _detailScheduleState

        private val _detailTodoState = MutableStateFlow<UiState<TodoDetail>>(UiState.Loading)
        val detailTodoState: StateFlow<UiState<TodoDetail>> = _detailTodoState

        private val _deleteState = MutableStateFlow<UiState<Unit>>(UiState.Loading)
        val deleteState: StateFlow<UiState<Unit>> = _deleteState

        fun getScheduleList(date: String) =
            viewModelScope.launch {
                scheduleRepository.getScheduleList(date)
                    .onSuccess { scheduleRepository ->
                        _scheduleListState.emit(UiState.Success(scheduleRepository))
                    }
                    .onFailure { exception ->
                        _scheduleListState.value = UiState.Failure
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
                }
            }
        }
    }
