package com.expense.manager.plan.smapleproject.data.repo

import com.expense.manager.plan.smapleproject.core.utils.pref.AppSharedPref
import com.expense.manager.plan.smapleproject.domain.repo.SettingsRepository

class SettingsRepositoryImpl(
    private val preferences: AppSharedPref
) : SettingsRepository {

    override fun isDarkModeEnabled(): Boolean {
        return preferences.isDarkModeEnabled()
    }

    override fun setDarkMode(enabled: Boolean) {
        preferences.setDarkMode(enabled)
    }
}