package com.expense.manager.plan.smapleproject.presentation.navigation

import androidx.navigation.NavHostController

class NavigationActions(private val navHostController: NavHostController) {

    /**
     * Entering the app proper ends the first-run flow, so drop the whole back stack behind it —
     * Back from home should exit, not walk back into the splash or onboarding.
     */
    val goToMainScreen: () -> Unit = {
        navHostController.navigate(AppRoute.MainRoute.route) {
            popUpTo(0) {
                inclusive = true
            }
        }
    }

    val goToPremium: () -> Unit = {
        navHostController.navigate(AppRoute.PremiumRoute.route)
    }

    /** First-run entry: the splash is done with, so it must not be reachable again. */
    val goToLanguageFromSplash: () -> Unit = {
        navHostController.navigate(AppRoute.LanguageRoute.createRoute(fromSplash = true)) {
            popUpTo(AppRoute.SplashRoute.route) {
                inclusive = true
            }
        }
    }

    /** Settings entry: an ordinary push the user can back out of. */
    val goToLanguage: () -> Unit = {
        navHostController.navigate(AppRoute.LanguageRoute.createRoute(fromSplash = false))
    }

    val goToOnboarding: () -> Unit = {
        navHostController.navigate(AppRoute.OnboardingRoute.route) {
            popUpTo(AppRoute.LanguageRoute.route) {
                inclusive = true
            }
        }
    }

    val goBack: () -> Unit = {
        navHostController.popBackStack()
    }
}
