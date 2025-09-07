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
import org.memento.domain.entity.AddSchedule
import org.memento.domain.entity.ScheduleDetail
import org.memento.domain.entity.Tag
import org.memento.domain.repository.AddPlanRepository
import org.memento.presentation.type.EventType
import org.memento.presentation.util.createLocalDateTime
import org.memento.presentation.util.formatDate
import org.memento.presentation.util.formatEditString
import org.memento.presentation.util.formatEditTime
import org.memento.presentation.util.formatTextLocalDateTime
import org.memento.presentation.util.formatTime
import org.memento.presentation.util.parseDateTime
import org.memento.presentation.util.toMillis
import timber.log.Timber
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class AddScheduleViewModel
    @Inject
    constructor(
        val addPlanRepository: AddPlanRepository,
        private val eventBus: EventBus,
    ) : ViewModel() {
        private val _eventText = MutableStateFlow("")
        val eventText: StateFlow<String> = _eventText

        private val _selectedStartDateText = MutableStateFlow("")
        val selectedStartDateText: StateFlow<String> = _selectedStartDateText

        private val _selectedEndDateText = MutableStateFlow("")
        val selectedEndDateText: StateFlow<String> = _selectedEndDateText

        private val _selectedStartTimeText = MutableStateFlow("")
        val selectedStartTimeText: StateFlow<String> = _selectedStartTimeText

        private val _selectedEndTimeText = MutableStateFlow("")
        val selectedEndTimeText: StateFlow<String> = _selectedEndTimeText

        private val _selectedTagId = MutableStateFlow(0)
        val selectedTagId: StateFlow<Int> = _selectedTagId

        private val _selectedTagText = MutableStateFlow("Untitled")
        val selectedTagText: StateFlow<String> = _selectedTagText

        private val _selectedTagColor = MutableStateFlow("#F0F0F3")
        val selectedTagColor: StateFlow<String> = _selectedTagColor

        private val _isAllDayChecked = MutableStateFlow(false)
        val isAllDayChecked: StateFlow<Boolean> = _isAllDayChecked

        private val _isTimeValid = MutableStateFlow(true)
        val isTimeValid: StateFlow<Boolean> = _isTimeValid

        private val _tagList = MutableStateFlow<List<Tag>>(emptyList())
        val tagList: StateFlow<List<Tag>> = _tagList.asStateFlow()

        private val _uiState = MutableStateFlow<UiState<Unit>>(UiState.Loading)
        val uiState: StateFlow<UiState<Unit>> = _uiState

        private val _detailState = MutableStateFlow<UiState<ScheduleDetail>>(UiState.Loading)
        val detailState: StateFlow<UiState<ScheduleDetail>> = _detailState

        private val _isSwitchOn = MutableStateFlow<Boolean>(false)
        val isSwitchOn: StateFlow<Boolean> = _isSwitchOn

        private var parseJob: Job? = null

        init {
            getTagList()
        }

        fun setLoadingState() {
            _detailState.value = UiState.Loading
            _uiState.value = UiState.Loading
        }

        fun getTagList() {
            viewModelScope.launch {
                addPlanRepository.getTagList()
                    .onSuccess { tags ->
                        _tagList.value = tags
                        if (_selectedTagId.value == 0) {
                            _selectedTagId.value = tags[0].id
                        }
                    }
                    .onFailure { throwable ->
                    }
            }
        }

        fun patchAddSchedule(scheduleId: Int) {
            viewModelScope.launch {
                _uiState.value = UiState.Loading
                val addSchedule =
                    AddSchedule(
                        description = _eventText.value,
                        startDate = formatTextLocalDateTime(_selectedStartDateText.value, _selectedStartTimeText.value).toString(),
                        endDate = formatTextLocalDateTime(_selectedEndDateText.value, _selectedEndTimeText.value).toString(),
                        isAllDay = _isAllDayChecked.value,
                        tagId = _selectedTagId.value,
                    )

                val result =
                    addPlanRepository.patchAddSchedule(
                        scheduleId = scheduleId,
                        addSchedule = addSchedule,
                    )

                _uiState.value =
                    result.fold(
                        onSuccess = {
                            viewModelScope.launch {
                                eventBus.emit(EventType.ScheduleUpdated)
                            }
                            UiState.Success(Unit)
                        },
                        onFailure = { throwable ->
                            Timber.e(throwable, "Failed to update schedule")
                            UiState.Failure
                        },
                    )
            }
        }

        fun getScheduleDetail(scheduleId: Int) {
            viewModelScope.launch {
                _detailState.value = UiState.Loading
                val result = addPlanRepository.getScheduleDetail(scheduleId = scheduleId)
                _detailState.value =
                    result.fold(
                        onSuccess = { scheduleDetail ->
                            if (scheduleDetail.startDate.isNotBlank() && scheduleDetail.endDate.isNotBlank()) {
                                _selectedStartDateText.value = formatEditString(scheduleDetail.startDate)
                                _selectedEndDateText.value = formatEditString(scheduleDetail.endDate)
                                _selectedStartTimeText.value = formatEditTime(scheduleDetail.startDate)
                                _selectedEndTimeText.value = formatEditTime(scheduleDetail.endDate)
                            } else {
                                initialTimeValue()
                            }

                            _eventText.value = scheduleDetail.description
                            UiState.Success(scheduleDetail)
                        },
                        onFailure = { throwable ->
                            Timber.e(throwable, "Failed to get schedule detail")
                            UiState.Failure
                        },
                    )
            }
        }

        fun postAddSchedule() {
            viewModelScope.launch {
                _uiState.value = UiState.Loading
                val result =
                    addPlanRepository.postAddSchedule(
                        AddSchedule(
                            description = _eventText.value,
                            startDate = createLocalDateTime(_selectedStartDateText.value, _selectedStartTimeText.value).toString(),
                            endDate = createLocalDateTime(_selectedEndDateText.value, _selectedEndTimeText.value).toString(),
                            isAllDay = _isAllDayChecked.value,
                            tagId = _selectedTagId.value,
                        ),
                    )
                _uiState.value =
                    result.fold(
                        onSuccess = {
                            viewModelScope.launch {
                                eventBus.emit(EventType.ScheduleAdded)
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

        fun initialTimeValue() {
            // calendar 에도 적용시켜 시간 증가 적용
            val calendar = Calendar.getInstance()

            val minute = calendar.get(Calendar.MINUTE)
            val roundedMinute =
                when (minute) {
                    in 0..15 -> 0
                    in 16..45 -> 30
                    else -> {
                        calendar.add(Calendar.HOUR_OF_DAY, 1)
                        0
                    }
                }

            val adjustedHour = calendar.get(Calendar.HOUR_OF_DAY)
            val adjustedTime = formatTime(adjustedHour, roundedMinute)
            val adjustedDate = formatDate(calendar.timeInMillis)

            // start
            _selectedStartDateText.value = adjustedDate
            _selectedStartTimeText.value = adjustedTime

            // end
            calendar.add(Calendar.HOUR_OF_DAY, 2)
            val endDate = formatDate(calendar.timeInMillis)
            val endTime = formatTime(calendar.get(Calendar.HOUR_OF_DAY), roundedMinute)

            _selectedEndDateText.value = endDate
            _selectedEndTimeText.value = endTime
        }

        private fun calculateEndTime(
            startDate: String,
            startTime: String,
            hoursToAdd: Int = 2,
        ): Pair<String, String> {
            val startDateTime = parseDateTime(startDate, startTime)
            val calendar = Calendar.getInstance().apply { time = startDateTime }
            calendar.add(Calendar.HOUR_OF_DAY, hoursToAdd)

            val endDate = formatDate(calendar.timeInMillis)
            val endHour = calendar.get(Calendar.HOUR_OF_DAY)
            val endMinute = calendar.get(Calendar.MINUTE)
            val endTime = formatTime(endHour, endMinute)

            return endDate to endTime
        }

        fun updateAllDayCheck() {
            val startDateTime = parseDateTime(_selectedStartDateText.value, _selectedStartTimeText.value)
            val endDateTime = parseDateTime(_selectedEndDateText.value, _selectedEndTimeText.value)

            val difference = endDateTime.time - startDateTime.time

            _isAllDayChecked.value = difference >= 86_400_000
        }

        fun updateEventTextWithParsing(input: String) {
            _eventText.value = input

            if (!_isSwitchOn.value || input.replace(" ", "").length > 30) return

            parseJob?.cancel()
            parseJob =
                viewModelScope.launch {
                    delay(500L)

                    val parsed = parseNaturalLanguage(input, isParseTime = true)
                    _eventText.value = parsed.title

                    parsed.startDate?.let { start ->
                        _selectedStartDateText.value = formatDate(start.toLocalDate().toMillis())
                        _selectedStartTimeText.value = formatTime(start.hour, start.minute)
                    }

                    parsed.endDate?.let { end ->
                        _selectedEndDateText.value = formatDate(end.toLocalDate().toMillis())
                        _selectedEndTimeText.value = formatTime(end.hour, end.minute)
                    }

                    validateTimeOrder()
                }
        }

        fun updateTag(
            id: Int,
            tag: String,
            color: String,
        ) {
            _selectedTagId.value = id
            _selectedTagText.value = tag
            _selectedTagColor.value = color
        }

        fun updateStartDate(newDate: String) {
            _selectedStartDateText.value = newDate
        }

        fun updateEndDate(newDate: String) {
            _selectedEndDateText.value = newDate
        }

        fun updateStartTime(newTime: String) {
            _selectedStartTimeText.value = newTime
        }

        fun updateEndTime(newTime: String) {
            _selectedEndTimeText.value = newTime
        }

        fun toggleAllDay(isChecked: Boolean) {
            _isAllDayChecked.value = isChecked
            if (!isChecked) {
                val (endDate, endTime) = calculateEndTime(_selectedStartDateText.value, _selectedStartTimeText.value)
                _selectedEndDateText.value = endDate
                _selectedEndTimeText.value = endTime
            }
        }

        fun validateTimeOrder() {
            try {
                val startDateTime = parseDateTime(_selectedStartDateText.value, _selectedStartTimeText.value)
                val endDateTime = parseDateTime(_selectedEndDateText.value, _selectedEndTimeText.value)

                if (startDateTime.after(endDateTime)) {
                    val (correctedEndDate, correctedEndTime) = calculateEndTime(_selectedStartDateText.value, _selectedStartTimeText.value)
                    _selectedEndDateText.value = correctedEndDate
                    _selectedEndTimeText.value = correctedEndTime
                    _isTimeValid.value = false
                } else {
                    _isTimeValid.value = true
                }
            } catch (e: Exception) {
                _isTimeValid.value = false
            }
        }

        fun updateSwitchState(isOn: Boolean) {
            _isSwitchOn.value = isOn
        }

        fun resetData() {
            initialTimeValue()
            _eventText.value = ""
            _selectedTagId.value = 0
            _selectedTagText.value = "Untitled"
            _selectedTagColor.value = "#F0F0F3"
            _isAllDayChecked.value = false
        }

        init {
            viewModelScope.launch {
                initialTimeValue()
            }
        }
    }
