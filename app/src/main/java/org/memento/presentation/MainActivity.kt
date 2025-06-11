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
import org.memento.data.datastore.TokenDataStore
import org.memento.presentation.main.MainScreen
import org.memento.presentation.navigator.rememberMainNavigator
import org.memento.presentation.navigator.route.MainNavigationBarRoute
import org.memento.presentation.onboarding.SplashScreen
import org.memento.presentation.onboarding.navigation.OnboardingRoute
import org.memento.ui.theme.MEMENTOTheme
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val viewModel: AppEntryViewModel = hiltViewModel()
            val isLoggedIn by viewModel.isLoggedIn.collectAsState()

            val isDarkMode by remember { mutableStateOf(true) }
            var showSplash by remember { mutableStateOf(true) }

            LaunchedEffect(Unit) {
                delay(SPLASH_SCREEN_DELAY)
                showSplash = false
            }
            MEMENTOTheme(darkTheme = isDarkMode) {
                if (showSplash || isLoggedIn == null) {
                    SplashScreen()
                } else {
                    val startDestination =
                        if (isLoggedIn == true) {
                            MainNavigationBarRoute.Today::class.simpleName!!
                        } else {
                            OnboardingRoute.ROUTE
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
        const val SPLASH_SCREEN_DELAY = 4000L
    }
}

@HiltViewModel
class AppEntryViewModel
    @Inject
    constructor(
        private val tokenDataStore: TokenDataStore,
    ) : ViewModel() {
        private val _isLoggedIn = MutableStateFlow<Boolean?>(null)
        val isLoggedIn: StateFlow<Boolean?> = _isLoggedIn

        init {
            viewModelScope.launch {
                _isLoggedIn.value = tokenDataStore.loginSuccess
                Timber.d("${_isLoggedIn.value}")
            }
        }
    }
