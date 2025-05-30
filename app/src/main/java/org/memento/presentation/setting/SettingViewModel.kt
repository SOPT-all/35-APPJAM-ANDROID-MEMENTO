package org.memento.presentation.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
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
