package com.expense.manager.plan.smapleproject

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.expense.manager.plan.smapleproject.AppClass.Companion.appContext
import com.expense.manager.plan.smapleproject.presentation.AppViewModel
import com.expense.manager.plan.smapleproject.presentation.navigation.AppNavHost
import com.expense.manager.plan.smapleproject.presentation.navigation.AppRoute
import com.expense.manager.plan.smapleproject.ui.theme.SmapleProjectTheme
import io.monetize.kit.sdk.core.utils.init.AdKit
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val languageChange = intent?.extras?.getBoolean("languageChange", false) ?: false
        if (languageChange.not()) {
            (appContext as AppClass).initializeAppClass()
            AdKit.openAdManager.setCurrentNavigationRoute(AppRoute.SplashRoute.route)
        }

        enableEdgeToEdge()
        setContent {

            val appViewModel: AppViewModel = koinViewModel()
            val settings by appViewModel.settings.collectAsStateWithLifecycle()
            val navHostController = rememberNavController()


            LaunchedEffect(key1 = navHostController) {
                navHostController.currentBackStackEntryFlow.collect { backStackEntry ->
                    val route = backStackEntry.destination.route
                    AdKit.openAdManager.setCurrentNavigationRoute(route)
                }
            }

            SmapleProjectTheme(
                darkTheme = settings.darkMode,
                dynamicColor = settings.dynamicColor
            ) {
                Scaffold { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        AppNavHost(navHostController, languageChange)
                    }
                }
            }
        }
    }
}
