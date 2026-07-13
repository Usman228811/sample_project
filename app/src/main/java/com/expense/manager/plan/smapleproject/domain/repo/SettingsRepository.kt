package com.expense.manager.plan.smapleproject.domain.repo

import com.expense.manager.plan.smapleproject.domain.models.Settings
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    fun isDarkModeEnabled(): Boolean

    fun setDarkMode(enabled: Boolean)
}