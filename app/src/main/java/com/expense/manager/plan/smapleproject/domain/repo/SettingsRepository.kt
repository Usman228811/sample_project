package com.expense.manager.plan.smapleproject.domain.repo

import com.expense.manager.plan.smapleproject.domain.models.Settings
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    val settings: Flow<Settings>

    /** The value available synchronously, for seeding state before the first [settings] emission. */
    fun currentSettings(): Settings

    suspend fun setDarkMode(enabled: Boolean)

    suspend fun setNotifications(enabled: Boolean)

    suspend fun setDynamicColor(enabled: Boolean)
}
