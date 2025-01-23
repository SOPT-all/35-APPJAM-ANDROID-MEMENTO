package org.memento.presentation.today

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.memento.R
import org.memento.core.util.UiState
import org.memento.domain.entity.ScheduleList
import org.memento.domain.repository.ScheduleRepository
import org.memento.presentation.type.DialogType
import org.memento.presentation.type.PriorityTagType
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TodayViewModel
    @Inject
    constructor(
        private val scheduleRepository: ScheduleRepository,
    ) : ViewModel() {
        private val _scheduleListState =
            MutableStateFlow<UiState<List<ScheduleList.ScheduleWithOrderInfo>>>(UiState.Loading)
        val scheduleListState get() = _scheduleListState.asStateFlow()

        private val _deleteState = MutableStateFlow<UiState<Unit>>(UiState.Loading)
        val deleteState get() = _deleteState.asStateFlow()

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

        fun getTodoDialogData(planId: Int): TodoData? {
            val schedule =
                (_scheduleListState.value as? UiState.Success)?.data
                    ?.find { it.id == planId }
            return schedule?.let {
                TodoData(
                    isChecked = false,
                    title = it.description,
                    tagColor = it.tagColorCode,
                    tagText = it.tagName,
                    priorityType = PriorityTagType.None,
                )
            }
        }

        fun getScheduleDialogData(planId: Int): ScheduleData? {
            val schedule =
                (_scheduleListState.value as? UiState.Success)?.data
                    ?.find { it.id == planId }
            return schedule?.let {
                ScheduleData(
                    title = it.description,
                    startDate = it.startDate,
                    endDate = it.endDate,
                    startTime = "8AM",
                    endTime = "6PM",
                    tagColor = it.tagColorCode,
                    tagText = it.tagName,
                    platform = R.drawable.ic_notion,
                    platformText = "Notion",
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

        data class TodoData(
            val isChecked: Boolean, // 체크 여부
            val title: String, // 할 일 제목
            val tagColor: String, // 태그 색상
            val tagText: String, // 태그 이름
            val priorityType: PriorityTagType, // 우선순위
        )

        data class ScheduleData(
            val title: String, // 일정 제목
            val startDate: String, // 시작 날짜
            val endDate: String, // 종료 날짜
            val startTime: String, // 시작 시간
            val endTime: String, // 종료 시간
            val tagColor: String, // 태그 색상
            val tagText: String, // 태그 이름
            val platform: Int, // 플랫폼
            val platformText: String, // 플랫폼 이름
        )
    }
