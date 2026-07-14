package com.expense.manager.plan.smapleproject.presentation.screens.onboarding

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import com.expense.manager.plan.smapleproject.R
import com.expense.manager.plan.smapleproject.core.utils.pref.AppSharedPref

data class OnboardingPage(
    @StringRes val title: Int,
    @StringRes val description: Int
)

class OnboardingViewModel(
    private val prefHelper: AppSharedPref
) : ViewModel() {

    val pages = listOf(
        OnboardingPage(
            title = R.string.onboarding_title_1,
            description = R.string.onboarding_description_1
        ),
        OnboardingPage(
            title = R.string.onboarding_title_2,
            description = R.string.onboarding_description_2
        ),
        OnboardingPage(
            title = R.string.onboarding_title_3,
            description = R.string.onboarding_description_3
        )
    )

    /** Called when the user leaves onboarding, so the first-run flow is never shown again. */
    fun completeOnboarding() {
        prefHelper.isOnboardingCompleted = true
    }
}
