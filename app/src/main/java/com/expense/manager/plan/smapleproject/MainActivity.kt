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
import com.expense.manager.plan.smapleproject.core.utils.AppFlowManager
import com.expense.manager.plan.smapleproject.presentation.AppViewModel
import com.expense.manager.plan.smapleproject.presentation.navigation.AppNavHost
import com.expense.manager.plan.smapleproject.presentation.navigation.AppRoute
import com.expense.manager.plan.smapleproject.ui.theme.SmapleProjectTheme
import io.monetize.kit.sdk.core.utils.init.AdKit
import org.koin.android.ext.android.inject
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {

    private val appFlowManager: AppFlowManager by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set when the Activity is relaunched to apply a locale, so the flow resumes where it left
        // off instead of replaying the splash.
        val startRoute = intent?.extras?.getString(EXTRA_START_ROUTE)

        // Only a locale relaunch carries a start route; a cold launch is the one that has to boot
        // the SDK, begin at the splash, and count as a new session. A recreation (savedInstanceState
        // present) is neither, or rotating the phone would burn through the language/onboarding rules.
        if (startRoute == null && savedInstanceState == null) {
            (appContext as AppClass).initializeAppClass()
            AdKit.openAdManager.setCurrentNavigationRoute(AppRoute.SplashRoute.route)
            appFlowManager.startNewSession()
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
                        AppNavHost(navHostController, startRoute)
                    }
                }
            }
        }
    }

    companion object {
        const val EXTRA_START_ROUTE = "startRoute"
    }
}
