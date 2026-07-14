package com.expense.manager.plan.smapleproject.presentation.navigation


sealed class AppRoute(val route: String) {
    data object SplashRoute : AppRoute("splash")

    data object MainRoute : AppRoute("main")

    data object PremiumRoute : AppRoute("Premium")

    data object OnboardingRoute : AppRoute("onboarding")

    /**
     * Reached both from the splash (first run) and from settings. The two entry points differ in
     * where Back and Apply lead, so the caller says which one it is.
     */
    data object LanguageRoute : AppRoute("language?fromSplash={fromSplash}") {
        const val ARG_FROM_SPLASH = "fromSplash"

        fun createRoute(fromSplash: Boolean) = "language?fromSplash=$fromSplash"
    }
}
