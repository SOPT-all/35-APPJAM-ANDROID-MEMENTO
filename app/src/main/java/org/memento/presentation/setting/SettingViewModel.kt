package org.memento.presentation.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.memento.data.datastore.TokenDataStore
import org.memento.domain.repository.MemberRepository
import org.memento.domain.repository.SettingRepository
import timber.log.Timber
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
                Timber.d("토큰 스토어 ${tokenDataStore.accessToken}")
                onLogout()
                Timber.d("온로그아웃")
            /*
            val result = memberRepository.deleteMember()
            Timber.d("$result delete member")
            result.onSuccess {
                Timber.d("go to the logout")
                tokenDataStore.clearInfo()
                onLogout()
            }.onFailure { throwable ->
                Timber.e(throwable, "Failed to delete Member")
                UiState.Failure
            }
             */
            }
        }
    }
