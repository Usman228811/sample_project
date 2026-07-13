package com.expense.manager.plan.smapleproject.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.expense.manager.plan.smapleproject.presentation.screens.main.MainScreen
import com.expense.manager.plan.smapleproject.presentation.screens.premium.SubscriptionScreen
import com.expense.manager.plan.smapleproject.presentation.screens.splash.SplashScreen
import io.monetize.kit.sdk.domain.model.PremiumOffer

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
                }
            )
        }
        composable(AppRoute.PremiumRoute.route) {
            SubscriptionScreen()
        }

    }
}
