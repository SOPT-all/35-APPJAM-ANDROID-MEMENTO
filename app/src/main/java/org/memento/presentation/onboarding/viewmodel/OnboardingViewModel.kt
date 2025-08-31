package org.memento.presentation.onboarding.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.memento.core.util.UiState
import org.memento.data.datastore.TokenDataStore
import org.memento.domain.entity.UserInfo
import org.memento.domain.repository.UserInfoUpdateRepository
import org.memento.presentation.type.YesNoButtonType
import org.memento.presentation.util.to24HourFormat
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel
    @Inject
    constructor(
        private val userInfoUpdateRepository: UserInfoUpdateRepository,
        private val tokenDataStore: TokenDataStore,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow<UiState<Unit>>(UiState.Loading)
        val uiState: StateFlow<UiState<Unit>> = _uiState

        private val _wakeUpTime = MutableStateFlow<String?>(null)
        val wakeUpTime: StateFlow<String?> = _wakeUpTime

        private val _windDownTime = MutableStateFlow<String?>(null)
        val windDownTime: StateFlow<String?> = _windDownTime

        private val _job = MutableStateFlow<String?>(null)
        val job: StateFlow<String?> = _job

        private val _jobOtherDetail = MutableStateFlow<String?>(null)
        val jobOtherDetail: StateFlow<String?> = _jobOtherDetail

        private val _isStressedUnorganizedSchedule = MutableStateFlow<Boolean?>(null)
        val isStressedUnorganizedSchedule: StateFlow<Boolean?> = _isStressedUnorganizedSchedule

        private val _isForgetImportantThings = MutableStateFlow<Boolean?>(null)
        val isForgetImportantThings: StateFlow<Boolean?> = _isForgetImportantThings

        private val _isPreferReminder = MutableStateFlow<Boolean?>(null)
        val isPreferReminder: StateFlow<Boolean?> = _isPreferReminder

        private val _isImportantBreaks = MutableStateFlow<Boolean?>(null)
        val isImportantBreaks: StateFlow<Boolean?> = _isImportantBreaks

        fun completeOnboarding() {
            viewModelScope.launch {
                tokenDataStore.onboardingCompleted = true
            }
        }

        fun fetchUserInfoUpdate() {
            viewModelScope.launch {
                _uiState.value = UiState.Loading
                val result =
                    userInfoUpdateRepository.fetchUserInfo(
                        UserInfo(
                            wakeUpTime = _wakeUpTime.value?.to24HourFormat() ?: "08:00",
                            windDownTime = _windDownTime.value?.to24HourFormat() ?: "22:00",
                            job = _job.value ?: "TECHNOLOGY",
                            jobOtherDetail = _jobOtherDetail.value ?: "",
                            isStressedUnorganizedSchedule = _isStressedUnorganizedSchedule.value ?: true,
                            isForgetImportantThings = _isForgetImportantThings.value ?: true,
                            isPreferReminder = _isPreferReminder.value ?: false,
                            isImportantBreaks = _isImportantBreaks.value ?: true,
                        ),
                    )
                _uiState.value =
                    result.fold(
                        onSuccess = {
                            UiState.Success(Unit)
                        },
                        onFailure = { throwable ->
                            UiState.Failure
                        },
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
            _job.value = newjob
        }

        fun setJobOtherDetail(detail: String) {
            _jobOtherDetail.value = detail
        }

        fun updateQuestionAnswer(
            index: Int,
            answer: YesNoButtonType,
        ) {
            val value = answer == YesNoButtonType.YES
            when (index) {
                0 -> _isStressedUnorganizedSchedule.value = value
                1 -> _isForgetImportantThings.value = value
                2 -> _isPreferReminder.value = value
                3 -> _isImportantBreaks.value = value
            }
        }
    }
