package com.expense.manager.plan.smapleproject.presentation.screens.main

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


data class MainScreenState(
    val selectedIndex: Int = 0,
    val showExitDialog: Boolean = false
)

class MainScreenViewModel : ViewModel() {

    val _state = MutableStateFlow(MainScreenState())
    val state = _state.asStateFlow()


    fun updateSelectedIndex(selectedIndex: Int) {
        _state.update {
            it.copy(
                selectedIndex = selectedIndex
            )
        }
    }

    /**
     * Back on any other tab returns to Home; back on Home itself asks before leaving the app, since
     * Home is the root of the stack and there is nowhere left to go.
     */
    fun onBackPressed() {
        if (state.value.selectedIndex != HOME_TAB_INDEX) {
            updateSelectedIndex(HOME_TAB_INDEX)
        } else {
            _state.update { it.copy(showExitDialog = true) }
        }
    }

    fun dismissExitDialog() {
        _state.update { it.copy(showExitDialog = false) }
    }

    companion object {
        private const val HOME_TAB_INDEX = 0
    }
}
