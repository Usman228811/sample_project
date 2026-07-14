package com.expense.manager.plan.smapleproject.presentation.screens.onboarding

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import com.expense.manager.plan.smapleproject.R

data class OnboardingPage(
    @StringRes val title: Int,
    @StringRes val description: Int
)

class OnboardingViewModel : ViewModel() {

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
}
