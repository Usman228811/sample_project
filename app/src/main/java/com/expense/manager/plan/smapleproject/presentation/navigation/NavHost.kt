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
import com.expense.manager.plan.smapleproject.core.utils.pref.AppSharedPref
import com.expense.manager.plan.smapleproject.presentation.screens.language.LanguageScreen
import com.expense.manager.plan.smapleproject.presentation.screens.main.MainScreen
import com.expense.manager.plan.smapleproject.presentation.screens.onboarding.OnboardingScreen
import com.expense.manager.plan.smapleproject.presentation.screens.onboarding.OnboardingViewModel
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
    val prefHelper: AppSharedPref = koinInject()

    NavHost(
        navController = navHostController,
        startDestination = startRoute ?: AppRoute.SplashRoute.route
    ) {
        composable(AppRoute.SplashRoute.route) {
            SplashScreen(
                moveToNext = {
                    if (prefHelper.isOnboardingCompleted) {
                        navigationActions.goToMainScreen()
                    } else {
                        navigationActions.goToLanguageFromSplash()
                    }
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

            // During the first run there is nothing behind this screen to go back to, so Back moves
            // forward instead. Opened from settings, it behaves like any other pushed screen.
            val onBack: () -> Unit = if (fromSplash) {
                navigationActions.goToOnboarding
            } else {
                navigationActions.goBack
            }

            BackHandler(onBack = onBack)

            LanguageScreen(
                viewModel = koinViewModel(),
                onBackClick = onBack,
                onApplyClick = {
                    val next = if (fromSplash) {
                        AppRoute.OnboardingRoute.route
                    } else {
                        AppRoute.MainRoute.route
                    }
                    activity.restartAt(next)
                }
            )
        }

        composable(AppRoute.OnboardingRoute.route) {

            val viewModel: OnboardingViewModel = koinViewModel()

            val finishOnboarding: () -> Unit = {
                viewModel.completeOnboarding()
                navigationActions.goToMainScreen()
            }

            // Same rule as the language screen: Back advances into the app.
            BackHandler(onBack = finishOnboarding)

            OnboardingScreen(
                viewModel = viewModel,
                onFinish = finishOnboarding
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
