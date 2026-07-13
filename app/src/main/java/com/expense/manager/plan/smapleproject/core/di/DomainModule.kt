package com.expense.manager.plan.smapleproject.core.di

import com.expense.manager.plan.smapleproject.domain.usecase.SettingsUseCase
import org.koin.dsl.module

val domainModule = module {

    factory { SettingsUseCase(get()) }

}