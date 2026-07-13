package com.expense.manager.plan.smapleproject.core.di

import com.expense.manager.plan.smapleproject.data.repo.SettingsRepositoryImpl
import com.expense.manager.plan.smapleproject.domain.repo.SettingsRepository
import org.koin.dsl.module

val dataModule = module {

    factory<SettingsRepository> { SettingsRepositoryImpl(get()) }

}