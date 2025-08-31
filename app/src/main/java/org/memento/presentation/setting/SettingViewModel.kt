package org.memento.presentation.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.memento.core.event.EventBus
import org.memento.core.util.UiState
import org.memento.data.datastore.TokenDataStore
import org.memento.domain.entity.CreateTag
import org.memento.domain.entity.EditTag
import org.memento.domain.entity.Tag
import org.memento.domain.entity.WakeUpTime
import org.memento.domain.repository.AddPlanRepository
import org.memento.domain.repository.MemberRepository
import org.memento.domain.repository.SettingRepository
import org.memento.presentation.type.EventType
import org.memento.presentation.util.to12HourFormat
import org.memento.presentation.util.to24HourFormat
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SettingViewModel
    @Inject
    constructor(
        private val settingRepository: SettingRepository,
        private val addPlanRepository: AddPlanRepository,
        private val memberRepository: MemberRepository,
        private val tokenDataStore: TokenDataStore,
        private val eventBus: EventBus,
    ) : ViewModel() {
        private val _tagList = MutableStateFlow<List<Tag>>(emptyList())
        val tagList: StateFlow<List<Tag>> = _tagList.asStateFlow()

        private val _addTagId = MutableStateFlow(0)
        val addTagId: StateFlow<Int> = _addTagId

        private val _tempTagId = MutableStateFlow(0)
        val tempTagId: StateFlow<Int> = _tempTagId

        private val _tempTagColor = MutableStateFlow("")
        val tempTagColor: StateFlow<String> = _tempTagColor

        private val _tempTagText = MutableStateFlow("")
        val tempTagText: StateFlow<String> = _tempTagText

        private val _editTagUiState = MutableStateFlow<UiState<Unit>>(UiState.Success(Unit))
        val editTagUiState: StateFlow<UiState<Unit>> = _editTagUiState

        private val _deleteState = MutableStateFlow<UiState<Unit>>(UiState.Loading)
        val deleteState: StateFlow<UiState<Unit>> = _deleteState

        private val _postTagUiState = MutableStateFlow<UiState<Unit>>(UiState.Success(Unit))
        val postTagUiState: StateFlow<UiState<Unit>> = _postTagUiState

        private val _userEmail = MutableStateFlow<String?>(null)
        val userEmail: StateFlow<String?> = _userEmail

        private val _selectedStartTimeText = MutableStateFlow<String>("00:00 AM")
        val selectedStartTimeText: StateFlow<String> = _selectedStartTimeText

        init {
            viewModelScope.launch {
                _userEmail.value = tokenDataStore.userEmail
            }
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

        fun patchEditTag(
            tagId: Int,
            name: String,
            colorHex: String,
        ) {
            viewModelScope.launch {
                _editTagUiState.value = UiState.Loading

                val editTag = EditTag(name = name, color = colorHex)
                val result = settingRepository.patchTag(tagId = tagId, editTag = editTag)

                _editTagUiState.value =
                    result.fold(
                        onSuccess = { UiState.Success(Unit) },
                        onFailure = {
                            Timber.e(it, "태그 수정 실패")
                            UiState.Failure
                        },
                    )
            }
        }

        fun deleteTag(
            tagId: Int,
        ) {
            viewModelScope.launch {
                _deleteState.value = UiState.Loading

                val result = settingRepository.deleteTag(tagId)
                _deleteState.value =
                    result.fold(
                        onSuccess = { UiState.Success(Unit) },
                        onFailure = { throwable ->
                            Timber.e(throwable, "태그 삭제 실패")
                            UiState.Failure
                        },
                    )
            }
        }

        fun postTag(
            name: String,
            colorHex: String,
        ) {
            viewModelScope.launch {
                _editTagUiState.value = UiState.Loading

                val createTag = CreateTag(name = name, hexCode = colorHex)
                val result = settingRepository.postTag(createTag = createTag)

                _editTagUiState.value =
                    result.fold(
                        onSuccess = { UiState.Success(Unit) },
                        onFailure = {
                            Timber.e(it, "태그 생성 실패")
                            UiState.Failure
                        },
                    )
            }
        }

        fun deleteMember() {
            viewModelScope.launch {
                val result = memberRepository.deleteMember()
                result.onSuccess {
                    eventBus.emit(EventType.AccountDeleted)
                }.onFailure { throwable ->
                    UiState.Failure
                }
            }
        }

        fun updateStartTime(newTime: String) {
            _selectedStartTimeText.value = newTime
        }

        fun fetchStartTime(newTime: String) {
            viewModelScope.launch {
                _selectedStartTimeText.value = newTime
                val wakeUpTime = WakeUpTime(wakeUpTime = newTime.to24HourFormat())
                val result = settingRepository.patchUptime(wakeUpTime = wakeUpTime)

                result.onSuccess {
                    UiState.Success(Unit)
                }.onFailure { throwable ->
                    UiState.Failure
                }
            }
        }

        fun getUpTime() {
            viewModelScope.launch {
                val response = settingRepository.getUptime()
                response.onSuccess { data ->
                    _selectedStartTimeText.value = data.wakeUpTime.to12HourFormat()
                    UiState.Success(Unit)
                }.onFailure { throwable ->
                    UiState.Failure
                }
            }
        }

        fun logout() {
            viewModelScope.launch {
                eventBus.emit(EventType.UserLogout)
            }
        }
    }
