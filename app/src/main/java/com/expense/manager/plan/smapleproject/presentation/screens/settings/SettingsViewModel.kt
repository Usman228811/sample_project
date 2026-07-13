package com.expense.manager.plan.smapleproject.presentation.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.expense.manager.plan.smapleproject.domain.models.Settings
import com.expense.manager.plan.smapleproject.domain.usecase.SettingsUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val settingsUseCase: SettingsUseCase,
) : ViewModel() {

    val state: StateFlow<Settings> = settingsUseCase.settings.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = settingsUseCase.currentSettings()
    )

    fun onDarkModeChanged(enabled: Boolean) {
        viewModelScope.launch {
            settingsUseCase.setDarkMode(enabled)
        }
    }

    fun onNotificationsChanged(enabled: Boolean) {
        viewModelScope.launch {
            settingsUseCase.setNotifications(enabled)
        }
    }

    fun onDynamicColorChanged(enabled: Boolean) {
        viewModelScope.launch {
            settingsUseCase.setDynamicColor(enabled)
        }
    }
}
