package com.expense.manager.plan.smapleproject.data.repo

import com.expense.manager.plan.smapleproject.core.utils.pref.AppSharedPref
import com.expense.manager.plan.smapleproject.domain.models.Settings
import com.expense.manager.plan.smapleproject.domain.repo.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class SettingsRepositoryImpl(
    private val preferences: AppSharedPref
) : SettingsRepository {

    override val settings: Flow<Settings> = combine(
        preferences.darkMode,
        preferences.notifications,
        preferences.dynamicColor,
        preferences.languageName
    ) { darkMode, notifications, dynamicColor, languageName ->
        Settings(
            darkMode = darkMode,
            notifications = notifications,
            dynamicColor = dynamicColor,
            languageName = languageName
        )
    }

    override fun currentSettings(): Settings = Settings(
        darkMode = preferences.isDarkModeEnabled(),
        notifications = preferences.isNotificationsEnabled(),
        dynamicColor = preferences.isDynamicColorEnabled(),
        languageName = preferences.selectedLanguageName
    )

    override suspend fun setDarkMode(enabled: Boolean) = preferences.setDarkMode(enabled)

    override suspend fun setNotifications(enabled: Boolean) = preferences.setNotifications(enabled)

    override suspend fun setDynamicColor(enabled: Boolean) = preferences.setDynamicColor(enabled)
}
