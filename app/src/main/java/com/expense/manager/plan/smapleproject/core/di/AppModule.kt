package com.expense.manager.plan.smapleproject.core.di

import com.expense.manager.plan.smapleproject.core.utils.AppFlowManager
import com.expense.manager.plan.smapleproject.core.utils.pref.AppSharedPref
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {

    singleOf(::AppSharedPref)
    singleOf(::AppFlowManager)

}