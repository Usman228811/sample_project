package com.expense.manager.plan.smapleproject.presentation.screens.main

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


data class MainScreenState(
    val selectedIndex: Int = 0
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

}