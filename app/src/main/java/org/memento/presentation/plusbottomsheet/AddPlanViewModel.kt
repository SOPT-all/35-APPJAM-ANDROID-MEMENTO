package org.memento.presentation.plusbottomsheet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.memento.core.util.UiState
import org.memento.domain.entity.AddPlan
import org.memento.domain.repository.AddScheduleRepository
import org.memento.presentation.util.createLocalDateTime
import org.memento.presentation.util.formatDate
import org.memento.presentation.util.formatTime
import org.memento.presentation.util.parseDateTime
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class AddPlanViewModel @Inject constructor(
    val addScheduleRepository: AddScheduleRepository
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

    private val _selectedTagText = MutableStateFlow("Untitled")
    val selectedTagText: StateFlow<String> = _selectedTagText

    private val _selectedTagColor = MutableStateFlow("#F0F0F3")
    val selectedTagColor: StateFlow<String> = _selectedTagColor

    private val _isAllDayChecked = MutableStateFlow(false)
    val isAllDayChecked: StateFlow<Boolean> = _isAllDayChecked

    private val _isTimeValid = MutableStateFlow(true)
    val isTimeValid: StateFlow<Boolean> = _isTimeValid

    private val _uiState = MutableStateFlow<UiState<Unit>>(UiState.Loading)
    val uiState: StateFlow<UiState<Unit>> = _uiState

    fun postAddPlan() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val result = addScheduleRepository.postAddPlan(
                AddPlan(
                    description = _eventText.value,
                    startDate = createLocalDateTime(_selectedStartDateText.value, _selectedStartTimeText.value).toString(),
                    endDate = createLocalDateTime(_selectedEndDateText.value, _selectedEndTimeText.value).toString(),
                    isAllDay = _isAllDayChecked.value,
                )
            )
        }
    }

    private fun initialTimeValue() {
        val currentTime = System.currentTimeMillis()
        val startDate = formatDate(currentTime)

        val calendar = Calendar.getInstance().apply { timeInMillis = currentTime }
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val roundedMinute =
            when (minute) {
                in 0..15 -> 0
                in 16..45 -> 30
                else -> 0
            }

        val adjustedHour = if (minute in 46..59) (hour + 1) % 24 else hour

        val startTime = formatTime(adjustedHour, roundedMinute)

        calendar.add(Calendar.HOUR_OF_DAY, 2)
        val endHour = calendar.get(Calendar.HOUR_OF_DAY)
        val endTime = formatTime(endHour, roundedMinute)

        _selectedStartDateText.value = startDate
        _selectedEndDateText.value = startDate
        _selectedStartTimeText.value = startTime
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

    fun updateEventText(newText: String) {
        _eventText.value = newText
    }

    fun updateTag(
        tag: String,
        color: String,
    ) {
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

    init {
        viewModelScope.launch {
            initialTimeValue()
        }
    }
}
