package com.expense.manager.plan.smapleproject.presentation.screens.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.expense.manager.plan.smapleproject.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = koinViewModel(),
    goLanguage: () -> Unit
) {

    val settings by viewModel.state.collectAsStateWithLifecycle()

    LazyColumn {

        item {
            SettingRow(
                title = stringResource(R.string.settings_language),
                subtitle = settings.languageName,
                onClick = goLanguage
            )

            HorizontalDivider()
        }

        item {
            SettingSwitch(
                title = stringResource(R.string.settings_dark_mode),
                checked = settings.darkMode,
                onCheckedChange = viewModel::onDarkModeChanged
            )
        }

        item {
            SettingSwitch(
                title = stringResource(R.string.settings_notifications),
                checked = settings.notifications,
                onCheckedChange = viewModel::onNotificationsChanged
            )
        }

        item {
            SettingSwitch(
                title = stringResource(R.string.settings_dynamic_color),
                checked = settings.dynamicColor,
                onCheckedChange = viewModel::onDynamicColorChanged
            )
        }
    }
}


@Composable
fun SettingRow(
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(modifier = Modifier.weight(1f)) {

            Text(title)

            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null
        )
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