package org.memento.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import org.memento.core.event.EventBus
import org.memento.data.datastore.TokenDataStore
import org.memento.presentation.main.MainScreen
import org.memento.presentation.navigator.component.OnboardingNavHost
import org.memento.presentation.navigator.rememberMainNavigator
import org.memento.presentation.navigator.route.MainNavigationBarRoute
import org.memento.presentation.onboarding.SplashScreen
import org.memento.presentation.type.EventType
import org.memento.ui.theme.MEMENTOTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var eventBus: EventBus

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val viewModel: AppEntryViewModel = hiltViewModel()
            val entryState by viewModel.entryState.collectAsState()
            val showSplash by viewModel.showSplash.collectAsState()

            val isDarkMode = true

            LaunchedEffect(Unit) {
                viewModel.startSplashTimer()
            }

            LaunchedEffect(Unit) {
                eventBus.events.collect { event ->
                    when (event) {
                        is EventType.UserLogout,
                        is EventType.AccountDeleted,
                        is EventType.TokenExpired,
                        -> {
                            viewModel.handleLogoutEvent(event)
                        }
                        else -> Unit
                    }
                }
            }

            MEMENTOTheme(darkTheme = isDarkMode) {
                if (showSplash) {
                    SplashScreen()
                } else {
                    when (entryState) {
                        is AppEntryState.LoggedIn -> {
                            MainScreen(
                                navigator = rememberMainNavigator(rememberNavController()),
                                startDestination = MainNavigationBarRoute.Today::class.simpleName!!,
                            )
                        }

                        is AppEntryState.LoggedOut, is AppEntryState.Onboarding -> {
                            OnboardingNavHost()
                        }
                        else -> Unit
                    }
                }
            }
        }
    }

    @HiltViewModel
    class AppEntryViewModel
        @Inject
        constructor(
            private val tokenDataStore: TokenDataStore,
        ) : ViewModel() {
            private val _entryState = MutableStateFlow<AppEntryState>(AppEntryState.Loading)
            val entryState: StateFlow<AppEntryState> = _entryState

            private val _showSplash = MutableStateFlow(true)
            val showSplash: StateFlow<Boolean> = _showSplash

            init {
                viewModelScope.launch {
                    tokenDataStore.loginSuccessFlow.combine(tokenDataStore.onboardingCompletedFlow) { isLoggedIn, isOnboardingCompleted ->
                        if (!isLoggedIn) {
                            AppEntryState.LoggedOut
                        } else {
                            if (isOnboardingCompleted) {
                                AppEntryState.LoggedIn
                            } else {
                                AppEntryState.Onboarding
                            }
                        }
                    }.collect { state ->
                        _entryState.value = state
                    }
                }
            }

            fun startSplashTimer() {
                viewModelScope.launch {
                    coroutineScope {
                        val job =
                            launch {
                                _entryState.first { it !is AppEntryState.Loading }
                            }

                        val delayJob =
                            launch {
                                delay(SPLASH_SCREEN_DELAY)
                            }

                        joinAll(job, delayJob)
                    }

                    _showSplash.value = false
                }
            }

            fun handleLogoutEvent(eventType: EventType) {
                viewModelScope.launch {
                    when (eventType) {
                        EventType.AccountDeleted -> tokenDataStore.clearAllInfo()
                        EventType.UserLogout, EventType.TokenExpired -> tokenDataStore.clearSession()
                        else -> Unit
                    }
                }
            }

            companion object {
                const val SPLASH_SCREEN_DELAY = 3000L
            }
        }
}

sealed class AppEntryState {
    object Loading : AppEntryState()

    object LoggedIn : AppEntryState()

    object Onboarding : AppEntryState()

    object LoggedOut : AppEntryState()
}
