package com.expense.manager.plan.smapleproject.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.expense.manager.plan.smapleproject.domain.models.Settings
import com.expense.manager.plan.smapleproject.domain.usecase.SettingsUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

/** Activity-scoped state that outlives navigation, so the theme survives destination changes. */
class AppViewModel(
    settingsUseCase: SettingsUseCase
) : ViewModel() {

    val settings: StateFlow<Settings> = settingsUseCase.settings.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = settingsUseCase.currentSettings()
    )
}
