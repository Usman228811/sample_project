package com.expense.manager.plan.smapleproject.presentation.screens.splash

import android.app.Activity
import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.expense.manager.plan.smapleproject.presentation.screens.splash.content.SplashScreenContent
import io.monetize.kit.sdk.core.utils.in_app_update.AdKitInAppUpdateFlowResultLauncher
import org.koin.androidx.compose.koinViewModel

@Composable
fun SplashScreen(
    splashViewModel: SplashScreenViewModel = koinViewModel(),
    moveToNext: () -> Unit,
) {

    val activity = LocalActivity.current as Activity
    val state by splashViewModel.state.collectAsState()
    val lifecycleOwner = LocalLifecycleOwner.current
    val launcher = AdKitInAppUpdateFlowResultLauncher(onFail = {
        splashViewModel.initConsent(activity)
    })

    LaunchedEffect(Unit) {
        splashViewModel.loadProducts(
            activity
        )
    }
    LaunchedEffect(Unit) {

        splashViewModel.checkForUpdate(activity, launcher)
        splashViewModel.observeLifecycle(lifecycleOwner)
    }

    LaunchedEffect(key1 = state.runSplash) {
        if (state.runSplash) {
            splashViewModel.initSplashAd(activity)
        }
    }

    LaunchedEffect(key1 = state.moveToMain) {
        if (state.moveToMain) {
            moveToNext()
        }
    }

    LaunchedEffect(state.isAppResumed) {
        if (state.isAppResumed) {
            splashViewModel.resumeSplashAd(activity)
        }
    }

    SplashScreenContent(state = state, showAd = {
        splashViewModel.showSplashOnClick(activity)
    })
}