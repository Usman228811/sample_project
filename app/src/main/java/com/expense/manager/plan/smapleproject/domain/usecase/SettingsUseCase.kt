package com.expense.manager.plan.smapleproject.domain.usecase

import com.expense.manager.plan.smapleproject.domain.models.Settings
import com.expense.manager.plan.smapleproject.domain.repo.SettingsRepository
import kotlinx.coroutines.flow.Flow

class SettingsUseCase(
    private val repository: SettingsRepository
) {

    val settings: Flow<Settings> = repository.settings

    fun currentSettings(): Settings = repository.currentSettings()

    suspend fun setDarkMode(enabled: Boolean) = repository.setDarkMode(enabled)

    suspend fun setNotifications(enabled: Boolean) = repository.setNotifications(enabled)

    suspend fun setDynamicColor(enabled: Boolean) = repository.setDynamicColor(enabled)
}
