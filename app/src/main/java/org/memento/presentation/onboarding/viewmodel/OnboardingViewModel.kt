package org.memento.presentation.onboarding.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.memento.core.util.UiState
import org.memento.domain.entity.UserInfo
import org.memento.domain.repository.UserInfoUpdateRepository
import org.memento.presentation.type.YesNoButtonType
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val userInfoUpdateRepository: UserInfoUpdateRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<Unit>>(UiState.Loading)
    val uiState: StateFlow<UiState<Unit>> = _uiState

    // Todo : 기획 초기 wake, wind time 재설정 (discord)
    private val _wakeUpTime = MutableStateFlow("07:00")
    val wakeUpTime: StateFlow<String> = _wakeUpTime

    private val _windDownTime = MutableStateFlow("23:00")
    val windDownTime: StateFlow<String> = _windDownTime

    private val _job = MutableStateFlow("")
    val job: StateFlow<String> = _job

    private val _jobOtherDetail = MutableStateFlow("")
    val jobOtherDetail: StateFlow<String> = _jobOtherDetail

    private val _isStressedUnorganizedSchedule = MutableStateFlow(false)
    val isStressedUnorganizedSchedule: StateFlow<Boolean> = _isStressedUnorganizedSchedule

    private val _isForgetImportantThings = MutableStateFlow(false)
    val isForgetImportantThings: StateFlow<Boolean> = _isForgetImportantThings

    private val _isPreferReminder = MutableStateFlow(false)
    val isPreferReminder: StateFlow<Boolean> = _isPreferReminder

    private val _isImportantBreaks = MutableStateFlow(false)
    val isImportantBreaks: StateFlow<Boolean> = _isImportantBreaks

    fun fetchUserInfoUpdate() {
        viewModelScope.launch {
            Log.d("onboarding viewmodel", "enter to fetch user info")
            _uiState.value = UiState.Loading
            val result = userInfoUpdateRepository.fetchUserInfo(
                UserInfo(
                    wakeUpTime = _wakeUpTime.value,
                    windDownTime = _windDownTime.value,
                    job = _job.value,
                    jobOtherDetail = _jobOtherDetail.value,
                    isStressedUnorganizedSchedule = _isStressedUnorganizedSchedule.value,
                    isForgetImportantThings = _isForgetImportantThings.value,
                    isPreferReminder = _isPreferReminder.value,
                    isImportantBreaks = _isImportantBreaks.value,
                )
            )
            _uiState.value =
                result.fold(
                    onSuccess = {
                        Log.e("onboarding success", "success to fetch user info")
                        UiState.Success(Unit)
                    },
                    onFailure = { throwable ->
                        Log.e("onboarding", "Failed to fetch user info")
                        UiState.Failure
                    }
                )
        }
    }

    fun setWakeUpTime(time: String) {
        _wakeUpTime.value = time
    }

    fun setWindDownTime(time: String) {
        _windDownTime.value = time
    }

    fun setJob(newjob: String) {
        Log.e("viewmodel job 1", _job.value)
        _job.value = newjob
        Log.e("viewmodel job", _job.value)
    }

    fun setJobOtherDetail(detail: String) {
        Log.e("viewmodel job detail 1", _jobOtherDetail.value)
        _jobOtherDetail.value = detail
        Log.e("viewmodel job detail", _jobOtherDetail.value)
    }

    fun updateQuestionAnswer(index: Int, answer: YesNoButtonType) {
        val value = answer == YesNoButtonType.YES
        when (index) {
            0 -> _isStressedUnorganizedSchedule.value = value
            1 -> _isForgetImportantThings.value = value
            2 -> _isPreferReminder.value = value
            3 -> _isImportantBreaks.value = value
        }
    }

}