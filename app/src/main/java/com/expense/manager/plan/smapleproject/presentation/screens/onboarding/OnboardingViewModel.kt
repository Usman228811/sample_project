package com.expense.manager.plan.smapleproject.presentation.screens.onboarding

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material.icons.filled.Tune
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import com.expense.manager.plan.smapleproject.R

data class OnboardingPage(
    val icon: ImageVector,
    @StringRes val title: Int,
    @StringRes val description: Int
)

class OnboardingViewModel : ViewModel() {

    val pages = listOf(
        OnboardingPage(
            icon = Icons.Default.Bolt,
            title = R.string.onboarding_title_1,
            description = R.string.onboarding_description_1
        ),
        OnboardingPage(
            icon = Icons.Default.Tune,
            title = R.string.onboarding_title_2,
            description = R.string.onboarding_description_2
        ),
        OnboardingPage(
            icon = Icons.Default.RocketLaunch,
            title = R.string.onboarding_title_3,
            description = R.string.onboarding_description_3
        )
    )
}
