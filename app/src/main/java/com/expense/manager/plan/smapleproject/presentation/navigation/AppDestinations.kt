package com.expense.manager.plan.smapleproject.presentation.navigation


sealed class AppRoute(val route: String) {
    data object SplashRoute : AppRoute("splash")

    data object MainRoute : AppRoute("main")

    data object PremiumRoute : AppRoute("Premium")

    data object LanguageRoute : AppRoute("language")

}