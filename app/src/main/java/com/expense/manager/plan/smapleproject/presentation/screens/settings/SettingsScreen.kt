package com.expense.manager.plan.smapleproject.presentation.screens.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = koinViewModel()
) {


    LazyColumn {

        item {
            SettingSwitch(
                title = "Dark Mode",
                checked = viewModel.isDarkModeEnabled(),
                onCheckedChange = viewModel::onDarkModeChanged
            )
        }

        item {
            SettingSwitch(
                title = "Notifications",
                checked = false,
                onCheckedChange = viewModel::onNotificationsChanged
            )
        }

        item {
            SettingSwitch(
                title = "Dynamic Color",
                checked = false,
                onCheckedChange = viewModel::onDynamicColorChanged
            )
        }
    }
}


@Composable
fun SettingSwitch(
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onCheckedChange(!checked)
            }
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(title)

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}