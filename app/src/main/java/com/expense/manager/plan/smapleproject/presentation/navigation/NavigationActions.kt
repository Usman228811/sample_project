package com.expense.manager.plan.smapleproject.presentation.navigation

import androidx.navigation.NavHostController

class NavigationActions(private val navHostController: NavHostController) {

    /**
     * Moves through the launch flow (splash -> language -> onboarding -> main), where each step is
     * done with for good. Clearing the stack is what lets Back advance instead of retreating, and
     * what makes Back on the home screen exit the app.
     */
    val navigateAsRoot: (String) -> Unit = { route ->
        navHostController.navigate(route) {
            popUpTo(0) {
                inclusive = true
            }
        }
    }

    val goToMainScreen: () -> Unit = {
        navigateAsRoot(AppRoute.MainRoute.route)
    }

    val goToPremium: () -> Unit = {
        navHostController.navigate(AppRoute.PremiumRoute.route)
    }

    /** Settings entry: an ordinary push the user can back out of. */
    val goToLanguage: () -> Unit = {
        navHostController.navigate(AppRoute.LanguageRoute.createRoute(fromSplash = false))
    }

    val goBack: () -> Unit = {
        navHostController.popBackStack()
    }
}
