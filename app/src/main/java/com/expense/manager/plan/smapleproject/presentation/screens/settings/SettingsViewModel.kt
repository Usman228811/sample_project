package com.expense.manager.plan.smapleproject.presentation.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.expense.manager.plan.smapleproject.domain.usecase.SettingsUseCase
import kotlinx.coroutines.launch


class SettingsViewModel(
    val settingsUseCase: SettingsUseCase,
) : ViewModel() {


    fun isDarkModeEnabled() = settingsUseCase.isDarkModeEnabled()

    fun onDarkModeChanged(enabled: Boolean) {
        viewModelScope.launch {
            settingsUseCase.setDarkMode(enabled)
        }
    }

    fun onNotificationsChanged(enabled: Boolean) {

    }

    fun onDynamicColorChanged(enabled: Boolean) {

    }
}