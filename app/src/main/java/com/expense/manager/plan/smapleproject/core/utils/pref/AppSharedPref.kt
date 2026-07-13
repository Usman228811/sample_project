package com.expense.manager.plan.smapleproject.core.utils.pref

import android.content.Context
import androidx.core.content.edit

class AppSharedPref(context: Context) {

    private val prefs = context.getSharedPreferences(
        "APP_SHARED_PREF",
        Context.MODE_PRIVATE
    )

    companion object {
        private const val KEY_DARK_MODE = "dark_mode"
    }

    fun isDarkModeEnabled(): Boolean {
        return prefs.getBoolean(KEY_DARK_MODE, false)
    }

    fun setDarkMode(enabled: Boolean) {
        prefs.edit {
            putBoolean(KEY_DARK_MODE, enabled)
        }
    }
}