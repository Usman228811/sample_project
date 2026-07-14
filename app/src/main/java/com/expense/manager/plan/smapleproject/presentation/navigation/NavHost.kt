package com.expense.manager.plan.smapleproject.presentation.navigation

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.expense.manager.plan.smapleproject.MainActivity
import com.expense.manager.plan.smapleproject.core.utils.AppLocaleManager
import com.expense.manager.plan.smapleproject.presentation.screens.language.LanguageScreen
import com.expense.manager.plan.smapleproject.presentation.screens.main.MainScreen
import com.expense.manager.plan.smapleproject.presentation.screens.premium.SubscriptionScreen
import com.expense.manager.plan.smapleproject.presentation.screens.splash.SplashScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppNavHost(
    navHostController: NavHostController,
    languageChange: Boolean
) {

    val navigationActions = NavigationActions(navHostController)
    val startDestination = if (languageChange) {
        AppRoute.MainRoute.route
    } else {
        AppRoute.SplashRoute.route
    }

    NavHost(
        navController = navHostController, startDestination = startDestination
    ) {
        composable(AppRoute.SplashRoute.route) {
            SplashScreen(
                moveToNext = {
                    navigationActions.goToMainScreen()
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

        composable(AppRoute.LanguageRoute.route) {
            val activity = LocalActivity.current as Activity

            LanguageScreen(
                viewModel = koinViewModel(),
                onBackClick = navigationActions.goBack,
                onApplyClick = {  ->
                    activity.restartForLanguageChange()
                }
            )
        }
    }
}

/**
 * Locale changes only take effect on a fresh Activity, so relaunch [MainActivity] with the
 * languageChange flag it already reads — that makes the NavHost skip the splash and its ad.
 */
private fun Activity.restartForLanguageChange() {
    val intent = Intent(this, MainActivity::class.java).apply {
        putExtra("languageChange", true)
        flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
    }
    startActivity(intent)
    finish()
}
