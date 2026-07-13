package com.expense.manager.plan.smapleproject

import android.R.attr.banner
import android.app.Application
import android.util.Log
import com.expense.manager.plan.smapleproject.core.di.appModule
import com.expense.manager.plan.smapleproject.core.di.dataModule
import com.expense.manager.plan.smapleproject.core.di.domainModule
import com.expense.manager.plan.smapleproject.core.di.presentationModule
import com.expense.manager.plan.smapleproject.presentation.navigation.AppRoute
import io.monetize.kit.sdk.BuildConfig
import io.monetize.kit.sdk.core.utils.adtype.BannerAdType
import io.monetize.kit.sdk.core.utils.adtype.NativeAdType
import io.monetize.kit.sdk.core.utils.init.AdKit
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import java.lang.Compiler.enable

class AppClass : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AppClass)
            modules(appModule, dataModule, domainModule, presentationModule)
        }

        AdKit.init(
            isDebug = BuildConfig.DEBUG,
            context = this,
            appFlyerSdkKey = "", // If App-Flyer-Dev-key is provided, AppFlyer will post the events; otherwise, it won’t.
            openAdId = "/21775744923/example/app-open",
            mapOfInterIds = mapOf(
                "splash_inter" to "/21775744923/example/interstitial",
                "home_inter" to "/21775744923/example/interstitial", // If single, uses this ID; otherwise, rotates for this placement.
                "inter_common" to listOf(
                    "/21775744923/example/interstitial",
                    "/21775744923/example/interstitial",
                    "/21775744923/example/interstitial"
                ) // If single, uses this ID; otherwise, rotates for this placement.
            ),
            mapOfNativeIds = mapOf(
                "home_native" to "/21775744923/example/native"
            ),
            mapOfBannerIds = mapOf(
                "home_banner" to "ca-app-pub-3940256099942544/9214589741",
                "premium_banner" to "ca-app-pub-3940256099942544/2014213617",
            ),
            mapOfRewardIds = emptyMap(),
            defaultRemoteConfigBuilder = {

                bool("OPEN_AD_ENABLE", true)
                bool("IS_OPEN_AD_INSTANT", false)
                bool("INTER_LOADING_ENABLE", true)
                bool("SPLASH_INTER_LOADING_ENABLE", true)
                bool("OPEN_AD_LOADING_ENABLE", true)
                long("OPEN_AD_INSTANT_TIME", 8)
                long("INTER_INSTANT_TIME", 8)
                long("splash_time", 16)


                native("exit_native"){
                    enable(true)
                    ctaColor("")
                    bgColor("")
                    adType(NativeAdType.SMALL_NATIVE)
                }
                native("home_native"){
                    enable(true)
                    ctaColor("#FFFFFF")
                    adType(NativeAdType.SMALL_NATIVE_MEDIA_VIEW)
                    refreshTime(7) // If provided, the native ad will refresh after 7 seconds
                }
                native("subscription_native"){
                    enable(true)
                    ctaColor("")
                    bgColor("")
                    adType(NativeAdType.SMALL_NATIVE)
                }

                fullScreen("splash_inter"){
                    enable(true)
                }
                fullScreen("home_inter"){
                    enable(true)
                    instantInter(true)
                }
                fullScreen("inter_btn_plant"){
                    enable(true)
                }
                fullScreen("inter_btn_plant"){
                    enable(true)
                    instantReward(true)
                }
                banner("home_banner"){
                    enable(true)
                    bannerType(BannerAdType.LARGE_ANCHORED_ADAPTIVE_BANNER)
                }
                banner("premium_banner"){
                    enable(true)
                    bannerType(BannerAdType.BOTTOM_COLLAPSIBLE_BANNER)
                }
                overAllNativeColor(ctaColor = "#964B00", bgColor = "#FF03DAC5")
            },
            onDefaultConfigGenerated = {  defaultConfigs ->
                Log.d("opoppp", "onDefaultConfigGenerated: $defaultConfigs")
            },
            onInitSdk = {
                AdKit.analytics.showToast(false)
                AdKit.initializer.disableAds(false)
            }
        )


    }
}
