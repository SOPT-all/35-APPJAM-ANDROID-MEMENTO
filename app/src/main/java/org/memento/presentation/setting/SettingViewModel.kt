package org.memento.presentation.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.memento.core.util.UiState
import org.memento.data.datastore.TokenDataStore
import org.memento.domain.repository.MemberRepository
import org.memento.domain.repository.SettingRepository
import javax.inject.Inject

@HiltViewModel
class SettingViewModel
    @Inject
    constructor(
        private val settingRepository: SettingRepository,
        private val memberRepository: MemberRepository,
        private val tokenDataStore: TokenDataStore,
    ) : ViewModel() {
        private val _userEmail = MutableStateFlow<String?>(null)
        val userEmail: StateFlow<String?> = _userEmail

        init {
            viewModelScope.launch {
                _userEmail.value = tokenDataStore.userEmail
            }
        }

        fun deleteMember(
            onLogout: () -> Unit,
        ) {
            viewModelScope.launch {
                tokenDataStore.clearInfo()
                onLogout()
                val result = memberRepository.deleteMember()
                result.onSuccess {
                    tokenDataStore.clearInfo()
                    onLogout()
                }.onFailure { throwable ->
                    UiState.Failure
                }
            }
        }
    }
