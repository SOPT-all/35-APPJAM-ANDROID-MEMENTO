package org.memento.presentation.navigator

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import org.memento.presentation.navigator.component.BottomNavigationType
import org.memento.presentation.navigator.route.MainNavigationBarRoute
import org.memento.presentation.onboarding.navigation.OnboardingRoute
import org.memento.presentation.onboarding.navigation.navigationOnboarding1
import org.memento.presentation.onboarding.navigation.navigationOnboarding2
import org.memento.presentation.onboarding.navigation.navigationOnboarding3
import org.memento.presentation.onboarding.navigation.navigationOnboarding4
import org.memento.presentation.plusbottomsheet.navigation.navigationBottomSheet
import org.memento.presentation.reqres.navigation.navigationReqres
import org.memento.presentation.today.navigation.navigationToday
import org.memento.presentation.todo.navigation.navigationTodo

class MainNavigator(
    val navHostController: NavHostController,
) {
    private val currentDestination: NavDestination?
        @Composable get() = navHostController.currentBackStackEntryAsState().value?.destination

    val startDestination = OnboardingRoute.ROUTE

    val currentMainNavigationBarItem: BottomNavigationType?
        @Composable get() =
            BottomNavigationType.find { mainNavigationBarRoute ->
                currentDestination?.route == mainNavigationBarRoute::class.simpleName
            }

    fun navigateMainNavigation(mainNavigationBarItemType: BottomNavigationType) {
        navOptions {
            popUpTo(MainNavigationBarRoute.Today::class.simpleName.orEmpty()) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }.let { navOptions ->
            when (mainNavigationBarItemType) {
                BottomNavigationType.TODAY -> navHostController.navigationToday(navOptions)
                BottomNavigationType.TODO -> navHostController.navigationTodo(navOptions)
            }
        }
    }

    fun navigateToReqres() {
        navHostController.navigationReqres()
    }


    fun navigateToOnboarding1() {
        navHostController.navigationOnboarding1()
    }

    fun navigateToOnboarding2() {
        navHostController.navigationOnboarding2()
    }

    fun navigateToOnboarding3() {
        navHostController.navigationOnboarding3()
    }

    fun navigateToOnboarding4() {
        navHostController.navigationOnboarding4()
    }

    fun popBackStack() {
        navHostController.popBackStack()
    }

    @Composable
    fun showBottomBar(): Boolean =
        BottomNavigationType.contains {
            currentDestination?.route == it::class.simpleName
        }
}

@Composable
fun rememberMainNavigator(
    navHostController: NavHostController = rememberNavController(),
): MainNavigator =
    remember(navHostController) {
        MainNavigator(navHostController = navHostController)
    }
