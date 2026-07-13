package com.expense.manager.plan.smapleproject.presentation.screens.splash

import android.animation.ValueAnimator
import android.app.Activity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.expense.manager.plan.smapleproject.core.utils.LIFE_TIME_ID
import com.expense.manager.plan.smapleproject.presentation.screens.splash.state.SplashScreenState
import io.monetize.kit.sdk.BuildConfig
import io.monetize.kit.sdk.ads.interstitial.InterstitialControllerListener
import io.monetize.kit.sdk.core.utils.firebaseLong
import io.monetize.kit.sdk.core.utils.in_app_update.UpdateState
import io.monetize.kit.sdk.core.utils.init.AdKit
import io.monetize.kit.sdk.core.utils.init.AdKit.adKitPref
import io.monetize.kit.sdk.core.utils.init.AdKit.consentManager
import io.monetize.kit.sdk.core.utils.init.AdKit.firebaseHelper
import io.monetize.kit.sdk.core.utils.init.AdKit.inAppUpdateManager
import io.monetize.kit.sdk.core.utils.init.AdKit.internetController
import io.monetize.kit.sdk.core.utils.init.AdKit.splashAdController
import io.monetize.kit.sdk.core.utils.purchase.BillingItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.collections.copy

class SplashScreenViewModel : ViewModel() {
    private var _state = MutableStateFlow(SplashScreenState())
    val state = _state.asStateFlow()
    private var isInterAdShowed = false
    private var isInterAdCalled = false
    private var animator: ValueAnimator? = null

    init {
        AdKit.analytics.postAnalytics("Splash_launch")
        AdKit.splashAdController.resetSplash()
        collections()
        startProgressAnimation()

    }

    fun loadProducts(activity:Activity){

        AdKit.premiumHelper.initBilling(activity,
            items = listOf(
                BillingItem.Lifetime(LIFE_TIME_ID, BillingItem.Type.REMOVE_ADS),
            )
        )
    }

    private fun onResume() {
        if (state.value.runSplash) {
            animator?.resume()
        }
        _state.update { it.copy(isAppResumed = true) }
    }

    private fun onPause() {
        if (state.value.runSplash) {
            animator?.pause()
        }
        if (!isInterAdShowed && isInterAdCalled) {
            AdKit.splashAdController.pauseAd()
        }
        _state.update { it.copy(isAppResumed = false) }
    }

    fun observeLifecycle(lifecycleOwner: LifecycleOwner) {
        val lifecycleObserver = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> onResume()
                Lifecycle.Event.ON_PAUSE -> onPause()
                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(lifecycleObserver)
        lifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onDestroy(owner: LifecycleOwner) {
                lifecycleOwner.lifecycle.removeObserver(lifecycleObserver)
            }
        })
    }

    fun checkForUpdate(activity: Activity, launcher: ActivityResultLauncher<IntentSenderRequest>) {
        inAppUpdateManager.setUpdateStateCallback { updateState ->
            when (updateState) {
                UpdateState.Available -> inAppUpdateManager.startUpdateFlow(launcher)
                UpdateState.Downloaded -> inAppUpdateManager.updateComplete()
                UpdateState.Failed -> initConsent(activity)
                UpdateState.Idle -> {}
            }
        }
        inAppUpdateManager.checkUpdate(activity)
    }

    private fun collections() {
        viewModelScope.apply {
            launch {
                consentManager.googleConsent.collectLatest { initializeSplash() }
            }
            launch {
                firebaseHelper.apply {
                    configFetched.collectLatest {
                        try {
//                            assignRemoteValues(this)
                            runSplash()
                        } catch (e: Exception) {
                            runSplash()
                        }
                    }
                }
            }
            launch {
                AdKit.premiumHelper.premiumState.collectLatest { premiumState ->
                    _state.update { it.copy(isPurchased = premiumState.isPremium) }
                }
            }
        }
    }

    fun initConsent(activity: Activity) {
        if (state.value.isConsentManager.not()) {
            _state.update { it.copy(isConsentManager = true) }
            if (!adKitPref.isAppPurchased && internetController.isConnected) {
                consentManager.gatherConsent(activity)
                if (consentManager.canRequestAds) {
                    initializeSplash()
                }
            } else {
                initializeSplash()
            }
        }
    }

    private fun initializeSplash() {
        if (state.value.initializeSplash.not()) {
            _state.update { it.copy(initializeSplash = true) }
            fetchFirebase()
        }
    }

    private fun fetchFirebase() {
        if (state.value.fireBaseFetch.not()) {
            _state.update { it.copy(fireBaseFetch = true) }
            firebaseHelper.fetchRemoteValues(isDebug = BuildConfig.DEBUG)
        }
    }

    private fun runSplash() {
        if (state.value.runSplash.not()) {
            _state.update { it.copy(runSplash = true) }
        }
    }

    private fun startProgressAnimation() {
        animator?.cancel()
        animator = ValueAnimator.ofInt(0, 100).apply {
            duration = 25_000L
            addUpdateListener { animation ->
                val value = animation.animatedValue as? Int
                _state.update { it.copy(progress = value ?: 50) }
            }
            start()
        }
    }

    fun initSplashAd(mContext: Activity) {
        if (!isInterAdCalled) {
            isInterAdCalled = true
            _state.update {
                it.copy(
                    loadAndShow = false
                )
            }
            splashAdController.initSplashInterstitial(
                activity = mContext,
                placementKey = "splash_inter",
                adIdKey = "splash_inter",
                loadAndShow = state.value.loadAndShow,
                splashTime = firebaseLong("splash_time", 16),
                listener = object : InterstitialControllerListener {
                    override fun onAdShow() {
                        super.onAdShow()
                        isInterAdShowed = true
                        animator?.cancel()
                        _state.update { it.copy(progress = 100) }
                    }
                    override fun onAdClosed(isInterShowed: Boolean, reason: String) {
                        animator?.cancel()
                        _state.update {
                            it.copy(progress = 100, moveToMain = true)
                        }
                    }
                    override fun onAdLoaded(reason: String) {
                        super.onAdLoaded(reason)
                        _state.update {
                            it.copy(
                                onAdLoaded = true,
                            )
                        }
                    }
                }
            )
        }
    }

    fun resumeSplashAd(activity: Activity) {
        if (!isInterAdShowed && isInterAdCalled) {
            AdKit.splashAdController.resumeAd(activity)
        }
    }

    fun showSplashOnClick(activity: Activity){
        splashAdController.showInterstitial(
            activity = activity,
            object :InterstitialControllerListener{
                override fun onAdClosed(isInterShowed: Boolean, reason: String) {
                    _state.update {
                        it.copy(
                            moveToMain = true,
                        )
                    }
                }

            }
        )
    }

    override fun onCleared() {
        super.onCleared()
        inAppUpdateManager.unRegisterLister()
        animator?.cancel()
    }
}
