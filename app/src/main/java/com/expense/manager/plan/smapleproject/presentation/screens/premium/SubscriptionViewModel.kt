package com.expense.manager.plan.smapleproject.presentation.screens.premium

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.expense.manager.plan.smapleproject.R
import com.expense.manager.plan.smapleproject.core.utils.FEATURE_1
import com.expense.manager.plan.smapleproject.core.utils.FEATURE_2
import com.expense.manager.plan.smapleproject.core.utils.FEATURE_3
import com.expense.manager.plan.smapleproject.core.utils.LIFE_TIME_ID
import com.expense.manager.plan.smapleproject.core.utils.REMOVE_ADS_ID
import io.monetize.kit.sdk.core.utils.init.AdKit
import io.monetize.kit.sdk.core.utils.purchase.BillingItem
import io.monetize.kit.sdk.domain.model.OfferType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


data class SubscriptionScreenState(
    val removeAdsPrice: String = "",
    val feature1Price: String = "",
    val feature2Price: String = "",
    val feature3Price: String = "",
    val selectedButtonPos: Int = 0,
    @StringRes val buttonText: Int = R.string.subscription_subscribe,
    @StringRes val buttonTextLifeTime: Int = R.string.subscription_purchase_one_time,
    val oneTimePrice: String = "",
    val purchasesList: List<String> = emptyList()
)


class SubscriptionViewModel : ViewModel() {

    private var _state = MutableStateFlow(SubscriptionScreenState())
    val state = _state.asStateFlow()

    companion object {
        const val TAG = "SubscriptionViewModelTAG"
    }

    private val subscriptionMap = mapOf(
        0 to REMOVE_ADS_ID,
        1 to FEATURE_1,
        2 to FEATURE_2,
        3 to FEATURE_3,
    )

    private fun selectedId() = subscriptionMap[state.value.selectedButtonPos]

    init {
        viewModelScope.apply {
            launch {
                AdKit.premiumHelper.premiumState.collectLatest { premiumState ->
                    Log.d(TAG, "purchasesList: ${premiumState.allPurchases} ")
                    Log.d(TAG, "isPremium: ${premiumState.isPremium} ")

                    val removeAdsPrice = AdKit.premiumHelper.getBillingPrice(REMOVE_ADS_ID)
                    val feature1Price = AdKit.premiumHelper.getBillingPrice(FEATURE_1)
                    val feature2Price = AdKit.premiumHelper.getBillingPrice(FEATURE_2)
                    val feature3Price = AdKit.premiumHelper.getBillingPrice(FEATURE_3)


                    when (removeAdsPrice.type) {
                        OfferType.FREE_TRIAL -> {
                            Log.d(TAG, ": FREE_TRIAL")
                        }

                        OfferType.PAID_TRIAL -> {
                            Log.d(TAG, ": PAID_TRIAL")
                        }

                        OfferType.STRAIGHT -> {
                            Log.d(TAG, ": STRAIGHT")
                        }
                    }

                    Log.d(
                        TAG,
                        "mainOfferText=${feature1Price.mainOfferText} - period=${feature1Price.period} - freeTrialText=${feature1Price.freeTrialText} - paidTrialText=${feature1Price.paidTrialText}"
                    )
                    Log.d(
                        TAG,
                        "mainOfferText=${feature1Price.mainOfferText} - period=${feature1Price.period}- freeTrialText=${feature1Price.freeTrialText} - paidTrialText=${feature1Price.paidTrialText}"
                    )
                    Log.d(
                        TAG,
                        "mainOfferText=${feature2Price.mainOfferText} - period=${feature2Price.period}- freeTrialText=${feature2Price.freeTrialText} - paidTrialText=${feature2Price.paidTrialText}"
                    )
                    Log.d(
                        TAG,
                        "mainOfferText=${feature3Price.mainOfferText} - period=${feature3Price.period}- freeTrialText=${feature3Price.freeTrialText} - paidTrialText=${feature3Price.paidTrialText}"
                    )
                    _state.update {

                        it.copy(
                            removeAdsPrice = "${removeAdsPrice.mainOfferText}",
                            feature1Price = "${feature1Price.mainOfferText}",
                            feature2Price = "${feature2Price.mainOfferText}",
                            feature3Price = "${feature3Price.mainOfferText}",
                            oneTimePrice = AdKit.premiumHelper.getBillingPrice(LIFE_TIME_ID).mainOfferText?: "",
                            buttonTextLifeTime = if (premiumState.allPurchases.contains(LIFE_TIME_ID)) {
                                R.string.subscription_purchased
                            } else {
                                R.string.subscription_purchase_one_time
                            },
                            purchasesList = premiumState.allPurchases
                        )
                    }



                    changeButtonText()


                }
            }
        }
    }

    fun changeButtonText() {

        val selectedId = subscriptionMap[state.value.selectedButtonPos]
        val purchases = state.value.purchasesList

        val buttonText = when {
            purchases.isEmpty() -> R.string.subscription_subscribe

            selectedId != null && purchases.contains(selectedId) ->
                R.string.subscription_cancel

            purchases.isNotEmpty() &&
                    AdKit.premiumHelper.isSubscriptionUpdateSupported() ->
                R.string.subscription_update

            else -> state.value.buttonText
        }

        _state.update {
            it.copy(buttonText = buttonText)
        }
    }

    fun loadProducts(
        activity: Activity,
    ) {

        AdKit.premiumHelper.initBilling(activity,
            items = listOf(
                BillingItem.Lifetime(LIFE_TIME_ID, BillingItem.Type.REMOVE_ADS),
                BillingItem.Subscription(REMOVE_ADS_ID, BillingItem.Type.REMOVE_ADS),
                BillingItem.Subscription(FEATURE_1, BillingItem.Type.FEATURE),
                BillingItem.Subscription(FEATURE_2, BillingItem.Type.FEATURE),
                BillingItem.Subscription(FEATURE_3, BillingItem.Type.FEATURE),
            )
        )
    }


    fun updateSelectedButtonPos(selectedButtonPos: Int) {
        _state.update {
            it.copy(
                selectedButtonPos = selectedButtonPos
            )
        }
        changeButtonText()
    }

    fun purchase(activity: Activity) {
        AdKit.premiumHelper.purchase(activity, selectedId(), false, onUserDismissedPaywall = {})
    }

    fun purchaseProduct(activity: Activity) {
        AdKit.premiumHelper.purchase(
            activity = activity,
            productId = LIFE_TIME_ID,
            onUserDismissedPaywall = {

            })
    }
}
