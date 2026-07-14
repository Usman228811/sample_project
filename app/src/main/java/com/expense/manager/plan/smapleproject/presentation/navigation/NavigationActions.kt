package com.expense.manager.plan.smapleproject.presentation.navigation

import androidx.navigation.NavHostController
import com.expense.manager.plan.smapleproject.presentation.navigation.AppRoute

class NavigationActions(private val navHostController: NavHostController) {


    val goToMainScreen: () -> Unit = {
        navHostController.navigate(AppRoute.MainRoute.route){
            popUpTo(AppRoute.SplashRoute.route){
                inclusive = true
            }
        }
    }
    val goToPremium: () -> Unit = {
        navHostController.navigate(AppRoute.PremiumRoute.route)
    }

    val goToLanguage: () -> Unit = {
        navHostController.navigate(AppRoute.LanguageRoute.route)
    }

    val goBack: () -> Unit = {
        navHostController.popBackStack()
    }


}