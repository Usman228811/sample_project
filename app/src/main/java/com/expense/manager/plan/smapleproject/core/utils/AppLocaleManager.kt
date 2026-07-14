package com.expense.manager.plan.smapleproject.core.utils

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.expense.manager.plan.smapleproject.core.utils.pref.AppSharedPref
import java.util.Locale

object AppLocaleManager {
    private const val DEFAULT_LANGUAGE_CODE = "en"
    private const val DEFAULT_LANGUAGE_NAME = "English"

    fun applyStoredLocale(context: Context) {
        val prefHelper = AppSharedPref(context)
        val languageCode = resolveLanguageCode(prefHelper)
        AppCompatDelegate.setApplicationLocales(
            LocaleListCompat.forLanguageTags(languageCode)
        )
    }

    fun updateLanguage(context: Context, languageCode: String) {
        val prefHelper = AppSharedPref(context)
        val resolvedLanguage = AppLanguageModel.entries.firstOrNull {
            it.languageAbbr == languageCode
        }
        val resolvedCode = resolvedLanguage?.languageAbbr ?: DEFAULT_LANGUAGE_CODE
        val resolvedName = resolvedLanguage?.languageName ?: DEFAULT_LANGUAGE_NAME

        prefHelper.selectedLanguageKey = resolvedCode
        prefHelper.selectedLanguageName = resolvedName

        AppCompatDelegate.setApplicationLocales(
            LocaleListCompat.forLanguageTags(resolvedCode)
        )
    }

    private fun resolveLanguageCode(prefHelper: AppSharedPref): String {
        val storedLanguageCode = prefHelper.selectedLanguageKey.trim()
        if (storedLanguageCode.isNotBlank()) {
            return storedLanguageCode
        }

        val deviceLanguageCode = Locale.getDefault().language.substringBefore("-")
        val selectedLanguage = AppLanguageModel.entries.firstOrNull { language ->
            language.languageAbbr == deviceLanguageCode
        }

        val fallbackLanguageCode = selectedLanguage?.languageAbbr ?: DEFAULT_LANGUAGE_CODE
        val fallbackLanguageName = selectedLanguage?.languageName ?: DEFAULT_LANGUAGE_NAME
        prefHelper.selectedLanguageKey = fallbackLanguageCode
        prefHelper.selectedLanguageName = fallbackLanguageName
        return fallbackLanguageCode
    }
}