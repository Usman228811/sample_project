package com.expense.manager.plan.smapleproject.presentation.screens.main


import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.expense.manager.plan.smapleproject.R

@Composable
fun BottomNavigationBar(
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit
) {
    val items = listOf(
        R.string.nav_home to Icons.Default.Home,
        R.string.nav_search to Icons.Default.Search,
        R.string.nav_settings to Icons.Default.Settings,
    )

    NavigationBar(
        windowInsets = WindowInsets.safeDrawing.only(WindowInsetsSides.Horizontal),
    ) {
        items.forEachIndexed { index, (labelRes, icon) ->
            val label = stringResource(labelRes)
            NavigationBarItem(
                selected = selectedIndex == index,
                onClick = { onItemSelected(index) },
                icon = {
                    Icon(
                        imageVector = icon,
                        contentDescription = label
                    )
                },
                label = {
                    Text(label)
                },
                alwaysShowLabel = true
            )
        }
    }
}