package com.expense.manager.plan.smapleproject.core.utils.pref

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged

class AppSharedPref(context: Context) {

    private val prefs = context.getSharedPreferences(
        "APP_SHARED_PREF",
        Context.MODE_PRIVATE
    )

    val darkMode: Flow<Boolean> = booleanFlow(KEY_DARK_MODE, DEFAULT_DARK_MODE)
    val notifications: Flow<Boolean> = booleanFlow(KEY_NOTIFICATIONS, DEFAULT_NOTIFICATIONS)
    val dynamicColor: Flow<Boolean> = booleanFlow(KEY_DYNAMIC_COLOR, DEFAULT_DYNAMIC_COLOR)
    val languageName: Flow<String> = stringFlow(KEY_LANGUAGE_NAME, DEFAULT_LANGUAGE_NAME)

    fun isDarkModeEnabled(): Boolean =
        prefs.getBoolean(KEY_DARK_MODE, DEFAULT_DARK_MODE)

    fun isNotificationsEnabled(): Boolean =
        prefs.getBoolean(KEY_NOTIFICATIONS, DEFAULT_NOTIFICATIONS)

    fun isDynamicColorEnabled(): Boolean =
        prefs.getBoolean(KEY_DYNAMIC_COLOR, DEFAULT_DYNAMIC_COLOR)

    fun setDarkMode(enabled: Boolean) = putBoolean(KEY_DARK_MODE, enabled)

    fun setNotifications(enabled: Boolean) = putBoolean(KEY_NOTIFICATIONS, enabled)

    fun setDynamicColor(enabled: Boolean) = putBoolean(KEY_DYNAMIC_COLOR, enabled)

    private fun putBoolean(key: String, value: Boolean) {
        prefs.edit { putBoolean(key, value) }
    }

    /** False until the user finishes the first-run flow (language -> onboarding). */
    var isOnboardingCompleted: Boolean
        get() = prefs.getBoolean(KEY_ONBOARDING_COMPLETED, false)
        set(value) {
            prefs.edit { putBoolean(KEY_ONBOARDING_COMPLETED, value) }
        }

    var selectedLanguageKey: String
        get() = prefs.getString(KEY_LANGUAGE_KEY, DEFAULT_LANGUAGE_KEY) ?: DEFAULT_LANGUAGE_KEY
        set(value) {
            prefs.edit { putString(KEY_LANGUAGE_KEY, value) }
        }

    var selectedLanguageName: String
        get() = prefs.getString(KEY_LANGUAGE_NAME, DEFAULT_LANGUAGE_NAME) ?: DEFAULT_LANGUAGE_NAME
        set(value) {
            prefs.edit { putString(KEY_LANGUAGE_NAME, value) }
        }

    private fun booleanFlow(key: String, default: Boolean): Flow<Boolean> = callbackFlow {
        trySend(prefs.getBoolean(key, default))

        val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, changedKey ->
            // A null key means the prefs were cleared, so the value is back to its default.
            if (changedKey == key || changedKey == null) {
                trySend(prefs.getBoolean(key, default))
            }
        }

        prefs.registerOnSharedPreferenceChangeListener(listener)
        awaitClose { prefs.unregisterOnSharedPreferenceChangeListener(listener) }
    }.distinctUntilChanged()

    private fun stringFlow(key: String, default: String): Flow<String> = callbackFlow {
        trySend(prefs.getString(key, default) ?: default)

        val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, changedKey ->
            // A null key means the prefs were cleared, so the value is back to its default.
            if (changedKey == key || changedKey == null) {
                trySend(prefs.getString(key, default) ?: default)
            }
        }

        prefs.registerOnSharedPreferenceChangeListener(listener)
        awaitClose { prefs.unregisterOnSharedPreferenceChangeListener(listener) }
    }.distinctUntilChanged()

    companion object {
        private const val KEY_DARK_MODE = "dark_mode"
        private const val KEY_NOTIFICATIONS = "notifications"
        private const val KEY_DYNAMIC_COLOR = "dynamic_color"
        private const val KEY_LANGUAGE_KEY = "selectedLanguageKey"
        private const val KEY_LANGUAGE_NAME = "selectedLanguageName"
        private const val KEY_ONBOARDING_COMPLETED = "onboarding_completed"

        private const val DEFAULT_DARK_MODE = false
        private const val DEFAULT_NOTIFICATIONS = true
        private const val DEFAULT_DYNAMIC_COLOR = true
        private const val DEFAULT_LANGUAGE_KEY = "en"
        private const val DEFAULT_LANGUAGE_NAME = "English"
    }
}
