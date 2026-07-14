package com.expense.manager.plan.smapleproject.presentation.navigation

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.expense.manager.plan.smapleproject.MainActivity
import com.expense.manager.plan.smapleproject.core.utils.AppFlowManager
import com.expense.manager.plan.smapleproject.presentation.screens.language.LanguageScreen
import com.expense.manager.plan.smapleproject.presentation.screens.main.MainScreen
import com.expense.manager.plan.smapleproject.presentation.screens.onboarding.OnboardingScreen
import com.expense.manager.plan.smapleproject.presentation.screens.premium.SubscriptionScreen
import com.expense.manager.plan.smapleproject.presentation.screens.splash.SplashScreen
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun AppNavHost(
    navHostController: NavHostController,
    startRoute: String?
) {

    val navigationActions = NavigationActions(navHostController)
    val appFlowManager: AppFlowManager = koinInject()

    // The order is fixed — splash -> language -> onboarding -> main. Remote config only decides
    // which of the middle two are skipped, so each step asks for the next one still standing.
    val routeAfterLanguage: () -> String = {
        if (appFlowManager.shouldShowOnboarding()) {
            AppRoute.OnboardingRoute.route
        } else {
            AppRoute.MainRoute.route
        }
    }

    val routeAfterSplash: () -> String = {
        if (appFlowManager.shouldShowLanguage()) {
            AppRoute.LanguageRoute.createRoute(fromSplash = true)
        } else {
            routeAfterLanguage()
        }
    }

    NavHost(
        navController = navHostController,
        startDestination = startRoute ?: AppRoute.SplashRoute.route
    ) {
        composable(AppRoute.SplashRoute.route) {
            SplashScreen(
                moveToNext = {
                    navigationActions.navigateAsRoot(routeAfterSplash())
                })
        }

        composable(AppRoute.MainRoute.route) {
            MainScreen(
                goPremium = {
                    navigationActions.goToPremium()
                },
                goLanguage = {
                    navigationActions.goToLanguage()
                }
            )
        }

        composable(AppRoute.PremiumRoute.route) {
            SubscriptionScreen()
        }

        composable(
            route = AppRoute.LanguageRoute.route,
            arguments = listOf(
                navArgument(AppRoute.LanguageRoute.ARG_FROM_SPLASH) {
                    type = NavType.BoolType
                    defaultValue = false
                }
            )
        ) { backStackEntry ->

            val fromSplash = backStackEntry.arguments
                ?.getBoolean(AppRoute.LanguageRoute.ARG_FROM_SPLASH) == true

            val activity = LocalActivity.current as Activity

            // In the first-run flow there is nothing behind this screen, so Back moves on to the
            // next step instead of popping. Opened from settings it behaves like any pushed screen.
            val onBack: () -> Unit = if (fromSplash) {
                { navigationActions.navigateAsRoot(routeAfterLanguage()) }
            } else {
                navigationActions.goBack
            }

            BackHandler(onBack = onBack)

            LanguageScreen(
                viewModel = koinViewModel(),
                onBackClick = onBack,
                onApplyClick = {
                    val next = if (fromSplash) {
                        routeAfterLanguage()
                    } else {
                        AppRoute.MainRoute.route
                    }
                    activity.restartAt(next)
                }
            )
        }

        composable(AppRoute.OnboardingRoute.route) {

            // Same rule as the language screen: Back advances into the app.
            BackHandler(onBack = navigationActions.goToMainScreen)

            OnboardingScreen(
                viewModel = koinViewModel(),
                onFinish = navigationActions.goToMainScreen
            )
        }
    }
}

/**
 * A new locale only takes hold on a freshly created Activity, so applying one relaunches
 * [MainActivity] and tells it where to pick the flow back up.
 */
private fun Activity.restartAt(route: String) {
    val intent = Intent(this, MainActivity::class.java).apply {
        putExtra(MainActivity.EXTRA_START_ROUTE, route)
        flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
    }
    startActivity(intent)
    finish()
}
