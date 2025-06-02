package org.memento.presentation.onboarding.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import org.memento.core.util.UiState
import org.memento.data.datastore.TokenDataStore
import org.memento.data.util.getTimeZoneOffsetString
import org.memento.domain.entity.Login
import org.memento.domain.entity.LoginInfo
import org.memento.domain.repository.AuthRepository
import org.memento.domain.repository.MemberRepository
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
    @Inject
    constructor(
        private val authRepository: AuthRepository,
        private val loginRepository: MemberRepository,
        private val tokenDataStore: TokenDataStore,
    ) : ViewModel() {
        private val _user = MutableStateFlow<FirebaseUser?>(null)
        val user = _user.asStateFlow()

        private val _uiState = MutableStateFlow<UiState<LoginInfo>>(UiState.Loading)
        val uiState: StateFlow<UiState<LoginInfo>> = _uiState

        private val _token = MutableStateFlow<String?>(null)
        val token: StateFlow<String?> = _token.asStateFlow()

        init {
            loadToken()
        }

        fun saveEmail(
            email: String,
        ) {
            viewModelScope.launch {
                tokenDataStore.userEmail = email
            }
        }

        fun loadToken() {
            viewModelScope.launch {
                _token.value = tokenDataStore.accessToken
            }
        }

        fun saveToken(
            accessToken: String,
            refreshToken: String,
            isNewUser: Boolean,
        ) {
            viewModelScope.launch {
                tokenDataStore.accessToken = accessToken
                tokenDataStore.refreshToken = refreshToken
                tokenDataStore.isNewUser = isNewUser
            }
        }

        fun postLogin(
            idToken: String,
            provider: String = "GOOGLE",
        ) {
            viewModelScope.launch {
                _uiState.value = UiState.Loading

                val fcmToken =
                    try {
                        FirebaseMessaging.getInstance().token.await()
                    } catch (e: Exception) {
                        Timber.d("FCM token 가져오기 실패 : ${e.message}")
                        ""
                    }

                val result =
                    loginRepository.postLogin(
                        Login(
                            idToken = idToken,
                            provider = provider,
                            timeZoneOffset = getTimeZoneOffsetString(),
                            fcmToken = fcmToken,
                        ),
                    )
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
    }
