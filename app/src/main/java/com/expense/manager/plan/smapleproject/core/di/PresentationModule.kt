package com.expense.manager.plan.smapleproject.core.di

import com.expense.manager.plan.smapleproject.presentation.AppViewModel
import com.expense.manager.plan.smapleproject.presentation.screens.language.LanguageViewModel
import com.expense.manager.plan.smapleproject.presentation.screens.main.MainScreenViewModel
import com.expense.manager.plan.smapleproject.presentation.screens.premium.SubscriptionViewModel
import com.expense.manager.plan.smapleproject.presentation.screens.settings.SettingsViewModel
import com.expense.manager.plan.smapleproject.presentation.screens.splash.SplashScreenViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val presentationModule = module {


    viewModelOf(::AppViewModel)
    viewModelOf(::SplashScreenViewModel)
    viewModelOf(::MainScreenViewModel)
    viewModelOf(::SettingsViewModel)
    viewModelOf(::SubscriptionViewModel)
    viewModelOf(::LanguageViewModel)

}