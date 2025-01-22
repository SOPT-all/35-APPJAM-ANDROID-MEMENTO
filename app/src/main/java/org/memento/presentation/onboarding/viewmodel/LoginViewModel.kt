package org.memento.presentation.onboarding.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.memento.core.util.UiState
import org.memento.data.local.TokenDataStore
import org.memento.domain.entity.Login
import org.memento.domain.entity.LoginInfo
import org.memento.domain.repository.AuthRepository
import org.memento.domain.repository.LoginRepository
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
    @Inject
    constructor(
        private val authRepository: AuthRepository,
        private val loginRepository: LoginRepository,
        private val tokenDataStore: TokenDataStore,
    ) : ViewModel() {
        private val _user = MutableStateFlow<FirebaseUser?>(null)
        val user = _user.asStateFlow()

        private val _uiState = MutableStateFlow<UiState<LoginInfo>>(UiState.Loading)
        val uiState: StateFlow<UiState<LoginInfo>> = _uiState

        fun saveToken(
            accessToken: String,
            refreshToken: String,
        ) {
            viewModelScope.launch {
                tokenDataStore.setAccessToken(accessToken)
                tokenDataStore.setRefreshToken(refreshToken)
            }
        }

        fun postLogin(
            idToken: String,
            provider: String = "GOOGLE",
        ) {
            viewModelScope.launch {
                _uiState.value = UiState.Loading
                val result = loginRepository.postLogin(Login(idToken = idToken, provider = provider))
                _uiState.value =
                    result.fold(
                        onSuccess = { data -> UiState.Success(data) },
                        onFailure = { data ->
                            Timber.tag("data").d(data.message.toString())
                            UiState.Failure
                        },
                    )
            }
        }

        fun signInWithGoogle(idToken: String) {
            viewModelScope.launch {
                val result = authRepository.signInWithGoogle(idToken)
                result.onSuccess { firebaseUser ->
                    _user.update { firebaseUser }
                }.onFailure {
                    Timber.d("Google Login Failed ${it.message}")
                }
            }
        }

        fun checkCurrentUser() {
            viewModelScope.launch {
                _user.update { authRepository.getCurrentUser() }
            }
        }
    }
