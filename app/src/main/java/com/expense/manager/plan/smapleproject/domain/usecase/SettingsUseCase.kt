package com.expense.manager.plan.smapleproject.domain.usecase

import com.expense.manager.plan.smapleproject.domain.repo.SettingsRepository

class SettingsUseCase(
    private val repository: SettingsRepository
) {


    fun isDarkModeEnabled() = repository.isDarkModeEnabled()

    fun setDarkMode(enabled: Boolean) {
        repository.setDarkMode(enabled)
    }
}