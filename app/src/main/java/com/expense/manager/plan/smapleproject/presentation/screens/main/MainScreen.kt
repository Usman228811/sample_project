package com.expense.manager.plan.smapleproject.presentation.screens.main

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.expense.manager.plan.smapleproject.presentation.components.ExitDialog
import com.expense.manager.plan.smapleproject.presentation.screens.home.HomeScreen
import com.expense.manager.plan.smapleproject.presentation.screens.search.SearchScreen
import com.expense.manager.plan.smapleproject.presentation.screens.settings.SettingsScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainScreen(
    viewModel: MainScreenViewModel = koinViewModel(),
    goPremium: () -> Unit,
    goLanguage: () -> Unit

) {

    val state by viewModel.state.collectAsState()
    val activity = LocalActivity.current as Activity

    BackHandler(onBack = viewModel::onBackPressed)

    if (state.showExitDialog) {

        ExitDialog(
            // finishAffinity, not finish, or Back would drop the user on whatever Activity the ad
            // SDK left in the task instead of leaving the app.
            onConfirm = { activity.finishAffinity() },
            onDismiss = viewModel::dismissExitDialog
        )
    }

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
                    SearchScreen()
                }

                2 -> {
                    SettingsScreen(
                        goLanguage = goLanguage
                    )
                }
            }
        }

        BottomNavigationBar(
            selectedIndex = state.selectedIndex,
            onItemSelected = { viewModel.updateSelectedIndex(it) }
        )
    }
}
