package com.expense.manager.plan.smapleproject.core.utils

import com.expense.manager.plan.smapleproject.core.utils.pref.AppSharedPref
import io.monetize.kit.sdk.core.utils.firebaseLong

/**
 * Decides whether the language and onboarding screens appear after the splash. The order is always
 * splash -> language -> onboarding -> main; these rules only decide which of the middle two are
 * skipped, driven by the `langAppear` / `onBoardAppear` remote config values:
 *
 *  - 0: never show
 *  - 1: show in the first session only
 *  - 2: show every session
 *  - 3: skip the first session, then show once in the second
 */
class AppFlowManager(
    private val prefHelper: AppSharedPref
) {

    /** 1 during the first launch, 2 during the second, and so on. */
    val sessionNumber: Int
        get() = prefHelper.sessionNumber

    /** Call once per cold start, before the splash decides where to go next. */
    fun startNewSession() {
        prefHelper.sessionNumber = prefHelper.sessionNumber + 1
    }

    fun shouldShowLanguage(): Boolean =
        shouldShow(firebaseLong(KEY_LANG_APPEAR, DEFAULT_APPEAR))

    fun shouldShowOnboarding(): Boolean =
        shouldShow(firebaseLong(KEY_ONBOARD_APPEAR, DEFAULT_APPEAR))

    private fun shouldShow(rule: Long): Boolean = when (rule) {
        APPEAR_NEVER -> false
        APPEAR_FIRST_SESSION -> sessionNumber == 1
        APPEAR_EVERY_SESSION -> true
        APPEAR_SECOND_SESSION -> sessionNumber == 2
        // A value we don't recognise means the remote config is out of step with this build, so
        // fall back to the shipped default rather than silently hiding the screen forever.
        else -> sessionNumber == 1
    }

    companion object {
        private const val KEY_LANG_APPEAR = "langAppear"
        private const val KEY_ONBOARD_APPEAR = "onBoardAppear"

        private const val APPEAR_NEVER = 0L
        private const val APPEAR_FIRST_SESSION = 1L
        private const val APPEAR_EVERY_SESSION = 2L
        private const val APPEAR_SECOND_SESSION = 3L

        private const val DEFAULT_APPEAR = APPEAR_FIRST_SESSION
    }
}
