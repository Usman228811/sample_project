package com.expense.manager.plan.smapleproject.presentation.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.expense.manager.plan.smapleproject.presentation.screens.home.HomeScreen
import com.expense.manager.plan.smapleproject.presentation.screens.settings.SettingsScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainScreen(
    viewModel: MainScreenViewModel = koinViewModel(),
    goPremium:() ->Unit

) {


    val state by viewModel.state.collectAsState()
    Column(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {

            when (state.selectedIndex) {

                0 -> {
                    HomeScreen(
                        goPremium = goPremium
                    )
                }

                1 -> {

                }

                2 -> {
                    SettingsScreen()
                }
            }

        }



        BottomNavigationBar(
            selectedIndex = state.selectedIndex,
            onItemSelected = { viewModel.updateSelectedIndex(it) }
        )
    }

}