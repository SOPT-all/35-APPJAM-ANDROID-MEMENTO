package org.memento.presentation.today

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.memento.core.util.UiState
import org.memento.domain.entity.ScheduleList
import org.memento.domain.repository.ScheduleRepository
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
    }
