package org.memento.presentation.onboarding.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.memento.domain.repository.AuthRepository
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
    @Inject
    constructor(
        private val authRepository: AuthRepository,
    ) : ViewModel() {
        private val _user = MutableStateFlow<FirebaseUser?>(null)
        val user = _user.asStateFlow()

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
