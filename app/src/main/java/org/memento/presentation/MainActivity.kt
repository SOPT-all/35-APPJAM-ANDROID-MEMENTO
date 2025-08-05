package org.memento.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.memento.core.event.GlobalLogoutEvent
import org.memento.data.datastore.TokenDataStore
import org.memento.presentation.main.MainScreen
import org.memento.presentation.navigator.rememberMainNavigator
import org.memento.presentation.navigator.route.MainNavigationBarRoute
import org.memento.presentation.onboarding.SplashScreen
import org.memento.presentation.onboarding.navigation.OnboardingRoute
import org.memento.ui.theme.MEMENTOTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val viewModel: AppEntryViewModel = hiltViewModel()
            val entryState by viewModel.entryState.collectAsState()

            val isDarkMode = true
            var showSplash by remember { mutableStateOf(true) }

            LaunchedEffect(Unit) {
                delay(SPLASH_SCREEN_DELAY)
                showSplash = false
            }

            LaunchedEffect(Unit) {
                GlobalLogoutEvent.trigger.collect {
                    viewModel.setLoggedOut()
                }
            }

            MEMENTOTheme(darkTheme = isDarkMode) {
                if (showSplash || entryState is AppEntryState.Loading) {
                    SplashScreen()
                } else {
                    val startDestination =
                        when (entryState) {
                            is AppEntryState.LoggedIn -> MainNavigationBarRoute.Today::class.simpleName!!
                            is AppEntryState.LoggedOut -> OnboardingRoute.ROUTE
                            else -> OnboardingRoute.ROUTE
                        }
                    val navController = rememberNavController()
                    val navigator = rememberMainNavigator(navController)

                    MainScreen(
                        navigator = navigator,
                        startDestination = startDestination,
                    )
                }
            }
        }
    }

    companion object {
        const val SPLASH_SCREEN_DELAY = 3000L
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

        init {
            viewModelScope.launch {
                val isLoggedIn = tokenDataStore.loginSuccess
                _entryState.value =
                    if (isLoggedIn) {
                        AppEntryState.LoggedIn
                    } else {
                        AppEntryState.LoggedOut
                    }
            }
        }

        fun setLoggedOut() {
            _entryState.value = AppEntryState.LoggedOut
        }
    }

sealed class AppEntryState {
    object Loading : AppEntryState()

    object LoggedIn : AppEntryState()

    object LoggedOut : AppEntryState()
}
